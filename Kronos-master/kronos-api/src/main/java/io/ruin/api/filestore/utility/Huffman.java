package io.ruin.api.filestore.utility;

import io.ruin.api.buffer.InBuffer;
import io.ruin.api.buffer.OutBuffer;
import io.ruin.api.filestore.FileStore;
import io.ruin.api.filestore.IndexFile;

import java.util.Arrays;

public class Huffman {

    //from 113
    private static final byte[] DATA = {22, 22, 22, 22, 22, 22, 21, 22, 22, 20, 22, 22, 22, 21, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 3, 8, 22, 16, 22, 16, 17, 7, 13, 13, 13, 16, 7, 10, 6, 16, 10, 11, 12, 12, 12, 12, 13, 13, 14, 14, 11, 14, 19, 15, 17, 8, 11, 9, 10, 10, 10, 10, 11, 10, 9, 7, 12, 11, 10, 10, 9, 10, 10, 12, 10, 9, 8, 12, 12, 9, 14, 8, 12, 17, 16, 17, 22, 13, 21, 4, 7, 6, 5, 3, 6, 6, 5, 4, 10, 7, 5, 6, 4, 4, 6, 10, 5, 4, 4, 5, 7, 6, 10, 6, 10, 22, 19, 22, 14, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 21, 22, 21, 22, 22, 22, 21, 22, 22};

    public static void printData(FileStore fileStore) {
        IndexFile index = fileStore.get(10);
        int archiveId = index.getArchiveId("huffman");
        int fileId = index.getFileId("");
        byte[] data = index.getFile(archiveId, fileId);
        System.out.println(Arrays.toString(data).replace("[", "").replace("]", ""));
    }

    /**
     * Huffman
     */

    private static final Huffman instance = new Huffman(DATA);

    byte[] aByteArray732;
    int[] anIntArray733;
    int[] anIntArray734;

    private Huffman(byte[] huffmanData) {
        int i = huffmanData.length;
        anIntArray733 = new int[i];
        aByteArray732 = huffmanData;
        int[] is_0_ = new int[33];
        anIntArray734 = new int[8];
        int i_1_ = 0;
        for(int i_2_ = 0; i_2_ < i; i_2_++) {
            int i_3_ = huffmanData[i_2_];
            if(i_3_ != 0) {
                int i_4_ = 1 << 32 - i_3_;
                int i_5_ = is_0_[i_3_];
                anIntArray733[i_2_] = i_5_;
                int i_6_;
                if((i_5_ & i_4_) != 0)
                    i_6_ = is_0_[i_3_ - 1];
                else {
                    i_6_ = i_5_ | i_4_;
                    for(int i_7_ = i_3_ - 1; i_7_ >= 1; i_7_--) {
                        int i_8_ = is_0_[i_7_];
                        if(i_5_ != i_8_)
                            break;
                        int i_9_ = 1 << 32 - i_7_;
                        if((i_8_ & i_9_) != 0) {
                            is_0_[i_7_] = is_0_[i_7_ - 1];
                            break;
                        }
                        is_0_[i_7_] = i_8_ | i_9_;
                    }
                }
                is_0_[i_3_] = i_6_;
                for(int i_10_ = i_3_ + 1; i_10_ <= 32; i_10_++) {
                    if(i_5_ == is_0_[i_10_])
                        is_0_[i_10_] = i_6_;
                }
                int i_11_ = 0;
                for(int i_12_ = 0; i_12_ < i_3_; i_12_++) {
                    int i_13_ = -2147483648 >>> i_12_;
                    if((i_5_ & i_13_) != 0) {
                        if(anIntArray734[i_11_] == 0)
                            anIntArray734[i_11_] = i_1_;
                        i_11_ = anIntArray734[i_11_];
                    } else
                        i_11_++;
                    if(i_11_ >= anIntArray734.length) {
                        int[] is_14_ = new int[anIntArray734.length * 2];
                        for(int i_15_ = 0; i_15_ < anIntArray734.length; i_15_++)
                            is_14_[i_15_] = anIntArray734[i_15_];
                        anIntArray734 = is_14_;
                    }
                    i_13_ >>>= 1;
                }
                anIntArray734[i_11_] = i_2_ ^ 0xffffffff;
                if(i_11_ >= i_1_)
                    i_1_ = i_11_ + 1;
            }
        }
    }

