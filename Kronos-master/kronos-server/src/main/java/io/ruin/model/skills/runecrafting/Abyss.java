package io.ruin.model.skills.runecrafting;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.Tool;
import io.ruin.model.skills.mining.Pickaxe;
import io.ruin.model.skills.woodcutting.Hatchet;
import io.ruin.model.stat.StatType;

public class Abyss {

    public static final Position[] OUTER_TELEPORTS = {
            new Position(3019, 4844, 0),
            new Position(3041, 4855, 0),
            new Position(3053, 4851, 0),
            new Position(3060, 4842, 0),
            new Position(3063, 4832, 0),
            new Position(3061, 4823, 0),
            new Position(3055, 4814, 0),
            new Position(3047, 4811, 0),
            new Position(3038, 4809, 0),
            new Position(3027, 4810, 0),
            new Position(3021, 4814, 0),
            new Position(3018, 4819, 0),
            new Position(3016, 4828, 0),
            new Position(3016, 4838, 0),
            new Position(3021, 4847, 0)
    };


    public static void randomize(Player player) {
        int maxOrder = 11; //must equal (blockageOrders.length - 1)
        Config.ABYSS_MAP.set(player, Random.get(maxOrder));
    }

    /**
     * Obstacles
     */

    private static void goThroughPassage(Player player, GameObject obj, Position dest) {
        player.startEvent(event -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            event.delay(1);
            player.getMovement().teleport(dest);
            randomize(player);
            player.unlock();
        });
    }

    private static void squeezeThroughGap(Player player, GameObject obj, Position dest) {
        player.startEvent(event -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.sendMessage("You attempt to squeeze through the narrow gap...");
            event.delay(2);
            player.animate(1331);
            event.delay(1);

            if (Random.rollDie(100, chanceToSucceed(player, StatType.Agility))) {
                player.sendMessage("...and you manage to crawl through.");
                event.delay(2);
                player.getStats().addXp(StatType.Agility, 25.0, true);
                player.getMovement().teleport(dest);
                randomize(player);
                player.resetAnimation();
            } else {
                player.animate(1332);
                player.sendMessage("...but you are not agile enough to get through the gap.");
            }

            event.delay(1);
            player.unlock();
        });
    }

    private static void distractEyes(Player player, GameObject obj, Position dest) {
        player.startEvent(event -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.sendMessage("You use your thieving skills to misdirect the eyes...");
            player.animate(1057);
            event.delay(4);

            if (Random.rollDie(100, chanceToSucceed(player, StatType.Thieving))) {
                Config.ABYSS_MAP.set(player, 18);
                event.delay(3);
                player.animate(864);
                Config.ABYSS_MAP.set(player, 19);
                event.delay(2);
                player.sendMessage("...and sneak past while they're not looking.");
                event.delay(1);
                player.getStats().addXp(StatType.Thieving, 25.0, true);
                player.getMovement().teleport(dest);
                randomize(player);
            } else {
                player.sendMessage("...but fail to distract them.");
            }

            player.resetAnimation();
            event.delay(1);
            player.unlock();
        });
    }

    private static void burnBoil(Player player, GameObject obj, Position dest) {
        player.startEvent(event -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.sendMessage("You attempt to set the blockade on fire...");
            event.delay(4);

            if (player.getInventory().hasId(Tool.TINDER_BOX)) {
                player.animate(733);
                event.delay(4);
                if (Random.rollDie(100, chanceToSucceed(player, StatType.Firemaking))) {
                    Config.ABYSS_MAP.set(player, 16);
                    event.delay(4);
                    World.sendGraphics(157, 0, 0, obj.x, obj.y, obj.z);
                    Config.ABYSS_MAP.set(player, 17);
                    player.sendMessage("...and manage to burn it down and load past.");
                    player.resetAnimation();
                    event.delay(1);
                    player.getStats().addXp(StatType.Firemaking, 25.0, true);
                    player.getMovement().teleport(dest);
                    randomize(player);
                } else {
                    player.resetAnimation();
                    player.sendMessage("...but fail to light it.");
                }
            } else {
                player.sendMessage("...but you don't have a tinderbox to burn it!");
            }

            event.delay(1);
            player.unlock();
        });
    }

    private static void cutTendrils(Player player, GameObject obj, Position dest) {
        Hatchet hatchet = Hatchet.find(player);
        if (hatchet == null) {
            player.sendMessage("You do not have an axe which you have the woodcutting level to use.");
            player.privateSound(2277);
            return;
        }

        player.startEvent(event -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.sendMessage("You attempt to chop your way through...");
            event.delay(3);

            if (Random.rollDie(100, chanceToSucceed(player, StatType.Woodcutting))) {
                player.animate(hatchet.animationId);
                event.delay(2);
                Config.ABYSS_MAP.set(player, 14);
                event.delay(3);
                Config.ABYSS_MAP.set(player, 15);
                event.delay(2);
                player.sendMessage("...and manage to cut your way through the tendrils.");
                event.delay(1);
                player.getStats().addXp(StatType.Woodcutting, 25.0, true);
                player.getMovement().teleport(dest);
                randomize(player);
            } else {
                player.sendMessage("...but fail to cut down the tendrils.");
            }

            player.resetAnimation();
            event.delay(1);
            player.unlock();
        });
    }

    private static void mineRocks(Player player, GameObject obj, Position dest) {
        Pickaxe pickaxe = Pickaxe.find(player);
        if (pickaxe == null) {
            player.dialogue(new MessageDialogue("You need a pickaxe to mine this rock. You do not have a pickaxe which " +
                    "you have the Mining level to use."));
            player.privateSound(2277);
            return;
        }

        player.startEvent(event -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.animate(pickaxe.regularAnimationID);
            event.delay(5);

            if (Random.rollDie(100, chanceToSucceed(player, StatType.Mining))) {
                Config.ABYSS_MAP.set(player, 12);
                event.delay(3);
                Config.ABYSS_MAP.set(player, 13);
                event.delay(2);
                player.sendMessage("...and manage to break through the rock.");
                event.delay(1);
                player.getStats().addXp(StatType.Mining, 25.0, true);
                player.getMovement().teleport(dest);
                randomize(player);
            } else {
                player.sendMessage("...but fail to break-up the rock.");
            }

            player.resetAnimation();
            event.delay(1);
            player.unlock();
        });
    }

    private static int chanceToSucceed(Player player, StatType statType) {
        return player.getStats().get(statType).currentLevel + 1;
    }

    /**
     * :)
     */

    private static final class Entrance {

        private final int id;

        private final Position dest;

        private Entrance(int id, Position dest) {
            this.id = id;
            this.dest = dest;
        }
        
    }

    private enum Blockage {

        DEFAULT(null),
        PASSAGE(Abyss::goThroughPassage),
        GAP(Abyss::squeezeThroughGap),
        EYES(Abyss::distractEyes),
        BOIL(Abyss::burnBoil),
        TENDRILS(Abyss::cutTendrils),
        ROCKS(Abyss::mineRocks);

        private final BlockageConsumer consumer;

        Blockage(BlockageConsumer consumer) {
            this.consumer = consumer;
        }

        @FunctionalInterface
        public interface BlockageConsumer {
            void accept(Player player, GameObject obj, Position dest);
        }

    }

    private enum Rift {

        AIR(25378, new Position(2841, 4830, 0)),
        MIND(25379, new Position(2792, 4827, 0)),
        WATER(25376, new Position(2726, 4832, 0)),
        EARTH(24972, new Position(2655, 4830, 0)),
        FIRE(24971, new Position(2574, 4849, 0)),
        BODY(24973, new Position(2521, 4834, 0)),
        COSMIC(24974, new Position(2162, 4833, 0)),
        LAW(25034, new Position(2464, 4818, 0)),
        NATURE(24975, new Position(2400, 4835, 0)),
        CHAOS(24976, new Position(2281, 4837, 0)),
        DEATH(25035, new Position(2208, 4830, 0)),
        BLOOD(25380, new Position(1727, 3825, 0)),
        SOUL(25377, new Position(1820, 3862, 0));

        private final int objectID;

        private final Position position;

        Rift(int objectID, Position position) {
            this.objectID = objectID;
            this.position = position;
        }

    }

    private static final int[] ABYSS_CREATURES = new int[] {2586, 2584, 2585};

    static {
        Entrance[] entrances = {
                new Entrance(26187, new Position(3042, 4819, 0)),
                new Entrance(26188, new Position(3029, 4823, 0)),
                new Entrance(26189, new Position(3028, 4824, 0)),
                new Entrance(26190, new Position(3024, 4834, 0)),
                new Entrance(26191, new Position(3028, 4841, 0)),
                new Entrance(26192, new Position(3031, 4843, 0)),
                new Entrance(26208, new Position(3038, 4846, 0)),
                new Entrance(26250, new Position(3048, 4842, 0)),
                new Entrance(26251, new Position(3051, 4839, 0)),
                new Entrance(26252, new Position(3054, 4830, 0)),
                new Entrance(26253, new Position(3051, 4823, 0)),
                new Entrance(26574, new Position(3048, 4821, 0)),
        };
        Blockage[][] blockageOrders = {
                {Blockage.DEFAULT, Blockage.ROCKS, Blockage.TENDRILS, Blockage.BOIL, Blockage.EYES, Blockage.GAP, Blockage.PASSAGE, Blockage.GAP, Blockage.EYES, Blockage.BOIL, Blockage.TENDRILS, Blockage.ROCKS},
                {Blockage.ROCKS, Blockage.DEFAULT, Blockage.ROCKS, Blockage.TENDRILS, Blockage.BOIL, Blockage.EYES, Blockage.GAP, Blockage.PASSAGE, Blockage.GAP, Blockage.EYES, Blockage.BOIL, Blockage.TENDRILS},
                {Blockage.TENDRILS, Blockage.ROCKS, Blockage.DEFAULT, Blockage.ROCKS, Blockage.TENDRILS, Blockage.BOIL, Blockage.EYES, Blockage.GAP, Blockage.PASSAGE, Blockage.GAP, Blockage.EYES, Blockage.BOIL},
                {Blockage.BOIL, Blockage.TENDRILS, Blockage.ROCKS, Blockage.DEFAULT, Blockage.ROCKS, Blockage.TENDRILS, Blockage.BOIL, Blockage.EYES, Blockage.GAP, Blockage.PASSAGE, Blockage.GAP, Blockage.EYES},
                {Blockage.EYES, Blockage.BOIL, Blockage.TENDRILS, Blockage.ROCKS, Blockage.DEFAULT, Blockage.ROCKS, Blockage.TENDRILS, Blockage.BOIL, Blockage.EYES, Blockage.GAP, Blockage.PASSAGE, Blockage.GAP},
                {Blockage.GAP, Blockage.EYES, Blockage.BOIL, Blockage.TENDRILS, Blockage.ROCKS, Blockage.DEFAULT, Blockage.ROCKS, Blockage.TENDRILS, Blockage.BOIL, Blockage.EYES, Blockage.GAP, Blockage.PASSAGE},
                {Blockage.PASSAGE, Blockage.ROCKS, Blockage.TENDRILS, Blockage.BOIL, Blockage.EYES, Blockage.GAP, Blockage.DEFAULT, Blockage.GAP, Blockage.EYES, Blockage.BOIL, Blockage.TENDRILS, Blockage.ROCKS},
                {Blockage.GAP, Blockage.PASSAGE, Blockage.ROCKS, Blockage.TENDRILS, Blockage.BOIL, Blockage.EYES, Blockage.GAP, Blockage.DEFAULT, Blockage.GAP, Blockage.EYES, Blockage.BOIL, Blockage.TENDRILS},
                {Blockage.EYES, Blockage.ROCKS, Blockage.PASSAGE, Blockage.ROCKS, Blockage.TENDRILS, Blockage.BOIL, Blockage.EYES, Blockage.GAP, Blockage.DEFAULT, Blockage.GAP, Blockage.EYES, Blockage.BOIL},
                {Blockage.BOIL, Blockage.EYES, Blockage.ROCKS, Blockage.PASSAGE, Blockage.ROCKS, Blockage.TENDRILS, Blockage.BOIL, Blockage.EYES, Blockage.GAP, Blockage.DEFAULT, Blockage.GAP, Blockage.EYES},
                {Blockage.TENDRILS, Blockage.BOIL, Blockage.TENDRILS, Blockage.ROCKS, Blockage.PASSAGE, Blockage.ROCKS, Blockage.TENDRILS, Blockage.BOIL, Blockage.EYES, Blockage.GAP, Blockage.DEFAULT, Blockage.GAP},
                {Blockage.ROCKS, Blockage.TENDRILS, Blockage.BOIL, Blockage.TENDRILS, Blockage.ROCKS, Blockage.PASSAGE, Blockage.ROCKS, Blockage.TENDRILS, Blockage.BOIL, Blockage.EYES, Blockage.GAP, Blockage.DEFAULT}
        };
        for(int i = 0; i < entrances.length; i++) {
            int idx = i;
            Entrance entrance = entrances[idx];
            ObjectAction.register(entrance.id, 1, (player, obj) -> {
                int abyssMap = Config.ABYSS_MAP.get(player);
                Blockage blockage = blockageOrders[abyssMap][idx];
                if(blockage.consumer != null)
                    blockage.consumer.accept(player, obj, entrance.dest);
            });
        }
        for(Rift rift : Rift.values())
            ObjectAction.register(rift.objectID, 1, (player, obj) -> player.getMovement().teleport(rift.position));

        /**
         * Entrance/exit to abyssal sire
         */
        ObjectAction.register(27054, "enter", (player, obj) -> player.startEvent(event -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            event.delay(1);
            player.getMovement().teleport(3039, 4800, 0);
            randomize(player);
            player.unlock();
        }));
        ObjectAction.register(27055, "enter", (player, obj) -> player.startEvent(event -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            event.delay(1);
            player.getMovement().teleport(3039, 4805, 0);
            randomize(player);
            player.unlock();
        }));
        SpawnListener.register(ABYSS_CREATURES, npc -> {
            npc.deathEndListener = (DeathListener.SimpleKiller) killer -> {
              if(killer.player != null) {
                  PlayerCounter.ABYSSAL_CREATURES_KC.increment(killer.player, 1);
              }
            };
        });
    }

}