package io.ruin.api.filestore.osrs;

import io.ruin.api.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileStore2 {

    public static final int FILE_COUNT = 17;

    public static File cacheFolder;

    public static Class121 mainCacheFile;

    public static Class121 mainCacheFile255;

    public static Class121[] cacheFiles;

    public FileStore2(String path) throws IOException {
        cacheFolder = FileUtils.get(path);
        mainCacheFile = new Class121(new Class122(method1134("main_file_cache.dat2"), "rw", 1048576000L), 5200, 0);
        mainCacheFile255 = (new Class121(new Class122(method1134("main_file_cache.idx255"), "rw", 1048576L), 6000, 0));
        cacheFiles = new Class121[FILE_COUNT];
        for(int i = 0; i < cacheFiles.length; i++)
            cacheFiles[i] = (new Class121(new Class122(method1134("main_file_cache.idx" + i), "rw", 1048576L), 6000, 0));
    }

    private static File method1134(String name) throws IOException {
        File file = new File(cacheFolder, name);
        try(RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            int idk = raf.read();
            raf.seek(0L);
            raf.write(idk);
            raf.seek(0L);
            raf.close();
            return file;
        }
    }

}
