package net.runelite.standalone;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class CustomUtils {


    private static class IconData {
        IconData(int id, byte[] data){
            this.id = id;
            this.data = data;
        }
        int id;
        byte[] data;
        int getId(){
            return id;
        }
    }
    /**
     * Icons
     */
    static void loadIcons(boolean display) throws Exception {
        List<IconData> iconData = Lists.newLinkedList();
//        List<File> files = Lists.newArrayList();
//        URI uri = CustomUtils.class.getResource("/img/icons/crowns/").toURI();
//        Path myPath;
//        if (uri.getScheme().equals("jar")) {
//            FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
//            myPath = fileSystem.getPath("/img/icons/crowns/");
//        } else {
//            myPath = Paths.get(uri);
//        }
//        Stream<Path> walk = Files.walk(myPath, 1);
//        for (Iterator<Path> it = walk.iterator(); it.hasNext();){
//            Path next = it.next();
//            if (next.getFileName().endsWith(".png")) {
//                files.add(it.next().toFile());
//            }
//        }
        try {
            for (int id = 16; id < 26; id++) { //16-25 ids
                //int id = Integer.parseInt(file.getName().replace(".png", "").trim());
                byte[] data = resourceToBytes("img/icons/crowns/" + id + ".png");
                if (data != null)
                    iconData.add(new IconData(id, data));
            }
        } catch (Exception ex) {
            System.err.println("Failed to read file name, please use an id as the name.");
            ex.printStackTrace();
        }
        int highestId = iconData.stream().mapToInt(IconData::getId).max().getAsInt();
        if (AbstractFont.AbstractFont_modIconSprites.length <= highestId) {
            AbstractFont.AbstractFont_modIconSprites = Arrays.copyOf(AbstractFont.AbstractFont_modIconSprites, highestId + 1);
        }
        for (IconData data : iconData) {
            try {
             AbstractFont.AbstractFont_modIconSprites[data.id] = IndexedSprite.fromBytes(data.data);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Pk Icons
     */
    static void loadPkIcons() {
        ArrayList<byte[]> iconData = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            byte[] data = resourceToBytes("img/icons/pk/" + i + ".png");
            if(data != null)
                iconData.add(data);
        }
        try {
            ArrayList<Sprite> icons = new ArrayList<>();
            for(Sprite icon : StructDefinition.headIconPkSprites) {
                if(icon != null)
                    icons.add(icon);
            }
            for(byte[] data : iconData)
                icons.add(new Sprite(data, ViewportMouse.client.canvas));
            StructDefinition.headIconPkSprites = icons.toArray(new Sprite[0]);
        } catch(Exception e) {
            e.printStackTrace();
            /* ignored */
        }
    }

    /**
     * Custom Client Background Loading
     */
    static void loadClientBackground(Component component) {
        byte[] clientBackgroundLeft = CustomUtils.resourceToBytes("img/leftTitleBackground.png");
        byte[] clientBackgroundRight = CustomUtils.resourceToBytes("img/rightTitleBackground.png");
        WorldMapID.leftTitleSprite  = new Sprite(clientBackgroundLeft, component);
        class37.rightTitleSprite = new Sprite(clientBackgroundRight, component);
    }

    /**
     * Interface sprites
     */

    private static final int customInterSpriteOffset = 1000000;

    public static Sprite[] customInterSprites;

    public static void loadCustomInterfaceSprites() {
        ArrayList<byte[]> spriteData = new ArrayList<byte[]>();
        for(int i = 0; i < 4; i++) {
            byte[] data = resourceToBytes("img/inter/" + i + ".png");
            if(data != null)
                spriteData.add(data);
        }
        try {
            ArrayList<Sprite> icons = new ArrayList<Sprite>();
            for(byte[] data : spriteData)
                icons.add(new Sprite(data, ViewportMouse.client.canvas));
            customInterSprites = icons.toArray(new Sprite[icons.size()]);
        } catch(Exception e) {
            /* ignored */
        }
    }

    public static int customInterSpriteId(int id) {
        return customInterSpriteOffset + id;
    }

    public static Sprite customInterSprite(int spriteId) {
        return spriteId < customInterSpriteOffset ? null : customInterSprites[spriteId - customInterSpriteOffset];
    }

    /**
     * Custom image stuff
     */
    private static BufferedImage createBufferedImage(int width, int height, int[] pixels) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bi.setRGB(0, 0, width, height, pixels, 0, width);
        Image img = makeColorTransparent(bi, new Color(0, 0, 0));
        return imageToBufferedImage(img);
    }

    private static BufferedImage imageToBufferedImage(Image image) {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return bufferedImage;
    }

    private static Image makeColorTransparent(BufferedImage im, final Color color) {
        RGBImageFilter filter = new RGBImageFilter() {
            final int markerRGB = color.getRGB() | 0xFF000000;

            public final int filterRGB(int x, int y, int rgb) {
                if((rgb | 0xFF000000) == markerRGB) {
                    return 0x00FFFFFF & rgb;
                } else {
                    return rgb;
                }
            }
        };
        ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }

    static BufferedImage getItemImage(int id) {
        ItemDefinition def = Occluder.getItemDefinition(id);
        if(def == null || def.name == null || def.name.equals("null"))
            return null;
        Sprite a = GrandExchangeOfferWorldComparator.createSprite(id, 10000, 1, 3355443, 0, false);
        if(a == null)
            return null;
        return createBufferedImage(a.width, a.height, a.pixels);
    }

    public static byte[] resourceToBytes(String resourcePath) {
        try (InputStream in = CustomUtils.class.getResourceAsStream("/" + resourcePath)) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(in.available());

            int rd;
            byte[] buffer = new byte[2048];
            while (true) {
                rd = in.read(buffer);
                if (rd == -1)
                    break;
                byteArrayOutputStream.write(buffer, 0, rd);
            }


            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            /* ignored */
        }
        /* ignored */
        return null;
    }

    static String getFormattedTime(int time) {
        int seconds = time / 50;
        if (seconds < 60)
            return "0:" + (seconds < 10 ? "0" : "") + seconds;
        else {
            int mins = seconds / 60;
            int remainderSecs = seconds - (mins * 60);
            if (mins < 60) {
                return mins + ":" + (remainderSecs < 10 ? "0" : "") + remainderSecs + "";
            } else {
                int hours = mins / 60;
                int remainderMins = mins - (hours * 60);
                return (hours < 10 ? "0" : "") + hours + "h " + (remainderMins < 10 ? "0" : "") + remainderMins + "m " + (remainderSecs < 10 ? "0" : "") + remainderSecs + "s";
            }
        }
    }


}