package io.ruin.api.filestore.osrs;

import java.io.EOFException;
import java.io.IOException;

public class Class121 {

    long aLong1749;
    long aLong1740;
    int anInt1748;
    long aLong1745 = -1L;
    long aLong1750;
    long aLong1742 = -1L;
    Class122 aClass122_1743;
    int anInt1746 = 0;
    byte[] aByteArray1744;
    byte[] aByteArray1741;
    long aLong1747;

    public Class121(Class122 var1, int var2, int var3) throws IOException {
        aClass122_1743 = var1;
        aLong1749 = aLong1747 = var1.method2378();
        aByteArray1741 = new byte[var2];
        aByteArray1744 = new byte[var3];
        aLong1750 = 0L;
    }

    public void read(byte[] var1, int var2, int var3) throws IOException {
        try {
            if(var3 + var2 > var1.length)
                throw new ArrayIndexOutOfBoundsException(var3 + var2 - var1.length);
            if(aLong1745 != -1L && aLong1750 >= aLong1745 && (long) var3 + aLong1750 <= (long) anInt1746 + aLong1745) {
                System.arraycopy(aByteArray1744, (int) (aLong1750 - aLong1745), var1, var2, var3);
                aLong1750 += (long) var3;
                return;
            }
            long var5 = aLong1750;
            int var8 = var3;
            if(aLong1750 >= aLong1742 && aLong1750 < aLong1742 + (long) anInt1748) {
                int var9 = (int) ((long) anInt1748 - (aLong1750 - aLong1742));
                if(var9 > var3)
                    var9 = var3;
                System.arraycopy(aByteArray1741, (int) (aLong1750 - aLong1742), var1, var2, var9);
                aLong1750 += (long) var9;
                var2 += var9;
                var3 -= var9;
            }
            if(var3 > aByteArray1741.length) {
                aClass122_1743.method2386(aLong1750);
                aLong1740 = aLong1750;
                int var9;
                for(/**/; var3 > 0; var3 -= var9) {
                    var9 = aClass122_1743.method2377(var1, var2, var3);
                    if(var9 == -1)
                        break;
                    aLong1740 += (long) var9;
                    aLong1750 += (long) var9;
                    var2 += var9;
                }
            } else if(var3 > 0) {
                method2366();
                int var9 = var3;
                if(var3 > anInt1748)
                    var9 = anInt1748;
                System.arraycopy(aByteArray1741, 0, var1, var2, var9);
                var2 += var9;
                var3 -= var9;
                aLong1750 += (long) var9;
            }
            if(aLong1745 != -1L) {
                if(aLong1745 > aLong1750 && var3 > 0) {
                    int var9 = var2 + (int) (aLong1745 - aLong1750);
                    if(var9 > var3 + var2)
                        var9 = var3 + var2;
                    while(var2 < var9) {
                        var1[var2++] = (byte) 0;
                        var3--;
                        aLong1750++;
                    }
                }
                long var14 = -1L;
                long var11 = -1L;
                if(aLong1745 >= var5 && aLong1745 < (long) var8 + var5)
                    var14 = aLong1745;
                else if(var5 >= aLong1745 && var5 < (long) anInt1746 + aLong1745)
                    var14 = var5;
                if(aLong1745 + (long) anInt1746 > var5 && aLong1745 + (long) anInt1746 <= (long) var8 + var5)
                    var11 = aLong1745 + (long) anInt1746;
                else if(var5 + (long) var8 > aLong1745 && var5 + (long) var8 <= (long) anInt1746 + aLong1745)
                    var11 = var5 + (long) var8;
                if(var14 > -1L && var11 > var14) {
                    int var13 = (int) (var11 - var14);
                    System.arraycopy(aByteArray1744, (int) (var14 - aLong1745), var1, (int) (var14 - var5) + var2, var13);
                    if(var11 > aLong1750) {
                        var3 -= var11 - aLong1750;
                        aLong1750 = var11;
                    }
                }
            }
        } catch(IOException var17) {
            aLong1740 = -1L;
            throw var17;
        }
        if(var3 > 0)
            throw new EOFException();
    }

