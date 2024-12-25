package io.ruin.api.filestore.osrs;

import java.nio.ByteBuffer;

public class Class167 extends Class172 {

    ByteBuffer aByteBuffer2333;

    void setBytes(byte[] var1) {
        aByteBuffer2333 = ByteBuffer.allocateDirect(var1.length);
        aByteBuffer2333.position(0);
        aByteBuffer2333.put(var1);
    }

    byte[] getBytes() {
        byte[] var2 = new byte[aByteBuffer2333.capacity()];
        aByteBuffer2333.position(0);
        aByteBuffer2333.get(var2);
        return var2;
    }

}