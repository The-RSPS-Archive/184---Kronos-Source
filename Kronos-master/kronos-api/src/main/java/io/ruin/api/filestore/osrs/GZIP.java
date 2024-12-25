package io.ruin.api.filestore.osrs;

import io.ruin.api.buffer.InBuffer;

import java.util.zip.Inflater;

public class GZIP {

    Inflater anInflater2311;

    public void method3122(InBuffer var1, byte[] var2) {
        if(var1.getPayload()[var1.position()] != 31 || var1.getPayload()[var1.position() + 1] != -117)
            throw new RuntimeException("Invalid GZIP header!");
        if(anInflater2311 == null)
            anInflater2311 = new Inflater(true);
        try {
            anInflater2311.setInput(var1.getPayload(), var1.position() + 10, (var1.getPayload().length - (var1.position() + 8 + 10)));
            anInflater2311.inflate(var2);
        } catch(Exception var5) {
            anInflater2311.reset();
            throw new RuntimeException("");
        }
        anInflater2311.reset();
    }

}