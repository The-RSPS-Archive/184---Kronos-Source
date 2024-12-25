package io.ruin.api.filestore.osrs;

public class NameHash {

    public static int method3988(CharSequence var0) {
        int var2 = var0.length();
        int var3 = 0;
        for(int var4 = 0; var4 < var2; var4++)
            var3 = (var3 << 5) - var3 + method1738(var0.charAt(var4));
        return var3;
    }

    public static byte method1738(char var0) {
        byte var2;
        if((var0 <= 0 || var0 >= '\u0080') && (var0 < '\u00a0' || var0 > '\u00ff')) {
            if(var0 == '\u20ac')
                var2 = (byte) -128;
            else if(var0 == '\u201a')
                var2 = (byte) -126;
            else if(var0 == '\u0192')
                var2 = (byte) -125;
            else if(var0 == '\u201e')
                var2 = (byte) -124;
            else if(var0 == '\u2026')
                var2 = (byte) -123;
            else if(var0 == '\u2020')
                var2 = (byte) -122;
            else if(var0 == '\u2021')
                var2 = (byte) -121;
            else if(var0 == '\u02c6')
                var2 = (byte) -120;
            else if(var0 == '\u2030')
                var2 = (byte) -119;
            else if(var0 == '\u0160')
                var2 = (byte) -118;
            else if(var0 == '\u2039')
                var2 = (byte) -117;
            else if(var0 == '\u0152')
                var2 = (byte) -116;
            else if(var0 == '\u017d')
                var2 = (byte) -114;
            else if(var0 == '\u2018')
                var2 = (byte) -111;
            else if(var0 == '\u2019')
                var2 = (byte) -110;
            else if(var0 == '\u201c')
                var2 = (byte) -109;
            else if(var0 == '\u201d')
                var2 = (byte) -108;
            else if(var0 == '\u2022')
                var2 = (byte) -107;
            else if(var0 == '\u2013')
                var2 = (byte) -106;
            else if(var0 == '\u2014')
                var2 = (byte) -105;
            else if(var0 == '\u02dc')
                var2 = (byte) -104;
            else if(var0 == '\u2122')
                var2 = (byte) -103;
            else if(var0 == '\u0161')
                var2 = (byte) -102;
            else if(var0 == '\u203a')
                var2 = (byte) -101;
            else if(var0 == '\u0153')
                var2 = (byte) -100;
            else if(var0 == '\u017e')
                var2 = (byte) -98;
            else if(var0 == '\u0178')
                var2 = (byte) -97;
            else
                var2 = (byte) 63;
        } else
            var2 = (byte) var0;
        return var2;
    }

}