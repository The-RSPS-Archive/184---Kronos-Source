package io.ruin.api.protocol;

import io.ruin.api.buffer.OutBuffer;
import io.ruin.api.filestore.utility.Huffman;

public class Protocol {

    public static final int CLIENT_BUILD = 171;

    public static char[] aCharArray710 = {'\u20ac', '\0', '\u201a', '\u0192', '\u201e', '\u2026', '\u2020', '\u2021', '\u02c6', '\u2030', '\u0160', '\u2039', '\u0152', '\0', '\u017d', '\0', '\0', '\u2018', '\u2019', '\u201c', '\u201d', '\u2022', '\u2013', '\u2014', '\u02dc', '\u2122', '\u0161', '\u203a', '\u0153', '\0', '\u017e', '\u0178'};

    static final char[] staticCharArray2 = {' ', '\u00a0', '_', '-', '\u00e0', '\u00e1', '\u00e2', '\u00e4', '\u00e3', '\u00c0', '\u00c1', '\u00c2', '\u00c4', '\u00c3', '\u00e8', '\u00e9', '\u00ea', '\u00eb', '\u00c8', '\u00c9', '\u00ca', '\u00cb', '\u00ed', '\u00ee', '\u00ef', '\u00cd', '\u00ce', '\u00cf', '\u00f2', '\u00f3', '\u00f4', '\u00f6', '\u00f5', '\u00d2', '\u00d3', '\u00d4', '\u00d6', '\u00d5', '\u00f9', '\u00fa', '\u00fb', '\u00fc', '\u00d9', '\u00da', '\u00db', '\u00dc', '\u00e7', '\u00c7', '\u00ff', '\u0178', '\u00f1', '\u00d1', '\u00df'};
    static final char[] staticCharArray3 = {'[', ']', '#'};

    public static String method360(CharSequence charsequence) {
        if(charsequence == null)
            return null;
        int i = 0;
        int i_0_;
        for(i_0_ = charsequence.length(); i < i_0_; i++) {
            char c = charsequence.charAt(i);
            boolean bool = c == '\u00a0' || c == ' ' || c == '_' || c == '-';
            if(!bool)
                break;
        }
        for(/**/; i_0_ > i; i_0_--) {
            char c = charsequence.charAt(i_0_ - 1);
            boolean bool = c == '\u00a0' || c == ' ' || c == '_' || c == '-';
            if(!bool)
                break;
        }
        int i_1_ = i_0_ - i;
        if(i_1_ >= 1) {
            /*
            int i_2_;
            if(class66 == null)
                i_2_ = 12;
            else {
                switch(class66.anInt209) {
                    case 8:
                        i_2_ = 20;
                        break;
                    default:
                        i_2_ = 12;
                }
            }
            */
            int maxLength = 12;
            if(i_1_ <= maxLength) {
                StringBuilder stringbuilder = new StringBuilder(i_1_);
                for(int i_3_ = i; i_3_ < i_0_; i_3_++) {
                    char c = charsequence.charAt(i_3_);
                    if(method336(c)) {
                        char c_4_;
                        switch(c) {
                            case ' ':
                            case '-':
                            case '_':
                            case '\u00a0':
                                c_4_ = '_';
                                break;
                            case '#':
                            case '[':
                            case ']':
                                c_4_ = c;
                                break;
                            case '\u00c0':
                            case '\u00c1':
                            case '\u00c2':
                            case '\u00c3':
                            case '\u00c4':
                            case '\u00e0':
                            case '\u00e1':
                            case '\u00e2':
                            case '\u00e3':
                            case '\u00e4':
                                c_4_ = 'a';
                                break;
                            case '\u00c7':
                            case '\u00e7':
                                c_4_ = 'c';
                                break;
                            case '\u00c8':
                            case '\u00c9':
                            case '\u00ca':
                            case '\u00cb':
                            case '\u00e8':
                            case '\u00e9':
                            case '\u00ea':
                            case '\u00eb':
                                c_4_ = 'e';
                                break;
                            case '\u00cd':
                            case '\u00ce':
                            case '\u00cf':
                            case '\u00ed':
                            case '\u00ee':
                            case '\u00ef':
                                c_4_ = 'i';
                                break;
                            case '\u00d1':
                            case '\u00f1':
                                c_4_ = 'n';
                                break;
                            case '\u00d2':
                            case '\u00d3':
                            case '\u00d4':
                            case '\u00d5':
                            case '\u00d6':
                            case '\u00f2':
                            case '\u00f3':
                            case '\u00f4':
                            case '\u00f5':
                            case '\u00f6':
                                c_4_ = 'o';
                                break;
                            case '\u00d9':
                            case '\u00da':
                            case '\u00db':
                            case '\u00dc':
                            case '\u00f9':
                            case '\u00fa':
                            case '\u00fb':
                            case '\u00fc':
                                c_4_ = 'u';
                                break;
                            case '\u00df':
                                c_4_ = 'b';
                                break;
                            case '\u00ff':
                            case '\u0178':
                                c_4_ = 'y';
                                break;
                            default:
                                c_4_ = Character.toLowerCase(c);
                        }
                        if(c_4_ != 0)
                            stringbuilder.append(c_4_);
                    }
                }
                if(stringbuilder.length() == 0)
                    return null;
                return stringbuilder.toString();
            }
        }
        return null;
    }

    static final boolean method336(char c) {
        if(Character.isISOControl(c))
            return false;
        if(method561(c))
            return true;
        char[] cs = staticCharArray2;
        for(int i = 0; i < cs.length; i++) {
            char c_0_ = cs[i];
            if(c_0_ == c)
                return true;
        }
        cs = staticCharArray3;
        for(int i = 0; i < cs.length; i++) {
            char c_1_ = cs[i];
            if(c == c_1_)
                return true;
        }
        return false;
    }

    public static boolean method561(char c) {
        return (c >= '0' && c <= '9' || c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z');
    }

    public static int strLen(String string) {
        return string == null ? 0 : string.length() + 1;
    }

    /**
     * Shared packets
     */

    public static OutBuffer messagePacket(String message, String extension, int type) {
        int estimatedSize = 4 + Protocol.strLen(extension) + Protocol.strLen(message);
        OutBuffer out = new OutBuffer(estimatedSize).sendVarBytePacket(62)
                .addSmart(type);
        if(extension == null) {
            out.addByte(0);
        } else {
            out.addByte(1);
            out.addString(extension);
        }
        out.addString(message);
        return out;
    }

    public static OutBuffer outgoingPm(String toUsername, String message) {
        OutBuffer out = new OutBuffer(255).sendVarShortPacket(82)
                .addString(toUsername);
        Huffman.encrypt(out, message);
        return out;
    }

}
