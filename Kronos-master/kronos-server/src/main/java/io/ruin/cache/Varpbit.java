package io.ruin.cache;

import io.ruin.Server;
import io.ruin.api.buffer.InBuffer;
import io.ruin.api.filestore.IndexFile;

import java.util.ArrayList;
import java.util.HashMap;

public class Varpbit {

    public static Varpbit[] LOADED;

    public static void load() {
        IndexFile index = Server.fileStore.get(2);
        LOADED = new Varpbit[index.getLastFileId(14) + 1];
        HashMap<Integer, ArrayList<Varpbit>> varpMap = new HashMap<>();
        int highestVarpId = -1;
        for(int id = 0; id < LOADED.length; id++) {
            byte[] data = index.getFile(14, id);
            Varpbit bit = new Varpbit(id);
            bit.decode(new InBuffer(data));

            ArrayList<Varpbit> bits = varpMap.get(bit.varpId);
            if(bits == null)
                bits = new ArrayList<>();
            bits.add(bit);
            varpMap.put(bit.varpId, bits);

            if(bit.varpId > highestVarpId)
                highestVarpId = bit.varpId;

            LOADED[id] = bit;
        }
        Varp.LOADED = new Varp[highestVarpId + 1];
        varpMap.forEach((varpId, bits) -> new Varp(varpId, bits.toArray(new Varpbit[bits.size()])));
    }

    public static Varpbit get(int id) {
        if(id < 0 || id >= LOADED.length)
            return null;
        return LOADED[id];
    }

    /**
     * Separator
     */

    public int id;

    public int varpId;

    public int leastSigBit;

    public int mostSigBit;

    public Varpbit(int id) {
        this.id = id;
    }

    private void decode(InBuffer in) {
        int opcode;
        while((opcode = in.readUnsignedByte()) != 0)
            decode(in, opcode);
    }

    private void decode(InBuffer in, int i) {
        if(i == 1) {
            varpId = in.readUnsignedShort();
            leastSigBit = in.readUnsignedByte();
            mostSigBit = in.readUnsignedByte();
        }
    }

}
