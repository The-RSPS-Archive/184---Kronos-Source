package io.ruin.api.filestore;

import io.ruin.api.buffer.InBuffer;
import io.ruin.api.filestore.utility.BZIP;
import io.ruin.api.filestore.utility.GZIP;

public class Archive {

    private static final GZIP gzip = new GZIP();

    protected int nameHash;
    protected int crc;
    protected int revision;
    protected int fileCount;
    protected int[] fileIds;
    protected File[] files;

    protected static byte[] decompress(byte[] archive, int[] keys) {
        InBuffer in = new InBuffer(archive);
        if(keys != null) {
            if(keys.length != 4)
                throw new RuntimeException("Invalid keys length!");
            in.decode(keys, 5, archive.length);
        }
        int compression = in.readUnsignedByte();
        int compressedLength = in.readInt();
        //todo - probably a check of this length like the check below
        if(compression == 0) {
            /**
             * Uncompressed
             */
            byte[] data = new byte[compressedLength];
            in.readBytes(data, 0, compressedLength);
            return data;
        }
        int length = in.readInt();
        if(length < 0 || length > 100000000) //todo - make sure this length is valid for osrs
            throw new RuntimeException("Invalid archive length!");
        byte[] data = new byte[length];
        if(compression != 1) {
            /**
             * Gzip
             */
            gzip.decompress(in, data);
        } else {
            /**
             * Bzip
             */
            BZIP.method3143(data, length, archive, compressedLength, 9);
        }
        return data;
    }

    protected int lastFileId() {
        return files.length - 1;
    }

    protected static final class File {
        int nameHash = -1;
    }

}