    public int encrypt(byte[] is, int i, int i_20_, byte[] is_21_, int i_22_) {
        int i_23_ = 0;
        int i_24_ = i_22_ << 3;
        for(i_20_ += i; i < i_20_; i++) {
            int i_25_ = is[i] & 0xff;
            int i_26_ = anIntArray733[i_25_];
            int i_27_ = aByteArray732[i_25_];
            if(i_27_ == 0)
                throw new RuntimeException("");
            int i_28_ = i_24_ >> 3;
            int i_29_ = i_24_ & 0x7;
            i_23_ &= -i_29_ >> 31;
            int i_30_ = (i_29_ + i_27_ - 1 >> 3) + i_28_;
            i_29_ += 24;
            is_21_[i_28_] = (byte) (i_23_ |= i_26_ >>> i_29_);
            if(i_28_ < i_30_) {
                i_28_++;
                i_29_ -= 8;
                is_21_[i_28_] = (byte) (i_23_ = i_26_ >>> i_29_);
                if(i_28_ < i_30_) {
                    i_28_++;
                    i_29_ -= 8;
                    is_21_[i_28_] = (byte) (i_23_ = i_26_ >>> i_29_);
                    if(i_28_ < i_30_) {
                        i_28_++;
                        i_29_ -= 8;
                        is_21_[i_28_] = (byte) (i_23_ = i_26_ >>> i_29_);
                        if(i_28_ < i_30_) {
                            i_28_++;
                            i_29_ -= 8;
                            is_21_[i_28_] = (byte) (i_23_ = i_26_ << -i_29_);
                        }
                    }
                }
            }
            i_24_ += i_27_;
        }
        return (i_24_ + 7 >> 3) - i_22_;
    }

    public int decrypt(byte[] is, int i, byte[] is_31_, int i_32_, int i_33_) {
        if(i_33_ == 0)
            return 0;
        int i_34_ = 0;
        i_33_ += i_32_;
        int i_35_ = i;
        for(; ; ) {
            byte i_36_ = is[i_35_];
            if(i_36_ >= 0)
                i_34_++;
            else
                i_34_ = anIntArray734[i_34_];
            int i_37_;
            if((i_37_ = anIntArray734[i_34_]) < 0) {
                is_31_[i_32_++] = (byte) (i_37_ ^ 0xffffffff);
                if(i_32_ >= i_33_)
                    break;
                i_34_ = 0;
            }
            if((i_36_ & 0x40) != 0)
                i_34_ = anIntArray734[i_34_];
            else
                i_34_++;
            if((i_37_ = anIntArray734[i_34_]) < 0) {
                is_31_[i_32_++] = (byte) (i_37_ ^ 0xffffffff);
                if(i_32_ >= i_33_)
                    break;
                i_34_ = 0;
            }
            if((i_36_ & 0x20) != 0)
                i_34_ = anIntArray734[i_34_];
            else
                i_34_++;
            if((i_37_ = anIntArray734[i_34_]) < 0) {
                is_31_[i_32_++] = (byte) (i_37_ ^ 0xffffffff);
                if(i_32_ >= i_33_)
                    break;
                i_34_ = 0;
            }
            if((i_36_ & 0x10) != 0)
                i_34_ = anIntArray734[i_34_];
            else
                i_34_++;
            if((i_37_ = anIntArray734[i_34_]) < 0) {
                is_31_[i_32_++] = (byte) (i_37_ ^ 0xffffffff);
                if(i_32_ >= i_33_)
                    break;
                i_34_ = 0;
            }
            if((i_36_ & 0x8) != 0)
                i_34_ = anIntArray734[i_34_];
            else
                i_34_++;
            if((i_37_ = anIntArray734[i_34_]) < 0) {
                is_31_[i_32_++] = (byte) (i_37_ ^ 0xffffffff);
                if(i_32_ >= i_33_)
                    break;
                i_34_ = 0;
            }
            if((i_36_ & 0x4) != 0)
                i_34_ = anIntArray734[i_34_];
            else
                i_34_++;
            if((i_37_ = anIntArray734[i_34_]) < 0) {
                is_31_[i_32_++] = (byte) (i_37_ ^ 0xffffffff);
                if(i_32_ >= i_33_)
                    break;
                i_34_ = 0;
            }
            if((i_36_ & 0x2) != 0)
                i_34_ = anIntArray734[i_34_];
            else
                i_34_++;
            if((i_37_ = anIntArray734[i_34_]) < 0) {
                is_31_[i_32_++] = (byte) (i_37_ ^ 0xffffffff);
                if(i_32_ >= i_33_)
                    break;
                i_34_ = 0;
            }
            if((i_36_ & 0x1) != 0)
                i_34_ = anIntArray734[i_34_];
            else
                i_34_++;
            if((i_37_ = anIntArray734[i_34_]) < 0) {
                is_31_[i_32_++] = (byte) (i_37_ ^ 0xffffffff);
                if(i_32_ >= i_33_)
                    break;
                i_34_ = 0;
            }
            i_35_++;
        }
        return i_35_ + 1 - i;
    }

