package net.runelite.standalone;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CustomDropViewerInterface {

    public static void init(String tableName, int petId, int petAverage, List<Integer[]> drops) {
        Widget infoX = Canvas.get(522, 17);
        if(infoX != null)
            infoX.onClick = null;

        Canvas.getWidget(383 << 16 | 1).children[1].text = "Viewing drop table for: " + tableName; //set title
        Canvas.getWidget(383 << 16 | 2).isHidden = true; //hide "Exchange" button

        Widget parent = Canvas.getWidget(383 << 16 | 3);
        parent.children = null; //reset previous children

        Collections.sort(drops, new Comparator<Integer[]>() {
            @Override
            public int compare(Integer[] d1, Integer[] d2) {
                int x = d1[4];
                int y = d2[4];
                return (x < y) ? -1 : ((x == y) ? 0 : 1);
            }
        });
        if(petId != 65535)
            drops.add(0, new Integer[]{petId, 1, -1, -1, petAverage}); //"pet" specifically identified by minAmount == -1

        int childId = 0;
        String[] columns = {"Min Amount", "Max Amount", "Drop Chance"};
        for(int i = 0; i < drops.size(); i++) {
            Integer[] drop = drops.get(i);

            int itemId = drop[0];
            int broadcastType = drop[1];
            int minAmount = drop[2];
            int maxAmount = drop[3];
            int average = drop[4];

            boolean pet = minAmount == -1;

            Widget bg = Widget.addChild(parent.id, 5, childId++);
            bg.rawX = 0;
            bg.rawY = i * 38;
            bg.rawWidth = 424;
            bg.rawHeight = 38;
            bg.spriteId = 1040;
            bg.spriteTiling = true;
            bg.fill = true;
            bg.onMouseOver = new Object[]{1015, -2147483645, -2147483643, 0, 255};
            if(i % 2 == 0) {
                bg.transparencyTop = 0;
                bg.onMouseLeave = new Object[]{1015, -2147483645, -2147483643, 0, 0};
            } else {
                bg.transparencyTop = 125;
                bg.onMouseLeave = new Object[]{1015, -2147483645, -2147483643, 0, 125};
            }
            bg.hasListener = true;
            WorldMapSectionType.method116(bg);

            ViewportMouse.client.revalidateWidget(bg);

            ItemDefinition def = Occluder.getItemDefinition(itemId);
            boolean note = def.noteTemplate >= 0 && def.notedId >= 0;
            String name = (note ? Occluder.getItemDefinition(def.notedId).name : def.name);

            Widget examine = Widget.addChild(parent.id, 5, childId++);
            examine.rawX = 0;
            examine.rawY = bg.rawY;
            examine.rawWidth = 40;
            examine.rawHeight = bg.rawHeight;
            examine.setAction(0, "Examine <col=ff9040>" + name + "</col>");
            WorldMapSectionType.method116(examine);
            ViewportMouse.client.revalidateWidget(examine);

            Widget item = Widget.addChild(parent.id, 5, childId++);
            item.rawX = 3;
            item.rawY = bg.rawY + 3;
            item.rawWidth = 36;
            item.rawHeight = 32;
            Widget.setItem(item, itemId, (minAmount == maxAmount ? minAmount : (maxAmount / 2)), 0);
            item.spriteShadow = 3355443;
            item.outline = 1;
            WorldMapSectionType.method116(item);
            ViewportMouse.client.revalidateWidget(item);

            Widget itemName = Widget.addChild(parent.id, 4, childId++);
            itemName.rawX = 38;
            itemName.rawY = bg.rawY + 5;
            itemName.rawWidth = 140;
            itemName.rawHeight = 38;
            itemName.fontId = 495;
            itemName.textShadowed = true;
            itemName.color = 16750623;
            itemName.text = "<col=ffb83f>" + name;
            itemName.textXAlignment = 1;
            itemName.textYAlignment = 0;
            WorldMapSectionType.method116(itemName);
            ViewportMouse.client.revalidateWidget(itemName);

            Widget broadcast = Widget.addChild(parent.id, 4, childId++);
            broadcast.rawX = itemName.rawX;
            broadcast.rawY = itemName.rawY + 16;
            broadcast.rawWidth = itemName.rawWidth;
            broadcast.rawHeight = 10;
            broadcast.fontId = 494;
            broadcast.textShadowed = true;
            broadcast.color = 16750623;
            broadcast.textXAlignment = 1;
            broadcast.textYAlignment = 0;
            String b = "None";
            if(broadcastType == 0)
                b = "Friends";
            else if(broadcastType == 1)
                b = "World";
            else if(broadcastType == 2)
                b = "Global";
            broadcast.text = "Broadcast: <col=ffb83f>" + b;
            WorldMapSectionType.method116(broadcast);
            ViewportMouse.client.revalidateWidget(broadcast);

            if(pet) {
                Widget info = Widget.addChild(parent.id, 4, childId++);
                info.rawX = 170;
                info.rawY = bg.rawY + 1;
                info.rawWidth = 165;
                info.rawHeight = bg.rawHeight;
                info.fontId = 494;
                info.textShadowed = true;
                info.color = 16750623;
                info.text = "<col=F5DEB3>Pet unlocks do not affect<br><col=F5DEB3>drops and vice versa.";
                info.textXAlignment = 1;
                info.textYAlignment = 1;
                WorldMapSectionType.method116(info);
                ViewportMouse.client.revalidateWidget(info);

                Widget column = Widget.addChild(parent.id, 4, childId++);
                column.rawX = 340;
                column.rawY = bg.rawY + 7;
                column.rawWidth = 80;
                column.rawHeight = 32;
                column.fontId = 494;
                column.textShadowed = true;
                column.color = 16750623;
                column.textXAlignment = 1;
                column.text = "Unlock Chance:";
                WorldMapSectionType.method116(column);
                ViewportMouse.client.revalidateWidget(column);

                Widget value = Widget.addChild(parent.id, 4, childId++);
                value.rawX = column.rawX;
                value.rawY = column.rawY + 12;
                value.rawWidth = column.rawWidth;
                value.rawHeight = 10;
                value.fontId = 494;
                value.textShadowed = true;
                value.color = 16750623;
                value.textXAlignment = 1;
                value.text = "<col=ffb83f>~ 1 / " + average;
                WorldMapSectionType.method116(value);
                ViewportMouse.client.revalidateWidget(value);
            } else {
                int x = 170;
                for(int c = 0; c < columns.length; c++) {
                    String s = columns[c];
                    Widget column = Widget.addChild(parent.id, 4, childId++);
                    column.rawX = x;
                    column.rawY = bg.rawY + 7;
                    column.rawWidth = 80;
                    column.rawHeight = 32;
                    column.fontId = 494;
                    column.textShadowed = true;
                    column.color = 16750623;
                    column.textXAlignment = 1;
                    column.text = s + ":";
                    WorldMapSectionType.method116(column);
                    ViewportMouse.client.revalidateWidget(column);
                    x += 85;

                    Widget value = Widget.addChild(parent.id, 4, childId++);
                    value.rawX = column.rawX;
                    value.rawY = column.rawY + 12;
                    value.rawWidth = column.rawWidth;
                    value.rawHeight = 10;
                    value.fontId = 494;
                    value.textShadowed = true;
                    value.color = 16750623;
                    value.textXAlignment = 1;

                    if(c == 0)
                        value.text = "<col=ffb83f>" + (minAmount == 0 ? "-" : minAmount);
                    else if(c == 1)
                        value.text = "<col=ffb83f>" + (maxAmount == 0 ? "-" : maxAmount);
                    else if(c == 2)
                        value.text = "<col=ffb83f>" + (average == 1 ? "Always" : ("~ 1 / " + average));
                    WorldMapSectionType.method116(value);
                    ViewportMouse.client.revalidateWidget(value);
                }
            }
        }
        /**
         * Scrolling
         */
        parent.scrollY = 0;
        parent.scrollHeight = Math.max(parent.rawHeight, drops.size() * 38);
        final ScriptEvent scriptevent_0 = new ScriptEvent();
        scriptevent_0.args = new Object[]{31, 25100292, 25100291, 792, 789, 790, 791, 773, 788};
        ParamDefinition.method4313(scriptevent_0);


        /**
         * Unlocking
         */
        CustomInterfaceEdits.unlock(parent.id, 0, childId, 0);

        /**
         * Refresh
         */
        WorldMapSectionType.method116(parent);
    }

}
