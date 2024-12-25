package io.ruin.api.filestore.osrs;

import io.ruin.api.buffer.InBuffer;

public class Class166 {

    static final byte[] method3152(byte[] var0) {
        InBuffer var2 = new InBuffer(var0);
        int var3 = var2.readUnsignedByte();
        int var4 = var2.readInt();
        if(var4 >= 0 && (ReferenceTable.anInt3202 == 0 || var4 <= ReferenceTable.anInt3202)) {
            if(var3 == 0) {
                byte[] var5 = new byte[var4];
                var2.readBytes(var5, 0, var4);
                return var5;
            }
            int var7 = var2.readInt();
            if(var7 >= 0 && (ReferenceTable.anInt3202 == 0 || var7 <= ReferenceTable.anInt3202)) {
                byte[] var6 = new byte[var7];
                if(var3 == 1)
                    BZIP.method3124(var6, var7, var0, var4, 9);
                else
                    ReferenceTable.aClass162_3208.method3122(var2, var6);
                return var6;
            }
            throw new RuntimeException();
        }
        throw new RuntimeException();
    }

}