package io.ruin.model.skills.fishing.lakemolch;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.MapListener;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.Tool;
import io.ruin.model.stat.StatType;

import java.util.ArrayList;
import java.util.List;

public enum ArielFishing {

    BLUEGILL(43, 11.5, 35, 16.5, 3.5, 22826, "Bluegill"),
    COMMON_TENCH(56, 40, 51, 45, 10, 22829, "Common tench"),
    MOTTLED_EEL(73, 65, 68, 90, 20, 22832, "Mottled eel"),
    GREATER_SIRE(91, 100, 87, 130, 25, 22835, "Greater sire");

    private final int fishLevel, hunterLevel, fishId;
    private final double fishExp, hunterExp, cookingExp;
    private String name;

    ArielFishing(int fishLevel, double fishExp, int hunterLevel, double hunterExp, double cookingExp, int fishId, String name) {
        this.fishLevel = fishLevel;
        this.fishExp = fishExp;
        this.hunterLevel = hunterLevel;
        this.hunterExp = hunterExp;
        this.cookingExp = cookingExp;
        this.fishId = fishId;
        this.name = name;
    }

    private static final int FISHING_SPOT = 8523;
    private static final Bounds SOUTH_BOUNDS = new Bounds(1360, 3618, 1378, 3624, 0);
    private static final Bounds WEST_BOUNDS = new Bounds(1351, 3623, 1358, 3637, 0);
    private static final Bounds EAST_BOUNDS = new Bounds(1377, 3622, 1384, 3637, 0);
    private static List<NPC> SOUTH_SPAWNS = new ArrayList<>();
    private static List<NPC> WEST_SPAWNS = new ArrayList<>();
    private static List<NPC> EAST_SPAWNS = new ArrayList<>();
    private static final int FISH_CHUNKS = 22818;
    private static final int KING_WORM = 2162;
    private static final int CORMORANTS_GLOVES = 22816;
    private static final int CORMORANTS_GLOVE_BIRD = 22817;
    private static final int GOLDEN_TENCH = 22840;
    private static final int MOLCH_PEARL = 22820;

    static {
        /**
         * Fishing spot spawning
         */
        for(int i = 0; i < 7; i ++) {
            SOUTH_SPAWNS.add(i, new NPC(FISHING_SPOT));
            WEST_SPAWNS.add(i, new NPC(FISHING_SPOT));
            EAST_SPAWNS.add(i, new NPC(FISHING_SPOT));
        }

        SOUTH_SPAWNS.forEach(it -> it.spawn(getRandomSpawn(SOUTH_BOUNDS)));
        WEST_SPAWNS.forEach(it -> it.spawn(getRandomSpawn(WEST_BOUNDS)));
        EAST_SPAWNS.forEach(it -> it.spawn(getRandomSpawn(EAST_BOUNDS)));

        SpawnListener.register(FISHING_SPOT, npc ->  {
            npc.skipMovementCheck = true;
            if(npc.getPosition().inBounds(SOUTH_BOUNDS))
                npc.spawnBounds = SOUTH_BOUNDS;
            else if(npc.getPosition().inBounds(EAST_BOUNDS))
                npc.spawnBounds = EAST_BOUNDS;
            else if(npc.getPosition().inBounds(WEST_BOUNDS))
                npc.spawnBounds = WEST_BOUNDS;
            npc.addEvent(e -> {
                while(true) {
                    if(!npc.isLocked())
                        npc.getMovement().teleport(getRandomSpawn(npc.spawnBounds));
                    e.delay(Random.get(10, 15));
                }
            });
        });

        /**
         * Catching
         */
        NPCAction.register(FISHING_SPOT, "catch", (player, npc) -> {
            if(!player.getStats().check(StatType.Fishing, BLUEGILL.fishLevel, BLUEGILL.fishId, "attempt this"))
                return;
            if(!player.getStats().check(StatType.Hunter, BLUEGILL.hunterLevel, BLUEGILL.fishId, "attempt this"))
                return;
            int weapon = player.getEquipment().get(Equipment.SLOT_WEAPON) == null ? -1 : player.getEquipment().get(Equipment.SLOT_WEAPON).getId();
            if(weapon != CORMORANTS_GLOVES && weapon != CORMORANTS_GLOVE_BIRD) {
                player.dialogue(new PlayerDialogue("I should speak with Alry the Angler before attempting this.."));
                return;
            }
            if(weapon == CORMORANTS_GLOVES) {
                player.sendMessage("You need to wait for your cormorant to return before trying to catch any more fish.");
                return;
            }
            if(!player.getInventory().hasId(FISH_CHUNKS) && !player.getInventory().hasId(KING_WORM)) {
                player.sendMessage("It wouldn't be fair to send the cormorant out to work with nothing to reward it.");
                return;
            }
            if(player.getInventory().isFull()) {
                player.sendMessage("You don't have enough inventory space to do that.");
                return;
            }
            npc.lock();
            Position npcPos = npc.getPosition();
            player.lock();
            World.startEvent(worldEvent -> {
                player.faceTemp(npc);
                player.animate(5162);
                worldEvent.delay(1);
                player.unlock();
                player.sendFilteredMessage("You send your cormorant to try and catch a fish from out to sea.");
                player.getEquipment().get(Equipment.SLOT_WEAPON).setId(CORMORANTS_GLOVES);
                int playerTileDist = player.getPosition().distance(npcPos);
                int sendDelay =  Math.max(1, (30 + (playerTileDist * 12)) / 35);
                new Projectile(1632, 30, 0, 0, 31, playerTileDist, 5, 64).send(player, npcPos);
                World.sendGraphics(1633, 0, sendDelay * 25, npcPos);
                npc.addEvent(npcEvent -> {
                    npcEvent.delay(sendDelay);
                    if(!npc.isHidden()) {
                        npc.setHidden(true);
                        npc.unlock();
                        npcEvent.delay(Random.get(4, 10));
                        npc.setHidden(false);
                    }
                });
                worldEvent.delay(sendDelay);
                int npcTileDistance = npcPos.distance(player.getPosition());
                int returnDelay = Math.max(1, (30 + (npcTileDistance * 12)) / 37);
                new Projectile(1632, 0, 30, 10, 31, npcTileDistance, 10, 64).send(npc, player);
                worldEvent.delay(returnDelay);
                player.sendMessage("Your cormorant returns with it's catch.");
                ArielFishing reward = rollForFish(player);
                player.getStats().addXp(StatType.Fishing, reward.fishExp * anglerBonus(player), true);
                player.getStats().addXp(StatType.Hunter, reward.hunterExp, true);
                player.getInventory().add(reward.fishId, 1);
                if(Random.rollDie(10, 3)) {
                    player.getInventory().addOrDrop(MOLCH_PEARL, 1);
                }
                rollToFeed(player);
                if(player.getEquipment().get(Equipment.SLOT_WEAPON).getId() == CORMORANTS_GLOVES)
                    player.getEquipment().get(Equipment.SLOT_WEAPON).setId(CORMORANTS_GLOVE_BIRD);
                else {
                    Item cormorantGloves = player.getInventory().findItem(CORMORANTS_GLOVES);
                    if(cormorantGloves != null)
                        cormorantGloves.setId(CORMORANTS_GLOVE_BIRD);
                }
            });
        });

        for(ArielFishing fish : ArielFishing.values()) {
            ItemItemAction.register(fish.fishId, Tool.KNIFE, (player, primary, secondary) -> {
                player.getInventory().remove(fish.fishId, 1);
                player.getInventory().add(FISH_CHUNKS, 1);
                player.getStats().addXp(StatType.Cooking, fish.cookingExp, true);
                player.sendMessage("You cut a " + fish.name + " into chunks.");
            });
        }

        /**
         * Region handler
         */
        MapListener.registerRegion(5432)
                .onExit((player, logout) -> {
                    player.getInventory().remove(CORMORANTS_GLOVES, Integer.MAX_VALUE);
                    player.getEquipment().remove(CORMORANTS_GLOVES, Integer.MAX_VALUE);
                    player.getInventory().remove(CORMORANTS_GLOVE_BIRD, Integer.MAX_VALUE);
                    player.getEquipment().remove(CORMORANTS_GLOVE_BIRD, Integer.MAX_VALUE);
                });
    }

