package io.ruin.model.map.object;

/*
    OBJECT TYPES:
     0	- straight walls, fences etc
     1	- diagonal walls corner, fences etc connectors
     2	- entire walls, fences etc corners
     3	- straight wall corners, fences etc connectors
     4	- straight inside wall decoration
     5	- straight outside wall decoration
     6	- diagonal outside wall decoration
     7	- diagonal inside wall decoration
     8	- diagonal in wall decoration
     9	- diagonal walls, fences etc
     10	- all kinds of objects, trees, statues, signs, fountains etc etc
     11	- ground objects like daisies etc
     12	- straight sloped roofs
     13	- diagonal sloped roofs
     14	- diagonal slope connecting roofs
     15	- straight sloped corner connecting roofs
     16	- straight sloped corner roof
     17	- straight flat top roofs
     18	- straight bottom egde roofs
     19	- diagonal bottom edge connecting roofs
     20	- straight bottom edge connecting roofs
     21	- straight bottom edge connecting corner roofs
     22	- ground decoration + map signs (quests, water fountains, shops etc)
 */
public enum ObjectType {

    TYPE_0(0, 0),
    TYPE_1(1, 0),
    TYPE_2(2, 0),
    TYPE_3(3, 0),
    TYPE_4(4, 1),
    TYPE_5(5, 1),
    TYPE_6(6, 1),
    TYPE_7(7, 1),
    TYPE_8(8, 1),
    TYPE_9(9, 2),
    TYPE_10(10, 2),
    TYPE_11(11, 2),
    TYPE_12(12, 2),
    TYPE_13(13, 2),
    TYPE_14(14, 2),
    TYPE_15(15, 2),
    TYPE_16(16, 2),
    TYPE_17(17, 2),
    TYPE_18(18, 2),
    TYPE_19(19, 2),
    TYPE_20(20, 2),
    TYPE_21(21, 2),
    TYPE_22(22, 3);

    public final int value;

    public final int unknown;

    ObjectType(int value, int unknown) {
        this.value = value;
        this.unknown = unknown;
    }

}
