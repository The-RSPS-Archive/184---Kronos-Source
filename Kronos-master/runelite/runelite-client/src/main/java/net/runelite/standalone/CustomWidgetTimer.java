package net.runelite.standalone;

public class CustomWidgetTimer {

    private static final int PADDING_X = 2;
    public static boolean SHOW_WIDGETS;
    private static final CustomWidgetTimer[] widgets = new CustomWidgetTimer[10];

    private int x;
    private int y;
    private int initialSeconds;
    private int timeLeft;
    private final int spriteId;
    private final String name, description;
    private int hoverCounter = 0;
    private final int stringWidth;

    CustomWidgetTimer(int spriteId, int seconds, String name, String description) {
        this.spriteId = spriteId;
        this.timeLeft = seconds;
        this.initialSeconds = seconds / 50;
        this.name = name;
        this.description = description;
        stringWidth = Math.max(class170.fontBold12.getTextWidth(name), class170.fontBold12.getTextWidth(description));
    }

    private void decrement() {
        timeLeft--;
    }

    private boolean terminated() {
        return timeLeft < 0;
    }

    static void draw() {
        int mouseX = MouseHandler.MouseHandler_xVolatile;
        int mouseY = MouseHandler.MouseHandler_yVolatile;
        for (int i = widgets.length - 1; i >= 0; i--) {
            CustomWidgetTimer widget = widgets[i];
            if (widget == null)
                continue;
            int x = Client.isResizable ? 0 : 454;
            int y = (Client.isResizable ? Login.xPadding - 90 + 314 : 314) - (24 * i);
            int alpha = 255;
            if (widget.y < -1)
                alpha = (int) (255 * ((i * 17.0 + widget.y) / (i * 17.0)));

            if (widget.y > 0)
                y -= widget.y;
            else if (widget.y < -1)
                y += -widget.y;

            if (widget.x > 0)
                x -= widget.x;
            else if (widget.x < 0)
                x += -widget.x;

            Rasterizer2D.transparentBox(22, y, x, 0x171717, 60, alpha - 40, 150);
            Rasterizer2D.drawRectangle(x, y, 61, 23, widget.getBorderColor());
            class170.fontBold12.drawTextLeftAligned("<img=" + widget.spriteId + ">", x + 3, y + 21, 0xffffff, 0);
            class170.fontBold12.drawTextLeftAligned(CustomUtils.getFormattedTime(widget.timeLeft), x + 27, y + 16, widget.getTimeColor(), 0);
        }
        for (int i = widgets.length - 1; i >= 0; i--) { // render tooltips
            CustomWidgetTimer widget = widgets[i];
            if (widget == null)
                continue;
            int x = Client.isResizable ? 0 : 454;
            int y = (Client.isResizable ? Login.xPadding - 90 + 314 : 314) - (24 * i);
            if (mouseX >= x && mouseX <= x + 61 && mouseY >= y && mouseY <= y + 22)
                widget.hoverCounter++;
            else
                widget.hoverCounter = 0;
            if (widget.hoverCounter >= 15) { // render tooltip
                Rasterizer2D.fillRectangleAlpha(Client.isResizable ? x : x - widget.stringWidth + 58, y - 35, widget.stringWidth + (PADDING_X * 2), 32, 0xFFFFA0, 255);
                Rasterizer2D.drawRectangle(Client.isResizable ? x : x - widget.stringWidth + 58, y - 35, widget.stringWidth + (PADDING_X * 2), 32, 0);
                if (Client.isResizable) {
                    class170.fontBold12.drawTextLeftAligned(widget.description, x + PADDING_X, y - 9, 0, -1);
                    class170.fontBold12.drawTextLeftAligned(widget.name, x + PADDING_X, y - 22, 0, -1);
                } else {
                    class170.fontBold12.drawTextLeftAligned(widget.description, x - widget.stringWidth + 58 + PADDING_X, y - 9, 0, -1);
                    class170.fontBold12.drawTextLeftAligned(widget.name, x - widget.stringWidth + 58 + PADDING_X, y - 22, 0, -1);
                }
            }
        }
    }


    static void add(CustomWidgetTimer widget) {
        int index = widgets.length - 1;
        for (int i = 0; i < widgets.length; i++) {
            if (widgets[i] == null || widgets[i].spriteId == widget.spriteId) {
                index = i;
                break;
            }
        }
        widget.x -= Client.isResizable ? -60 : 60;
        widgets[index] = widget;
    }

    static void tick() {
        for (CustomWidgetTimer widget : widgets) {
            if (widget != null) {
                widget.decrement();
                if (widget.terminated())
                    remove(widget);
                if (widget.y != -1) {
                    if (widget.y > -1)
                        widget.y--;
                    else
                        widget.y++;

                    if (widget.y == 0)
                        widget.y = -1;
                }
                if (widget.x != 0) {
                    if (widget.x > 0)
                        widget.x--;
                    else
                        widget.x++;
                }
            }
        }
    }

    private static void remove(CustomWidgetTimer widget) {
        for (int i = 0; i < widgets.length; i++) {
            if (widgets[i] == null)
                return;
            if (widgets[i].spriteId == widget.spriteId) {
                widgets[i] = null;
                for (int i2 = i; i2 < widgets.length - 1; i2++) {
                    CustomWidgetTimer temp = widgets[i2];
                    widgets[i2] = widgets[i2 + 1];
                    widgets[i2 + 1] = temp;

                    if (widgets[i2] != null)
                        widgets[i2].y = 17;

                    if (widgets[i2 + 1] != null)
                        widgets[i2 + 1].y = 17;
                }
                break;
            }
        }
    }

    private int secondsLeft() {
        return timeLeft / 50;
    }

    @Override
    public String toString() {
        return String.format("[%s, %s]", timeLeft, spriteId);
    }

    public int getTimeColor() {
        return initialSeconds >= 1 && secondsLeft() <= 5 ? 0xff0000 : 0xffffff;
    }

    public int getBorderColor() {
        return initialSeconds >= 1 && secondsLeft() <= 5 ? 0xff0000 : 0x666666;
    }
}
