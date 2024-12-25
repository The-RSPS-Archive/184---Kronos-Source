package io.ruin.api.filestore.osrs;

public class Class88 {

    static boolean aBool2402 = false;

    public static Object method1733(byte[] bytes, boolean var1) {
        if(bytes == null)
            return null;
        if(bytes.length > 136 && !aBool2402) {
            try {
                Class167 var3 = new Class167();
                var3.setBytes(bytes);
                return var3;
            } catch(Throwable var4) {
                aBool2402 = true;
            }
        }
        return bytes;
    }

}