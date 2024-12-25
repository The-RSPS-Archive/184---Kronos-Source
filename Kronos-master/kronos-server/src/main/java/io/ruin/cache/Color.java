package io.ruin.cache;

/**
 * Remember: If we add/remove/change a color, remember to also change it in the custom JournalInterface client class!
 */
public enum Color {

    RED("FF0000"),
    YELLOW("f4d03f"),
    GREEN("27ae60"),

    ORANGE("FFA500"),
    ORANGE_RED("FF4500"),
    TOMATO("FF6347"),
    CRIMSON("DC143C"),
    MAROON("800000"),

    BLUE("0000FF"),
    COOL_BLUE("0040ff"),
    BABY_BLUE("1E90FF"),
    CYAN("00FFFF"),

    PURPLE("800080"),
    VIOLET("EE82EE"),
    PINK("FFC0CB"),

    WHITE("FFFFFF"),
    WHEAT("F5DEB3"),
    SILVER("C0C0C0"),

    OLIVE("808000"),
    BRONZE("D37E2A"),
    GOLD("FFD700"),

    DARK_RED("6f0000"),
    DARK_GREEN("006600"),

    RAID_PURPLE("ef20ff");

    public final String html;

    Color(String html) {
        this.html = html;
    }

    public String tag() {
        return "<col=" + html + ">";
    }

    public String wrap(String s) {
        return tag() + s + "</col>";
    }

}