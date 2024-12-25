package io.ruin.model.skills.fletching;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.skills.Tool;
import io.ruin.model.stat.StatType;
import io.ruin.process.event.Event;

public enum LogCutting {
    //Normal log
    ARROW_SHAFT(1511, 1, 5.0, new Item(52, 15), "15 arrow shafts", ""),
    JAVELIN_SHAFT(1511, 3, 5.0, new Item(19584, 15), "15 javelin shafts", "fletch Javelin shafts"),
    SHORTBOW(1511, 5, 5.0, new Item(50, 1), "a shortbow", "make a Shortbow"),
    LONGBOW(1511, 10, 10.0, new Item(48, 1), "a longbow", "make a Longbow"),
    WOODEN_STOCK(1511, 9, 6.0, new Item(9440, 1), "a crossbow stock", "make a Wooden stock"),

    //Oak log
    OAK_SHAFT(1521, 15, 10.0, new Item(52, 30), "30 arrow shafts", "make shafts from oak logs"),
    OAK_SHORTBOW(1521, 20, 16.5, new Item(54, 1), "a shorbow", "make an Oak Shortbow"),
    OAK_LONGBOW(1521, 25, 16.5, new Item(56, 1), "a longbow", "make an Oak Longbow"),
    OAK_STOCK(1521, 24, 16.0, new Item(9442, 1), "a crossbow stock", "make an Oak Stock"),

    //Willow log
    WILLOW_SHAFT(1519, 30, 15.0, new Item(52, 45), "45 arrow shafts", "make shafts from willow logs"),
    WILLOW_SHORTBOW(1519, 35, 33.3, new Item(60, 1), "a shortbow", "make a Willow Shortbow"),
    WILLOW_LONGBOW(1519, 40, 41.5, new Item(58, 1), "a longbow", "make a Willow Longbow"),
    WILLOW_STOCK(1519, 39, 22.0, new Item(9444, 1), "a crossbow stock", "make a Willow Stock"),

    //Maple log
    MAPLE_SHAFT(1517, 45, 20.0, new Item(52, 60), "60 arrow shafts", "make shafts from maple logs"),
    MAPLE_SHORTBOW(1517, 50, 50.0, new Item(64, 1), "a shortbow", "make a Maple Shortbow"),
    MAPLE_LONGBOW(1517, 55, 55.0, new Item(62, 1), "a longbow", "make a Maple Longbow"),
    MAPLE_STOCK(1517, 54, 32.0, new Item(9448, 1), "a crossbow stock", "make a Maple Stock"),

    //Yew log
    YEW_SHAFT(1515, 60, 25.0, new Item(52, 75), "75 arrow shafts", "make shafts from yew logs"),
    YEW_SHORTBOW(1515, 65, 67.5, new Item(68, 1), "a shortbow", "make a Yew Shortbow"),
    YEW_LONGBOW(1515, 70, 75.0, new Item(66, 1), "a longbow", "make a Yew Longbow"),
    YEW_STOCK(1515, 69, 32.0, new Item(9452, 1), "a crossbow stock", "make a Yew Stock"),

    //Magic log
    MAGIC_SHAFT(1513, 75, 30.0, new Item(52, 90), "90 arrow shafts", "make shafts from magic logs"),
    MAGIC_STAFF(1513, 78, 50, new Item(1379, 1), "a staff", "make a Staff"),
    MAGIC_SHORTBOW(1513, 80, 83.3, new Item(72, 1), "a shortbow", "make a Magic Shortbow"),
    MAGIC_LONGBOW(1513, 85, 91.5, new Item(70, 1), "a longbow", "make a Magic Longbow"),
    MAGIC_STOCK(1513, 78, 70.0, new Item(21952, 1), "a crossbow stock", "make a Magic Stock"),

    //Redwood log
    REDWOOD_SHAFT(19669, 90, 35.0, new Item(52, 105), "105 arrow shafts", "make shafts from redwood logs"),
    REDWOOD_STAFF(19669, 91, 65.0, new Item(1379, 1), "a staff", "make a staff from redwood logs"),

