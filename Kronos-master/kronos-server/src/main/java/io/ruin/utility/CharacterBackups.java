package io.ruin.utility;

import io.ruin.api.protocol.world.WorldStage;
import io.ruin.model.World;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author ReverendDread on 6/27/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
@Slf4j
public class CharacterBackups {

    private static final String BACKUP_PATH = System.getProperty("user.home") + "/Desktop/Kronos/backups/";
    private static String CHARACTER_SAVES_PATH;
    private static final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    private ScheduledFuture<?> backup;
    private final long backup_period = TimeUnit.MINUTES.toMillis(15);

    public void start() {
        if (World.stage == WorldStage.LIVE) {
            backup = service.scheduleAtFixedRate(this::backup, backup_period, backup_period, TimeUnit.MILLISECONDS);
            CHARACTER_SAVES_PATH = System.getProperty("user.home") + "/Desktop/Kronos/_saved/players/" + World.stage.name().toLowerCase() + "/" + World.type.name().toLowerCase() + "/";
        }
    }

    @SneakyThrows
    public void backup() {

        try {
            log.info("Performing character backup...");

            File folder = new File(BACKUP_PATH);

            if (!folder.exists())
                folder.mkdirs();

            File backup = new File(folder, "players-" + DateFormatUtils.format(System.currentTimeMillis(), "MM-dd-yyyy HH-mm-ss") + ".zip");

            FileOutputStream fos = new FileOutputStream(backup);
            ZipOutputStream zos = new ZipOutputStream(fos);

            File characters = new File(CHARACTER_SAVES_PATH);
            File[] saves = characters.listFiles();

            assert saves != null;

            for (File file : saves) {
                writeSaveToZip(file, zos);
            }

            zos.close();

            log.info("Successfully backed up {} character file(s).", saves.length);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void writeSaveToZip(File file, ZipOutputStream zos) {
        try {

            //Create entry
            ZipEntry zipEntry = new ZipEntry(file.getName());
            zos.putNextEntry(zipEntry);

            //Write the data to the entry
            byte[] data = Files.readAllBytes(file.toPath());
            zos.write(data, 0, data.length);
            zos.closeEntry();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
