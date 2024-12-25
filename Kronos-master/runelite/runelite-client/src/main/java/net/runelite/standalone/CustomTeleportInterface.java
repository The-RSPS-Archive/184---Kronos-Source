package net.runelite.standalone;

public class CustomTeleportInterface {

    private static String TITLE;
    private static String[] CATEGORIES;
    private static String[] SUBCATEGORIES;
    private static String[] OPTIONS;

    public static void load(String title, final int n, String[] categories, final int size, String[] subcategories, String[] options) {
        if (title.isEmpty()) {
            title = TITLE;
        } else {
            TITLE = title;
        }
        if (categories.length == 0) {
            categories = CATEGORIES;
        } else {
            CATEGORIES = categories;
        }
        if (subcategories.length == 0) {
            subcategories = SUBCATEGORIES;
        } else {
            SUBCATEGORIES = subcategories;
        }
        if (options.length == 0) {
            options = OPTIONS;
        } else {
            OPTIONS = options;
        }

        Canvas.get(583, 14).text = title;
        for (int i = 17; i <= 63; ++i)
            Canvas.get(583, i).isHidden = true;

        final Widget mainWidget = Canvas.get(583, 16);
        mainWidget.rawX = 23;
        mainWidget.rawY = 58;
        mainWidget.rawWidth = 469;
        mainWidget.rawHeight = 260;
        mainWidget.scrollWidth = 0;
        mainWidget.scrollHeight = 0;
        int n3 = 18;

        final Widget cat = addChild(n3++);
        cat.isIf3 = true;
        cat.rawX = 0;
        cat.rawY = 0;
        cat.rawWidth = 120;
        cat.rawHeight = 240;
        cat.hasListener = true;
        cat.onLoad = new Object[]{991, -2147483645, -1};

        final Widget b2 = addChild(n3++);
        b2.parentId = cat.id;
        b2.isIf3 = true;
        b2.yAlignment = 1;
        b2.widthAlignment = 1;
        b2.heightAlignment = 1;
        b2.rawX = 4;
        b2.rawWidth = 0;
        b2.rawHeight = 8;

        final Widget subCat = addChild(n3++);
        subCat.isIf3 = true;
        subCat.rawX = 122;
        subCat.rawY = 0;
        subCat.rawWidth = 120;
        subCat.rawHeight = 240;
        subCat.hasListener = true;
        subCat.onLoad = new Object[]{991, -2147483645, -1};

        final Widget b5 = addChild(n3++);
        b5.parentId = subCat.id;
        b5.isIf3 = true;
        b5.yAlignment = 1;
        b5.widthAlignment = 1;
        b5.heightAlignment = 1;
        b5.rawX = 4;
        b5.rawWidth = 0;
        b5.rawHeight = 8;

        final Widget b6 = addChild(n3++);
        b6.parentId = subCat.id;
        b6.isIf3 = true;
        b6.xAlignment = 2;
        b6.yAlignment = 1;
        b6.buttonType = 1;
        b6.rawX = 4;
        b6.rawWidth = 16;
        b6.rawHeight = 8;
        load(b5, subcategories, size);
        cat.rawHeight = 240;
        load(b2, categories, n);
        boolean b7 = false;
        boolean b8 = false;
        boolean b9 = false;
        for (final String s : options) {
            if (s.contains("<img=47>"))
                b7 = true;
            if (s.contains("<img=46>"))
                b8 = true;
            if (s.contains("<img=43>"))
                b9 = true;
        }
        int n4 = 0;

        final Widget teleports = addChild(n3++);
        teleports.isIf3 = true;
        teleports.rawX = 245;
        teleports.rawWidth = 220;
        if (n4 == 0) {
            teleports.rawY = 0;
            teleports.rawHeight = 240;
        } else {
            teleports.rawY = n4 * 15 + 5;
            teleports.rawHeight = 241 - (n4 * 15 + 5);
        }
        teleports.hasListener = true;
        teleports.onLoad = new Object[]{991, -2147483645, -1};
        final Widget b14 = addChild(63);
        b14.parentId = teleports.id;
        b14.isIf3 = true;
        b14.yAlignment = 1;
        b14.widthAlignment = 1;
        b14.heightAlignment = 1;
        b14.rawX = 4;
        b14.rawWidth = 24;
        b14.rawHeight = 8;
        final Widget b15 = addChild(n3++); //this is the scroll bar
        b15.parentId = teleports.id;
        b15.isIf3 = true;
        b15.xAlignment = 2;
        b15.yAlignment = 1;
        b15.heightAlignment =1;
        b15.rawX = 4;
        b15.rawWidth = 16;
        b15.rawHeight = 8;
        load(b14, options, -1);
        addScrollBar(b14, b15, options.length * 25);
        final Widget b16 = addChild(n3++);
        b16.isIf3 = true;
        b16.type = 4;
        b16.rawX = 90;
        b16.rawY = 246;
        b16.rawWidth = 310;
        b16.rawHeight = 20;
        b16.fontId = 494;
        b16.text = "<img=91> The wizard will heal you after using any teleport.";
        b16.textShadowed = true;
        b16.color = 16777215;
    }

