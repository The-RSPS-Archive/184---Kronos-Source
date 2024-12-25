package io.ruin.model.skills.fletching;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.stat.StatType;

public enum Bolt {

    /**
     * Gem-tipped bolts
     */
    OPAL(11, 1.6, 877, 45, 879),
    JADE(26, 2.4, 9139, 9187, 9335),
    PEARL(41, 3.0, 9140, 46, 880),
    RED_TOPAZ(48, 4.0, 9141, 9188, 9336),
    SAPPHIRE(56, 4.0, 9142, 9189, 9337),
    EMERALD(55, 4.0, 9142, 9190, 9338),
    RUBY(63, 6.3, 9143, 9191, 9339),
    DIAMOND(65, 7.0, 9143, 9192, 9340),
    DRAGONSTONE(71, 8.2, 9144, 9193, 9341),
    ONYX(73, 10.0, 9144, 9194, 9342),

    /**
     * Dragon gem-tipped bolts
     */
    OPAL_DRAGON(84, 12.0, 21905, 45, 21955),
    JADE_DRAGON(84, 12.0, 21905, 9187, 21957),
    PEARL_DRAGON(84, 12.0, 21905, 46, 21959),
    RED_TOPAZ_DRAGON(84, 12.0, 21905, 9188, 21961),
    SAPPHIRE_DRAGON(84, 12.0, 21905, 9189, 21963),
    EMERALD_DRAGON(84, 12.0, 21905, 9190, 21965),
    RUBY_DRAGON(84, 12.0, 21905, 9191, 21967),
    DIAMOND_DRAGON(84, 12.0, 21905, 9192, 21969),
    DRAGONSTONE_DRAGON(84, 12.0, 21905, 9193, 21971),
    ONYX_DRAGON(84, 12.0, 21905, 9194, 21973),

    /**
     * Other
     */
    BROAD(55, 3.0, 11875, 21338, 21316),
    AMETHYST(76, 10.6, 11875, 21338, 21316);

    public final int levelRequirement;
    public final double experience;
    public final int id;
    public final int tip;
    public final int tipped;
    public final String tippedName;

    Bolt(int levelRequirement, double experience, int id, int tip, int tipped) {
        this.levelRequirement = levelRequirement;
        this.experience = experience;
        this.id = id;
        this.tip = tip;
        this.tipped = tipped;
        this.tippedName = ItemDef.get(tipped).name;
    }

    private void make(Player player, Item boltItem, Item tipItem, int amount) {
        boltItem.remove(amount);
        tipItem.remove(amount);
        player.getInventory().add(tipped, amount);
        player.getStats().addXp(StatType.Fletching, experience * amount, true);
        boolean broad = boltItem.getId() == BROAD.id;
        if (broad)
            player.sendFilteredMessage("You attach feathers to " + amount + " broad bolts.");
        else if (amount == 1)
            player.sendFilteredMessage("You fletch a bolt.");
        else
            player.sendFilteredMessage("You fletch " + amount + " bolts");

    }

    static {
        for (Bolt bolt : values()) {
            SkillItem item = new SkillItem(bolt.tipped).addAction((player, amount, event) -> {
                while (amount-- > 0) {
                    Item boltItem = player.getInventory().findItem(bolt.id);
                    if (boltItem == null)
                        return;
                    Item tipItem = player.getInventory().findItem(bolt.tip);
                    if (tipItem == null)
                        return;
                    int maxAmount = Math.min(boltItem.getAmount(), tipItem.getAmount());
                    if (maxAmount > 10) {
                        bolt.make(player, boltItem, tipItem, 10);
                        event.delay(2);
                        continue;
                    }
                    bolt.make(player, boltItem, tipItem, maxAmount);
                    break;
                }
            });
            ItemItemAction.register(bolt.id, bolt.tip, (player, boltItem, tipItem) -> {
                if (!player.getStats().check(StatType.Fletching, bolt.levelRequirement, bolt.tipped, "make " + bolt.tippedName))
                    return;
                if (bolt == BROAD && Config.BROADER_FLETCHING.get(player) == 0) {
                    player.sendMessage("You haven't unlocked the ability to fletch broad bolts.");
                    return;
                }
                int maxAmount = Math.min(boltItem.getAmount(), tipItem.getAmount());
                if (maxAmount > 10) {
                    SkillDialogue.make(player, item);
                    return;
                }
                bolt.make(player, boltItem, tipItem, maxAmount);
            });
        }
    }

}