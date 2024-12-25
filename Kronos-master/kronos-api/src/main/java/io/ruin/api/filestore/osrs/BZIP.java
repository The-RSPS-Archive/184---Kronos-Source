package io.ruin.api.filestore.osrs;

public final class BZIP {

    static Class168 aClass168_2321 = new Class168();

    static int[] anIntArray1415;

    public static int method3124(byte[] var0, int var1, byte[] var2, int var3, int var4) {
        synchronized(aClass168_2321) {
            aClass168_2321.aByteArray2340 = var2;
            aClass168_2321.anInt2341 = var4;
            aClass168_2321.aByteArray2343 = var0;
            aClass168_2321.anInt2342 = 0;
            aClass168_2321.anInt2345 = var1;
            aClass168_2321.anInt2350 = 0;
            aClass168_2321.anInt2349 = 0;
            aClass168_2321.anInt2337 = 0;
            aClass168_2321.anInt2364 = 0;
            method3139(aClass168_2321);
            var1 -= aClass168_2321.anInt2345;
            aClass168_2321.aByteArray2340 = null;
            aClass168_2321.aByteArray2343 = null;
            return var1;
        }
    }

    static void method3139(Class168 var0) {
        boolean var4 = false;
        boolean var5 = false;
        boolean var6 = false;
        boolean var7 = false;
        boolean var8 = false;
        boolean var9 = false;
        boolean var10 = false;
        boolean var11 = false;
        boolean var12 = false;
        boolean var13 = false;
        boolean var14 = false;
        boolean var15 = false;
        boolean var16 = false;
        boolean var17 = false;
        boolean var18 = false;
        boolean var19 = false;
        boolean var20 = false;
        boolean var21 = false;
        int var22 = 0;
        int[] var23 = null;
        int[] var24 = null;
        int[] var25 = null;
        var0.anInt2351 = 1;
        if(anIntArray1415 == null)
            anIntArray1415 = new int[var0.anInt2351 * 100000];
        boolean var26 = true;
        while(var26) {
            byte var1 = method3133(var0);
            if(var1 == 23)
                break;
            var1 = method3133(var0);
            var1 = method3133(var0);
            var1 = method3133(var0);
            var1 = method3133(var0);
            var1 = method3133(var0);
            var1 = method3133(var0);
            var1 = method3133(var0);
            var1 = method3133(var0);
            var1 = method3133(var0);
            var1 = method3128(var0);
            if(var1 == 0) {
                /* empty */
            }
            var0.anInt2354 = 0;
            var1 = method3133(var0);
            var0.anInt2354 = var0.anInt2354 << 8 | var1 & 0xff;
            var1 = method3133(var0);
            var0.anInt2354 = var0.anInt2354 << 8 | var1 & 0xff;
            var1 = method3133(var0);
            var0.anInt2354 = var0.anInt2354 << 8 | var1 & 0xff;
            for(int var36 = 0; var36 < 16; var36++) {
                var1 = method3128(var0);
                if(var1 == 1)
                    var0.aBoolArray2362[var36] = true;
                else
                    var0.aBoolArray2362[var36] = false;
            }
            for(int var36 = 0; var36 < 256; var36++)
                var0.aBoolArray2352[var36] = false;
            for(int var36 = 0; var36 < 16; var36++) {
                if(var0.aBoolArray2362[var36]) {
                    for(int var37 = 0; var37 < 16; var37++) {
                        var1 = method3128(var0);
                        if(var1 == 1)
                            var0.aBoolArray2352[var37 + var36 * 16] = true;
                    }
                }
            }
            method3130(var0);
            int var39 = var0.anInt2358 + 2;
            int var40 = method3131(3, var0);
            int var41 = method3131(15, var0);
            int var36 = 0;
            while(var36 < var41) {
                int var37 = 0;
                for(; ; ) {
                    var1 = method3128(var0);
                    if(var1 == 0) {
                        var0.aByteArray2365[var36] = (byte) var37;
                        var36++;
                        break;
                    }
                    var37++;
                }
            }
            byte[] var27 = new byte[6];
            byte var29 = 0;
            while(var29 < var40) {
                byte[] is = var27;
                byte i = var29;
                byte i_0_ = var29;
                var29++;
                is[i] = i_0_;
            }
            for(var36 = 0; var36 < var41; var36++) {
                var29 = var0.aByteArray2365[var36];
                byte var28 = var27[var29];
                for(/**/; var29 > 0; var29--)
                    var27[var29] = var27[var29 - 1];
                var27[0] = var28;
                var0.aByteArray2338[var36] = var28;
            }
            for(int var38 = 0; var38 < var40; var38++) {
                int var50 = method3131(5, var0);
                var36 = 0;
                while(var36 < var39) {
                    for(; ; ) {
                        var1 = method3128(var0);
                        if(var1 == 0) {
                            var0.aByteArrayArray2366[var38][var36] = (byte) var50;
                            var36++;
                            break;
                        }
                        var1 = method3128(var0);
                        if(var1 == 0)
                            var50++;
                        else
                            var50--;
                    }
                }
            }
            for(int var38 = 0; var38 < var40; var38++) {
                byte var2 = 32;
                byte var3 = 0;
                for(var36 = 0; var36 < var39; var36++) {
                    if(var0.aByteArrayArray2366[var38][var36] > var3)
                        var3 = var0.aByteArrayArray2366[var38][var36];
                    if(var0.aByteArrayArray2366[var38][var36] < var2)
                        var2 = var0.aByteArrayArray2366[var38][var36];
                }
                method3127(var0.anIntArrayArray2367[var38], var0.anIntArrayArray2368[var38], var0.anIntArrayArray2373[var38], var0.aByteArrayArray2366[var38], var2, var3, var39);
                var0.anIntArray2336[var38] = var2;
            }
            int var42 = var0.anInt2358 + 1;
            int var43 = -1;
            byte var44 = 0;
            for(var36 = 0; var36 <= 255; var36++)
                var0.anIntArray2355[var36] = 0;
            int var56 = 4095;
            for(int var35 = 15; var35 >= 0; var35--) {
                for(int var55 = 15; var55 >= 0; var55--) {
                    var0.aByteArray2344[var56] = (byte) (var55 + var35 * 16);
                    var56--;
                }
                var0.anIntArray2363[var35] = var56 + 1;
            }
            int var47 = 0;
            if(var44 == 0) {
                var43++;
                var44 = (byte) 50;
                byte var54 = var0.aByteArray2338[var43];
                var22 = var0.anIntArray2336[var54];
                var23 = var0.anIntArrayArray2367[var54];
                var25 = var0.anIntArrayArray2373[var54];
                var24 = var0.anIntArrayArray2368[var54];
            }
            int var45 = var44 - 1;
            int var51 = var22;
            byte var53;
            int var52;
            for(var52 = method3131(var22, var0); var52 > var23[var51]; var52 = var52 << 1 | var53) {
                var51++;
                var53 = method3128(var0);
            }
            int var46 = var25[var52 - var24[var51]];
            while(var46 != var42) {
                if(var46 != 0 && var46 != 1) {
                    int var33 = var46 - 1;
                    if(var33 < 16) {
                        int var30 = var0.anIntArray2363[0];
                        var1 = var0.aByteArray2344[var30 + var33];
                        for(/**/; var33 > 3; var33 -= 4) {
                            int var34 = var30 + var33;
                            var0.aByteArray2344[var34] = var0.aByteArray2344[var34 - 1];
                            var0.aByteArray2344[var34 - 1] = var0.aByteArray2344[var34 - 2];
                            var0.aByteArray2344[var34 - 2] = var0.aByteArray2344[var34 - 3];
                            var0.aByteArray2344[var34 - 3] = var0.aByteArray2344[var34 - 4];
                        }
                        for(/**/; var33 > 0; var33--)
                            var0.aByteArray2344[var30 + var33] = var0.aByteArray2344[var30 + var33 - 1];
                        var0.aByteArray2344[var30] = var1;
                    } else {
                        int var31 = var33 / 16;
                        int var32 = var33 % 16;
                        int var30 = var0.anIntArray2363[var31] + var32;
                        var1 = var0.aByteArray2344[var30];
                        for(/**/; var30 > var0.anIntArray2363[var31]; var30--)
                            var0.aByteArray2344[var30] = var0.aByteArray2344[var30 - 1];
                        var0.anIntArray2363[var31]++;
                        for(/**/; var31 > 0; var31--) {
                            var0.anIntArray2363[var31]--;
                            var0.aByteArray2344[var0.anIntArray2363[var31]] = (var0.aByteArray2344[var0.anIntArray2363[var31 - 1] + 16 - 1]);
                        }
                        var0.anIntArray2363[0]--;
                        var0.aByteArray2344[var0.anIntArray2363[0]] = var1;
                        if(var0.anIntArray2363[0] == 0) {
                            var56 = 4095;
                            for(int var35 = 15; var35 >= 0; var35--) {
                                for(int var55 = 15; var55 >= 0; var55--) {
                                    var0.aByteArray2344[var56] = (var0.aByteArray2344[(var0.anIntArray2363[var35] + var55)]);
                                    var56--;
                                }
                                var0.anIntArray2363[var35] = var56 + 1;
                            }
                        }
                    }
                    var0.anIntArray2355[(var0.aByteArray2361[var1 & 0xff] & 0xff)]++;
                    anIntArray1415[var47] = var0.aByteArray2361[var1 & 0xff] & 0xff;
                    var47++;
                    if(var45 == 0) {
                        var43++;
                        var45 = 50;
                        byte var54 = var0.aByteArray2338[var43];
                        var22 = var0.anIntArray2336[var54];
                        var23 = var0.anIntArrayArray2367[var54];
                        var25 = var0.anIntArrayArray2373[var54];
                        var24 = var0.anIntArrayArray2368[var54];
                    }
                    var45--;
                    var51 = var22;
                    for(var52 = method3131(var22, var0); var52 > var23[var51]; var52 = var52 << 1 | var53) {
                        var51++;
                        var53 = method3128(var0);
                    }
                    var46 = var25[var52 - var24[var51]];
                } else {
                    int var48 = -1;
                    int var49 = 1;
                    do {
                        if(var46 == 0)
                            var48 += var49;
                        else if(var46 == 1)
                            var48 += var49 * 2;
                        var49 *= 2;
                        if(var45 == 0) {
                            var43++;
                            var45 = 50;
                            byte var54 = var0.aByteArray2338[var43];
                            var22 = var0.anIntArray2336[var54];
                            var23 = var0.anIntArrayArray2367[var54];
                            var25 = var0.anIntArrayArray2373[var54];
                            var24 = var0.anIntArrayArray2368[var54];
                        }
                        var45--;
                        var51 = var22;
                        for(var52 = method3131(var22, var0); var52 > var23[var51]; var52 = var52 << 1 | var53) {
                            var51++;
                            var53 = method3128(var0);
                        }
                        var46 = var25[var52 - var24[var51]];
                    } while(var46 == 0 || var46 == 1);
                    var48++;
                    var1 = (var0.aByteArray2361[(var0.aByteArray2344[var0.anIntArray2363[0]] & 0xff)]);
                    var0.anIntArray2355[var1 & 0xff] += var48;
                    for(/**/; var48 > 0; var48--) {
                        anIntArray1415[var47] = var1 & 0xff;
                        var47++;
                    }
                }
            }
            var0.anInt2371 = 0;
            var0.aByte2347 = (byte) 0;
            var0.anIntArray2357[0] = 0;
            for(var36 = 1; var36 <= 256; var36++)
                var0.anIntArray2357[var36] = var0.anIntArray2355[var36 - 1];
            for(var36 = 1; var36 <= 256; var36++)
                var0.anIntArray2357[var36] += var0.anIntArray2357[var36 - 1];
            for(var36 = 0; var36 < var47; var36++) {
                var1 = (byte) (anIntArray1415[var36] & 0xff);
                anIntArray1415[var0.anIntArray2357[var1 & 0xff]] |= var36 << 8;
                var0.anIntArray2357[var1 & 0xff]++;
            }
            var0.anInt2353 = anIntArray1415[var0.anInt2354] >> 8;
            var0.anInt2356 = 0;
            var0.anInt2353 = anIntArray1415[var0.anInt2353];
            var0.anInt2360 = (byte) (var0.anInt2353 & 0xff);
            var0.anInt2353 >>= 8;
            var0.anInt2356++;
            var0.anInt2348 = var47;
            method3125(var0);
            if(var0.anInt2348 + 1 == var0.anInt2356 && var0.anInt2371 == 0)
                var26 = true;
            else
                var26 = false;
        }
    }

