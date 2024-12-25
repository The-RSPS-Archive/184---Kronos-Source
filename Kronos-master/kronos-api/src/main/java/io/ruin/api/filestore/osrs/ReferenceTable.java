package io.ruin.api.filestore.osrs;

import io.ruin.api.buffer.InBuffer;

public abstract class ReferenceTable {

    static GZIP aClass162_3208 = new GZIP();
    static int anInt3202 = 0;

    int[][] anIntArrayArray3203;
    int[] anIntArray3197;
    Object[] anObjectArray3200;
    Object[][] anObjectArrayArray3210;
    boolean aBool3207;
    Class182[] aClass182Array3205;
    Class182 aClass182_3199;
    int[] anIntArray3201;
    int[] anIntArray3196;
    public int anInt3209;
    boolean aBool3211;
    int[] anIntArray3198;
    int anInt3212;
    int[] anIntArray3206;
    int[][] anIntArrayArray3204;

    ReferenceTable(boolean var1, boolean var2) {
        aBool3207 = var1;
        aBool3211 = var2;
    }

    public byte[] getData(int archiveId, int fileId, int[] keys) {
        if(archiveId >= 0 && archiveId < anObjectArrayArray3210.length && anObjectArrayArray3210[archiveId] != null && fileId >= 0 && fileId < anObjectArrayArray3210[archiveId].length) {
            if(anObjectArrayArray3210[archiveId][fileId] == null) {
                boolean var5 = method4130(archiveId, keys);
                if(!var5) {
                    vmethod4212(archiveId);
                    var5 = method4130(archiveId, keys);
                    if(!var5)
                        return null;
                }
            }
            byte[] var6 = Class130.method2512(anObjectArrayArray3210[archiveId][fileId], false);
            if(aBool3211)
                anObjectArrayArray3210[archiveId][fileId] = null;
            return var6;
        }
        return null;
    }

    public int method4194() {
        return anObjectArrayArray3210.length;
    }

    public byte[] method4112(int var1, int var2) {
        if(var1 >= 0 && var1 < anObjectArrayArray3210.length && anObjectArrayArray3210[var1] != null && var2 >= 0 && var2 < anObjectArrayArray3210[var1].length) {
            if(anObjectArrayArray3210[var1][var2] == null) {
                boolean var4 = method4130(var1, null);
                if(!var4) {
                    vmethod4212(var1);
                    var4 = method4130(var1, null);
                    if(!var4)
                        return null;
                }
            }
            return Class130.method2512(anObjectArrayArray3210[var1][var2], false);
        }
        return null;
    }

    void vmethod4212(int var1) {
        /* empty */
    }

    public byte[] getData(int var1, int var2) {
        return getData(var1, var2, null);
    }

    public byte[] method4146(int var1) {
        if(anObjectArrayArray3210.length == 1)
            return method4112(0, var1);
        if(anObjectArrayArray3210[var1].length == 1)
            return method4112(var1, 0);
        throw new RuntimeException();
    }

    int vmethod4214(int var1) {
        return anObjectArray3200[var1] != null ? 100 : 0;
    }

    public int method4126(int var1) {
        return anObjectArrayArray3210[var1].length;
    }

