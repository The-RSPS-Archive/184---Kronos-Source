package io.ruin.model.activities.wintertodt;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.actions.ItemNPCAction;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.Tool;
import io.ruin.model.skills.woodcutting.Hatchet;
import io.ruin.model.stat.StatType;

import java.util.Arrays;
import java.util.List;

public class WintertodtActions {

    private static final List<Integer> REJUVENATION_POTION = Arrays.asList(20699, 20700, 20701, 20702);

    static {
        ObjectAction.register(29322, 1, ((player, obj) -> {
            if(!player.talkedToIgnisia) {
                player.dialogue(new PlayerDialogue("Maybe I should figure out where this leads before entering.."));
                player.sendMessage("You must talk to Ignisia before entering.");
                return;
            }

            boolean entering = player.getAbsY() < 3966;
            if (entering) {
                if (!player.getStats().check(StatType.Firemaking, 50, "assist in subduing Wintertodt")) {
                    return;
                }
                transition(player, entering);
            } else {
                player.dialogue(new OptionsDialogue("Are you sure you want to leave?",
                        new Option("Leave and lose all progress.", () -> transition(player, entering)),
                        new Option("Stay.", () -> {
                        })));
            }
        }));

        ItemItemAction.register(Wintertodt.REJUV_POT, Wintertodt.BRUMA_HERB, WintertodtActions::makePot);
        ItemItemAction.register(Tool.KNIFE, Wintertodt.BRUMA_ROOT, (player, primary, secondary) -> fletch(player));
        ObjectAction.register(29315, 1, (player, obj) -> pickHerbs(player));
        ObjectAction.register(29311, 1, (player, obj) -> cutRoots(player));

        for (int i = 0; i <= 4; i += 2)
            Tile.get(1628 + i, 4023, 0).flagUnmovable();
        Tile.get(1629, 4022, 0, true).flagUnmovable();
        Tile.get(1629, 4024, 0, true).flagUnmovable();
        Tile.get(1631, 4022, 0, true).flagUnmovable();
        Tile.get(1631, 4024, 0, true).flagUnmovable();

        ObjectAction.register(29326, "jump", WintertodtActions::jumpGap);
        REJUVENATION_POTION.forEach(id -> {
            ItemAction.registerInventory(id, 5, delete("vial"));
            ItemNPCAction.register(id, 7371, WintertodtActions::healPyromancer);
            ItemNPCAction.register(id, Wintertodt.INCAPACITATED_PYROMANCER, (player, item, npc) -> resurrectPyromancer(player, npc));
        });
        NPCAction.register(7372, 1, WintertodtActions::resurrectPyromancer);

        ItemAction.registerInventory(Wintertodt.BRUMA_HERB, 5, delete("herb"));
        ItemAction.registerInventory(Wintertodt.REJUV_POT, 5, delete("vial"));
        ItemAction.registerInventory(Wintertodt.BRUMA_KINDLING, 5, delete("kindling"));
        ItemAction.registerInventory(Wintertodt.BRUMA_ROOT, 5, delete("root"));

        ObjectAction.register(29320, 1, (player, obj) -> potionCrate(player, 1));
        ObjectAction.register(29320, 2, (player, obj) -> potionCrate(player, 5));
        ObjectAction.register(29320, 3, (player, obj) -> potionCrate(player, 10));

        ObjectAction.register(29316, 1, (player, obj) -> singleItemCrate(player, Tool.HAMMER, "a hammer"));
        ObjectAction.register(29317, 1, (player, obj) -> singleItemCrate(player, Tool.KNIFE, "a knife"));
        ObjectAction.register(29318, 1, (player, obj) -> singleItemCrate(player, 1351, "an axe"));
        ObjectAction.register(29319, 1, (player, obj) -> singleItemCrate(player, Tool.TINDER_BOX, "a tinderbox"));

        for (Brazier b : Wintertodt.BRAZIERS) {
            ObjectAction.register(b.getObject(), 1, (player, obj) -> {
                if (!activeCheck(player)) {
                    return;
                }
                if (obj.id == Wintertodt.BURNING_BRAZIER) {
                    feed(player, b);
                } else if (obj.id == Wintertodt.EMPTY_BRAZIER) {
                    light(player, b);
                } else {
                    fix(player, b);
                }
            });
        }
    }