    /**
     * Static utils
     */

    static char[] aCharArray1146 = {'\u20ac', '\0', '\u201a', '\u0192', '\u201e', '\u2026', '\u2020', '\u2021', '\u02c6', '\u2030', '\u0160', '\u2039', '\u0152', '\0', '\u017d', '\0', '\0', '\u2018', '\u2019', '\u201c', '\u201d', '\u2022', '\u2013', '\u2014', '\u02dc', '\u2122', '\u0161', '\u203a', '\u0153', '\0', '\u017e', '\u0178'};

    public static int encrypt(OutBuffer out, String string) {
        int i = out.position();
        int i_0_ = string.length();
        byte[] is = new byte[i_0_];
        for(int i_1_ = 0; i_1_ < i_0_; i_1_++) {
            char c = string.charAt(i_1_);
            if((c <= 0 || c >= '\u0080') && (c < '\u00a0' || c > '\u00ff')) {
                if(c == '\u20ac')
                    is[i_1_] = (byte) -128;
                else if(c == '\u201a')
                    is[i_1_] = (byte) -126;
                else if(c == '\u0192')
                    is[i_1_] = (byte) -125;
                else if(c == '\u201e')
                    is[i_1_] = (byte) -124;
                else if(c == '\u2026')
                    is[i_1_] = (byte) -123;
                else if(c == '\u2020')
                    is[i_1_] = (byte) -122;
                else if(c == '\u2021')
                    is[i_1_] = (byte) -121;
                else if(c == '\u02c6')
                    is[i_1_] = (byte) -120;
                else if(c == '\u2030')
                    is[i_1_] = (byte) -119;
                else if(c != '\u0160') {
                    if(c == '\u2039')
                        is[i_1_] = (byte) -117;
                    else if(c == '\u0152')
                        is[i_1_] = (byte) -116;
                    else if(c == '\u017d')
                        is[i_1_] = (byte) -114;
                    else if(c == '\u2018')
                        is[i_1_] = (byte) -111;
                    else if(c == '\u2019')
                        is[i_1_] = (byte) -110;
                    else if(c == '\u201c')
                        is[i_1_] = (byte) -109;
                    else if(c == '\u201d')
                        is[i_1_] = (byte) -108;
                    else if(c == '\u2022')
                        is[i_1_] = (byte) -107;
                    else if(c == '\u2013')
                        is[i_1_] = (byte) -106;
                    else if(c == '\u2014')
                        is[i_1_] = (byte) -105;
                    else if(c == '\u02dc')
                        is[i_1_] = (byte) -104;
                    else if(c == '\u2122')
                        is[i_1_] = (byte) -103;
                    else if(c == '\u0161')
                        is[i_1_] = (byte) -102;
                    else if(c == '\u203a')
                        is[i_1_] = (byte) -101;
                    else if(c == '\u0153')
                        is[i_1_] = (byte) -100;
                    else if(c == '\u017e')
                        is[i_1_] = (byte) -98;
                    else if(c == '\u0178')
                        is[i_1_] = (byte) -97;
                    else
                        is[i_1_] = (byte) 63;
                } else
                    is[i_1_] = (byte) -118;
            } else
                is[i_1_] = (byte) c;
        }
        out.addSmart(is.length);
        out.resizeIfNeeded(out.position() + string.length() * 2); //added
        int length = instance.encrypt(is, 0, is.length, out.payload(), out.position());
        out.skip(length);
        return out.position() - i;
    }

    public static String decrypt(InBuffer in, int maxLength) {
        try {
            int i = in.readSmart();
            if(i > maxLength)
                i = maxLength;
            byte[] is = new byte[i];
            int length = instance.decrypt(in.getPayload(), in.position(), is, 0, i);
            in.skip(length);
            return method1296(is, 0, i);
        } catch(Exception exception) {
            return "Cabbage";
        }
    }

    public static String method1296(byte[] is, int i, int i_0_) {
        char[] cs = new char[i_0_];
        int i_1_ = 0;
        for(int i_2_ = 0; i_2_ < i_0_; i_2_++) {
            int i_3_ = is[i + i_2_] & 0xff;
            if(i_3_ != 0) {
                if(i_3_ >= 128 && i_3_ < 160) {
                    int i_4_ = aCharArray1146[i_3_ - 128];
                    if(i_4_ == 0)
                        i_4_ = 63;
                    i_3_ = i_4_;
                }
                cs[i_1_++] = (char) i_3_;
            }
        }
        return new String(cs, 0, i_1_);
    }
}
