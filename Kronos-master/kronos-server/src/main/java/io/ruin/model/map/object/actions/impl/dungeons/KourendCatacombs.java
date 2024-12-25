package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.Server;
import io.ruin.api.utils.Random;
import io.ruin.model.activities.bosses.Skotizo;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;
import io.ruin.model.skills.prayer.Bone;
import io.ruin.model.stat.StatType;

public class KourendCatacombs {

    private static final Bounds CATACOMBS_BOUNDS = new Bounds(1578, 9960, 1760, 10110, 0);

    static { // Object actions

        int[] totemPieces = {19679, 19681, 19683};
        for (int id1: totemPieces) {
            for (int id2 : totemPieces) {
                if (id1 == id2)
                    continue;
                ItemItemAction.register(id1, id2, (player, primary, secondary) -> {
                    for (int piece : totemPieces) {
                        if (!player.getInventory().contains(piece, 1)) {
                            player.sendMessage("You'll need a base, middle, and top to complete the dark totem.");
                            return;
                        }
                    }
                    player.getInventory().remove(19679, 1);
                    player.getInventory().remove(19681, 1);
                    player.getInventory().remove(19683, 1);
                    player.getInventory().add(19685, 1);
                    player.sendMessage("You combine the pieces to create a complete totem.");
                });
            }
        }

        //Skotizo boss creation
        ObjectAction.register(28900, "teleport", KourendCatacombs::startSkotizoFight);

        //Main Entrance
        ObjectAction.register(27785, "investigate", (player, obj) -> Ladder.climb(player, 1665, 10050, 0, false, true, false));
        ObjectAction.register(28894, "climb-up", (player, obj) -> Ladder.climb(player, 1639, 3673, 0, true, true, false));

        //Secondary entrances/exits
        ObjectAction.register(28895, "climb-up", (player, obj) -> { // NW
            Ladder.climb(player, 1562, 3791, 0, true, true, false);
            Config.CATACOMBS_ENTRANCE_NW.set(player, 1);
        });
        ObjectAction.register(28921, 1, (player, obj) -> {
            if (Config.CATACOMBS_ENTRANCE_NW.get(player) == 1) {
                Ladder.climb(player, 1617, 10101, 0, false, true, false);
            }
        });

        ObjectAction.register(28896, "climb-up", (player, obj) -> { // SW
            Ladder.climb(player, 1469, 3653, 0, true, true, false);
            Config.CATACOMBS_ENTRANCE_SW.set(player, 1);
        });
        ObjectAction.register(28919, 1, (player, obj) -> {
            if (Config.CATACOMBS_ENTRANCE_SW.get(player) == 1) {
                Ladder.climb(player, 1650, 9987, 0, false, true, false);
            }
        });

        ObjectAction.register(28897, "climb-up", (player, obj) -> { // SE
            Ladder.climb(player, 1667, 3565, 0, true, true, false);
            Config.CATACOMBS_ENTRANCE_SE.set(player, 1);
        });
        ObjectAction.register(28918, 1, (player, obj) -> {
            if (Config.CATACOMBS_ENTRANCE_SE.get(player) == 1) {
                Ladder.climb(player, 1725, 9993, 0, false, true, false);
            }
        });

        ObjectAction.register(28898, "climb-up", (player, obj) -> { // NE
            Ladder.climb(player, 1696, 3864, 0, true, true, false);
            Config.CATACOMBS_ENTRANCE_NE.set(player, 1);
        });
        ObjectAction.register(28920, 1, (player, obj) -> {
            if (Config.CATACOMBS_ENTRANCE_NE.get(player) == 1) {
                Ladder.climb(player, 1719, 10101, 0, false, true, false);
            }
        });

        //Shortcuts
        ObjectAction.register(28893, "jump-to", (player, obj) -> { // Stepping stones
            if (!player.getStats().check(StatType.Agility, 34, "use this shortcut"))
                return;
            Position objPosition = new Position(obj.x,obj.y,obj.z);
            try {
                Direction movementDirection = Direction.getDirection(player.getPosition(), objPosition);
                player.animate(741);
                player.getMovement().force(movementDirection.deltaX, movementDirection.deltaY, 0, 0, 15, 30, movementDirection);
            } catch (Exception e) {
                Server.logError("Error invoking forced movement: player="+ player.getPosition().toString() +", obj="+ obj.toString());
            }
        });

        ObjectAction.register(28892, 1706, 10077, 0, "squeeze-through", (player, obj) -> squeezeThroughCrack(player, obj, new Position(1716, 10056, 0), 34)); // crack
        ObjectAction.register(28892, 1716, 10057, 0, "squeeze-through", (player, obj) -> squeezeThroughCrack(player, obj, new Position(1706, 10078, 0), 34)); // crack

        ObjectAction.register(28892, 1646, 10001, 0, "squeeze-through", (player, obj) -> squeezeThroughCrack(player, obj, new Position(1648, 10009, 0), 25)); // crack
        ObjectAction.register(28892, 1648, 10008, 0, "squeeze-through", (player, obj) -> squeezeThroughCrack(player, obj, new Position(1646, 10000, 0), 25)); // crack

    }