    private static void feed(Player player, Brazier b) {
        if (!player.getInventory().contains(Wintertodt.BRUMA_ROOT, 1) && !player.getInventory().contains(Wintertodt.BRUMA_KINDLING, 1)) {
            player.sendMessage("You don't have any bruma roots.");
            return;
        }
        player.startEvent(event -> {
            while (true) {
                if (b.getObject().id != Wintertodt.BURNING_BRAZIER) {
                    player.sendMessage("The brazier has gone out.");
                    player.resetAnimation();
                    return;
                }
                if (!player.getInventory().contains(Wintertodt.BRUMA_ROOT, 1) && !player.getInventory().contains(Wintertodt.BRUMA_KINDLING, 1)) {
                    player.sendMessage("You have run out of bruma roots.");
                    return;
                }
                player.animate(832);
                event.delay(2);
                int baseExperience = player.getStats().get(StatType.Firemaking).currentLevel + 5;
                if (player.getInventory().remove(Wintertodt.BRUMA_KINDLING, 1) > 0) {
                    player.getStats().addXp(StatType.Firemaking, (player.getStats().get(StatType.Firemaking).fixedLevel * 3.8) + baseExperience, true);
                    Wintertodt.addPoints(player, 25);
                } else {
                    player.getInventory().remove(Wintertodt.BRUMA_ROOT, 1);
                    player.getStats().addXp(StatType.Firemaking, (player.getStats().get(StatType.Firemaking).fixedLevel * 3) + baseExperience, true);
                    Wintertodt.addPoints(player, 10);
                }
                event.delay(2);
            }
        });
    }

    private static void light(Player player, Brazier b) {
        if (!player.getInventory().contains(Tool.TINDER_BOX, 1)) {
            player.sendMessage("You need a tinderbox to light that brazier.");
            return;
        }
        if (!b.isPyromancerAlive()) {
            player.sendMessage("You must heal the Pyromancer before lighting the brazier.");
            return;
        }
        player.startEvent(event -> {
            player.animate(733);
            event.delay(3);
            /* the pyromancer may die during this delay */
            if (!b.isPyromancerAlive()) {
                player.sendMessage("You must heal the Pyromancer before lighting the brazier.");
                return;
            }
            b.getObject().setId(Wintertodt.BURNING_BRAZIER);
            player.resetAnimation();
            player.getStats().addXp(StatType.Firemaking, player.getStats().get(StatType.Firemaking).fixedLevel * 6, true);
            Wintertodt.addPoints(player, 25);
            player.sendMessage("You light the brazier.");
        });
    }

    private static void fix(Player player, Brazier b) {
        if (!player.getInventory().contains(Tool.HAMMER, 1)) {
            player.sendMessage("You need a hammer to repair that brazier.");
            return;
        }
        player.startEvent(event -> {
            player.animate(3676);
            event.delay(3);
            b.getObject().setId(Wintertodt.EMPTY_BRAZIER);
            player.resetAnimation();
            player.sendMessage("You fix the brazier.");
            Wintertodt.addPoints(player, 25);
            player.getStats().addXp(StatType.Construction, player.getStats().get(StatType.Construction).currentLevel * 4, true);
        });
    }

    private static void fletch(Player player) {
        player.startEvent(event -> {
            while (player.getInventory().contains(Wintertodt.BRUMA_ROOT, 1)) {
                player.animate(1248);
                event.delay(3);
                player.getInventory().remove(Wintertodt.BRUMA_ROOT, 1);
                player.getInventory().add(Wintertodt.BRUMA_KINDLING, 1);
                player.getStats().addXp(StatType.Fletching, player.getStats().get(StatType.Fletching).fixedLevel * 0.6, true);
            }
        });
    }

    private static void transition(Player player, boolean entering) {
        World.startEvent(event -> {
            player.lock();
            player.getPacketSender().fadeOut();
            event.delay(2);
            if (entering)
                player.getMovement().teleport(1630, 3982, 0);
            else {
                player.getMovement().teleport(1630, 3958, 0);
                player.wintertodtPoints = 0;
            }
            player.getPacketSender().fadeIn();
            event.delay(2);
            player.closeInterfaces();
            player.unlock();
        });
    }

    private static ItemAction delete(String name) {
        return (player, item) -> {
            item.remove();
            player.sendMessage("The " + name + " shatters as it hits the floor.");
        };
    }

    private static void makePot(Player player, Item primary, Item secondary) {
        primary.setId(20699);
        secondary.remove();
        player.getStats().addXp(StatType.Herblore, 1, true);
    }


