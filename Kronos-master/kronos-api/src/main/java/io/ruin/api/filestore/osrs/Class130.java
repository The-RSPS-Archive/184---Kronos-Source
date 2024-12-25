package io.ruin.api.filestore.osrs;

public final class Class130 {

    public static byte[] method2512(Object var0, boolean var1) {
        if(var0 == null)
            return null;
        if(var0 instanceof byte[]) {
            byte[] var7 = (byte[]) var0;
            if(var1) {
                int var5 = var7.length;
                byte[] var6 = new byte[var5];
                System.arraycopy(var7, 0, var6, 0, var5);
                return var6;
            }
            return var7;
        }
        if(var0 instanceof Class172) {
            Class172 var3 = (Class172) var0;
            return var3.getBytes();
        }
        throw new IllegalArgumentException();
    }

}