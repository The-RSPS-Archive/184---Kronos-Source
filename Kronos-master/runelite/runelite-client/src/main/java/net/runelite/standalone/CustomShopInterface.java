package net.runelite.standalone;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class CustomShopInterface {

    public static int[][] ITEMS;
    public static int[] REMAINING;
    private static final String[] OPTIONS = new String[] { "Value", "Buy-1", "Buy-5", "Buy-10", "Buy-50", "Buy-X" };;
    private static final TreeMap<Long, String> a = new TreeMap<Long, String>();

    static {
        a.put(1000L, "k");
        a.put(1000000L, "M");
    }

    public static void init(final String title, final int[][] items, final int[] remaining) {
        Canvas.get(100, 1).onLoad[2] = title;
        CustomInterfaceEdits.unlock(100 << 16 | 4, -1, items.length, 0, 1, 2, 3, 4, 5, 9);
        CustomShopInterface.ITEMS = items;
        CustomShopInterface.REMAINING = remaining;
        final Widget b = Widget.addChild(6553602, 4, 0);
        b.isIf3 = true;
        b.rawX = -5;
        b.y = -5;
        b.width = 438;
        b.rawWidth = 438;
        b.height = 25;
        b.rawHeight = 25;
        b.textXAlignment = 1;
        b.fontId = 494;
        b.text = "";
        b.textYAlignment = 1;
        b.textShadowed = true;
        b.color = 16777215;
    }

    public static void updateMappedContainer(int scriptId, TileItem[] stock) {
        EnumDefinition script = UserComparator10.getEnum(scriptId);
        script.outputCount = stock.length;
        script.keys = new int[stock.length];
        script.intVals = new int[stock.length];
        for (int i = 0; i < stock.length; i++) {
            script.keys[i] = i;
            script.intVals[i] = stock[i].id;
        }
    }

    public static void open(final Widget widget) {
        if (ITEMS == null || widget.id >> 16 != 100) {
            return;
        }
        final Widget a = Canvas.get(100, 4);

        //Widget loadouts = Widget.get(100, 2);
        //loadouts.isHidden = true;
        final ArrayList<Widget> list = new ArrayList<Widget>();
        int n = 0;
        for (final Widget em2 : a.children) {
            if (em2.type != 4) {
                em2.childIndex = n++;
                list.add(em2);
            }
        }
        a.children = list.toArray(new Widget[0]);
        int length2 = a.children.length;
        int n2 = 0;
        int n3 = 0;
        for (final Widget em3 : a.children) {
            if (em3.itemId != -1 && !em3.isHidden) {
                final ItemDefinition a2 = Occluder.getItemDefinition(em3.itemId);
                if (a2.placeholder != -1 && a2.placeholderTemplate != -1) {
                    widget.transparencyTop = 180;
                }
                if(em3.childIndex >= ITEMS.length)
                    return;
                final int n5 = REMAINING[em3.childIndex];
                if(n5 != 0) {
                    final Widget b3 = Widget.addChild(a.id, 4, length2++);
                    b3.isIf3 = true;
                    b3.type = 4;
                    b3.rawX = em3.rawX;
                    b3.rawY = em3.rawY + 4;
                    b3.rawWidth = em3.rawWidth;
                    b3.rawHeight = em3.rawHeight;
                    b3.fontId = 494;
                    final int n4 = ITEMS[em3.childIndex][1];
                    if (n4 == 0) {
                        ++n2;
                        b3.text = "";
                    } else {
                        ++n3;
                        b3.text = "<col=ffff00>" + format(n4);
                    }
                    b3.textShadowed = true;
                    b3.textXAlignment = 2;
                    b3.textYAlignment = 2;
                    b3.color = 16777215;
                    WorldMapSectionType.method116(b3);
                    ViewportMouse.client.revalidateWidget(b3);
                }
                // remaining
                final Widget b4 = Widget.addChild(a.id, 4, length2++);
                b4.isIf3 = true;
                b4.type = 4;
                b4.rawX = em3.rawX;
                b4.rawY = em3.rawY - 20;
                b4.rawWidth = em3.rawWidth;
                b4.rawHeight = em3.rawHeight;
                b4.fontId = 494;
                if (n5 == -1) {
                    b4.text = "";
                } else if(n5 == 0) {
                    b4.text = "<col=b20c0c> Sold ";
                    b4.rawX = em3.rawX - 7;
                    b4.rawY = em3.rawY - 10;
                }
                else {
                    b4.text = "<col=438c0b>x" + format(n5);
                }
                b4.width = 100;
                b4.textShadowed = true;
                b4.textXAlignment = 2;
                b4.textYAlignment  = 2;
                b4.color = 16777215;
                WorldMapSectionType.method116(b4);
                ViewportMouse.client.revalidateWidget(b4);
            }
        }
        a.rawY += 20;
        a.rawHeight += 20;
        a.scrollHeight += 10;

        Widget[] children = UserComparator5.Widget_interfaceComponents[100];
        int childCount = Widget.Widget_archive.fileCount(100);
        ArrayList<Widget> newChildren = new ArrayList<Widget>();
        for(int i = 0; i < childCount; i++)
            newChildren.add(children[i]);

        Widget priceText = new Widget();
        priceText.fontId = 494;
        priceText.rawWidth = 100;
        priceText.isIf3 = true;
        priceText.type = 4;
        priceText.rawX = 5;
        priceText.rawY = 50;
        priceText.rawWidth = 500;
        priceText.rawHeight = 20;
        priceText.textShadowed = true;
        priceText.textXAlignment = 1;
        priceText.color = 16777215;

        if (n2 > 0) {
            if (n3 > 0)
                priceText.text = "Prices are displayed at the bottom right of each item. Items without a price are <col=4dff4d>free</col>!";
            else
                priceText.text = "All items in this shop are <col=4dff4d>free</col>!";
        } else {
            priceText.text = "Prices are displayed at the bottom right of each item.";
        }

        WorldMapSectionType.method116(priceText);
        ViewportMouse.client.revalidateWidget(priceText);
        newChildren.add(priceText);

        /**
         * Children (Any new children have to become before this!)
         */
        for(int i = childCount; i < newChildren.size(); i++) {
            Widget child = newChildren.get(i);
            child.id = 100 << 16 | i;
            if(child.type == 5) {
                long var37 = ((long) child.id << 32) + (long) -1; //-1 is min/max value
                final Node node_0 = (Node) Client.widgetClickMasks.get(var37);
                if (node_0 != null)
                    node_0.unlink();
                Client.widgetClickMasks.put(new IntegerNode(2), var37);
            }
            WorldMapSectionType.method116(child);
            ViewportMouse.client.revalidateWidget(child);
        }
        UserComparator5.Widget_interfaceComponents[100] = newChildren.toArray(new Widget[newChildren.size()]);
    }

    public static void updateOptions(final Widget widget) {
        if (ITEMS == null || widget.id >> 16 != 100) {
            return;
        }
        for(int x = 0; x < OPTIONS.length; x++) {
            String s = OPTIONS[x];
            widget.setAction(x, s.isEmpty() ? null : s);
        }
    }

    public static void updateItem(Widget inter, ItemDefinition def) {
        if(inter.id >> 16 != 100)
            return;
        if(def.placeholder != -1 && def.placeholderTemplate != -1) {
            /* dim placeholder items properly in shop */
            inter.transparencyTop = 120;
        }
    }

    static String format(final long number) {
        if (number == Long.MIN_VALUE) {
            return format(-9223372036854775807L);
        }
        if (number < 0L) {
            return "-" + format(-number);
        }
        if (number < 1000L) {
            return Long.toString(number);
        }
        final Map.Entry<Long, String> floorEntry = CustomShopInterface.a.floorEntry(number);
        final Long n2 = floorEntry.getKey();
        final String s = floorEntry.getValue();
        final long n3 = number / (n2 / 10L);
        return (n3 < 100L && n3 / 10.0 != n3 / 10L) ? (n3 / 10.0 + s) : (n3 / 10L + s);
    }

}
