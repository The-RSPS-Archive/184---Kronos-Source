package net.runelite.standalone;

public class CustomClanSettingsInterface {

    public static void update(String name, int enterRank, int talkRank, int kickRank) {
        Widget nameInter = Canvas.get(94, 10);
        nameInter.text = name;
        WorldMapSectionType.method116(nameInter);

        Widget enterInter = Canvas.get(94, 13);
        enterInter.text = enterInter.actions[enterRank == -1 ? 0 : enterRank];
        WorldMapSectionType.method116(enterInter);

        Widget talkInter = Canvas.get(94, 16);
        talkInter.text = talkInter.actions[talkRank == -1 ? 0 : talkRank];
        WorldMapSectionType.method116(talkInter);

        Widget kickInter = Canvas.get(94, 19);
        kickInter.text = kickInter.actions[kickRank == -1 ? 3 : kickRank + 1];
        WorldMapSectionType.method116(kickInter);
    }

    public static void update(Widget inter, int option) {
        int interfaceId = inter.id >> 16;
        if (interfaceId != 94)
            return;
        int childId = inter.id & 0xffff;
        if (childId == 13 || childId == 16 || childId == 19)
            inter.text = inter.actions[option - 1];
    }

}
