package io.ruin.api.filestore.utility;

import io.ruin.api.buffer.InBuffer;

import java.util.zip.Inflater;

public class GZIP {

    private Inflater inflater;

    public void decompress(InBuffer stream, byte[] is) {
        if(stream.getPayload()[stream.position()] != 31 || stream.getPayload()[stream.position() + 1] != -117)
            throw new RuntimeException("Invalid GZIP header!");
        if(inflater == null)
            inflater = new Inflater(true);
        try {
            inflater.setInput(stream.getPayload(), stream.position() + 10, (stream.getPayload().length - (stream.position() + 8 + 10)));
            inflater.inflate(is);
        } catch(Exception e) {
            inflater.reset();
            throw new RuntimeException("Invalid GZIP compressed data!");
        }
        inflater.reset();
    }

}