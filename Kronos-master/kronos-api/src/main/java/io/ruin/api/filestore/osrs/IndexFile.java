package io.ruin.api.filestore.osrs;

import io.ruin.api.utils.ServerWrapper;

import java.io.EOFException;
import java.io.IOException;

public final class IndexFile {

    static byte[] cached = new byte[520];

    int index;
    Class121 mainDataFile = null;
    Class121 indexDataFile = null;
    int maxLength = 65000;

    public IndexFile(int index, Class121 mainDataFile, Class121 indexDataFile, int maxLength) {
        this.index = index;
        this.mainDataFile = mainDataFile;
        this.indexDataFile = indexDataFile;
        this.maxLength = maxLength;
    }

    boolean method3048(int archiveId, byte[] var2, int archiveLength, boolean idk) {
        synchronized(mainDataFile) {
            try {
                int var7;
                if(idk) {
                    if(indexDataFile.length() < (long) (archiveId * 6 + 6)) {
                        return false;
                    }
                    indexDataFile.seek((long) (archiveId * 6));
                    indexDataFile.read(cached, 0, 6);
                    var7 = ((cached[5] & 0xff) + ((cached[3] & 0xff) << 16) + ((cached[4] & 0xff) << 8));
                    if(var7 <= 0 || ((long) var7 > mainDataFile.length() / 520L)) {
                        return false;
                    }
                } else {
                    var7 = (int) ((mainDataFile.length() + 519L) / 520L);
                    if(var7 == 0)
                        var7 = 1;
                }
                cached[0] = (byte) (archiveLength >> 16);
                cached[1] = (byte) (archiveLength >> 8);
                cached[2] = (byte) archiveLength;
                cached[3] = (byte) (var7 >> 16);
                cached[4] = (byte) (var7 >> 8);
                cached[5] = (byte) var7;
                indexDataFile.seek((long) (archiveId * 6));
                indexDataFile.method2353(cached, 0, 6);
                int readBytesCount = 0;
                int part = 0;
                while(readBytesCount < archiveLength) {
                    int var10 = 0;
                    if(idk) {
                        mainDataFile.seek((long) (var7 * 520));
                        try {
                            mainDataFile.read(cached, 0, 8);
                        } catch(EOFException var17) {
                            break;
                        }
                        int var11 = ((cached[1] & 0xff) + ((cached[0] & 0xff) << 8));
                        int var12 = ((cached[3] & 0xff) + ((cached[2] & 0xff) << 8));
                        var10 = (((cached[5] & 0xff) << 8) + ((cached[4] & 0xff) << 16) + (cached[6] & 0xff));
                        int var13 = cached[7] & 0xff;
                        if(var11 != archiveId || var12 != part || var13 != index) {
                            return false;
                        }
                        if(var10 < 0 || ((long) var10 > (mainDataFile.length() / 520L))) {
                            return false;
                        }
                    }
                    if(var10 == 0) {
                        idk = false;
                        var10 = (int) ((mainDataFile.length() + 519L) / 520L);
                        if(var10 == 0)
                            var10++;
                        if(var7 == var10)
                            var10++;
                    }
                    if(archiveLength - readBytesCount <= 512)
                        var10 = 0;
                    cached[0] = (byte) (archiveId >> 8);
                    cached[1] = (byte) archiveId;
                    cached[2] = (byte) (part >> 8);
                    cached[3] = (byte) part;
                    cached[4] = (byte) (var10 >> 16);
                    cached[5] = (byte) (var10 >> 8);
                    cached[6] = (byte) var10;
                    cached[7] = (byte) index;
                    mainDataFile.seek((long) (var7 * 520));
                    mainDataFile.method2353(cached, 0, 8);
                    int var11 = archiveLength - readBytesCount;
                    if(var11 > 512)
                        var11 = 512;
                    mainDataFile.method2353(var2, readBytesCount, var11);
                    readBytesCount += var11;
                    var7 = var10;
                    part++;
                }
                return true;
            } catch(IOException e) {
                return false;
            }
        }
    }

    public boolean method3046(int var1, byte[] var2, int var3) {
        synchronized(mainDataFile) {
            if(var3 >= 0 && var3 <= maxLength) {
                boolean var6 = method3048(var1, var2, var3, true);
                if(!var6)
                    var6 = method3048(var1, var2, var3, false);
                boolean bool = var6;
                return bool;
            }
            throw new IllegalArgumentException();
        }
    }

    public byte[] getArchiveData(int archiveId) {
        synchronized(mainDataFile) {
            try {
                if(indexDataFile.length() < (long) (archiveId * 6 + 6)) {
                    return null;
                }
                indexDataFile.seek((long) (archiveId * 6));
                indexDataFile.read(cached, 0, 6);
                int archiveLength = (((cached[0] & 0xff) << 16) + (cached[2] & 0xff) + ((cached[1] & 0xff) << 8));
                int sector = ((cached[5] & 0xff) + ((cached[3] & 0xff) << 16) + ((cached[4] & 0xff) << 8));
                if(archiveLength < 0 || archiveLength > maxLength) {
                    return null;
                }
                if(sector > 0 && ((long) sector <= mainDataFile.length() / 520L)) {
                    byte[] archiveData = new byte[archiveLength];
                    int readBytesCount = 0;
                    int part = 0;
                    while(readBytesCount < archiveLength) {
                        if(sector == 0)
                            return null;
                        mainDataFile.seek((long) (sector * 520));
                        int dataBlockSize = archiveLength - readBytesCount;
                        if(dataBlockSize > 512)
                            dataBlockSize = 512;
                        mainDataFile.read(cached, 0, dataBlockSize + 8);
                        int currentArchive = ((cached[1] & 0xff) + ((cached[0] & 0xff) << 8));
                        int currentPart = ((cached[3] & 0xff) + ((cached[2] & 0xff) << 8));
                        int nextSector = (((cached[5] & 0xff) << 8) + ((cached[4] & 0xff) << 16) + (cached[6] & 0xff));
                        int currentIndex = cached[7] & 0xff;
                        if(currentArchive != archiveId || part != currentPart || currentIndex != index)
                            return null;
                        if(nextSector < 0 || ((long) nextSector > mainDataFile.length() / 520L))
                            return null;
                        for(int var14 = 0; var14 < dataBlockSize; var14++)
                            archiveData[readBytesCount++] = cached[var14 + 8];
                        sector = nextSector;
                        part++;
                    }
                    return archiveData;
                }
            } catch(IOException e) {
                ServerWrapper.logError("Can't get archive data", e);
                return null;
            }
            return null;
        }
    }

}