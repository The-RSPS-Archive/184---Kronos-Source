package io.ruin.api.filestore.utility;

public final class BZIP {
    
    private static int[] anIntArray367;

    private static Class167 aClass167_2306 = new Class167();

    public static int method3143(byte[] var0, int var1, byte[] var2, int var3, int var4) {
        Class167 var5 = aClass167_2306;
        synchronized(aClass167_2306) {
            aClass167_2306.aByteArray2330 = var2;
            aClass167_2306.anInt2331 = var4;
            aClass167_2306.aByteArray2345 = var0;
            aClass167_2306.anInt2334 = 0;
            aClass167_2306.anInt2335 = var1;
            aClass167_2306.anInt2340 = 0;
            aClass167_2306.anInt2339 = 0;
            aClass167_2306.anInt2332 = 0;
            aClass167_2306.anInt2352 = 0;
            method3139(aClass167_2306);
            var1 -= aClass167_2306.anInt2335;
            aClass167_2306.aByteArray2330 = null;
            aClass167_2306.aByteArray2345 = null;
            int i = var1;
            return i;
        }
    }

    static void method3139(Class167 var0) {
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
        var0.anInt2341 = 1;
        if(anIntArray367 == null)
            anIntArray367 = new int[var0.anInt2341 * 100000];
        boolean var26 = true;
        while(var26) {
            byte var1 = method3140(var0);
            if(var1 == 23)
                break;
            var1 = method3140(var0);
            var1 = method3140(var0);
            var1 = method3140(var0);
            var1 = method3140(var0);
            var1 = method3140(var0);
            var1 = method3140(var0);
            var1 = method3140(var0);
            var1 = method3140(var0);
            var1 = method3140(var0);
            var1 = method3141(var0);
            if(var1 == 0) {
                /* empty */
            }
            var0.anInt2342 = 0;
            var1 = method3140(var0);
            var0.anInt2342 = var0.anInt2342 << 8 | var1 & 0xff;
            var1 = method3140(var0);
            var0.anInt2342 = var0.anInt2342 << 8 | var1 & 0xff;
            var1 = method3140(var0);
            var0.anInt2342 = var0.anInt2342 << 8 | var1 & 0xff;
            for(int var36 = 0; var36 < 16; var36++) {
                var1 = method3141(var0);
                if(var1 == 1)
                    var0.aBoolArray2336[var36] = true;
                else
                    var0.aBoolArray2336[var36] = false;
            }
            for(int var36 = 0; var36 < 256; var36++)
                var0.aBoolArray2358[var36] = false;
            for(int var36 = 0; var36 < 16; var36++) {
                if(var0.aBoolArray2336[var36]) {
                    for(int var37 = 0; var37 < 16; var37++) {
                        var1 = method3141(var0);
                        if(var1 == 1)
                            var0.aBoolArray2358[var37 + var36 * 16] = true;
                    }
                }
            }
            method3146(var0);
            int var39 = var0.anInt2337 + 2;
            int var40 = method3142(3, var0);
            int var41 = method3142(15, var0);
            int var36 = 0;
            while(var36 < var41) {
                int var37 = 0;
                for(; ; ) {
                    var1 = method3141(var0);
                    if(var1 == 0) {
                        var0.aByteArray2326[var36] = (byte) var37;
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
                var29 = var0.aByteArray2326[var36];
                byte var28 = var27[var29];
                for(/**/; var29 > 0; var29--)
                    var27[var29] = var27[var29 - 1];
                var27[0] = var28;
                var0.aByteArray2354[var36] = var28;
            }
            for(int var38 = 0; var38 < var40; var38++) {
                int var50 = method3142(5, var0);
                var36 = 0;
                while(var36 < var39) {
                    for(; ; ) {
                        var1 = method3141(var0);
                        if(var1 == 0) {
                            var0.aByteArrayArray2356[var38][var36] = (byte) var50;
                            var36++;
                            break;
                        }
                        var1 = method3141(var0);
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
                    if(var0.aByteArrayArray2356[var38][var36] > var3)
                        var3 = var0.aByteArrayArray2356[var38][var36];
                    if(var0.aByteArrayArray2356[var38][var36] < var2)
                        var2 = var0.aByteArrayArray2356[var38][var36];
                }
                method3144(var0.anIntArrayArray2357[var38], var0.anIntArrayArray2362[var38], var0.anIntArrayArray2359[var38], var0.aByteArrayArray2356[var38], var2, var3, var39);
                var0.anIntArray2360[var38] = var2;
            }
            int var42 = var0.anInt2337 + 1;
            int var43 = -1;
            byte var44 = 0;
            for(var36 = 0; var36 <= 255; var36++)
                var0.anIntArray2324[var36] = 0;
            int var56 = 4095;
            for(int var35 = 15; var35 >= 0; var35--) {
                for(int var55 = 15; var55 >= 0; var55--) {
                    var0.aByteArray2344[var56] = (byte) (var35 * 16 + var55);
                    var56--;
                }
                var0.anIntArray2353[var35] = var56 + 1;
            }
            int var47 = 0;
            if(var44 == 0) {
                var43++;
                var44 = (byte) 50;
                byte var54 = var0.aByteArray2354[var43];
                var22 = var0.anIntArray2360[var54];
                var23 = var0.anIntArrayArray2357[var54];
                var25 = var0.anIntArrayArray2359[var54];
                var24 = var0.anIntArrayArray2362[var54];
            }
            int var45 = var44 - 1;
            int var51 = var22;
            byte var53;
            int var52;
            for(var52 = method3142(var22, var0); var52 > var23[var51]; var52 = var52 << 1 | var53) {
                var51++;
                var53 = method3141(var0);
            }
            int var46 = var25[var52 - var24[var51]];
            while(var46 != var42) {
                if(var46 != 0 && var46 != 1) {
                    int var33 = var46 - 1;
                    if(var33 < 16) {
                        int var30 = var0.anIntArray2353[0];
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
                        int var30 = var0.anIntArray2353[var31] + var32;
                        var1 = var0.aByteArray2344[var30];
                        for(/**/; var30 > var0.anIntArray2353[var31]; var30--)
                            var0.aByteArray2344[var30] = var0.aByteArray2344[var30 - 1];
                        var0.anIntArray2353[var31]++;
                        for(/**/; var31 > 0; var31--) {
                            var0.anIntArray2353[var31]--;
                            var0.aByteArray2344[var0.anIntArray2353[var31]] = (var0.aByteArray2344[var0.anIntArray2353[var31 - 1] + 16 - 1]);
                        }
                        var0.anIntArray2353[0]--;
                        var0.aByteArray2344[var0.anIntArray2353[0]] = var1;
                        if(var0.anIntArray2353[0] == 0) {
                            var56 = 4095;
                            for(int var35 = 15; var35 >= 0; var35--) {
                                for(int var55 = 15; var55 >= 0; var55--) {
                                    var0.aByteArray2344[var56] = (var0.aByteArray2344[(var0.anIntArray2353[var35] + var55)]);
                                    var56--;
                                }
                                var0.anIntArray2353[var35] = var56 + 1;
                            }
                        }
                    }
                    var0.anIntArray2324[(var0.aByteArray2351[var1 & 0xff] & 0xff)]++;
                    anIntArray367[var47] = var0.aByteArray2351[var1 & 0xff] & 0xff;
                    var47++;
                    if(var45 == 0) {
                        var43++;
                        var45 = 50;
                        byte var54 = var0.aByteArray2354[var43];
                        var22 = var0.anIntArray2360[var54];
                        var23 = var0.anIntArrayArray2357[var54];
                        var25 = var0.anIntArrayArray2359[var54];
                        var24 = var0.anIntArrayArray2362[var54];
                    }
                    var45--;
                    var51 = var22;
                    for(var52 = method3142(var22, var0); var52 > var23[var51]; var52 = var52 << 1 | var53) {
                        var51++;
                        var53 = method3141(var0);
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
                            byte var54 = var0.aByteArray2354[var43];
                            var22 = var0.anIntArray2360[var54];
                            var23 = var0.anIntArrayArray2357[var54];
                            var25 = var0.anIntArrayArray2359[var54];
                            var24 = var0.anIntArrayArray2362[var54];
                        }
                        var45--;
                        var51 = var22;
                        for(var52 = method3142(var22, var0); var52 > var23[var51]; var52 = var52 << 1 | var53) {
                            var51++;
                            var53 = method3141(var0);
                        }
                        var46 = var25[var52 - var24[var51]];
                    } while(var46 == 0 || var46 == 1);
                    var48++;
                    var1 = (var0.aByteArray2351[(var0.aByteArray2344[var0.anIntArray2353[0]] & 0xff)]);
                    var0.anIntArray2324[var1 & 0xff] += var48;
                    for(/**/; var48 > 0; var48--) {
                        anIntArray367[var47] = var1 & 0xff;
                        var47++;
                    }
                }
            }
            var0.anInt2333 = 0;
            var0.aByte2355 = (byte) 0;
            var0.anIntArray2347[0] = 0;
            for(var36 = 1; var36 <= 256; var36++)
                var0.anIntArray2347[var36] = var0.anIntArray2324[var36 - 1];
            for(var36 = 1; var36 <= 256; var36++)
                var0.anIntArray2347[var36] += var0.anIntArray2347[var36 - 1];
            for(var36 = 0; var36 < var47; var36++) {
                var1 = (byte) (anIntArray367[var36] & 0xff);
                anIntArray367[var0.anIntArray2347[var1 & 0xff]] |= var36 << 8;
                var0.anIntArray2347[var1 & 0xff]++;
            }
            var0.anInt2343 = anIntArray367[var0.anInt2342] >> 8;
            var0.anInt2346 = 0;
            var0.anInt2343 = anIntArray367[var0.anInt2343];
            var0.anInt2348 = (byte) (var0.anInt2343 & 0xff);
            var0.anInt2343 >>= 8;
            var0.anInt2346++;
            var0.anInt2361 = var47;
            method3138(var0);
            if(var0.anInt2346 == var0.anInt2361 + 1 && var0.anInt2333 == 0)
                var26 = true;
            else
                var26 = false;
        }
    }

    static byte method3140(Class167 var0) {
        return (byte) method3142(8, var0);
    }

    static int method3142(int var0, Class167 var1) {
        while(var1.anInt2340 < var0) {
            var1.anInt2339 = (var1.anInt2339 << 8 | var1.aByteArray2330[var1.anInt2331] & 0xff);
            var1.anInt2340 += 8;
            var1.anInt2331++;
            var1.anInt2332++;
            if(var1.anInt2332 == 0) {
		        /* empty */
            }
        }
        int var3 = var1.anInt2339 >> var1.anInt2340 - var0 & (1 << var0) - 1;
        var1.anInt2340 -= var0;
        return var3;
    }

    static byte method3141(Class167 var0) {
        return (byte) method3142(1, var0);
    }

    static void method3146(Class167 var0) {
        var0.anInt2337 = 0;
        for(int var1 = 0; var1 < 256; var1++) {
            if(var0.aBoolArray2358[var1]) {
                var0.aByteArray2351[var0.anInt2337] = (byte) var1;
                var0.anInt2337++;
            }
        }
    }

    static void method3144(int[] var0, int[] var1, int[] var2, byte[] var3, int var4, int var5, int var6) {
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

    static void method3138(Class167 var0) {
        byte var2 = var0.aByte2355;
        int var3 = var0.anInt2333;
        int var4 = var0.anInt2346;
        int var5 = var0.anInt2348;
        int[] var6 = anIntArray367;
        int var7 = var0.anInt2343;
        byte[] var8 = var0.aByteArray2345;
        int var9 = var0.anInt2334;
        int var10 = var0.anInt2335;
        int var12 = var0.anInt2361 + 1;
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
        int var13 = var0.anInt2352;
        var0.anInt2352 += var10 - var10;
        if(var0.anInt2352 >= var13) {
	        /* empty */
        }
        var0.aByte2355 = var2;
        var0.anInt2333 = var3;
        var0.anInt2346 = var4;
        var0.anInt2348 = var5;
        anIntArray367 = var6;
        var0.anInt2343 = var7;
        var0.aByteArray2345 = var8;
        var0.anInt2334 = var9;
        var0.anInt2335 = var10;
    }

    private static final class Class167 {

        int anInt2331 = 0;
        int anInt2334 = 0;
        int[] anIntArray2324 = new int[256];
        int[] anIntArray2347 = new int[257];
        boolean[] aBoolArray2358 = new boolean[256];
        boolean[] aBoolArray2336 = new boolean[16];
        byte[] aByteArray2351 = new byte[256];
        byte[] aByteArray2344 = new byte[4096];
        byte[] aByteArray2330;
        int[] anIntArray2353 = new int[16];
        byte[] aByteArray2354 = new byte[18002];
        byte[] aByteArray2326 = new byte[18002];
        byte[] aByteArray2345;
        byte[][] aByteArrayArray2356 = new byte[6][258];
        int[][] anIntArrayArray2357 = new int[6][258];
        int[][] anIntArrayArray2362 = new int[6][258];
        int[][] anIntArrayArray2359 = new int[6][258];
        int anInt2335;
        int[] anIntArray2360 = new int[6];
        int anInt2340;
        int anInt2339;
        int anInt2332;
        int anInt2352;
        int anInt2341;
        int anInt2342;
        int anInt2337;
        int anInt2333;
        byte aByte2355;
        int anInt2343;
        int anInt2346;
        int anInt2348;
        int anInt2361;

    }

}