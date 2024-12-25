package io.ruin.api.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.ruin.api.utils.ISAACCipher;
import io.ruin.api.utils.ServerWrapper;

import java.util.Arrays;

public class OutBuffer {

    private static final byte FIXED_TYPE = 1, VAR_BYTE_TYPE = 2, VAR_SHORT_TYPE = 3, VAR_INT_TYPE = 4;

    private static final int[] BIT_MASK = new int[32];

    static {
        for(int i = 0; i < 32; i++)
            BIT_MASK[i] = (1 << i) - 1;
    }

    /**
     * Separator
     */

    private byte[] payload;

    public OutBuffer(int size) {
        this.payload = new byte[size];
    }
/*
    public byte[] toByteArray() {
        byte[] data = new byte[position];
        int length = Math.min(position, payload.length);
        for(int i = 0; i < length; i++)
            data[i] = payload[i];
        return data;
    }
*/

    public byte[] toByteArray() {
        byte[] bytes = new byte[position];
        System.arraycopy(payload, 0, bytes, 0, bytes.length);

        byte[] bytes2 = new byte[position];
        int length = Math.min(position, payload.length);
        for(int i = 0; i < length; i++)
            bytes2[i] = payload[i];
        if(!Arrays.equals(bytes, bytes2))
            ServerWrapper.logError("", new Throwable()); //I want to see if this happens lol..

        return bytes;
    }

    public ByteBuf toBuffer() {
        return Unpooled.wrappedBuffer(toByteArray());
    }

    public byte[] payload() {
        return payload;
    }

    /**
     * Position
     */

    private int position;

    public void position(int position) {
        this.position = position;
    }

    public OutBuffer skip(int skip) {
        resizeIfNeeded(position + skip);
        position += skip;
        return this;
    }

    public int position() {
        return position;
    }

    /**
     * Setting
     */

    public OutBuffer setByte(int position, int value) {
        resizeIfNeeded(position);
        payload[position] = (byte) value;
        return this;
    }

    public void resizeIfNeeded(int newLength) {
        if(newLength < payload.length)
            return;
        if(type == FIXED_TYPE) {
            int opcode = payload[0] & 0xff;
            System.err.println("Increase packet size for: " + opcode);
        }
        byte[] newBuffer = new byte[newLength * 2];
        System.arraycopy(payload, 0, newBuffer, 0, payload.length);
        payload = newBuffer;
    }

    /**
     * Encryption
     */

    private int startEncryptPosition = -1;

    private int stopEncryptPosition = -1;

    public OutBuffer startEncrypt() {
        startEncryptPosition = position;
        return this;
    }

    public OutBuffer stopEncrypt() {
        stopEncryptPosition = position;
        return this;
    }

    public void encrypt(ISAACCipher cipher) {
        int length = stopEncryptPosition == -1 ? position : stopEncryptPosition;
        for(int i = startEncryptPosition; i < length; i++)
            payload[i] += (byte) cipher.readKey();
        startEncryptPosition = stopEncryptPosition = -1;
    }

    /**
     * Packets
     */

    private int sizePosition;

    private int type;

    public OutBuffer sendFixedPacket(int opcode) {
        addByte(opcode);
        type = FIXED_TYPE;
        return this;
    }

    public OutBuffer sendVarBytePacket(int opcode) {
        addByte(opcode);
        type = VAR_BYTE_TYPE;
        sizePosition = position;
        addByte(0); //"byte" size
        return this;
    }

    public OutBuffer sendVarShortPacket(int opcode) {
        addByte(opcode);
        type = VAR_SHORT_TYPE;
        sizePosition = position;
        addShort(0); //"short" size
        return this;
    }

    public OutBuffer sendVarIntPacket(int opcode) {
        addByte(opcode);
        type = VAR_INT_TYPE;
        sizePosition = position;
        addInt(0); //"int" size
        return this;
    }

    private void debug(int opcode) {
        if (opcode != 53 && opcode != 4)
            System.out.println("Outgoing: " + opcode);
    }

    public OutBuffer encode(ISAACCipher cipher) {
        if(cipher != null)
            payload[0] += (byte) cipher.readKey();
        if(type == VAR_BYTE_TYPE) {
            int size = position - (sizePosition + 1);
            setByte(sizePosition, size);
        } else if(type == VAR_SHORT_TYPE) {
            int size = position - (sizePosition + 2);
            setByte(sizePosition, size >> 8);
            setByte(sizePosition + 1, size);
        } else if(type == VAR_INT_TYPE) {
            int size = position - (sizePosition + 4);
            setByte(sizePosition, size >> 24);
            setByte(sizePosition + 1, size >> 16);
            setByte(sizePosition + 2, size >> 8);
            setByte(sizePosition + 3, size);
        }
        if(startEncryptPosition != -1) {
            if(cipher == null)
                throw new RuntimeException("Packet cannot be encrypted without cipher!");
            encrypt(cipher);
        }
        return this;
    }

    /**
     * Add methods
     */