    private static void pickHerbs(Player player) {
        if (!activeCheck(player)) {
            return;
        }
        if (player.getInventory().isFull()) {
            player.sendMessage("Not enough space in your inventory.");
            return;
        }
        player.startEvent(event -> {
            while (!player.getInventory().isFull() && Wintertodt.isActive()) {
                player.animate(2282);
                event.delay(3);
                player.getInventory().add(Wintertodt.BRUMA_HERB, 1);
                player.sendMessage("You pick a bruma herb.");
                player.getStats().addXp(StatType.Farming, 1, true);
            }
        });
    }

    private static void cutRoots(Player player) {
        if (!activeCheck(player)) {
            return;
        }
        Hatchet hatchet = Hatchet.find(player);
        if (hatchet == null) {
            player.sendMessage("You do not have an axe which you have the Woodcutting level to use.");
            return;
        }
        if (player.getInventory().isFull()) {
            player.sendMessage("Not enough space in your inventory.");
            return;
        }
        player.startEvent(event -> {
            while (!player.getInventory().isFull() && Wintertodt.isActive()) {
                player.animate(hatchet.animationId);
                event.delay(3);
                if (Random.rollDie(100, 20 + hatchet.points)) {
                    player.collectResource(Wintertodt.BRUMA_ROOT, 1);
                    player.getInventory().add(Wintertodt.BRUMA_ROOT, 1);
                    player.sendMessage("You get a bruma root.");
                    player.getStats().addXp(StatType.Woodcutting, player.getStats().get(StatType.Woodcutting).fixedLevel * 0.3, true);
                }
            }
        });
    }

    private static boolean activeCheck(Player player) {
        if (!Wintertodt.isActive()) {
            player.sendMessage("There's no need to do that at this time.");
            return false;
        }
        return true;
    }

    private static void healPyromancer(Player player, Item item, NPC npc) {
        if (!activeCheck(player)) {
            return;
        }
        if (npc.getHp() == npc.getMaxHp()) {
            player.sendMessage("The Pyromancer doesn't need any healing right now.");
            return;
        }
        item.setId(item.getId() == 20702 ? 229 : item.getId() + 1);
        npc.setHp(Math.min(npc.getMaxHp(), npc.getHp() + 5));
        Wintertodt.addPoints(player, 30);
    }

    private static void resurrectPyromancer(Player player, NPC npc) {
        if (!activeCheck(player)) {
            return;
        }
        Item potion = player.getInventory().findFirst(20699, 20700, 20701, 20702);
        if (potion == null) {
            player.sendMessage("You'll need a rejuvenation potion to heal the Pyromancer.");
            return;
        }
        potion.setId(potion.getId() == 20702 ? 229 : potion.getId() + 1);
        npc.transform(7371);
        npc.setHp(npc.getMaxHp());
    }

    private static void jumpGap(Player player, GameObject obj) {
        if(player.getStats().get(StatType.Agility).currentLevel < 60) {
            player.dialogue(new ItemDialogue().one(6514, "You need an agility level of 60 to jump across this pillar."));
            return;
        }
        boolean west = player.getAbsX() < obj.x;
        player.startEvent(event -> {
            event.delay(1);
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.animate(741);
            player.getMovement().force(west ? 2 : -2, 0, 0, 0, 15, 30, west ? Direction.EAST : Direction.WEST);
            event.delay(1);
            player.getMovement().teleport(west ? obj.x + 1 : obj.x - 1, obj.y, obj.z);
            player.getStats().addXp(StatType.Agility, 18, true);
            player.unlock();
        });
    }

    private static void singleItemCrate(Player player, int item, String name) {
        if (player.getInventory().contains(item, 1)) {
            player.sendMessage("You already have " + name + ".");
        } else if (player.getInventory().isFull()) {
            player.sendMessage("Not enough space in your inventory.");
        } else {
            player.getInventory().add(item, 1);
            player.sendMessage("You take " + name + " from the chest.");
        }
    }

    private static void potionCrate(Player player, int amount) {
        if (player.getInventory().isFull()) {
            player.sendMessage("Not enough space in your inventory.");
            return;
        }
        amount = Math.min(player.getInventory().getFreeSlots(), amount);
        player.getInventory().add(Wintertodt.REJUV_POT, amount);
        player.sendMessage("You take the unfinished potion" + (amount > 1 ? "s" : "") + " from the chest");
    }

}
