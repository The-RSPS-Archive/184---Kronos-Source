package io.ruin.api.filestore.osrs;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public final class Class122 {

    long aLong1756;
    RandomAccessFile aRandomAccessFile1754;
    long aLong1757;

    public Class122(File var1, String var2, long var3) throws IOException {
        if(var3 == -1L)
            var3 = 9223372036854775807L;
        if(var1.length() >= var3)
            var1.delete();
        aRandomAccessFile1754 = new RandomAccessFile(var1, var2);
        aLong1757 = var3;
        aLong1756 = 0L;
        int var5 = aRandomAccessFile1754.read();
        if(var5 != -1 && !var2.equals("r")) {
            aRandomAccessFile1754.seek(0L);
            aRandomAccessFile1754.write(var5);
        }
        aRandomAccessFile1754.seek(0L);
    }

    public final long method2378() throws IOException {
        return aRandomAccessFile1754.length();
    }

    public final void method2387() throws IOException {
        if(aRandomAccessFile1754 != null) {
            aRandomAccessFile1754.close();
            aRandomAccessFile1754 = null;
        }
    }

    public final int method2377(byte[] var1, int var2, int var3) throws IOException {
        int var5 = aRandomAccessFile1754.read(var1, var2, var3);
        if(var5 > 0)
            aLong1756 += (long) var5;
        return var5;
    }

    final void method2386(long var1) throws IOException {
        aRandomAccessFile1754.seek(var1);
        aLong1756 = var1;
    }

    public final void method2375(byte[] var1, int var2, int var3) throws IOException {
        if(aLong1756 + (long) var3 > aLong1757) {
            aRandomAccessFile1754.seek(aLong1757 + 1L);
            aRandomAccessFile1754.write(1);
            throw new EOFException();
        }
        aRandomAccessFile1754.write(var1, var2, var3);
        aLong1756 += (long) var3;
    }

    protected void finalize() throws Throwable {
        if(aRandomAccessFile1754 != null) {
            System.out.println("");
            method2387();
        }
    }

}