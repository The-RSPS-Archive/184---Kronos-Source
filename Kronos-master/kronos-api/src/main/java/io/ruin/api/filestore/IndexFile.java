package io.ruin.api.filestore;

import io.ruin.api.buffer.InBuffer;
import io.ruin.api.filestore.utility.NameHash;
import io.ruin.api.utils.ServerWrapper;

import java.io.IOException;
import java.io.RandomAccessFile;

public class IndexFile {

    private static final byte[] readCachedBuffer = new byte[520];
    private final RandomAccessFile mainDataFile;
    private final RandomAccessFile indexDataFile;
    private final int maxLength;
    public ReferenceTable table;
    private int index;
    private byte[][][] cachedFiles;

    protected IndexFile(int index, RandomAccessFile mainDataFile, RandomAccessFile indexDataFile, int maxLength) {
        this.index = index;
        this.mainDataFile = mainDataFile;
        this.indexDataFile = indexDataFile;
        this.maxLength = maxLength;
    }

    public void setReferenceTable(ReferenceTable table) {
        this.table = table;
        this.cachedFiles = new byte[table.archives.length][][];
    }

    public boolean archiveExists(int archiveId) {
        if(archiveId < 0)
            return false;
        Archive[] archives = table.archives;
        return archives.length > archiveId && archives[archiveId] != null;
    }

    public Archive getArchive(int archiveId) {
        if(archiveId < 0)
            return null;
        Archive[] archives = table.archives;
        if(archives.length > archiveId)
            return archives[archiveId];
        return null;
    }

    public int getArchiveId(String name) {
        int nameHash = NameHash.get(name);
        for(int archiveId : table.archiveIds) {
            Archive archive = table.archives[archiveId];
            if(archive != null && archive.nameHash == nameHash)
                return archiveId;
        }
        return -1;
    }

    public int getFileId(String name) {
        int nameHash = NameHash.get(name);
        for(Archive archive : table.archives) {
            if(archive == null)
                continue;
            for(int fileId : archive.fileIds) {
                Archive.File file = archive.files[fileId];
                if(file != null && file.nameHash == nameHash)
                    return fileId;
            }
        }
        return -1;
    }

    public int getLastArchiveId() {
        return table.archives.length - 1;
    }

    public boolean fileExists(int archiveId, int fileId) {
        if(!archiveExists(archiveId))
            return false;
        Archive.File[] files = table.archives[archiveId].files;
        return files.length > fileId && files[fileId] != null;
    }

    public int getLastFileId(int archiveId) {
        if(!archiveExists(archiveId))
            return -1;
        return table.archives[archiveId].files.length - 1;
    }

    public int getValidFilesCount(int archiveId) {
        if(!archiveExists(archiveId))
            return -1;
        return table.archives[archiveId].fileCount;
    }

    public byte[] getFile(int archiveId) {
        return getFile(archiveId, table.archives[archiveId].fileIds[0], null);
    }

    public byte[] getFile(int archiveId, int fileId) {
        return getFile(archiveId, fileId, null);
    }

    public byte[] getFile(int archiveId, int fileId, int[] keys) {
        if(!fileExists(archiveId, fileId))
            return null;
        if(cachedFiles[archiveId] == null || cachedFiles[archiveId][fileId] == null)
            cacheArchiveFiles(archiveId, keys);
        byte[] file = cachedFiles[archiveId][fileId];
        cachedFiles[archiveId][fileId] = null;
        return file;
    }

    private void cacheArchiveFiles(int archiveId, int[] keys) {
        int lastFileId = getLastFileId(archiveId);
        cachedFiles[archiveId] = new byte[lastFileId + 1][];
        byte[] data = Archive.decompress(getArchiveData(archiveId), keys);
        if(data == null)
            return;
        int filesCount = getValidFilesCount(archiveId);
        if(filesCount <= 1) {
            cachedFiles[archiveId][lastFileId] = data;
            return;
        }
        int readPosition = data.length;
        int amtOfLoops = data[--readPosition] & 0xff;
        readPosition -= amtOfLoops * (filesCount * 4);
        InBuffer in = new InBuffer(data);
        in.position(readPosition);
        int filesSize[] = new int[filesCount];
        for(int loop = 0; loop < amtOfLoops; loop++) {
            int offset = 0;
            for(int i = 0; i < filesCount; i++)
                filesSize[i] += offset += in.readInt();
        }
        byte[][] filesData = new byte[filesCount][];
        for(int i = 0; i < filesCount; i++) {
            filesData[i] = new byte[filesSize[i]];
            filesSize[i] = 0;
        }
        in.position(readPosition);
        int sourceOffset = 0;
        for(int loop = 0; loop < amtOfLoops; loop++) {
            int dataRead = 0;
            for(int i = 0; i < filesCount; i++) {
                dataRead += in.readInt();
                System.arraycopy(data, sourceOffset, filesData[i], filesSize[i], dataRead);
                sourceOffset += dataRead;
                filesSize[i] += dataRead;
            }
        }
        int count = 0;
        for(int fileId : table.archives[archiveId].fileIds)
            cachedFiles[archiveId][fileId] = filesData[count++];
    }