    private static void rollToFeed(Player player) {
        if(Random.rollDie(3, 1)) {
            Item kingWorm = player.getInventory().findItem(KING_WORM);
            if(kingWorm != null) {
                kingWorm.remove(1);
                player.sendMessage("You feed your cormorant a king worm as a reward.");
                return;
            }

            Item fishChunks = player.getInventory().findItem(FISH_CHUNKS);
            if(fishChunks != null) {
                fishChunks.remove(1);
                player.sendMessage("You feed your cormorant some fish chunks as a reward.");
            }
        }
    }

    private static ArielFishing rollForFish(Player player) {
        int fishingLvl = player.getStats().get(StatType.Fishing).currentLevel;
        int hunterLvl = player.getStats().get(StatType.Hunter).currentLevel;
        if(Random.rollDie(3, 1) && fishingLvl >= GREATER_SIRE.fishLevel && hunterLvl >= GREATER_SIRE.hunterLevel) {
            return GREATER_SIRE;
        } else if(Random.rollDie(3, 1) && fishingLvl >= MOTTLED_EEL.fishLevel && hunterLvl >= MOTTLED_EEL.hunterLevel) {
            return MOTTLED_EEL;
        } else if(Random.rollDie(3, 1) && fishingLvl >= COMMON_TENCH.fishLevel && hunterLvl >= COMMON_TENCH.hunterLevel) {
            return COMMON_TENCH;
        }
        return BLUEGILL;
    }

    private static void rollForGoldenTench(Player player) {
        if(Random.rollDie(5000, 1)) {
            player.getInventory().add(GOLDEN_TENCH, 1);
            player.sendMessage(Color.COOL_BLUE.wrap("Your cormorant finds a golden tench!"));
        }
    }

    private static Position getRandomSpawn(Bounds bounds) {
        Position spawn = bounds.randomPosition();
        while(spawn == null || spawn.getTile().npcCount > 0)
            spawn = bounds.randomPosition();
        return spawn;
    }

    private static double anglerBonus(Player player) {
        double bonus = 1.0;
        Item hat = player.getEquipment().get(Equipment.SLOT_HAT);
        Item top = player.getEquipment().get(Equipment.SLOT_CHEST);
        Item waders = player.getEquipment().get(Equipment.SLOT_LEGS);
        Item boots = player.getEquipment().get(Equipment.SLOT_FEET);

        if (hat != null && hat.getId() == 13258)
            bonus += 0.4;
        if (top != null && top.getId() == 13259)
            bonus += 0.8;
        if (waders != null && waders.getId() == 13260)
            bonus += 0.6;
        if (boots != null && boots.getId() == 13261)
            bonus += 0.2;

        /* Whole set gives an additional 0.5% exp bonus */
        if (bonus >= 3.0)
            bonus += 0.5;

        return bonus;
    }

}