    private static void startSkotizoFight(Player player, GameObject gameObject) {
        if (!player.getInventory().contains(19685, 1)) {
            player.dialogue(new ItemDialogue().one(19685, "A completed dark totem is required to activate the altar."));
            return;
        }
        player.dialogue(new MessageDialogue("WARNING: You are about to enter Skotizo's lair.<br>Your dark totem will be consumed.<br><br>Are you sure you want to continue?"), new OptionsDialogue(new Option("Yes.", () -> {
            if (!player.getInventory().contains(19685, 1)) {
                return;
            }
            player.getInventory().remove(19685, 1);
            Skotizo.startFight(player, gameObject);
        }), new Option("No.")));
    }


    private static void squeezeThroughCrack(Player player, GameObject crack, Position destination, int levelReq) {
        if (!player.getStats().check(StatType.Agility, levelReq, "use this shortcut"))
            return;
        player.lock();
        player.startEvent(event -> {
            player.face(crack);
            player.animate(746);
            event.delay(1);
            player.getMovement().teleport(destination);
            player.animate(748);
            event.delay(1);
            player.unlock();
        });
    }

    public static int getNextTotemPiece(Player player) {
        int base = 0, middle = 0, top = 0;
        for (Item item : player.getInventory().getItems()) {
            if (item == null)
                continue;
            if (item.getId() == 19679)
                base += item.getAmount();
            else if (item.getId() == 19681)
                middle += item.getAmount();
            else if (item.getId() == 19683)
                top += item.getAmount();
        }
        for (Item item : player.getBank().getItems()) {
            if (item == null)
                continue;
            if (item.getId() == 19679)
                base += item.getAmount();
            else if (item.getId() == 19681)
                middle += item.getAmount();
            else if (item.getId() == 19683)
                top += item.getAmount();
        }

        int lowest = Math.min(base, Math.min(middle, top));
        if (lowest == base)
            return 19679;
        else if (lowest == middle)
            return 19681;
        else return 19683;
    }


    public static void drop(Player pKiller, NPC npc, Position dropPosition) {
        if (!npc.getPosition().inBounds(CATACOMBS_BOUNDS))
            return;
        if (rollTotemDrop(pKiller, npc)) {
            int nextPiece = getNextTotemPiece(pKiller);
            if (nextPiece > 0) {
                GroundItem item = new GroundItem(nextPiece, 1).position(dropPosition).owner(pKiller).spawn();

            }
        }
        if (rollShardDrop(pKiller, npc)) {
            GroundItem item = new GroundItem(19677, 1).position(dropPosition).owner(pKiller).spawn();
        }

    }

    private static boolean rollTotemDrop(Player pKiller, NPC npc) {
        double chance = 1d / (500 - Math.min(400, npc.getMaxHp()));
        return Random.get() <= chance;
    }

    private static boolean rollShardDrop(Player pKiller, NPC npc) {
        double chance = 1d / (2d/3 * (500 - Math.min(400, npc.getMaxHp())));
        return Random.get() <= chance;
    }


    public static void buriedBone(Player player, Bone bone) {
        if (!player.getPosition().inBounds(CATACOMBS_BOUNDS))
            return;
        if (bone.exp < 15)
            player.getStats().get(StatType.Prayer).restore(1, 0);
        else if (bone.exp < 72)
            player.getStats().get(StatType.Prayer).restore(2, 0);
        else
            player.getStats().get(StatType.Prayer).restore(4, 0);
    }
}
