package io.ruin.model.skills.construction.actions;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.PrayerAltar;
import io.ruin.model.skills.Tool;
import io.ruin.model.skills.construction.Buildable;
import io.ruin.model.skills.construction.Hotspot;
import io.ruin.model.skills.construction.RoomDefinition;
import io.ruin.model.skills.herblore.Herb;
import io.ruin.model.skills.prayer.Bone;
import io.ruin.model.stat.StatType;

import java.util.Arrays;
import java.util.List;

import static io.ruin.model.skills.construction.Buildable.*;

public class Chapel {

    private static final List<Buildable> INCENSE_BURNERS = Arrays.asList(INCENSE_BURNER, MAHOGANY_BURNER, MARBLE_BURNER);

    static { // burners
        for (Buildable burner : INCENSE_BURNERS) {
            for (int id : new int[]{burner.getBuiltObjects()[0], burner.getBuiltObjects()[0] + 1}) {
                ObjectAction.register(id, 1, (player, obj) -> {
                    if (!player.getInventory().containsAll(false, new Item(Tool.TINDER_BOX, 1), new Item(Herb.MARRENTILL.cleanId, 1))) {
                        player.sendMessage("You'll need a tinderbox and a clean marrentill to light the burner.");
                        return;
                    }
                    player.getInventory().remove(Herb.MARRENTILL.cleanId, 1);
                    obj.set("BURNER_TIME", 250);
                    player.animate(3687);
                    player.sendFilteredMessage("You light the burner.");
                    if (obj.id == burner.getBuiltObjects()[0]) {
                        obj.setId(obj.id + 1);
                        World.startEvent(event -> {
                            while (true) {
                                int timeLeft = obj.get("BURNER_TIME", 0);
                                if (timeLeft > 0) {
                                    obj.set("BURNER_TIME", timeLeft - 1);
                                    event.delay(1);
                                } else
                                    break;
                            }
                            obj.setId(burner.getBuiltObjects()[0]);
                        });
                    }
                });
            }
        }
    }

    static {
        for (Buildable lamp : Arrays.asList(WOODEN_TORCHES, STEEL_TORCHES, STEEL_CANDLESTICKS, GOLD_CANDLESTICKS)) {
            for (int id : new int[]{lamp.getBuiltObjects()[0], lamp.getBuiltObjects()[0] + 1}) {
                ObjectAction.register(id, 1, (player, obj) -> {
                    if (!player.getInventory().contains(Tool.TINDER_BOX, 1)) {
                        player.sendMessage("You'll need a tinderbox and a clean marrentill to light the burner.");
                        return;
                    }
                    obj.set("BURNER_TIME", 250);
                    player.animate(3687);
                    if (obj.id == lamp.getBuiltObjects()[0]) {
                        obj.setId(obj.id + 1);
                        World.startEvent(event -> {
                            while (true) {
                                int timeLeft = obj.get("BURNER_TIME", 0);
                                if (timeLeft > 0) {
                                    obj.set("BURNER_TIME", timeLeft - 1);
                                    event.delay(1);
                                } else
                                    break;
                            }
                            obj.setId(lamp.getBuiltObjects()[0]);
                        });
                    }
                });
            }
        }
    }

    enum Altar {
        OAK(OAK_ALTAR, 1),
        TEAK(TEAK_ALTAR, 1.1),
        CLOTH(CLOTH_ALTAR, 1.25),
        MAHOGANY(MAHOGANY_ALTAR, 1.5),
        LIMESTONE(LIMESTONE_ALTAR, 1.75),
        MARBLE(MARBLE_ALTAR, 2),
        GILDED(GILDED_ALTAR, 2.5);

        Altar(Buildable buildable, double xpBonus) {
            this.objectIds = new int[]{buildable.getBuiltObjects()[0], buildable.getBuiltObjects()[0] + 1, buildable.getBuiltObjects()[0] + 2}; // 3 gods
            this.xpBonus = xpBonus;
        }

        int[] objectIds;
        double xpBonus; // without burners!
    }

    static { // altars
        for (Altar altar : Altar.values()) {
            for (int altarId : altar.objectIds) {
                for (Bone boneType : Bone.values()) {
                    ItemObjectAction.register(boneType.id, altarId, (player, item, obj) -> offer(player, obj, altar, boneType));
                }
                ObjectAction.register(altarId, 1, (player, obj) -> PrayerAltar.pray(player));
            }
        }
    }

    public static void offer(Player player, GameObject obj, Altar altarType, Bone boneType) {
        player.startEvent(event -> {
            while (player.getInventory().contains(boneType.id, 1)) {
                player.getInventory().remove(boneType.id, 1);
                player.animate(3705);
                player.sendFilteredMessage("The gods are pleased with your offerings.");
                double xpBonus = 0;
                xpBonus += altarType.xpBonus;
                xpBonus += getBurnersLit(player) * 0.5;
                player.getStats().addXp(StatType.Prayer, boneType.exp * xpBonus, true);
                World.sendGraphics(624, 0, 0, obj.x, obj.y, obj.z);
                boneType.altarCounter.increment(player, 1);
                event.delay(3);
            }
        });
    }

    private static int getBurnersLit(Player player) {
        if (player.getCurrentRoom() != null && player.getCurrentRoom().getDefinition() == RoomDefinition.CHAPEL) {
            Buildable built = player.getCurrentRoom().getBuilt(Hotspot.LAMP);
            if (!INCENSE_BURNERS.contains(built))
                return 0;
            return player.getCurrentRoom().getHotspotObjects(Hotspot.LAMP)
                    .stream()
                    .mapToInt(obj -> (obj.id == built.getBuiltObjects()[0] + 1) ? 1 : 0)
                    .sum();
        }
        return 0;
    }
}
