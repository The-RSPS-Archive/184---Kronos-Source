package io.ruin.model.activities.jail;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemNPCAction;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

public class Jail {

    private static final int GUARD_ID = 5442, ROCK = 968;

    private static final Bounds BOUNDS = new Bounds(3283, 9427, 3292, 9452, 0);

    static {
        /**
         * Gate
         */
        int gateX = 3292, gateY = 9434;
        GameObject[] gates = {
                Tile.getObject(2686, gateX, gateY, 0),
                Tile.getObject(2685, gateX, gateY + 1, 0),
        };
        for(GameObject gate : gates) {
            ObjectAction.register(gate, "Open", (player, obj) -> player.sendMessage("The gate is locked."));
            ObjectAction.register(gate, "Search", (player, obj) -> player.sendMessage("You search the gate but find nothing."));
        }
        /**
         * Guard
         */
        NPC guard = new NPC(GUARD_ID).spawn(3287, 9440, 0, Direction.SOUTH, 3);
        guard.skipReachCheck = pos -> pos.equals(gateX, gateY, 0) || pos.equals(gateX, gateY + 1, 0);
        NPCAction.register(guard, "Talk-to", Jail::talkToGuard);
        ItemNPCAction.register(guard, Jail::itemOnGuard);
        /**
         * Slaves
         */
        NPC[] slaves = {
                new NPC(4670).spawn(3286, 9430, 0, Direction.SOUTH, 0),
                new NPC(4671).spawn(3291, 9445, 0, Direction.NORTH, 0),
                new NPC(4672).spawn(3289, 9438, 0, Direction.EAST, 0),
                new NPC(4673).spawn(3285, 9452, 0, Direction.WEST, 0),
        };
        for(NPC slave : slaves)
            NPCAction.register(slave, "Talk-to", (player, npc) -> player.dialogue(new NPCDialogue(GUARD_ID, "No socializing prisoner, get to work!")));
        World.startEvent(e -> {
            while(true) {
                for(NPC slave : slaves)
                    slave.animate(627);
                e.delay(10);
            }
        });
        /**
         * Mine care
         */
        ObjectAction.register(6045, 1, (player, obj) -> {
            if(player.jailOresAssigned > 0) {
                Item rock = player.getInventory().findItem(ROCK);
                if(rock == null) {
                    player.dialogue(new ItemDialogue().one(ROCK, "You don't have any rocks to dump."));
                    return;
                }
                int count = rock.count();
                player.startEvent(event -> {
                    player.lock();
                    player.getInventory().remove(ROCK, count);
                    player.animate(832);
                    event.delay(1);
                    player.jailOresCollected += count;
                    player.dialogue(new ItemDialogue().one(ROCK, "You dump " + count + " " + (count == 1 ? "rock" : "rocks") + "."));
                    player.unlock();
                });
            }
        });
        /**
         * Listener
         */
        LoginListener.register(player -> {
            if(player.jailerName != null)
                startEvent(player);
        });
    }

    public static void startEvent(Player player) {
        if(!player.getPosition().inBounds(BOUNDS))
            player.getMovement().teleport(3289, 9435, 0);
        player.teleportListener = p -> {
            p.sendMessage("You must finish your jail sentence before you can leave.");
            return false;
        };
        player.deathEndListener = (DeathListener.Simple) () -> {}; //no escaping lol
        player.addEvent(e -> {
            while(player.jailOresCollected < player.jailOresAssigned)
                e.delay(10);
            player.jailerName = null;
            player.jailOresAssigned = player.jailOresCollected = 0;
            player.teleportListener = null;
            player.deathEndListener = null;
            player.getMovement().teleport(3092, 3492, 0);
            player.sendMessage(Color.OLIVE.tag() + "Your jail sentence is up.");
        });
    }

    private static void talkToGuard(Player player, NPC npc) {
        if(player.jailerName == null) {
            player.dialogue(new NPCDialogue(npc, "I love my job."));
            return;
        }
        if(player.jailOresCollected >= player.jailOresAssigned) {
            player.dialogue(new NPCDialogue(npc, "Your sentence is up, you will be able to leave shortly."));
            return;
        }
        player.dialogue(
                new NPCDialogue(npc, "You were sentenced to " + player.jailOresAssigned + " rocks by " + player.jailerName + "."),
                new NPCDialogue(npc, "Rocks Remaining: " + (player.jailOresAssigned - player.jailOresCollected))
        );
    }

    private static void itemOnGuard(Player player, Item item, NPC npc) {
        if(player.jailOresAssigned > 0) {
            if(item.getId() == ROCK) {
                int count = 0;
                for(Item i : player.getInventory().getItems()) {
                    if(i != null && i.getId() == item.getId()) {
                        count++;
                        i.remove();
                    }
                }
                player.jailOresCollected += count;
                player.dialogue(new ItemDialogue().one(ROCK, "You turn in " + count + " " + (count == 1 ? "rock" : "rocks") + "."));
                return;
            }
        }
        player.sendMessage("Nothing interesting happens.");
    }

    /**
     * Mining
     */

    static {
        ObjectAction.register(2704, "Mine", (player, obj) -> mine(player));
        ObjectAction.register(2704, "Prospect", (player, obj) -> player.sendMessage("It's a rock."));
    }

    private static void mine(Player player) {
        player.startEvent(event -> {
            player.animate(627);
            player.sendFilteredMessage("You swing your pick at the rock.");
            event.delay(Random.get(3, 8));
            while(true) {
                player.animate(627);
                player.getInventory().add(ROCK, 1);
                player.sendFilteredMessage("You manage to mine some rock.");
                event.delay(Random.get(3, 8));
            }
        });
    }

}