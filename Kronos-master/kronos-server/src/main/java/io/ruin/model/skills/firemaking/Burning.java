package io.ruin.model.skills.firemaking;

import io.ruin.api.utils.Random;
import io.ruin.cache.ItemDef;
import io.ruin.model.World;
import io.ruin.model.activities.raids.xeric.ChambersOfXeric;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Tile;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.ground.GroundItemAction;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.skills.Tool;
import io.ruin.model.stat.StatType;

import java.util.ArrayList;

import static io.ruin.model.skills.Tool.TINDER_BOX;

public enum Burning {

    /**
     * Regular
     */
    NORMAL(1511, 40.0, 1, 21, 200, 26185, PlayerCounter.NORMAL_LOGS_BURNT),
    ACHEY(2862, 40.0, 1, 21, 200, 26185, PlayerCounter.ACHEY_LOGS_BURNT),
    OAK(1521, 60.0, 15, 35, 233, 26185, PlayerCounter.OAK_LOGS_BURNT),
    WILLOW(1519, 90.0, 30, 50, 284, 26185, PlayerCounter.WILLOW_LOGS_BURNT),
    TEAK(6333, 105.0, 35, 55, 316, 26185, PlayerCounter.TEAK_LOGS_BURNT),
    ARCTIC_PINE(10810, 125.0, 42, 62, 330, 26185, PlayerCounter.ARCTIC_PINE_LOGS_BURNT),
    MAPLE(1517, 135.0, 45, 65, 350, 26185, PlayerCounter.MAPLE_LOGS_BURNT),
    MAHOGANY(6332, 157.5, 50, 70, 400, 26185, PlayerCounter.MAHOGANY_LOGS_BURNT),
    YEW(1515, 202.5, 60, 80, 500, 26185, PlayerCounter.YEW_LOGS_BURNT),
    MAGIC(1513, 303.8, 75, 95, 550, 26185, PlayerCounter.MAGIC_LOGS_BURNT),
    REDWOOD(19669, 350.0, 90, 99, 600, 26185, PlayerCounter.REDWOOD_LOGS_BURNT),

    /**
     * Colored
     */
    RED_LOGS(7404, 50.0, 1, 21, 200, 26186, PlayerCounter.RED_LOGS_BURNT),
    GREEN_LOGS(7405, 50.0, 1, 21, 200, 26575, PlayerCounter.GREEN_LOGS_BURNT),
    BLUE_LOGS(7406, 50.0, 1, 21, 200, 26576, PlayerCounter.BLUE_LOGS_BURNT),
    WHITE_LOGS(10328, 50.0, 1, 21, 200, 20000, PlayerCounter.WHITE_LOGS_BURNT),
    PURPLE_LOGS(10329, 50.0, 1, 21, 200, 20001, PlayerCounter.PURPLE_LOGS_BURNT),

    /**
     * Raids
     */
    KINDLING(20799, 40.0, 1, 21, 200, 26185, PlayerCounter.KINDLING_BURNT),

    /*
     * Custom
     */
    CORRUPT_LOGS(30105, 450.0, 95, 99, 600, 20001, PlayerCounter.PURPLE_LOGS_BURNT),
    ;


    public final int itemId, levelReq, barbLevelReq, lifeSpan, fireId;
    public final double exp;
    public final String pluralName;
    public PlayerCounter counter;

    Burning(int logId, double exp, int levelReq, int barbLevelReq, int lifeSpan, int fireId, PlayerCounter counter) {
        this.itemId = logId;
        this.exp = exp;
        this.levelReq = levelReq;
        this.barbLevelReq = barbLevelReq;
        this.lifeSpan = lifeSpan;
        this.fireId = fireId;
        this.counter = counter;
        this.pluralName = ItemDef.get(logId).name.toLowerCase();
    }

    public static Burning get(int log) {
        for (Burning b: values())
            if (b.itemId == log)
                return b;
        return null;
    }

    private enum BarbarianBurning {

        /**
         * Shortbows
         */
        SHORTBOW(841, 6714),
        OAK_SHORTBOW(843, 6715),
        WILLOW_SHORTBOW(849, 6716),
        MAPLE_SHORTBOW(853, 6717),
        YEW_SHORTBOW(857, 6718),
        MAGIC_SHORTBOW(861, 6719),

        /**
         * Longbows
         */
        LONGBOW(839, 6714),
        OAK_LONGBOW(845, 6715),
        WILLOW_LONGBOW(847, 6716),
        MAPLE_LONGBOW(851, 6717),
        YEW_LONGBOW(855, 6718),
        MAGIC_LONGBOW(859, 6719),

        /**
         * Misc
         */
        TRAINING_BOW(9705, 6713),
        SEERCULL(6724, 6720);

        public final int itemId, animationId;

        BarbarianBurning(int itemId, int animationId) {
            this.itemId = itemId;
            this.animationId = animationId;
        }
    }

    private static final int[] barbarianLighters = {841, 843, 849, 853, 857, 861, 839, 845, 847, 851, 855, 859, 9705, 6724};