    CORRUPT_ARROW_SHAFT(30105, 90, 50.0, new Item(30115, 5), "5 corrupted arrow shafts", "makes corrupted arrows shafts from corrupted logs"),
    CORRUPT_SHORTBOW(30105, 92, 70.0, new Item(30151, 1), "a corrupt short", "makes a corrupt shortbow from corrupted logs"),
    CORRUPT_LONGBOW(30105, 92, 70.0, new Item(30157, 1), "a corrupt longbow", "makes a corrupt longbow from corrupted logs"),
    CORRUPT_PICKAXE_HANDLE(30105, 90, 50.0, new Item(30092, 1), "a corrupt pickaxe handle", "makes a corrupt pickaxe handle from corrupted logs"),

    ;


    public final int logID, levelReq;
    public final double exp;
    public final Item item;
    public final String name, descriptionName;

    LogCutting(int logID, int levelReq, double exp, Item item, String name, String descriptionName) {
        this.logID = logID;
        this.levelReq = levelReq;
        this.exp = exp;
        this.item = item;
        this.name = name;
        this.descriptionName = descriptionName;
    }

    private static void cut(Player player, LogCutting log, int amount) {
        if(!player.getStats().check(StatType.Fletching, log.levelReq, log.descriptionName))
            return;
        if (log == MAGIC_STAFF && !player.getInventory().contains(log.logID, 2)) {
            player.sendMessage("You'll need 2 magic logs to make a staff.");
            return;
        }
        player.startEvent(event -> {
            int made = 0;
            while (made++ < amount) {
                player.lock();
                Item knife = player.getInventory().findItem(Tool.KNIFE);
                if(knife == null) {
                    player.unlock();
                    break;
                }
                Item logToCut = player.getInventory().findItem(log.logID);
                if(logToCut == null) {
                    player.unlock();
                    break;
                }
                if (log == MAGIC_STAFF && !player.getInventory().contains(log.logID, 2)) {
                    player.unlock();
                    break;
                }
                logToCut.remove(log == MAGIC_STAFF ? 2 : 1);
                player.getInventory().add(log.item);
                player.sendFilteredMessage("You carefully cut the wood into " + log.name + ".");
                player.getStats().addXp(StatType.Fletching, log.exp, true);
                player.animate(1248);
                event.delay(2);
                player.unlock();
            }
        });
        player.unlock();
    }

    private static final int NORMAL_LOG = 1511;
    private static final int OAK_LOG = 1521;
    private static final int WILLOW_LOG = 1519;
    private static final int MAPLE_LOG = 1517;
    private static final int YEW_LOG = 1515;
    private static final int MAGIC_LOG = 1513;
    private static final int CORRUPT_LOG = 30105;