    void method4164(byte[] var1) {
        anInt3209 = Checksum.get(var1, var1.length);
        InBuffer var3 = new InBuffer(Class166.method3152(var1));
        int var4 = var3.readUnsignedByte();
        if(var4 >= 5 && var4 <= 7) {
            if(var4 >= 6)
                var3.readInt();
            int var5 = var3.readUnsignedByte();
            if(var4 >= 7)
                anInt3212 = var3.readBigSmart();
            else
                anInt3212 = var3.readUnsignedShort();
            int var6 = 0;
            int var7 = -1;
            anIntArray3197 = new int[anInt3212];
            if(var4 >= 7) {
                for(int var8 = 0; var8 < anInt3212; var8++) {
                    anIntArray3197[var8] = var6 += var3.readBigSmart();
                    if(anIntArray3197[var8] > var7)
                        var7 = anIntArray3197[var8];
                }
            } else {
                for(int var8 = 0; var8 < anInt3212; var8++) {
                    anIntArray3197[var8] = var6 += var3.readUnsignedShort();
                    if(anIntArray3197[var8] > var7)
                        var7 = anIntArray3197[var8];
                }
            }
            anIntArray3198 = new int[var7 + 1];
            anIntArray3201 = new int[var7 + 1];
            anIntArray3196 = new int[var7 + 1];
            anIntArrayArray3203 = new int[var7 + 1][];
            anObjectArray3200 = new Object[var7 + 1];
            anObjectArrayArray3210 = new Object[var7 + 1][];
            if(var5 != 0) {
                anIntArray3206 = new int[var7 + 1];
                for(int var8 = 0; var8 < anInt3212; var8++)
                    anIntArray3206[anIntArray3197[var8]] = var3.readInt();
                aClass182_3199 = new Class182(anIntArray3206);
            }
            for(int var8 = 0; var8 < anInt3212; var8++)
                anIntArray3198[anIntArray3197[var8]] = var3.readInt();
            for(int var8 = 0; var8 < anInt3212; var8++)
                anIntArray3201[anIntArray3197[var8]] = var3.readInt();
            for(int var8 = 0; var8 < anInt3212; var8++)
                anIntArray3196[anIntArray3197[var8]] = var3.readUnsignedShort();
            if(var4 >= 7) {
                for(int var8 = 0; var8 < anInt3212; var8++) {
                    int var9 = anIntArray3197[var8];
                    int var10 = anIntArray3196[var9];
                    var6 = 0;
                    int var11 = -1;
                    anIntArrayArray3203[var9] = new int[var10];
                    for(int var12 = 0; var12 < var10; var12++) {
                        int var13 = (anIntArrayArray3203[var9][var12] = var6 += var3.readBigSmart());
                        if(var13 > var11)
                            var11 = var13;
                    }
                    anObjectArrayArray3210[var9] = new Object[var11 + 1];
                }
            } else {
                for(int var8 = 0; var8 < anInt3212; var8++) {
                    int var9 = anIntArray3197[var8];
                    int var10 = anIntArray3196[var9];
                    var6 = 0;
                    int var11 = -1;
                    anIntArrayArray3203[var9] = new int[var10];
                    for(int var12 = 0; var12 < var10; var12++) {
                        int var13 = (anIntArrayArray3203[var9][var12] = var6 += var3.readUnsignedShort());
                        if(var13 > var11)
                            var11 = var13;
                    }
                    anObjectArrayArray3210[var9] = new Object[var11 + 1];
                }
            }
            if(var5 != 0) {
                anIntArrayArray3204 = new int[var7 + 1][];
                aClass182Array3205 = new Class182[var7 + 1];
                for(int var8 = 0; var8 < anInt3212; var8++) {
                    int var9 = anIntArray3197[var8];
                    int var10 = anIntArray3196[var9];
                    anIntArrayArray3204[var9] = new int[anObjectArrayArray3210[var9].length];
                    for(int var11 = 0; var11 < var10; var11++)
                        anIntArrayArray3204[var9][(anIntArrayArray3203[var9][var11])] = var3.readInt();
                    aClass182Array3205[var9] = new Class182(anIntArrayArray3204[var9]);
                }
            }
        } else
            throw new RuntimeException("");
    }

    public boolean method4117(int var1, int var2) {
        if(var1 >= 0 && var1 < anObjectArrayArray3210.length && anObjectArrayArray3210[var1] != null && var2 >= 0 && var2 < anObjectArrayArray3210[var1].length) {
            if(anObjectArrayArray3210[var1][var2] != null)
                return true;
            if(anObjectArray3200[var1] != null)
                return true;
            vmethod4212(var1);
            return anObjectArray3200[var1] != null;
        }
        return false;
    }

    public boolean method4136(int var1) {
        if(anObjectArray3200[var1] != null)
            return true;
        vmethod4212(var1);
        return anObjectArray3200[var1] != null;
    }

    public boolean method4135(String var1, String var2) {
        var1 = var1.toLowerCase();
        var2 = var2.toLowerCase();
        int var4 = aClass182_3199.method3522(NameHash.method3988(var1));
        int var5 = aClass182Array3205[var4].method3522(NameHash.method3988(var2));
        return method4117(var4, var5);
    }

    void vmethod4209(int var1) {
	    /* empty */
    }

    public byte[] method4121(int var1) {
        if(anObjectArrayArray3210.length == 1)
            return getData(0, var1);
        if(anObjectArrayArray3210[var1].length == 1)
            return getData(var1, 0);
        throw new RuntimeException();
    }

    public int[] method4125(int var1) {
        return anIntArrayArray3203[var1];
    }

    public int method4127(String var1) {
        var1 = var1.toLowerCase();
        return aClass182_3199.method3522(NameHash.method3988(var1));
    }

    public boolean method4133(String var1, String var2) {
        var1 = var1.toLowerCase();
        var2 = var2.toLowerCase();
        int var4 = aClass182_3199.method3522(NameHash.method3988(var1));
        if(var4 < 0)
            return false;
        int var5 = aClass182Array3205[var4].method3522(NameHash.method3988(var2));
        return var5 >= 0;
    }

