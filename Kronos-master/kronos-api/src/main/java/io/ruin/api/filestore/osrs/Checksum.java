package io.ruin.api.filestore.osrs;

public final class Checksum {

    static int[] anIntArray2377 = new int[256];

    public static int get(byte[] bytes, int length) {
        int checksum = -1;
        for(int i = 0; i < length; i++)
            checksum = (checksum >>> 8 ^ anIntArray2377[(checksum ^ bytes[i]) & 0xff]);
        checksum ^= 0xffffffff;
        return checksum;
    }

    static {
        for(int i = 0; i < 256; i++) {
            int idk = i;
            for(int var2 = 0; var2 < 8; var2++) {
                if((idk & 0x1) == 1)
                    idk = idk >>> 1 ^ ~0x12477cdf;
                else
                    idk >>>= 1;
            }
            anIntArray2377[i] = idk;
        }
    }

}