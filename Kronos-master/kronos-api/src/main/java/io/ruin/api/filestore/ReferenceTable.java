package io.ruin.api.filestore;

import io.ruin.api.buffer.InBuffer;

import java.util.zip.CRC32;

public class ReferenceTable {

    public final int crc;
    public final int revision;

    public final int archiveCount;
    public final int[] archiveIds;
    public final Archive[] archives;

    public ReferenceTable(byte[] archiveData) {

        CRC32 crc32 = new CRC32();
        crc32.update(archiveData);
        this.crc = (int) crc32.getValue();

        InBuffer in = new InBuffer(Archive.decompress(archiveData, null));

        int protocol = in.readUnsignedByte();
        if(protocol < 5 || protocol > 7)
            throw new RuntimeException("Invalid Protocol!");
        revision = protocol >= 6 ? in.readInt() : 0;

        int hash = in.readUnsignedByte();
        boolean named = (0x1 & hash) != 0;

        this.archiveCount = protocol >= 7 ? in.readBigSmart() : in.readUnsignedShort();
        this.archiveIds = new int[archiveCount];

        int lastArchiveId = 0;
        int highestArchiveId = -1;
        for(int index = 0; index < archiveCount; index++) {
            int archiveId = lastArchiveId += protocol >= 7 ? in.readBigSmart() : in.readUnsignedShort();
            if(archiveId > highestArchiveId)
                highestArchiveId = archiveId;
            archiveIds[index] = archiveId;
        }
        archives = new Archive[highestArchiveId + 1];
        for(int index = 0; index < archiveCount; index++) {
            int archiveId = archiveIds[index];
            archives[archiveId] = new Archive();
        }
        if(named) {
            for(int index = 0; index < archiveCount; index++) {
                int archiveId = archiveIds[index];
                archives[archiveId].nameHash = in.readInt();
            }
        }
        for(int index = 0; index < archiveCount; index++) {
            int archiveId = archiveIds[index];
            archives[archiveId].crc = in.readInt();
        }
        for(int index = 0; index < archiveCount; index++) {
            int archiveId = archiveIds[index];
            archives[archiveId].revision = in.readInt();
        }
        for(int index = 0; index < archiveCount; index++) {
            int archiveId = archiveIds[index];
            int fileCount = protocol >= 7 ? in.readBigSmart() : in.readUnsignedShort();
            archives[archiveId].fileCount = fileCount;
            archives[archiveId].fileIds = new int[fileCount];
        }
        for(int index = 0; index < archiveCount; index++) {
            int archiveId = archiveIds[index];
            Archive archive = archives[archiveId];

            int lastFileId = 0;
            int highestFileId = -1;
            for(int i = 0; i < archive.fileCount; i++) {
                int fileId = lastFileId += protocol >= 7 ? in.readBigSmart() : in.readUnsignedShort();
                if(fileId > highestFileId)
                    highestFileId = fileId;
                archive.fileIds[i] = fileId;
            }
            archive.files = new Archive.File[highestFileId + 1];
            for(int i = 0; i < archive.fileCount; i++) {
                int fileId = archive.fileIds[i];
                archive.files[fileId] = new Archive.File();
            }
        }
        if(named) {
            for(int index = 0; index < archiveCount; index++) {
                int archiveId = archiveIds[index];
                Archive archive = archives[archiveId];
                boolean b = archive.files.length == archive.fileCount;
                for(int i = 0; i < archive.fileCount; i++) {
                    int fileId;
                    if(!b) {
                        fileId = archive.fileIds[i];
                    } else {
                        //System.out.println("@@" + archiveId + ": " + i + "," + archive.fileIds[i]);
                        fileId = i;
                    }
                    archive.files[fileId].nameHash = in.readInt();
                }
            }
        }
    }

}