    private static void load(final Widget em, final String[] array, final int n) {
        int n2 = 0;
        for (int i = 0; i < array.length; ++i) {
            final Widget b = Widget.addChild(em.id, 3, n2);
            b.isIf3 = true;
            b.rawX = 0;
            b.rawY = n2 * 25;
            b.widthAlignment = 1;
            b.rawHeight = 25;
            b.fill = true;
            b.dataText = "<col=ff9040>" + array[i].split("\\|")[0] + "</col>";
            b.setAction(0, (n == -1) ? "Teleport" : "Select");
            if (i == n) {
                b.transparencyTop = 125;
                b.onMouseOver = new Object[]{1015, -2147483645, -2147483643, 0, 80};
                b.onMouseLeave = new Object[]{1015, -2147483645, -2147483643, 0, 125};
            } else if ((n2 + 1) % 2 != 0) {
                b.transparencyTop = 225;
                b.onMouseOver = new Object[]{1015, -2147483645, -2147483643, class12.method154((n == -1) ? "743BA9" : "B03A2E", 16), 0};
                b.onMouseLeave = new Object[]{1015, -2147483645, -2147483643, 16777215, 255};
            } else {
                b.transparencyTop = 200;
                b.onMouseOver = new Object[]{1015, -2147483645, -2147483643,class12.method154((n == -1) ? "743BA9" : "B03A2E", 16), 0};
                b.onMouseLeave = new Object[]{1015, -2147483645, -2147483643, 0, 225};
            }
            b.hasListener = true;
            ++n2;
        }
        if (n == -1) {
            for (int j = 0; j < array.length; ++j) {
                final String[] split = array[j].split("\\|");
                final Widget b2 = Widget.addChild(em.id, 4, n2++);
                b2.rawX = 3;
                b2.rawY = j * 25;
                b2.rawWidth = 190;
                b2.rawHeight = 25;
                b2.fontId = 495;
                b2.text = split[0];
                b2.textXAlignment = 1;
                b2.textYAlignment = 1; //here orig 0
                b2.textShadowed = true;
                b2.color = 16758847;
                if (split.length > 2) {
                    final Widget b3 = Widget.addChild(em.id, 4, n2++);
                    b3.rawX = 3;
                    b3.rawY = j * 25;
                    b3.rawWidth = 277;
                    b3.rawHeight = 25;
                    b3.fontId = 494;
                    b3.text = split[1];
                    b3.textXAlignment = 1;
                    b3.textYAlignment = 1;
                    b3.textShadowed = true;
                    b3.color = 16758847;
                    final Widget b4 = Widget.addChild(em.id, 4, n2++);
                    b4.rawX = 3;
                    b4.rawY = j * 25;
                    b4.rawWidth = 277;
                    b4.rawHeight = 25;
                    b4.fontId = 494;
                    b4.text = split[2];
                    b4.textXAlignment = 1;
                    b4.textYAlignment = 2;
                    b4.textShadowed = true;
                    b4.color = 16758847;
                } else if (split.length > 1) {
                    final Widget b5 = Widget.addChild(em.id, 4, n2++);
                    b5.rawX = 3;
                    b5.rawY = j * 25;
                    b5.rawWidth = 277;
                    b5.rawHeight = 25;
                    b5.fontId = 494;
                    b5.text = split[1];
                    b5.textXAlignment = 1;
                    b5.textYAlignment = 2;
                    b5.textShadowed = true;
                    b5.color = 16758847;
                }
            }
        } else {
            for (int k = 0; k < array.length; ++k) {
                final Widget b6 = Widget.addChild(em.id, 4, n2++);
                b6.rawX = -5;
                b6.rawY = k * 25;
                b6.rawWidth = 121;
                b6.rawHeight = 25;
                b6.fontId = 496;
                b6.text = array[k];
                b6.textXAlignment = 1;
                b6.textYAlignment = 1;
                b6.textShadowed = true;
                b6.color = 16758847;
            }
        }
        CustomInterfaceEdits.unlock(em.id, 0, n2, 0);
    }

    private static void addScrollBar(final Widget widget, final Widget child, final int scrollHeight) {
        widget.scrollY = 0;
        widget.scrollHeight = Math.max(widget.rawHeight, scrollHeight);
        final ScriptEvent script = new ScriptEvent();
        script.args = new Object[]{31, child.id, widget.id, 792, 789, 790, 791, 773, 788};
        ParamDefinition.method4313(script);
    }

    private static Widget addChild(final int n) {
        final Widget a = Canvas.get(583, n);
        final Widget em = new Widget();
        em.parentId = a.parentId;
        em.id = a.id;
        return UserComparator5.Widget_interfaceComponents[583][n] = em;
    }
}
