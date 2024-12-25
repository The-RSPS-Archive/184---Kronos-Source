package io.ruin.model.skills.prayer;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.object.actions.impl.dungeons.KourendCatacombs;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;

public enum Bone {

    REGULAR_BONES(526, 4.5, PlayerCounter.REGULAR_BONES_BURIED, PlayerCounter.REGULAR_BONES_ALTAR),
    BURNT_BONES(528, 4.5, PlayerCounter.BURNT_BONES_BURIED, PlayerCounter.BURNT_BONES_ALTAR),
    BAT_BONES(530, 4.5, PlayerCounter.BAT_BONES_BURIED, PlayerCounter.BAT_BONES_ALTAR),
    WOLF_BONES(2859, 4.5, PlayerCounter.WOLF_BONES_BURIED, PlayerCounter.WOLF_BONES_ALTAR),
    BIG_BONES(532, 15.0, PlayerCounter.BIG_BONES_BURIED, PlayerCounter.BIG_BONES_ALTAR),
    LONG_BONE(10976, 15.0, PlayerCounter.LONG_BONES_BURIED, PlayerCounter.LONG_BONES_ALTAR),
    CURVED_BONE(10977, 15.0, PlayerCounter.CURVED_BONES_BURIED, PlayerCounter.CURVED_BONES_ALTAR),
    JOGRE_BONE(3125, 15.0, PlayerCounter.JOGRE_BONES_BURIED, PlayerCounter.JOGRE_BONES_ALTAR),
    BABYDRAGON_BONES(534, 30.0, PlayerCounter.BABYDRAGON_BONES_BURIED, PlayerCounter.BABYDRAGON_BONES_ALTAR),
    DRAGON_BONES(536, 72.0, PlayerCounter.DRAGON_BONES_BURIED, PlayerCounter.DRAGON_BONES_ALTAR),
    ZOGRE_BONES(4812, 22.5, PlayerCounter.ZOGRE_BONES_BURIED, PlayerCounter.ZOGRE_BONES_ALTAR),
    OURG_BONES(4834, 140.0, PlayerCounter.OURG_BONES_BURIED, PlayerCounter.OURG_BONES_ALTAR),
    WYVERN_BONES(6812, 72.0, PlayerCounter.WYVERN_BONES_BURIED, PlayerCounter.WYVERN_BONES_ALTAR),
    DAGANNOTH_BONES(6729, 125.0, PlayerCounter.DAGANNOTH_BONES_BURIED, PlayerCounter.DAGANNOTH_BONES_ALTAR),
    LAVA_DRAGON_BONES(11943, 85.0, PlayerCounter.LAVA_DRAGON_BONES_BURIED, PlayerCounter.LAVA_DRAGON_BONES_ALTAR),
    SUPERIOR_DRAGON_BONES(22124, 150.0, PlayerCounter.SUPERIOR_DRAGON_BONES_BURIED, PlayerCounter.SUPERIOR_DRAGON_BONES_ALTAR),
    WYRM_BONES(22780, 30.0, PlayerCounter.WYRM_BONES_BURIED, PlayerCounter.WYRM_BONES_ALTAR),
    DRAKE_BONES(22783, 60.0, PlayerCounter.DRAKE_BONES_BURIED, PlayerCounter.DRAKE_BONES_ALTAR),
    HYDRA_BONES(22786, 90.0, PlayerCounter.HYDRA_BONES_BURIED, PlayerCounter.HYDRA_BONES_ALTAR),

    /* monkey bones */
    MONKEY_BONES_ONE(3179, 5.0, PlayerCounter.MONKEY_BONES_BURIED, PlayerCounter.MONKEY_BONES_ALTAR),
    MONKEY_BONES_TWO(3180, 5.0, PlayerCounter.MONKEY_BONES_BURIED, PlayerCounter.MONKEY_BONES_ALTAR),
    MONKEY_BONES_THREE(3181, 5.0, PlayerCounter.MONKEY_BONES_BURIED, PlayerCounter.MONKEY_BONES_ALTAR),
    MONKEY_BONES_FOUR(3182, 5.0, PlayerCounter.MONKEY_BONES_BURIED, PlayerCounter.MONKEY_BONES_ALTAR),
    MONKEY_BONES_FIVE(3183, 5.0, PlayerCounter.MONKEY_BONES_BURIED, PlayerCounter.MONKEY_BONES_ALTAR),
    MONKEY_BONES_SIX(3185, 5.0, PlayerCounter.MONKEY_BONES_BURIED, PlayerCounter.MONKEY_BONES_ALTAR),
    MONKEY_BONES_SEVEN(3186, 5.0, PlayerCounter.MONKEY_BONES_BURIED, PlayerCounter.MONKEY_BONES_ALTAR);

    public final int id, notedId;
    public final double exp;
    public final PlayerCounter buryCounter, altarCounter;

    Bone(int id, double exp, PlayerCounter buryCounter, PlayerCounter altarCounter) {
        this.id = id;
        this.notedId = ItemDef.get(id).notedId;;
        this.exp = exp;
        this.buryCounter = buryCounter;
        this.altarCounter = altarCounter;
    }


    private static Bounds CHAOS_ALTAR = new Bounds(2947, 3817, 2958, 3825, -1);
    private static final int DRAGONBONE_NECKLACE = 22111;

    private void checkBeforeBury(Player player, Item bone) {
        if (player.getPosition().inBounds(CHAOS_ALTAR)) {
            player.dialogue(new OptionsDialogue("Are you sure you want to do that?",
                    new Option("Bury the bone", () -> bury(player, bone)),
                    new Option("Cancel", player::closeDialogue))
            );
        } else {
            bury(player, bone);
        }
    }

    private void bury(Player player, Item bone) {
        player.resetActions(true, false, true);
        player.startEvent(event -> {
            if(player.boneBuryDelay.isDelayed())
                return;
            ItemDef neckDef = player.getEquipment().getDef(Equipment.SLOT_AMULET);
            if(neckDef != null && neckDef.id == DRAGONBONE_NECKLACE) {
                boneNecklaceEffect(player, bone);
            }
            bone.remove();
            player.animate(827);
            player.sendMessage("You dig a hole in the ground...");
            player.getStats().addXp(StatType.Prayer, exp, true);
            player.privateSound(2738);
            buryCounter.increment(player, 1);
            player.karamDelay.delay(2);
            player.sendMessage("You bury the bones.");
            KourendCatacombs.buriedBone(player, this);
        });
    }

    private void boneNecklaceEffect(Player player, Item bone) {
        int boneId = bone.getId();
        Stat prayer = player.getStats().get(StatType.Prayer);
        if(boneId == REGULAR_BONES.id)
            prayer.restore(1, 0);
        else if(boneId == BIG_BONES.id)
            prayer.restore(2, 0);
        else if(boneId == BABYDRAGON_BONES.id)
            prayer.restore(3, 0);
        else if(boneId == DRAGON_BONES.id || boneId == DAGANNOTH_BONES.id)
            prayer.restore(4, 0);
        else if(boneId == SUPERIOR_DRAGON_BONES.id)
            prayer.restore(5, 0);
    }

    static {
        for(Bone bone : values()) {
            if(bone.exp <= Bone.BIG_BONES.exp)
                ItemDef.get(bone.id).allowFruit = true;
            ItemAction.registerInventory(bone.id, "bury", bone::checkBeforeBury);
        }
    }

    public static Bone get(int boneId) {
        for (Bone b : values()) {
            if (boneId == b.id)
                return b;
        }
        return null;
    }

}
