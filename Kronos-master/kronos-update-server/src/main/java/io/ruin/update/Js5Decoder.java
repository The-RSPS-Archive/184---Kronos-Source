package io.ruin.update;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.util.ByteProcessor;
import io.ruin.api.buffer.InBuffer;
import io.ruin.api.buffer.OutBuffer;
import io.ruin.api.netty.MessageDecoder;
import io.ruin.api.utils.IPAddress;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.nio.ByteBuffer;

@Slf4j
public class Js5Decoder extends MessageDecoder<Channel> {

    private int encryptionValue;

    public Js5Decoder() {
        super(null, false);
    }

    @Override
    protected void handle(Channel channel, InBuffer in, int opcode) {
        if (opcode == 0 || opcode == 1) {
            int index = in.readUnsignedByte();
            int archiveId = in.readUnsignedShort();
            boolean priority = opcode == 1;
            try {

                OutBuffer fileBuffer = CacheManager.get(index, archiveId, priority);
                if(fileBuffer == null){
                    throw new FileNotFoundException();
                }
                byte[] data = fileBuffer.toByteArray();
                if(index == 255 && archiveId == 255) {

                    channel.writeAndFlush((Object) fileBuffer.toBuffer());
                } else {
                    if(encryptionValue != 0)
                        for(int dataIndex = 0;dataIndex<data.length;dataIndex++){
                            data[dataIndex] ^= encryptionValue;
                        }
                    channel.writeAndFlush((Object) Unpooled.wrappedBuffer(data));
                }

            }
            catch (Exception e) {
                System.err.println("Invalid File Request (" + index + "," + archiveId + "," + priority + ") from: " + IPAddress.get(channel));
                channel.close();
            }

            return;
        }
        if (opcode == 4) {
            this.encryptionValue = in.readUnsignedByte();
            return;
        }
    }

    @Override
    protected int getSize(int opcode) {
        switch (opcode) {
            case 0: 
            case 1: 
            case 2: 
            case 3: 
            case 4: {
                return 3;
            }
        }
        return -3;
    }
}