    static {
        /**
         * Normal log
         */
        ItemItemAction.register(Tool.KNIFE, NORMAL_LOG, (player, knife, normalLog) -> {
            SkillDialogue.make(player,
                    new SkillItem(ARROW_SHAFT.item.getId()).addAction((p, amount, event) -> cut(p, ARROW_SHAFT, amount)),
                    new SkillItem(JAVELIN_SHAFT.item.getId()).addAction((p, amount, event) -> cut(p, JAVELIN_SHAFT, amount)),
                    new SkillItem(SHORTBOW.item.getId()).addAction((p, amount, event) -> cut(p, SHORTBOW, amount)),
                    new SkillItem(LONGBOW.item.getId()).addAction((p, amount, event) -> cut(p, LONGBOW, amount)),
                    new SkillItem(WOODEN_STOCK.item.getId()).addAction((p, amount, event) -> cut(p, WOODEN_STOCK, amount)));
        });

        /**
         * Oak log
         */
        ItemItemAction.register(Tool.KNIFE, OAK_LOG, (player, knife, oakLog) -> {
            SkillDialogue.make(player,
                    item(OAK_SHAFT),
                    new SkillItem(OAK_SHORTBOW.item.getId()).addAction((p, amount, event) -> cut(p, OAK_SHORTBOW, amount)),
                    new SkillItem(OAK_LONGBOW.item.getId()).addAction((p, amount, event) -> cut(p, OAK_LONGBOW, amount)),
                    new SkillItem(OAK_STOCK.item.getId()).addAction((p, amount, event) -> cut(p, OAK_STOCK, amount)));
        });

        /**
         * Willow log
         */
        ItemItemAction.register(Tool.KNIFE, WILLOW_LOG, (player, knife, willowLog) -> {
            SkillDialogue.make(player,
                    item(WILLOW_SHAFT),
                    new SkillItem(WILLOW_SHORTBOW.item.getId()).addAction((p, amount, event) -> cut(p, WILLOW_SHORTBOW, amount)),
                    new SkillItem(WILLOW_LONGBOW.item.getId()).addAction((p, amount, event) -> cut(p, WILLOW_LONGBOW, amount)),
                    new SkillItem(WILLOW_STOCK.item.getId()).addAction((p, amount, event) -> cut(p, WILLOW_STOCK, amount)));
        });

        /**
         * Maple log
         */
        ItemItemAction.register(Tool.KNIFE, MAPLE_LOG, (player, knife, mapleLog) -> {
            SkillDialogue.make(player,
                    item(MAPLE_SHAFT),
                    new SkillItem(MAPLE_SHORTBOW.item.getId()).addAction((p, amount, event) -> cut(p, MAPLE_SHORTBOW, amount)),
                    new SkillItem(MAPLE_LONGBOW.item.getId()).addAction((p, amount, event) -> cut(p, MAPLE_LONGBOW, amount)),
                    new SkillItem(MAPLE_STOCK.item.getId()).addAction((p, amount, event) -> cut(p, MAPLE_STOCK, amount)));
        });

        /**
         * Yew log
         */
        ItemItemAction.register(Tool.KNIFE, YEW_LOG, (player, knife, yewLog) -> {
            SkillDialogue.make(player,
                    item(YEW_SHAFT),
                    new SkillItem(YEW_SHORTBOW.item.getId()).addAction((p, amount, event) -> cut(p, YEW_SHORTBOW, amount)),
                    new SkillItem(YEW_LONGBOW.item.getId()).addAction((p, amount, event) -> cut(p, YEW_LONGBOW, amount)),
                    new SkillItem(YEW_STOCK.item.getId()).addAction((p, amount, event) -> cut(p, YEW_STOCK, amount)));
        });

        /**
         * Magic log
         */
        ItemItemAction.register(Tool.KNIFE, MAGIC_LOG, (player, knife, magicLog) -> {
            SkillDialogue.make(player,
                    item(MAGIC_SHAFT),
                    item(MAGIC_STAFF),
                    new SkillItem(MAGIC_SHORTBOW.item.getId()).addAction((p, amount, event) -> cut(p, MAGIC_SHORTBOW, amount)),
                    new SkillItem(MAGIC_LONGBOW.item.getId()).addAction((p, amount, event) -> cut(p, MAGIC_LONGBOW, amount)),
                    item(MAGIC_STOCK));
        });

        /**
         * Redwood log
         */
        ItemItemAction.register(Tool.KNIFE, 19669, (player, knife, magicLog) -> {
            SkillDialogue.make(player,
                    item(REDWOOD_SHAFT),
                    item(REDWOOD_STAFF));
        });

        /**
         * Corrupt log
         */
        ItemItemAction.register(Tool.KNIFE, CORRUPT_LOG, (player, knife, magicLog) ->
                SkillDialogue.make(player, item(CORRUPT_ARROW_SHAFT), item(CORRUPT_SHORTBOW), item(CORRUPT_LONGBOW), item(CORRUPT_PICKAXE_HANDLE)));

    }

    private static SkillItem item(LogCutting lc) {
        return new SkillItem(lc.item.getId()).addAction(action(lc));
    }

    private static SkillItem.SkillItemConsumer<Player, Integer, Event> action(LogCutting log) {
        return (player, amount, event) -> cut(player, log, amount);
    }
}