    public byte[] getArchiveData(int archiveId) {
        synchronized(mainDataFile) {
            try {
                if(indexDataFile.length() < (long) (archiveId * 6 + 6)) {
                    System.out.println("Invalid Index Data File Length!");
                    return null;
                }
                indexDataFile.seek((long) (6 * archiveId));
                indexDataFile.read(readCachedBuffer, 0, 6);
                int archiveLength = (((readCachedBuffer[1] & 0xff) << 8) + ((readCachedBuffer[0] & 0xff) << 16) + (readCachedBuffer[2] & 0xff));
                int sector = (((readCachedBuffer[3] & 0xff) << 16) + ((readCachedBuffer[4] & 0xff) << 8) + (readCachedBuffer[5] & 0xff));
                if(archiveLength < 0 || archiveLength > maxLength) {
                    System.err.println("Invalid Archive Length!");
                    return null;
                }
                if(sector <= 0 || (long) sector > mainDataFile.length() / 520L) {
                    System.err.println("Invalid Sector (" + sector + "): " + (mainDataFile.length() / 520L) + " (" + this.index + "," + archiveId + ")");
                    return null;
                }
                byte[] archiveData = new byte[archiveLength];
                int readBytesCount = 0;
                int part = 0;
                while(readBytesCount < archiveLength) {
                    if(sector == 0) {
                        System.err.println("Invalid Sector 0!");
                        return null;
                    }
                    mainDataFile.seek((long) sector * 520L);
                    int dataBlockSize = archiveLength - readBytesCount;
                    int headerSize;
                    int currentArchive;
                    int currentPart;
                    int nextSector;
                    int currentIndex;
                    if(archiveId > 65535) {
                        if(dataBlockSize > 510)
                            dataBlockSize = 510;
                        headerSize = 10;
                        mainDataFile.read(readCachedBuffer, 0, dataBlockSize + headerSize);
                        currentArchive = (((readCachedBuffer[0] & 0xff) << 24) + ((readCachedBuffer[1] & 0xff) << 16) + ((readCachedBuffer[2] & 0xff) << 8) + (readCachedBuffer[3] & 0xff));
                        currentPart = ((readCachedBuffer[5] & 0xff) + ((readCachedBuffer[4] & 0xff) << 8));
                        nextSector = (((readCachedBuffer[7] & 0xff) << 8) + ((readCachedBuffer[6] & 0xff) << 16) + (readCachedBuffer[8] & 0xff));
                        currentIndex = readCachedBuffer[9] & 0xff;
                    } else {
                        if(dataBlockSize > 512)
                            dataBlockSize = 512;
                        headerSize = 8;
                        mainDataFile.read(readCachedBuffer, 0, headerSize + dataBlockSize);
                        currentArchive = (((readCachedBuffer[0] & 0xff) << 8) + (readCachedBuffer[1] & 0xff));
                        currentPart = ((readCachedBuffer[3] & 0xff) + ((readCachedBuffer[2] & 0xff) << 8));
                        nextSector = (((readCachedBuffer[4] & 0xff) << 16) + ((readCachedBuffer[5] & 0xff) << 8) + (readCachedBuffer[6] & 0xff));
                        currentIndex = readCachedBuffer[7] & 0xff;
                    }
                    if(archiveId != currentArchive || currentPart != part || this.index != currentIndex) {
                        System.err.println("Invalid Next File!");
                        return null;
                    }
                    if(nextSector < 0 || (long) nextSector > (mainDataFile.length() / 520L)) {
                        System.err.println("Invalid Next Sector!");
                        return null;
                    }
                    int length = dataBlockSize + headerSize;
                    for(int i = headerSize; i < length; i++)
                        archiveData[readBytesCount++] = readCachedBuffer[i];
                    sector = nextSector;
                    part++;
                }
                return archiveData;
            } catch(IOException e) {
                ServerWrapper.logError("Can't load archive data: ", e);
                return null;
            }
        }
    }

    public int getArchivesCount() throws IOException {
        return (int) (indexDataFile.length() / 6);
    }

}