    static byte method3133(Class168 var0) {
        return (byte) method3131(8, var0);
    }

    static int method3131(int var0, Class168 var1) {
        while(var1.anInt2350 < var0) {
            var1.anInt2349 = (var1.anInt2349 << 8 | var1.aByteArray2340[var1.anInt2341] & 0xff);
            var1.anInt2350 += 8;
            var1.anInt2341++;
            var1.anInt2337++;
            if(var1.anInt2337 == 0) {
		        /* empty */
            }
        }
        int var3 = var1.anInt2349 >> var1.anInt2350 - var0 & (1 << var0) - 1;
        var1.anInt2350 -= var0;
        return var3;
    }

    static byte method3128(Class168 var0) {
        return (byte) method3131(1, var0);
    }

    static void method3130(Class168 var0) {
        var0.anInt2358 = 0;
        for(int var1 = 0; var1 < 256; var1++) {
            if(var0.aBoolArray2352[var1]) {
                var0.aByteArray2361[var0.anInt2358] = (byte) var1;
                var0.anInt2358++;
            }
        }
    }

    static void method3127(int[] var0, int[] var1, int[] var2, byte[] var3, int var4, int var5, int var6) {
        int var7 = 0;
        for(int var8 = var4; var8 <= var5; var8++) {
            for(int var9 = 0; var9 < var6; var9++) {
                if(var8 == var3[var9]) {
                    var2[var7] = var9;
                    var7++;
                }
            }
        }
        for(int var8 = 0; var8 < 23; var8++)
            var1[var8] = 0;
        for(int var8 = 0; var8 < var6; var8++)
            var1[var3[var8] + 1]++;
        for(int var8 = 1; var8 < 23; var8++)
            var1[var8] += var1[var8 - 1];
        for(int var8 = 0; var8 < 23; var8++)
            var0[var8] = 0;
        int var10 = 0;
        for(int var8 = var4; var8 <= var5; var8++) {
            var10 += var1[var8 + 1] - var1[var8];
            var0[var8] = var10 - 1;
            var10 <<= 1;
        }
        for(int var8 = var4 + 1; var8 <= var5; var8++)
            var1[var8] = (var0[var8 - 1] + 1 << 1) - var1[var8];
    }

