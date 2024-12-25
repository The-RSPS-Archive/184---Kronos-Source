package io.ruin;

import com.google.gson.annotations.Expose;
import io.ruin.api.database.Database;
import io.ruin.api.database.DatabaseUtils;
import io.ruin.api.filestore.FileStore;
import io.ruin.cache.ItemDef;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.PreparedStatement;
import java.util.Arrays;

import static io.ruin.api.utils.JsonUtils.GSON;

public class Test {

    private static class XTEA {
        @Expose int region;
        @Expose int[] keys;
    }

    public int getProtectValue(int itemId) {
        ItemDef def = ItemDef.get(itemId);
        return def == null ? 0 : def.protectValue;
    }

    public static void main(String[] args) throws Exception {
    }

    private static void xteaFileToDB() throws Exception {
        XTEA[] xteas = GSON.fromJson(new FileReader("C:/users/edu/desktop/xtea.txt"), XTEA[].class);
        for (XTEA t : xteas) {
            System.out.println("Region " + t.region + ": " + Arrays.toString(t.keys));
        }
        System.out.println(xteas.length + " regions!");
        DatabaseUtils.connect(new Database[]{Server.dumpsDb}, errors -> {
            if(!errors.isEmpty()) {
                for(Throwable t : errors)
                    System.err.println(t.getMessage());
                System.exit(1);
            }
        });
        for (XTEA xtea : xteas) {
            Server.dumpsDb.executeAwait(connection -> {
                PreparedStatement st = connection.prepareStatement("INSERT INTO region_dumps (id, k1, k2, k3, k4) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE k1=?, k2=?, k3=?, k4=?;");
                st.setInt(1, xtea.region);
                st.setInt(2, xtea.keys[0]);
                st.setInt(3, xtea.keys[1]);
                st.setInt(4, xtea.keys[2]);
                st.setInt(5, xtea.keys[3]);
                st.setInt(6, xtea.keys[0]);
                st.setInt(7, xtea.keys[1]);
                st.setInt(8, xtea.keys[2]);
                st.setInt(9, xtea.keys[3]);
                st.executeUpdate();
            });
        }
        System.out.println("done");
    }

    private static int getTokkul(int wave) {
        return 2 + (wave * (3 + wave));
    }

    public static void main2(String[] args) throws Exception {
        if(true) {
            int baseId = 5;
            System.out.println(baseId & 0x3);
            return;
        }
        if(true) {
            try(BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.home") + "/Desktop/Schedule.txt"))) {
                String line;
                while((line = br.readLine()) != null) {
                    if((line = line.trim()).isEmpty() || line.startsWith("Date") || line.toLowerCase().contains("anderson"))
                        System.out.println(line);
                }
            }
            return;
        }
        if(true) {
            Server.fileStore = new FileStore(System.getProperty("user.home") + File.separator + "/jagexcache/vorkath/LIVE/");
            return;
        }
        int setting = 0;
        for(int i : new int[]{0, 1, 2, 3, 9})
            setting |= 2 << i;
        System.out.println(setting);
        //Region.exportKeys(System.getProperty("user.home") + File.separator + "/Desktop/keys_export/");
        //Region.importKeys("C:/Users/Jordan/Documents/RSPS/oldschool/keys_export");
        //Server.fileStore = new FileStore(System.getProperty("user.home") + File.separator + "/jagexcache/runite/LIVE/", false, false);
        //Region.validateKeys(false);    public static final int WEST = 0x42240000, EAST = 0x60240000, SOUTH = 0x40a40000, NORTH = 0x48240000, SOUTH_WEST = 0x43a40000, SOUTH_EAST = 0x60e40000, NORTH_WEST = 0x4e240000, NORTH_EAST = 0x78240000;
    }

}