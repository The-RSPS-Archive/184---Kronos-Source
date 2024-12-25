package io.ruin.model.activities.wilderness;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Region;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

public enum WildernessObelisk {

    LEVEL_13(14829, 3156, 3620),
    LEVEL_19(14830, 3227, 3667),
    LEVEL_27(14827, 3035, 3732),
    LEVEL_35(14828, 3106, 3794),
    LEVEL_44(14826, 2980, 3866),
    LEVEL_50(14831, 3307, 3916);

    public final int id;
    public final Bounds bounds;
    public final GameObject[] pillars;
    private boolean active;

    WildernessObelisk(int id, int centerX, int centerY) {
        this.id = id;
        this.bounds = new Bounds(centerX, centerY, 0, 1);
        this.pillars = new GameObject[]{
                Tile.getObject(id, centerX - 2, centerY - 2, 0),
                Tile.getObject(id, centerX - 2, centerY + 2, 0),
                Tile.getObject(id, centerX + 2, centerY - 2, 0),
                Tile.getObject(id, centerX + 2, centerY + 2, 0),
        };
    }

    private void activate(Player player, WildernessObelisk nextObelisk) {
        if(active)
            return;
        active = true;
        for(GameObject pillar : pillars) {
            pillar.setId(14825);
            player.privateSound(204, 1, 10);
        }
        World.startEvent(event -> {
            event.delay(11);
            Region region = bounds.getRegion();
            for(Player p : region.players) {
                if(p.getPosition().inBounds(bounds))
                    teleport(p, nextObelisk);
            }
            for(GameObject pillar : pillars)
                pillar.setId(pillar.originalId);
            active = false;
        });
    }

    private WildernessObelisk randomObelisk() {
        WildernessObelisk[] obelisks = values();
        WildernessObelisk randomObelisk = Random.get(obelisks);
        if(randomObelisk == this) {
            int index = ordinal() + 1;
            if(index >= obelisks.length)
                index = 0;
            randomObelisk = obelisks[index];
        }
        return randomObelisk;
    }

    public static void teleport(Player player, WildernessObelisk obelisk) {
        player.getMovement().startTeleport(-1, event -> {
            player.animate(3945);
            event.delay(2);
            player.getMovement().teleport(obelisk.bounds);
            player.sendMessage("Ancient magic teleports you to a place within the wilderness!");
        });
    }

    private static void setDestination(Player player, WildernessObelisk obelisk, String name) {
        player.obeliskDestination = obelisk;
        player.sendFilteredMessage(Color.DARK_RED.wrap("You have set your obelisk destination to " + name + " wilderness."));
    }

    static {
        for(WildernessObelisk obelisk : values()) {
            for(GameObject pillar : obelisk.pillars) {
                ObjectAction.register(pillar.id, "activate", (player, obj) -> obelisk.activate(player, obelisk.randomObelisk()));
                ObjectAction.register(pillar.id, "set destination", (player, obj) -> chooseDestination(player));
                ObjectAction.register(pillar.id, "teleport to destination", (player, obj) -> {
                    if(player.obeliskRedirectionScroll || player.getEquipment().getId(Equipment.SLOT_WEAPON) == 13111) {
                        if(player.obeliskDestination == null)
                            player.sendFilteredMessage("You need to set a destination before attempting to do this.");
                        else
                            obelisk.activate(player, player.obeliskDestination);
                        return;
                    }
                    player.sendFilteredMessage("You need learn how to set an obelisk destination before attempting this.");
                });
            }
        }
    }

    public static void chooseDestination(Player player) {
        if(player.obeliskRedirectionScroll || player.getEquipment().getId(Equipment.SLOT_WEAPON) == 13111) {
            OptionScroll.open(player, "Select an Obelisk to teleport to",
                    new Option("Level 13 Wilderness", () -> setDestination(player, LEVEL_13, "level 13")),
                    new Option("Level 19 Wilderness", () -> setDestination(player, LEVEL_19, "level 19")),
                    new Option("Level 27 Wilderness", () -> setDestination(player, LEVEL_27, "level 27")),
                    new Option("Level 35 Wilderness", () -> setDestination(player, LEVEL_35, "level 35")),
                    new Option("Level 44 Wilderness", () -> setDestination(player, LEVEL_44, "level 44")),
                    new Option("Level 50 Wilderness", () -> setDestination(player, LEVEL_50, "level 50"))
            );
            return;
        }
        player.sendFilteredMessage("You need learn how to set an obelisk destination before attempting this.");
    }
}
