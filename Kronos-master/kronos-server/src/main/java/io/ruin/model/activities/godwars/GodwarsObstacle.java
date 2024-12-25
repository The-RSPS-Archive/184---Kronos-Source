package io.ruin.model.activities.godwars;

import io.ruin.cache.ItemID;
import io.ruin.model.entity.shared.Renders;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.Tool;
import io.ruin.model.stat.StatType;

public class GodwarsObstacle {

    static {
        /**
         * Zamorak
         */
        ObjectAction.register(26518, 2885, 5344, 2, 1, (player, obj) -> {
            if (player.getHp() < 70 || player.getStats().get(StatType.Hitpoints).fixedLevel < 70) {
                player.sendFilteredMessage("Without at least 70 Hitpoints, you would never survive the icy water.");
                return;
            }
            player.startEvent(event -> {
                player.lock();
                player.step(0, -2, StepType.FORCE_WALK);
                event.delay(2);
                player.getMovement().teleport(2885, 5343, 2);
                player.graphics(68);
                player.getAppearance().setCustomRenders(Renders.SWIM);
                player.getPacketSender().fadeOut();
                event.delay(6);
                player.getPacketSender().fadeIn();
                player.getAppearance().removeCustomRenders();
                player.getMovement().teleport(2885, 5332, 2);
                player.sendFilteredMessage("Dripping, you climb out of the water.");
                player.unlock();
            });
        });
        Tile.getObject(26518, 2885, 5344, 2).walkTo = new Position(2885, 5347, 2);
        ObjectAction.register(26518, 2885, 5333, 2, 1, (player, obj) -> {
            if (player.getHp() < 70 || player.getStats().get(StatType.Hitpoints).fixedLevel < 70) {
                player.sendFilteredMessage("Without at least 70 Hitpoints, you would never survive the icy water.");
                return;
            }

            player.startEvent(event -> {
                player.lock();
                player.step(0, 2, StepType.FORCE_WALK);
                event.delay(2);
                player.getMovement().teleport(2885, 5334, 2);
                player.graphics(68);
                player.getAppearance().setCustomRenders(Renders.SWIM);
                player.getPacketSender().fadeOut();
                event.delay(6);
                player.getPacketSender().fadeIn();
                player.getAppearance().removeCustomRenders();
                player.getMovement().teleport(2885, 5346, 2);
                player.sendFilteredMessage("Dripping, you climb out of the water.");
                if (player.getStats().get(StatType.Prayer).currentLevel > 0)
                    player.getPrayer().drain(99);
                player.sendFilteredMessage("The extreme evil of this area leaves your Prayer drained.");
                player.unlock();
            });
        });
        Tile.getObject(26518, 2885, 5333, 2).walkTo = new Position(2885, 5331, 2);

        /**
         * Bandos
         */
        ObjectAction.register(26461, 2851, 5333, 2, "open", (player, obj) -> {

            boolean hammer = player.getInventory().hasId(Tool.HAMMER) || player.getInventory().hasId(ItemID.DRAGON_WARHAMMER) || player.getEquipment().hasId(ItemID.DRAGON_WARHAMMER);
            if (!hammer) {
                player.sendFilteredMessage("You need a hammer to ring the gong.");
                return;
            }

            if (player.getAbsX() >= 2851) {
                player.startEvent(event -> {
                    player.lock();
                    player.animate(7214);
                    event.delay(2);
                    obj.remove();
                    player.stepAbs(2850, 5333, StepType.FORCE_WALK);
                    event.delay(2);
                    obj.restore();
                    player.unlock();
                });
            } else {
                player.startEvent(event -> {
                    player.lock();
                    obj.remove();
                    player.stepAbs(2851, 5333, StepType.FORCE_WALK);
                    event.delay(2);
                    obj.restore();
                    player.unlock();
                });
            }
        });

        /**
         * Armadyl
         */
        ObjectAction.register(26380, "grapple", (player, obj) -> {
            if (player.getAbsY() >= 5279)
                player.getMovement().teleport(2871, 5269, 2);
            else
                player.getMovement().teleport(2871, 5279, 2);
        });

        /**
         * Saradomin
         */
        ObjectAction.register(26561, 2913, 5300, 2, 1, (player, obj) -> {
            if (Config.GODWARS_SARADOMIN_FIRST_ROPE.get(player) == 0) {
                Item rope = player.getInventory().findItem(954);
                if (rope == null) {
                    player.sendFilteredMessage("You aren't carrying a rope with you.");
                    return;
                }
                rope.remove();
                Config.GODWARS_SARADOMIN_FIRST_ROPE.set(player, 1);
            }
        });
        ObjectAction.register(26561, 2913, 5300, 2, 2, (player, obj) -> {
            if (!player.getStats().check(StatType.Agility, 70, "use this shortcut"))
                return;
            player.startEvent(event -> {
                player.lock();
                player.animate(828);
                event.delay(2);
                player.getMovement().teleport(2914, 5300, 1);
                player.unlock();
            });
        });
        ObjectAction.register(26371, 2914, 5300, 1, 1, (player, obj) -> {
            player.startEvent(event -> {
                player.lock();
                player.animate(828);
                event.delay(1);
                player.getMovement().teleport(2912, 5299, 2);
                player.unlock();
            });
        });
        ObjectAction.register(26562, 2920, 5274, 1, 1, (player, obj) -> {
            if (Config.GODWARS_SARADOMIN_SECOND_ROPE.get(player) == 0) {
                Item rope = player.getInventory().findItem(954);
                if (rope == null) {
                    player.sendFilteredMessage("You aren't carrying a rope with you.");
                    return;
                }
                rope.remove();
                Config.GODWARS_SARADOMIN_SECOND_ROPE.set(player, 1);
            }
        });
        ObjectAction.register(26562, 2920, 5274, 1, 2, (player, obj) -> {
            player.startEvent(event -> {
                player.lock();
                player.animate(828);
                event.delay(2);
                player.getMovement().teleport(2920, 5274, 0);
                player.unlock();
            });
        });
        ObjectAction.register(26375, 2920, 5274, 0, 1, (player, obj) -> {
            player.startEvent(event -> {
                player.lock();
                player.animate(828);
                event.delay(1);
                player.getMovement().teleport(2919, 5276, 1);
                player.unlock();
            });
        });
    }
}