    public OutBuffer addBytes(byte[] bytes) {
        return addBytes(bytes, 0, bytes.length);
    }

    public OutBuffer addBytes184(byte[] bytes) {
        return addBytes184(bytes, bytes.length);
    }

    public OutBuffer addBytes(byte[] bytes, int offset, int length) {
        resizeIfNeeded(position + (length - offset));
        for(int i = offset; i < length; i++)
            payload[position++] = bytes[i];
        return this;
    }

    public OutBuffer addBytes184(byte[] bytes, int length) {
        resizeIfNeeded(position + (length));
        for(int i = 0; i < length; ++i)
            payload[position++] = (byte) (bytes[i] - 128);
        return this;
    }

    public OutBuffer addBytes184(byte[] bytes, int offset, int length) {
        resizeIfNeeded(position + (length - offset));
        for(int i = offset + length - 1; i >= offset; --i)
            payload[position++] = (byte) (bytes[i] - 128);
        return this;
    }

    public OutBuffer addByte(int b) {
        return setByte(position++, b);
    }

    public OutBuffer addByteA(int b) {
        addByte(b + 128);
        return this;
    }

    public OutBuffer addByteC(int b) {
        addByte(-b);
        return this;
    }

    public OutBuffer addByteS(int b) {
        addByte(128 - b);
        return this;
    }

    public OutBuffer addShort(int s) {
        addByte(s >> 8);
        addByte(s);
        return this;
    }

    public OutBuffer addShortA(int s) {
        addByte(s >> 8);
        addByte(s + 128);
        return this;
    }

    public OutBuffer addLEShort(int s) {
        addByte(s);
        addByte(s >> 8);
        return this;
    }

    public OutBuffer addLEShortA(int s) {
        addByte(s + 128);
        addByte(s >> 8);
        return this;
    }

    public OutBuffer addMedium(int i) {
        addByte(i >> 16);
        addByte(i >> 8);
        addByte(i);
        return this;
    }

    public OutBuffer add24BitInt(int i) {
        addByte(i >> 16);
        addByte(i >> 8);
        addByte(i);
        return this;
    }

    public OutBuffer addInt(int i) {
        addByte(i >> 24);
        addByte(i >> 16);
        addByte(i >> 8);
        addByte(i);
        return this;
    }

    public OutBuffer addInt1(int i) {
        addByte(i >> 8);
        addByte(i);
        addByte(i >> 24);
        addByte(i >> 16);
        return this;
    }

    public OutBuffer addInt2(int i) {
        addByte(i >> 16);
        addByte(i >> 24);
        addByte(i);
        addByte(i >> 8);
        return this;
    }

    public OutBuffer addLEInt(int i) {
        addByte(i);
        addByte(i >> 8);
        addByte(i >> 16);
        addByte(i >> 24);
        return this;
    }

    public OutBuffer addSmart(int i) {
        if(i >= 128)
            addShort(i + 32768);
        else
            addByte(i);
        return this;
    }

    public void addBigSmart(int i) {
        if(i >= Short.MAX_VALUE)
            addInt(i - Integer.MAX_VALUE - 1);
        else
            addShort(i >= 0 ? i : 32767);
    }

    public OutBuffer addLong(long l) {
        addByte((int) (l >> 56));
        addByte((int) (l >> 48));
        addByte((int) (l >> 40));
        addByte((int) (l >> 32));
        addByte((int) (l >> 24));
        addByte((int) (l >> 16));
        addByte((int) (l >> 8));
        addByte((int) l);
        return this;
    }

    public OutBuffer addString(String s) {
        byte[] bytes = s.getBytes();
        resizeIfNeeded(position + bytes.length + 1);
        for(byte b : bytes)
            payload[position++] = b;
        payload[position++] = 0;
        return this;
    }

    /**
     * Bit access
     */

    private int bitPosition;

    public OutBuffer initBitAccess() {
        bitPosition = position * 8;
        return this;
    }

    public OutBuffer addBits(int numBits, int value) {
        int bytePos = bitPosition >> 3;
        int bitOffset = 8 - (bitPosition & 7);
        bitPosition += numBits;
        for(; numBits > bitOffset; bitOffset = 8) {
            resizeIfNeeded(bytePos);
            payload[bytePos] &= ~BIT_MASK[bitOffset];
            payload[bytePos++] |= value >> numBits - bitOffset & BIT_MASK[bitOffset];
            numBits -= bitOffset;
        }
        resizeIfNeeded(bytePos);
        if(numBits == bitOffset) {
            payload[bytePos] &= ~BIT_MASK[bitOffset];
            payload[bytePos] |= value & BIT_MASK[bitOffset];
        } else {
            payload[bytePos] &= ~(BIT_MASK[numBits] << bitOffset - numBits);
            payload[bytePos] |= (value & BIT_MASK[numBits]) << bitOffset - numBits;
        }
        return this;
    }

    public OutBuffer finishBitAccess() {
        position = (bitPosition + 7) / 8;
        return this;
    }

}