    void method2348() throws IOException {
        if(-1L != aLong1745) {
            if(aLong1745 != aLong1740) {
                aClass122_1743.method2386(aLong1745);
                aLong1740 = aLong1745;
            }
            aClass122_1743.method2375(aByteArray1744, 0, anInt1746);
            aLong1740 += (long) (anInt1746 * 667786469) * -1754087187L;
            if(aLong1740 > aLong1747)
                aLong1747 = aLong1740;
            long var2 = -1L;
            long var4 = -1L;
            if(aLong1745 >= aLong1742 && aLong1745 < aLong1742 + (long) anInt1748)
                var2 = aLong1745;
            else if(aLong1742 >= aLong1745 && aLong1742 < aLong1745 + (long) anInt1746)
                var2 = aLong1742;
            if(aLong1745 + (long) anInt1746 > aLong1742 && ((long) anInt1746 + aLong1745 <= (long) anInt1748 + aLong1742))
                var4 = aLong1745 + (long) anInt1746;
            else if(aLong1742 + (long) anInt1748 > aLong1745 && (aLong1742 + (long) anInt1748 <= (long) anInt1746 + aLong1745))
                var4 = aLong1742 + (long) anInt1748;
            if(var2 > -1L && var4 > var2) {
                int var6 = (int) (var4 - var2);
                System.arraycopy(aByteArray1744, (int) (var2 - aLong1745), aByteArray1741, (int) (var2 - aLong1742), var6);
            }
            aLong1745 = -1L;
            anInt1746 = 0;
        }
    }

    public long length() {
        return aLong1749;
    }

    public void seek(long var1) throws IOException {
        if(var1 < 0L)
            throw new IOException("");
        aLong1750 = var1;
    }

    public void method2353(byte[] var1, int var2, int var3) throws IOException {
        try {
            if(aLong1750 + (long) var3 > aLong1749)
                aLong1749 = (long) var3 + aLong1750;
            if(aLong1745 != -1L && (aLong1750 < aLong1745 || aLong1750 > aLong1745 + (long) anInt1746))
                method2348();
            if(-1L != aLong1745 && ((long) var3 + aLong1750 > aLong1745 + (long) aByteArray1744.length)) {
                int var5 = (int) ((long) aByteArray1744.length - (aLong1750 - aLong1745));
                System.arraycopy(var1, var2, aByteArray1744, (int) (aLong1750 - aLong1745), var5);
                aLong1750 += (long) var5;
                var2 += var5;
                var3 -= var5;
                anInt1746 = aByteArray1744.length;
                method2348();
            }
            if(var3 <= aByteArray1744.length) {
                if(var3 > 0) {
                    if(aLong1745 == -1L)
                        aLong1745 = aLong1750;
                    System.arraycopy(var1, var2, aByteArray1744, (int) (aLong1750 - aLong1745), var3);
                    aLong1750 += (long) var3;
                    if(aLong1750 - aLong1745 > (long) anInt1746)
                        anInt1746 = (int) (aLong1750 - aLong1745);
                }
            } else {
                if(aLong1740 != aLong1750) {
                    aClass122_1743.method2386(aLong1750);
                    aLong1740 = aLong1750;
                }
                aClass122_1743.method2375(var1, var2, var3);
                aLong1740 += (long) var3;
                if(aLong1740 > aLong1747)
                    aLong1747 = aLong1740;
                long var10 = -1L;
                long var7 = -1L;
                if(aLong1750 >= aLong1742 && aLong1750 < aLong1742 + (long) anInt1748)
                    var10 = aLong1750;
                else if(aLong1742 >= aLong1750 && aLong1742 < aLong1750 + (long) var3)
                    var10 = aLong1742;
                if((long) var3 + aLong1750 > aLong1742 && aLong1750 + (long) var3 <= (long) anInt1748 + aLong1742)
                    var7 = (long) var3 + aLong1750;
                else if((long) anInt1748 + aLong1742 > aLong1750 && ((long) anInt1748 + aLong1742 <= (long) var3 + aLong1750))
                    var7 = aLong1742 + (long) anInt1748;
                if(var10 > -1L && var7 > var10) {
                    int var9 = (int) (var7 - var10);
                    System.arraycopy(var1, (int) (var10 + (long) var2 - aLong1750), aByteArray1741, (int) (var10 - aLong1742), var9);
                }
                aLong1750 += (long) var3;
            }
        } catch(IOException var13) {
            aLong1740 = -1L;
            throw var13;
        }
    }

    void method2366() throws IOException {
        anInt1748 = 0;
        if(aLong1750 != aLong1740) {
            aClass122_1743.method2386(aLong1750);
            aLong1740 = aLong1750;
        }
        aLong1742 = aLong1750;
        int var2;
        for(/**/; anInt1748 < aByteArray1741.length; anInt1748 += var2) {
            var2 = aClass122_1743.method2377(aByteArray1741, anInt1748, aByteArray1741.length - anInt1748);
            if(var2 == -1)
                break;
            aLong1740 += (long) var2;
        }
    }

    public void method2358() throws IOException {
        method2348();
        aClass122_1743.method2387();
    }

    public void method2350(byte[] var1) throws IOException {
        read(var1, 0, var1.length);
    }

}