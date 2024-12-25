package io.ruin.model.activities.raids.xeric.chamber.skilling;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.stat.StatType;

public enum XericBat {
    GUANIC(7587, 1, 20870, 5),
    PRAEL(7588, 15, 20872, 9),
    GIRAL(7589, 30, 20874, 12),
    PHLUXIA(7590, 45, 20876, 15),
    KRYKET(7591, 60, 20878, 18),
    MURNG(7592, 75, 20880, 24),
    PSYKK(7593, 90, 20882, 30),
    ;

    private int npcId, levelReq, itemId;
    private double xp;

    XericBat(int npcId, int levelReq, int itemId, double xp) {
        this.npcId = npcId;
        this.levelReq = levelReq;
        this.itemId = itemId;
        this.xp = xp;
    }

    private void net(Player player, NPC npc) {
        if (!player.getStats().check(StatType.Hunter, levelReq, "catch this bat")) {
            return;
        }
        if (!player.getEquipment().hasId(10010)) {
            player.sendMessage("You'll need a butterfly net to catch bats.");
            return;
        }
        if (!player.getInventory().hasFreeSlots(1)) {
            player.sendMessage("Not enough space in your inventory.");
            return;
        }
        player.startEvent(event -> {
            player.animate(6606);
            event.delay(2);
            if (((200 - levelReq) + player.getStats().get(StatType.Hunter).currentLevel) / 255d >= Random.get()) { // success
                player.getStats().addXp(StatType.Hunter, xp, true);
                player.collectResource(itemId, 1);
                player.getInventory().add(itemId, 1);
                player.sendFilteredMessage("You catch the bat.");
                npc.setHidden(true);
                npc.addEvent(e -> {
                    e.delay(5);
                    npc.getMovement().teleport(npc.getSpawnPosition());
                    npc.setHidden(false);
                });
            } else {
                player.sendMessage("You fail to catch the bat.");
            }
        });
    }

    static {
        for (XericBat bat : values()) {
            NPCAction.register(bat.npcId, "catch", bat::net);
        }
    }

    public int getLevelReq() {
        return levelReq;
    }

    public int getNpcId() {
        return npcId;
    }
}