    private static void burn(Player player, Item inventoryLog, Burning burning, GroundItem groundItem, Boolean barbarianBurning, int animationId) {
        if (!player.getStats().check(StatType.Firemaking, barbarianBurning ? burning.barbLevelReq : burning.levelReq,
                barbarianBurning ? "burn " + burning.pluralName + " in a barbarian fashion" : "burn " + burning.pluralName))
            return;
        if (!Tile.allowObjectPlacement(player.getPosition()) && !ChambersOfXeric.isRaiding(player)) {
            player.sendMessage("You can't light a fire here.");
            return;
        }

        GroundItem groundLog = groundItem == null ? new GroundItem(inventoryLog.getId(), 1).owner(player).position(player.getPosition()).spawn() : groundItem;
        player.startEvent(event -> {
            int attempts = 0;
            if (inventoryLog != null)
                inventoryLog.remove();

            player.getMovement().reset();
            player.sendFilteredMessage("You attempt to light the logs.");
            if (chainLighting(player, burning))
                event.delay(1);

            while (Random.get(100) > lightChance(player, burning)) {
                if (groundLog.isRemoved()) {
                    player.sendMessage("The logs you were trying to light have disappeared.");
                    player.resetAnimation();
                } else if (attempts++ % 12 == 0) {
                    player.animate(animationId);
                    event.delay(2);
                } else {
                    event.delay(1);
                }
            }

            player.lock();
            GameObject fire = new GameObject(burning.fireId, player.getAbsX(), player.getAbsY(), 0, 10, 0);
            player.resetAnimation();
            player.getRouteFinder().routeSelf();
            event.delay(1);
            if (groundLog == null) {
                player.sendMessage("The logs you were trying to light have disappeared.");
                player.resetAnimation();
                player.unlock();
            } else {
                groundLog.remove();
                player.sendFilteredMessage("The fire catches and the logs begin to burn.");
                player.getStats().addXp(StatType.Firemaking, burning.exp * pyromancerBonus(player), true);
                burning.counter.increment(player, 1);
                createFire(burning, fire);
                player.face(fire);
                player.unlock();
            }
        });
    }

    private static void createFire(Burning log, GameObject obj) {
        World.startEvent(event -> {
            GameObject fire = GameObject.spawn(obj.id, obj.x, obj.y, obj.z, obj.type, obj.direction);
            event.delay(log.lifeSpan + Random.get(15));
            fire.remove();
            new GroundItem(592, 1).position(fire.x, fire.y, fire.z).spawn();
        });
    }

    private static double lightChance(Player player, Burning log) {
        int points = 20;
        int level = player.getStats().get(StatType.Firemaking).currentLevel;
        double difference = (level - log.levelReq) * (level > 95 ? 3 : 2);
        return Math.min(100, points + difference);
    }

    private static boolean chainLighting(Player player, Burning log) {
        return player.getInventory().hasMultiple(log.itemId) && player.getInventory().getFreeSlots() > 1;
    }

    private static double pyromancerBonus(Player player) {
        double bonus = 1.0;
        Item hood = player.getEquipment().get(Equipment.SLOT_HAT);
        Item garb = player.getEquipment().get(Equipment.SLOT_CHEST);
        Item robe = player.getEquipment().get(Equipment.SLOT_LEGS);
        Item boots = player.getEquipment().get(Equipment.SLOT_FEET);

        if (hood != null && hood.getId() == 20708)
            bonus += 0.4;
        if (garb != null && garb.getId() == 20704)
            bonus += 0.8;
        if (robe != null && robe.getId() == 20706)
            bonus += 0.6;
        if (boots != null && boots.getId() == 20710)
            bonus += 0.2;

        /* Whole set gives an additional 0.5% exp bonus */
        if (bonus >= 3.0)
            bonus += 0.5;

        return bonus;
    }

    static {
        /**
         * Log lighting
         */
        for (Burning log : values()) {
            /**
             * Tinderbox
             */
            ItemItemAction.register(log.itemId, TINDER_BOX, (player, primary, secondary) -> burn(player, primary, log, null, false, 733));

            /**
             * Barbarian
             */
            for (BarbarianBurning barbarianBurning : BarbarianBurning.values()) {
                ItemItemAction.register(log.itemId, barbarianBurning.itemId, (player, primary, secondary) -> {
                    burn(player, primary, log, null, true, barbarianBurning.animationId);
                });
            }

            /**
             * Ground item
             */
            GroundItemAction.register(log.itemId, "light", (player, groundItem, distance) -> {
                if (distance != 0) {
                    player.getMovement().outOfReach();
                    return;
                }
                Item tinderBox = player.getInventory().findItem(Tool.TINDER_BOX);
                ArrayList<Item> bows = player.getInventory().collectItems(barbarianLighters);
                /* Doesn't have something to light the fire? */
                if (tinderBox == null && bows == null) {
                    player.sendMessage("You need a tinderbox to light a fire.");
                    return;
                }
                /* Prioritize the tinderbox */
                if (tinderBox != null) {
                    burn(player, null, log, groundItem, false, 733);
                    return;
                }
                for (BarbarianBurning barbarianBurning : BarbarianBurning.values()) {
                    bows.forEach(bow -> {
                        if (bow.getId() == barbarianBurning.itemId) {
                            burn(player, null, log, groundItem, false, barbarianBurning.animationId);
                            return;
                        }
                    });
                }
            });
        }

        /**
         * Firelighter
         */
        int[][] lighterData = {
                {7329, RED_LOGS.itemId},
                {7330, GREEN_LOGS.itemId},
                {7331, BLUE_LOGS.itemId},
                {10326, PURPLE_LOGS.itemId},
                {10327, WHITE_LOGS.itemId},
        };
        for (int[] id : lighterData) {
            ItemItemAction.register(id[0], Burning.NORMAL.itemId, (player, primary, secondary) -> {
                primary.remove(1);
                secondary.setId(id[1]);
            });
        }
    }

}
