package io.ruin.cache;

import io.ruin.Server;
import io.ruin.api.buffer.InBuffer;
import io.ruin.api.filestore.IndexFile;

public class IdentityKit {

    /**
     * Storage
     */

    public static IdentityKit[] LOADED;

    public static void load() {
        IndexFile index = Server.fileStore.get(2);
        LOADED = new IdentityKit[index.getLastFileId(3) + 1];
        for(int id = 0; id < LOADED.length; id++) {
            byte[] data = index.getFile(3, id);
            IdentityKit def = new IdentityKit();
            def.id = id;
            def.decode(new InBuffer(data));
            LOADED[id] = def;
        }
    }

    public static IdentityKit get(int id) {
        if(id < 0 || id >= LOADED.length)
            return null;
        return LOADED[id];
    }

    /**
     * Default
     */

    public int id;
    public int anInt405 = -1;
    public boolean aBool53 = false;
    int[] anIntArray84;
    short[] aShortArray3;
    short[] aShortArray2;
    short[] aShortArray4;
    short[] aShortArray5;
    int[] anIntArray85 = {-1, -1, -1, -1, -1};

    void decode(InBuffer stream) {
        for(; ; ) {
            int i = stream.readUnsignedByte();
            if(i == 0)
                break;
            method707(stream, i);
        }
    }

    void method707(InBuffer stream, int i) {
        if(i == 1)
            anInt405 = stream.readUnsignedByte();
        else if(i == 2) {
            int i_7_ = stream.readUnsignedByte();
            anIntArray84 = new int[i_7_];
            for(int i_8_ = 0; i_8_ < i_7_; i_8_++)
                anIntArray84[i_8_] = stream.readUnsignedShort();
        } else if(i == 3)
            aBool53 = true;
        else if(i == 40) {
            int i_9_ = stream.readUnsignedByte();
            aShortArray3 = new short[i_9_];
            aShortArray2 = new short[i_9_];
            for(int i_10_ = 0; i_10_ < i_9_; i_10_++) {
                aShortArray3[i_10_] = (short) stream.readUnsignedShort();
                aShortArray2[i_10_] = (short) stream.readUnsignedShort();
            }
        } else if(i == 41) {
            int i_11_ = stream.readUnsignedByte();
            aShortArray4 = new short[i_11_];
            aShortArray5 = new short[i_11_];
            for(int i_12_ = 0; i_12_ < i_11_; i_12_++) {
                aShortArray4[i_12_] = (short) stream.readUnsignedShort();
                aShortArray5[i_12_] = (short) stream.readUnsignedShort();
            }
        } else if(i >= 60 && i < 70)
            anIntArray85[i - 60] = stream.readUnsignedShort();
    }

}
