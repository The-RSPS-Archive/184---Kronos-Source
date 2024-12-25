package io.ruin.api.utils;

public class ISAACCipher {

    int count;
    int anInt564;
    int anInt568;
    int anInt563;
    int[] mem = new int[256];
    int[] rsl = new int[256];

    public ISAACCipher(int[] is) {
        for(int i = 0; i < is.length; i++)
            rsl[i] = is[i];
        init();
    }

    final void init() {
        int i = -1640531527;
        int i_4_ = -1640531527;
        int i_5_ = -1640531527;
        int i_6_ = -1640531527;
        int i_7_ = -1640531527;
        int i_8_ = -1640531527;
        int i_9_ = -1640531527;
        int i_10_ = -1640531527;
        for(int i_11_ = 0; i_11_ < 4; i_11_++) {
            i_10_ ^= i_9_ << 11;
            i_7_ += i_10_;
            i_9_ += i_8_;
            i_9_ ^= i_8_ >>> 2;
            i_6_ += i_9_;
            i_8_ += i_7_;
            i_8_ ^= i_7_ << 8;
            i_5_ += i_8_;
            i_7_ += i_6_;
            i_7_ ^= i_6_ >>> 16;
            i_4_ += i_7_;
            i_6_ += i_5_;
            i_6_ ^= i_5_ << 10;
            i += i_6_;
            i_5_ += i_4_;
            i_5_ ^= i_4_ >>> 4;
            i_10_ += i_5_;
            i_4_ += i;
            i_4_ ^= i << 8;
            i_9_ += i_4_;
            i += i_10_;
            i ^= i_10_ >>> 9;
            i_8_ += i;
            i_10_ += i_9_;
        }
        for(int i_12_ = 0; i_12_ < 256; i_12_ += 8) {
            i_10_ += rsl[i_12_];
            i_9_ += rsl[i_12_ + 1];
            i_8_ += rsl[i_12_ + 2];
            i_7_ += rsl[i_12_ + 3];
            i_6_ += rsl[i_12_ + 4];
            i_5_ += rsl[i_12_ + 5];
            i_4_ += rsl[i_12_ + 6];
            i += rsl[i_12_ + 7];
            i_10_ ^= i_9_ << 11;
            i_7_ += i_10_;
            i_9_ += i_8_;
            i_9_ ^= i_8_ >>> 2;
            i_6_ += i_9_;
            i_8_ += i_7_;
            i_8_ ^= i_7_ << 8;
            i_5_ += i_8_;
            i_7_ += i_6_;
            i_7_ ^= i_6_ >>> 16;
            i_4_ += i_7_;
            i_6_ += i_5_;
            i_6_ ^= i_5_ << 10;
            i += i_6_;
            i_5_ += i_4_;
            i_5_ ^= i_4_ >>> 4;
            i_10_ += i_5_;
            i_4_ += i;
            i_4_ ^= i << 8;
            i_9_ += i_4_;
            i += i_10_;
            i ^= i_10_ >>> 9;
            i_8_ += i;
            i_10_ += i_9_;
            mem[i_12_] = i_10_;
            mem[i_12_ + 1] = i_9_;
            mem[i_12_ + 2] = i_8_;
            mem[i_12_ + 3] = i_7_;
            mem[i_12_ + 4] = i_6_;
            mem[i_12_ + 5] = i_5_;
            mem[i_12_ + 6] = i_4_;
            mem[i_12_ + 7] = i;
        }
        for(int i_13_ = 0; i_13_ < 256; i_13_ += 8) {
            i_10_ += mem[i_13_];
            i_9_ += mem[i_13_ + 1];
            i_8_ += mem[i_13_ + 2];
            i_7_ += mem[i_13_ + 3];
            i_6_ += mem[i_13_ + 4];
            i_5_ += mem[i_13_ + 5];
            i_4_ += mem[i_13_ + 6];
            i += mem[i_13_ + 7];
            i_10_ ^= i_9_ << 11;
            i_7_ += i_10_;
            i_9_ += i_8_;
            i_9_ ^= i_8_ >>> 2;
            i_6_ += i_9_;
            i_8_ += i_7_;
            i_8_ ^= i_7_ << 8;
            i_5_ += i_8_;
            i_7_ += i_6_;
            i_7_ ^= i_6_ >>> 16;
            i_4_ += i_7_;
            i_6_ += i_5_;
            i_6_ ^= i_5_ << 10;
            i += i_6_;
            i_5_ += i_4_;
            i_5_ ^= i_4_ >>> 4;
            i_10_ += i_5_;
            i_4_ += i;
            i_4_ ^= i << 8;
            i_9_ += i_4_;
            i += i_10_;
            i ^= i_10_ >>> 9;
            i_8_ += i;
            i_10_ += i_9_;
            mem[i_13_] = i_10_;
            mem[i_13_ + 1] = i_9_;
            mem[i_13_ + 2] = i_8_;
            mem[i_13_ + 3] = i_7_;
            mem[i_13_ + 4] = i_6_;
            mem[i_13_ + 5] = i_5_;
            mem[i_13_ + 6] = i_4_;
            mem[i_13_ + 7] = i;
        }
        isaac();
        count = 256;
    }

    public final int readKey() {
        if(--count + 1 == 0) {
            isaac();
            count = 255;
        }
        return rsl[count];
    }

    final void isaac() {
        anInt568 += ++anInt564;
        for(int i = 0; i < 256; i++) {
            int i_2_ = mem[i];
            if((i & 0x2) != 0) {
                if((i & 0x1) == 0)
                    anInt563 ^= anInt563 << 2;
                else
                    anInt563 ^= anInt563 >>> 16;
            } else if((i & 0x1) == 0)
                anInt563 ^= anInt563 << 13;
            else
                anInt563 ^= anInt563 >>> 6;
            anInt563 += mem[i + 128 & 0xff];
            int i_3_;
            mem[i] = i_3_ = anInt568 + anInt563 + mem[(i_2_ & 0x3fc) >> 2];
            rsl[i] = anInt568 = mem[(i_3_ >> 8 & 0x3fc) >> 2] + i_2_;
        }
    }

}
