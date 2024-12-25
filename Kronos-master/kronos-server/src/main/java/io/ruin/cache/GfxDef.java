package io.ruin.cache;

import io.ruin.Server;
import io.ruin.api.buffer.InBuffer;
import io.ruin.api.filestore.IndexFile;

public class GfxDef {

    public static GfxDef[] LOADED;

    public static void load() {
        IndexFile index = Server.fileStore.get(2);
        LOADED = new GfxDef[index.getLastFileId(13) + 1];
        for(int id = 0; id < LOADED.length; id++) {
            byte[] data = index.getFile(13, id);
            GfxDef def = new GfxDef();
            def.id = id;
            def.method4211(new InBuffer(data));
            LOADED[id] = def;
        }
    }

    public static GfxDef get(int id) {
        if(id < 0 || id >= LOADED.length)
            return null;
        return LOADED[id];
    }

    /**
     * Cache data
     */

    public int id;
    public int animationId = -1;
    public short[] aShortArray3331;
    public int modelId;
    public short[] aShortArray3337;
    public short[] aShortArray3338;
    public int anInt3339 = 0;
    public int anInt3341 = 128;
    public int anInt3342 = 128;
    public int anInt3343 = 0;
    public int anInt3344 = 0;
    public short[] aShortArray3347;

    void method4211(InBuffer var1) {
        for(; ; ) {
            int var3 = var1.readUnsignedByte();
            if(var3 == 0)
                break;
            method4212(var1, var3);
        }
    }

    void method4212(InBuffer var1, int var2) {
        if(var2 == 1)
            modelId = var1.readUnsignedShort();
        else if(var2 == 2)
            animationId = var1.readUnsignedShort();
        else if(var2 == 4)
            anInt3341 = var1.readUnsignedShort();
        else if(var2 == 5)
            anInt3342 = var1.readUnsignedShort();
        else if(var2 == 6)
            anInt3343 = var1.readUnsignedShort();
        else if(var2 == 7)
            anInt3344 = var1.readUnsignedByte();
        else if(var2 == 8)
            anInt3339 = var1.readUnsignedByte();
        else if(var2 == 40) {
            int var4 = var1.readUnsignedByte();
            aShortArray3337 = new short[var4];
            aShortArray3338 = new short[var4];
            for(int var5 = 0; var5 < var4; var5++) {
                aShortArray3337[var5] = (short) var1.readUnsignedShort();
                aShortArray3338[var5] = (short) var1.readUnsignedShort();
            }
        } else if(var2 == 41) {
            int var4 = var1.readUnsignedByte();
            aShortArray3347 = new short[var4];
            aShortArray3331 = new short[var4];
            for(int var5 = 0; var5 < var4; var5++) {
                aShortArray3347[var5] = (short) var1.readUnsignedShort();
                aShortArray3331[var5] = (short) var1.readUnsignedShort();
            }
        }
    }

}