    boolean method4130(int var1, int[] keys) {
        if(anObjectArray3200[var1] == null)
            return false;
        int var4 = anIntArray3196[var1];
        int[] var5 = anIntArrayArray3203[var1];
        Object[] var6 = anObjectArrayArray3210[var1];
        boolean var7 = true;
        for(int var8 = 0; var8 < var4; var8++) {
            if(var6[var5[var8]] == null) {
                var7 = false;
                break;
            }
        }
        if(var7)
            return true;
        byte[] var19;
        if(keys != null && (keys[0] != 0 || keys[1] != 0 || keys[2] != 0 || keys[3] != 0)) {
            var19 = Class130.method2512(anObjectArray3200[var1], true);
            InBuffer var9 = new InBuffer(var19);
            var9.decode(keys, 5, var9.getPayload().length);
        } else
            var19 = Class130.method2512(anObjectArray3200[var1], false);
        byte[] var21 = Class166.method3152(var19);
        if(aBool3207)
            anObjectArray3200[var1] = null;
        if(var4 > 1) {
            int var10 = var21.length;
            var10--;
            int var11 = var21[var10] & 0xff;
            var10 -= var4 * var11 * 4;
            InBuffer var12 = new InBuffer(var21);
            int[] var13 = new int[var4];
            var12.position(var10);
            for(int var14 = 0; var14 < var11; var14++) {
                int var15 = 0;
                for(int var16 = 0; var16 < var4; var16++) {
                    var15 += var12.readInt();
                    var13[var16] += var15;
                }
            }
            byte[][] var20 = new byte[var4][];
            for(int var15 = 0; var15 < var4; var15++) {
                var20[var15] = new byte[var13[var15]];
                var13[var15] = 0;
            }
            var12.position(var10);
            int var15 = 0;
            for(int var16 = 0; var16 < var11; var16++) {
                int var17 = 0;
                for(int var18 = 0; var18 < var4; var18++) {
                    var17 += var12.readInt();
                    System.arraycopy(var21, var15, var20[var18], var13[var18], var17);
                    var13[var18] += var17;
                    var15 += var17;
                }
            }
            for(int var16 = 0; var16 < var4; var16++) {
                if(!aBool3211)
                    var6[var5[var16]] = Class88.method1733(var20[var16], false);
                else
                    var6[var5[var16]] = var20[var16];
            }
        } else if(!aBool3211)
            var6[var5[0]] = Class88.method1733(var21, false);
        else
            var6[var5[0]] = var21;
        return true;
    }

    public int method4132(int var1, String var2) {
        var2 = var2.toLowerCase();
        return aClass182Array3205[var1].method3522(NameHash.method3988(var2));
    }

    public int method4138(String var1) {
        var1 = var1.toLowerCase();
        int var3 = aClass182_3199.method3522(NameHash.method3988(var1));
        return vmethod4214(var3);
    }

    public byte[] method4144(String var1, String var2) {
        var1 = var1.toLowerCase();
        var2 = var2.toLowerCase();
        int var4 = aClass182_3199.method3522(NameHash.method3988(var1));
        int var5 = aClass182Array3205[var4].method3522(NameHash.method3988(var2));
        return getData(var4, var5);
    }

    public boolean method4131() {
        boolean var2 = true;
        for(int var3 = 0; var3 < anIntArray3197.length; var3++) {
            int var4 = anIntArray3197[var3];
            if(anObjectArray3200[var4] == null) {
                vmethod4212(var4);
                if(anObjectArray3200[var4] == null)
                    var2 = false;
            }
        }
        return var2;
    }

    public boolean method4168(String var1) {
        var1 = var1.toLowerCase();
        int var3 = aClass182_3199.method3522(NameHash.method3988(var1));
        return method4136(var3);
    }

    public void method4129() {
        for(int var2 = 0; var2 < anObjectArrayArray3210.length; var2++) {
            if(anObjectArrayArray3210[var2] != null) {
                for(int var3 = 0; var3 < anObjectArrayArray3210[var2].length; var3++)
                    anObjectArrayArray3210[var2][var3] = null;
            }
        }
    }

    public void method4160(int var1) {
        for(int var3 = 0; var3 < anObjectArrayArray3210[var1].length; var3++)
            anObjectArrayArray3210[var1][var3] = null;
    }

    public void method4137(String var1) {
        var1 = var1.toLowerCase();
        int var3 = aClass182_3199.method3522(NameHash.method3988(var1));
        if(var3 >= 0)
            vmethod4209(var3);
    }

}