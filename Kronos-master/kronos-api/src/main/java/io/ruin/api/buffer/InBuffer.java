package io.ruin.api.buffer;

import io.ruin.api.protocol.Protocol;

public class InBuffer {

    private int position;

    private byte[] payload;

    public InBuffer(byte[] payload) {
        if(payload == null)
            payload = new byte[0];
        this.payload = payload;
    }

    public void position(int position) {
        this.position = position;
    }

    public void skip(int skip) {
        position += skip;
    }

    public void readBytes(byte[] bytes) {
        readBytes(bytes, 0, bytes.length);
    }

    public void readBytes(byte[] bytes, int offset, int length) {
        for(int i = offset; i < offset + length; i++)
            bytes[i] = readByte();
    }

    public void skipByte() {
        position++;
    }

    public byte readByte() {
        if(position >= payload.length)
            return 0;
        return payload[position++];
    }

    public byte readByteUnsafe() {
        return payload[position++];
    }

    public int readUnsignedByte() {
        return readByte() & 0xff;
    }

    public byte readByteA() {
        return (byte) (readByte() - 128);
    }

    public int readUnsignedByteA() {
        return readByteA() & 0xff;
    }

    public byte readByteC() {
        return (byte) -readByte();
    }

    public int readUnsignedByteC() {
        return readByteC() & 0xff;
    }

    public int readByteS() {
        return (byte) (128 - readByte());
    }

    public int readUnsignedByteS() {
        return readByteS() & 0xff;
    }

    public int readShort() {
        int i = (readUnsignedByte() << 8) + readUnsignedByte();
        if(i > 32767) {
            i -= 0x10000;
        }
        return i;
    }

    public int readUnsignedShort() {
        return (readUnsignedByte() << 8) + readUnsignedByte();
    }

    public int readShortA() {
        int i = (readUnsignedByte() << 8) + readUnsignedByteA();
        if(i > 32767) {
            i -= 0x10000;
        }
        return i;
    }

    public int readUnsignedShortA() {
        return (readUnsignedByte() << 8) + readUnsignedByteA();
    }

    public int readLEShort() {
        int i = readUnsignedByte() + (readUnsignedByte() << 8);
        if(i > 32767) {
            i -= 0x10000;
        }
        return i;
    }

    public int readUnsignedLEShort() {
        return readUnsignedByte() + (readUnsignedByte() << 8);
    }

    public int readLEShortA() {
        int i = readUnsignedByteA() + (readUnsignedByte() << 8);
        if(i > 32767) {
            i -= 0x10000;
        }
        return i;
    }

    public int readUnsignedLEShortA() {
        return readUnsignedByteA() + (readUnsignedByte() << 8);
    }

    public int readMedium() {
        return (readUnsignedByte() << 16) + (readUnsignedByte() << 8) + readUnsignedByte();
    }

    public int readInt() {
        return (readUnsignedByte() << 24) + (readUnsignedByte() << 16) + (readUnsignedByte() << 8) + readUnsignedByte();
    }

    public int readInt1() {
        return (readUnsignedByte() << 8) + readUnsignedByte() + (readUnsignedByte() << 24) + (readUnsignedByte() << 16);
    }

    public int readInt2() {
        return (readUnsignedByte() << 16) + (readUnsignedByte() << 24) + readUnsignedByte() + (readUnsignedByte() << 8);
    }

    public int readLEInt() {
        return readUnsignedByte() + (readUnsignedByte() << 8) + (readUnsignedByte() << 16) + (readUnsignedByte() << 24);
    }



    public int readSmart() {
        int i = payload[position] & 0xff;
        if(i < 128)
            return readUnsignedByte();
        return readUnsignedShort() - 32768;
    }

    public int readSmart2() {
        int i = 0;
        int base = readSmart();
        while(base == 32767) {
            base = readSmart();
            i += 32767;
        }
        return i + base;
    }

    public int readSomeSmart() {
        int i = payload[position] & 0xff;
        if(i < 128)
            return readUnsignedByte() - 64;
        return readUnsignedShort() - 49152;
    }

    public int readBigSmart() {
        if(payload[position] >= 0)
            return readUnsignedShort();
        return readInt() & 0x7fffffff;
    }

    public long readLong() {
        long l1 = (long) readInt() & 0xffffffffL;
        long l2 = (long) readInt() & 0xffffffffL;
        return (l1 << 32) + l2;
    }

    public String readStringNew() {
        StringBuilder s = new StringBuilder();
        byte b;
        while((b = readByte()) != 0)
            s.append((char) b);
        return s.toString();
    }

    public String readSafeString() {
        if(payload[position] != 0)
            return readString();
        position++;
        return null;
    }

    public String readString() {
        StringBuilder s = new StringBuilder();
        int read;
        while((read = readByte()) != 0) {
            read &= 0xff;
            if(read >= 128 && read < 160) {
                int curChar = Protocol.aCharArray710[read - 128];
                if(curChar == 0)
                    curChar = 63;
                read = curChar;
            }
            s.append((char) read);
        }
        return s.toString();
    }

    public void decode(int[] keys, int offset, int length) {
        int var5 = position;
        position = offset;
        int var6 = (length - offset) / 8;
        for(int var7 = 0; var7 < var6; var7++) {
            int var8 = readInt();
            int var9 = readInt();
            int var10 = -957401312;
            int var11 = -1640531527;
            int var12 = 32;
            while(var12-- > 0) {
                var9 -= (var8 + (var8 << 4 ^ var8 >>> 5) ^ keys[var10 >>> 11 & 0x3] + var10);
                var10 -= var11;
                var8 -= (var9 + (var9 << 4 ^ var9 >>> 5) ^ var10 + keys[var10 & 0x3]);
            }
            position -= 8;
            //addInt
            payload[position++] = (byte) (var8 >> 24);
            payload[position++] = (byte) (var8 >> 16);
            payload[position++] = (byte) (var8 >> 8);
            payload[position++] = (byte) var8;
            //addInt
            payload[position++] = (byte) (var9 >> 24);
            payload[position++] = (byte) (var9 >> 16);
            payload[position++] = (byte) (var9 >> 8);
            payload[position++] = (byte) var9;
        }
        position = var5;
    }

    public int remaining() {
        return payload.length - position;
    }

    public int position() {
        return position;
    }

    public byte[] getPayload() {
        return payload;
    }

}