    static void method3125(Class168 var0) {
        byte var2 = var0.aByte2347;
        int var3 = var0.anInt2371;
        int var4 = var0.anInt2356;
        int var5 = var0.anInt2360;
        int[] var6 = anIntArray1415;
        int var7 = var0.anInt2353;
        byte[] var8 = var0.aByteArray2343;
        int var9 = var0.anInt2342;
        int var10 = var0.anInt2345;
        int var12 = var0.anInt2348 + 1;
        while_33_:
        for(; ; ) {
            if(var3 > 0) {
                for(; ; ) {
                    if(var10 == 0)
                        break while_33_;
                    if(var3 == 1) {
                        if(var10 == 0)
                            var3 = 1;
                        else {
                            var8[var9] = var2;
                            var9++;
                            var10--;
                            break;
                        }
                        break while_33_;
                    }
                    var8[var9] = var2;
                    var3--;
                    var9++;
                    var10--;
                }
            }
            boolean var14 = true;
            while(var14) {
                var14 = false;
                if(var4 == var12) {
                    var3 = 0;
                    break while_33_;
                }
                var2 = (byte) var5;
                var7 = var6[var7];
                byte var1 = (byte) (var7 & 0xff);
                var7 >>= 8;
                var4++;
                if(var1 != var5) {
                    var5 = var1;
                    if(var10 == 0) {
                        var3 = 1;
                        break while_33_;
                    }
                    var8[var9] = var2;
                    var9++;
                    var10--;
                    var14 = true;
                } else if(var4 == var12) {
                    if(var10 == 0) {
                        var3 = 1;
                        break while_33_;
                    }
                    var8[var9] = var2;
                    var9++;
                    var10--;
                    var14 = true;
                }
            }
            var3 = 2;
            var7 = var6[var7];
            byte var1 = (byte) (var7 & 0xff);
            var7 >>= 8;
            if(++var4 != var12) {
                if(var1 != var5)
                    var5 = var1;
                else {
                    var3 = 3;
                    var7 = var6[var7];
                    var1 = (byte) (var7 & 0xff);
                    var7 >>= 8;
                    if(++var4 != var12) {
                        if(var1 != var5)
                            var5 = var1;
                        else {
                            var7 = var6[var7];
                            var1 = (byte) (var7 & 0xff);
                            var7 >>= 8;
                            var4++;
                            var3 = (var1 & 0xff) + 4;
                            var7 = var6[var7];
                            var5 = (byte) (var7 & 0xff);
                            var7 >>= 8;
                            var4++;
                        }
                    }
                }
            }
        }
        int var13 = var0.anInt2364;
        var0.anInt2364 += var10 - var10;
        if(var0.anInt2364 >= var13) {
	    /* empty */
        }
        var0.aByte2347 = var2;
        var0.anInt2371 = var3;
        var0.anInt2356 = var4;
        var0.anInt2360 = var5;
        anIntArray1415 = var6;
        var0.anInt2353 = var7;
        var0.aByteArray2343 = var8;
        var0.anInt2342 = var9;
        var0.anInt2345 = var10;
    }

