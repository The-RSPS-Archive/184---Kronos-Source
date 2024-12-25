package io.ruin.model.skills.runecrafting;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.rune.RuneRemoval;
import io.ruin.model.stat.StatType;

public enum MysticStaff {
    WATER(2151, 1395, 1403, 149, Rune.COSMIC.toItem(6), Rune.WATER.toItem(50)),
    EARTH(2150, 1399, 1407, 151, Rune.COSMIC.toItem(6), Rune.EARTH.toItem(50)),
    FIRE(2153, 1393, 1401, 152, Rune.COSMIC.toItem(6), Rune.FIRE.toItem(50)),
    AIR(2152, 1397, 1405, 150, Rune.COSMIC.toItem(6), Rune.AIR.toItem(50)),

    LAVA(new int[]{2150, 2153}, 3053, 3054, 152, Rune.COSMIC.toItem(6), Rune.LAVA.toItem(50)),
    MUD(new int[]{2150, 2151}, 6562, 6563, 151, Rune.COSMIC.toItem(6), Rune.MUD.toItem(50)),
    STEAM(new int[]{2151, 2153}, 11787, 11789, 149, Rune.COSMIC.toItem(6), Rune.STEAM.toItem(50)),
    SMOKE(new int[]{2152, 2153}, 11998, 12000, 150, Rune.COSMIC.toItem(6), Rune.SMOKE.toItem(50)),
    MIST(new int[]{2151, 2152}, 20730, 20733, 149, Rune.COSMIC.toItem(6), Rune.MIST.toItem(50)),
    DUST(new int[]{2152, 2150}, 20736, 20739, 151, Rune.COSMIC.toItem(6), Rune.DUST.toItem(50)),

    ;

    private int battle, mystic, playerGfx;
    Item[] runes;
    private int[] obelisks;

    MysticStaff(int[] obelisks, int battle, int mystic, int playerGfx, Item... runes) {
        this.obelisks = obelisks;
        this.battle = battle;
        this.mystic = mystic;
        this.playerGfx = playerGfx;
        this.runes = runes;
    }

    MysticStaff(int obelisks, int battle, int mystic, int playerGfx, Item... runes) {
        this(new int[]{obelisks}, battle, mystic, playerGfx, runes);
    }

    private void make(Player player) {
        if (!player.getStats().check(StatType.Runecrafting, LEVEL_REQ, "imbue battlestaves")) {
            return;
        }
        player.startEvent(event -> {
            while (true) {
                Item staff = player.getInventory().findItem(battle);
                if (staff == null) {
                    return;
                }
                RuneRemoval removal = RuneRemoval.get(player, runes);
                if (removal == null) {
                    player.sendMessage("You don't have the required runes to imbue that staff.");
                    player.sendMessage("Imbuing a battlestaff requires 6 Cosmic runes and 50 runes of the same type as the staff.");
                    return;
                }
                removal.remove();
                player.animate(726);
                player.graphics(playerGfx, 100, 0);
                staff.setId(mystic);
                player.getStats().addXp(StatType.Runecrafting, XP, true);
                player.sendFilteredMessage("You channel the obelisk's energy into the staff, improving it.");
                event.delay(4);
            }
        });
    }
    private static final int LEVEL_REQ = 93;

    private static final double XP = 20;

    static {
        for (MysticStaff staff : values()) {
            for (int obelisk : staff.obelisks) {
                ItemObjectAction.register(staff.battle, obelisk, (player, item, obj) -> staff.make(player));
            }
        }
    }

}
