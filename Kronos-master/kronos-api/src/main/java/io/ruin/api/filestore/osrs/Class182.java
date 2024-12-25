package io.ruin.api.filestore.osrs;

public class Class182 {

    int[] anIntArray2435;

    public Class182(int[] var1) {
        int var2;
        for(var2 = 1; var2 <= (var1.length >> 1) + var1.length; var2 <<= 1) {
            /* empty */
        }
        anIntArray2435 = new int[var2 + var2];
        for(int var3 = 0; var3 < var2 + var2; var3++)
            anIntArray2435[var3] = -1;
        int var3 = 0;
        while(var3 < var1.length) {
            int var4;
            for(var4 = var1[var3] & var2 - 1; anIntArray2435[var4 + var4 + 1] != -1; var4 = var4 + 1 & var2 - 1) {
		        /* empty */
            }
            anIntArray2435[var4 + var4] = var1[var3];
            anIntArray2435[var4 + var4 + 1] = var3++;
        }
    }

    public int method3522(int var1) {
        int var2 = (anIntArray2435.length >> 1) - 1;
        int var3 = var1 & var2;
        for(; ; ) {
            int var4 = anIntArray2435[var3 + var3 + 1];
            if(var4 == -1)
                return -1;
            if(anIntArray2435[var3 + var3] == var1)
                return var4;
            var3 = var3 + 1 & var2;
        }
    }

}