    private static final class Class168 {
        final int anInt2359 = 4096;
        final int anInt2335 = 16;
        final int anInt2346 = 258;
        final int anInt2370 = 6;
        final int anInt2334 = 50;
        final int anInt2339 = 18002;
        int anInt2341 = 0;
        int anInt2342 = 0;
        int[] anIntArray2355 = new int[256];
        int[] anIntArray2357 = new int[257];
        boolean[] aBoolArray2352 = new boolean[256];
        boolean[] aBoolArray2362 = new boolean[16];
        byte[] aByteArray2361 = new byte[256];
        byte[] aByteArray2344 = new byte[4096];
        byte[] aByteArray2340;
        int[] anIntArray2363 = new int[16];
        byte[] aByteArray2338 = new byte[18002];
        byte[] aByteArray2365 = new byte[18002];
        byte[] aByteArray2343;
        byte[][] aByteArrayArray2366 = new byte[6][258];
        int[][] anIntArrayArray2367 = new int[6][258];
        int[][] anIntArrayArray2368 = new int[6][258];
        int[][] anIntArrayArray2373 = new int[6][258];
        int anInt2345;
        int[] anIntArray2336 = new int[6];
        int anInt2350;
        int anInt2349;
        int anInt2337;
        int anInt2364;
        int anInt2351;
        int anInt2354;
        int anInt2358;
        int anInt2371;
        byte aByte2347;
        int anInt2353;
        int anInt2356;
        int anInt2360;
        int anInt2348;
    }

}