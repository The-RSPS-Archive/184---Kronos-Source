package io.ruin.model.skills.prayer;

import io.ruin.api.utils.StringUtils;
import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.journal.toggles.RiskProtection;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.stat.StatType;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Ordered by child ids (NOT DISPLAY ORDER)
 */
public enum Prayer {
    THICK_SKIN(4104, 2690, p -> {
        p.level = 1;
        p.drain = 3;
        p.defenceBoost = 0.05;
    }),
    BURST_OF_STRENGTH(4105, 2688, p -> {
        p.level = 4;
        p.drain = 3;
        p.strengthBoost = 0.05;
    }),
    CLARITY_OF_THOUGHT(4106, 2664, p -> {
        p.level = 7;
        p.drain = 3;
        p.attackBoost = 0.05;
    }),
    ROCK_SKIN(4107, 2684, p -> {
        p.level = 10;
        p.drain = 6;
        p.defenceBoost = 0.10;
    }),
    SUPERHUMAN_STRENGTH(4108, 2689, p -> {
        p.level = 13;
        p.drain = 6;
        p.strengthBoost = 0.10;
    }),
    IMPROVED_REFLEXES(4109, 2662, p -> {
        p.level = 16;
        p.drain = 6;
        p.attackBoost = 0.10;
    }),
    RAPID_RESTORE(4110, 2679, p -> {
        p.level = 19;
        p.drain = 1;
    }),
    RAPID_HEAL(4111, 2678, p -> {
        p.level = 22;
        p.drain = 2;
    }),
    PROTECT_ITEM(4112, 1982, p -> {
        p.level = 25;
        p.drain = 2;
        p.activationCheck = player -> {
            RiskProtection.monitorRiskProtection(player);
            if(player.getCombat().highRiskSkull) {
                player.sendMessage(Color.ORANGE_RED.wrap("Warning:") + " The Protect Item prayer is disabled when marked with a high-risk skull.");
                return false;
            }
            return true;
        };
    }),
    STEEL_SKIN(4113, 2687, p -> {
        p.level = 28;
        p.drain = 12;
        p.defenceBoost = 0.15;
    }),
    ULTIMATE_STRENGTH(4114, 2691, p -> {
        p.level = 31;
        p.drain = 12;
        p.strengthBoost = 0.15;
    }),
    INCREDIBLE_REFLEXES(4115, 2667, p -> {
        p.level = 34;
        p.drain = 12;
        p.attackBoost = 0.15;
    }),
    PROTECT_FROM_MAGIC(4116, 2675, p -> {
        p.level = 37;
        p.drain = 12;
        p.headIcon = 2;
        p.activationCheck = player ->  {
            if(player.getPrayer().slashDelay.isDelayed()) {
                player.sendMessage("Your protection prayers are currently slashed!");
                return false;
            }
            return true;
        };
    }),
    PROTECT_FROM_MISSILES(4117, 2677, p -> {
        p.level = 40;
        p.drain = 12;
        p.headIcon = 1;
        p.activationCheck = PROTECT_FROM_MAGIC.activationCheck;
    }),
    PROTECT_FROM_MELEE(4118, 2676, p -> {
        p.level = 43;
        p.drain = 12;
        p.headIcon = 0;
        p.activationCheck = PROTECT_FROM_MAGIC.activationCheck;
    }),
    RETRIBUTION(4119, 2682, p -> {
        p.level = 46;
        p.drain = 3;
        p.headIcon = 3;
    }),
    REDEMPTION(4120, 2680, p -> {
        p.level = 49;
        p.drain = 6;
        p.headIcon = 5;
    }),
    SMITE(4121, 2686, p -> {
        p.level = 52;
        p.drain = 18;
        p.headIcon = 4;
    }),
    SHARP_EYE(4122, 2685, p -> {
        p.level = 8;
        p.drain = 3;
        p.rangedAttackBoost = 0.05;
        p.rangedStrengthBoost = 0.05;
    }),
    MYSTIC_WILL(4123, 2670, p -> {
        p.level = 9;
        p.drain = 3;
        p.magicBoost = 0.05;
    }),
    HAWK_EYE(4124, 2666, p -> {
        p.level = 26;
        p.drain = 6;
        p.rangedAttackBoost = 0.10;
        p.rangedStrengthBoost = 0.10;
    }),
    MYSTIC_LORE(4125, 2668, p -> {
        p.level = 27;
        p.drain = 6;
        p.magicBoost = 0.10;
    }),
    EAGLE_EYE(4126, 2665, p -> {
        p.level = 44;
        p.drain = 12;
        p.rangedAttackBoost = 0.15;
        p.rangedStrengthBoost = 0.15;
    }),
    MYSTIC_MIGHT(4127, 2669, p -> {
        p.level = 45;
        p.drain = 12;
        p.magicBoost = 0.15;
    }),
    CHIVALRY(4128, 3826, p -> {
        p.level = 60;
        p.drain = 24;
        p.attackBoost = 0.15;
        p.defenceBoost = 0.20;
        p.strengthBoost = 0.18;
        p.activationCheck = player -> player.getStats().checkFixed(StatType.Defence, 65, "use this prayer");
    }),
    PIETY(4129, 3825, p -> {
        p.level = 70;
        p.drain = 24;
        p.attackBoost = 0.20;
        p.defenceBoost = 0.25;
        p.strengthBoost = 0.23;
        p.activationCheck = player -> player.getStats().checkFixed(StatType.Defence, 70, "use this prayer");
    }),
    RIGOUR(5464, 2685, p -> {
        p.level = 74;
        p.drain = 24;
        p.rangedAttackBoost = 0.20;
        p.rangedStrengthBoost = 0.23;
        p.defenceBoost = 0.25;
        p.activationCheck = player -> {
            if(!player.getStats().checkFixed(StatType.Defence, 70, "use this prayer"))
                return false;
            if (player.tempUseRaidPrayers) {
                return true;
            }
            if(Config.RIGOUR_UNLOCK.get(player) == 0 && !player.joinedTournament) {
                player.dialogue(new MessageDialogue("You have to learn how to use <col=000080>Rigour</col> before activating it."));
                return false;
            }
            return true;
        };
    }),
    AUGURY(5465, 2670, p -> {
        p.level = 77;
        p.drain = 24;
        p.magicBoost = 0.25;
        p.defenceBoost = 0.25;
        p.activationCheck = player -> {
            if(!player.getStats().checkFixed(StatType.Defence, 70, "use this prayer"))
                return false;
            if (player.tempUseRaidPrayers) {
                return true;
            }
            if(Config.AUGURY_UNLOCK.get(player) == 0 && !player.joinedTournament) {
                player.dialogue(new MessageDialogue("You have to learn how to use <col=000080>Augury</col> before activating it."));
                return false;
            }
            return true;
        };
    }),
    PRESERVE(5466, 2679, p -> {
        p.level = 55;
        p.drain = 3;
        p.activationCheck = player -> {
            if (player.tempUseRaidPrayers) {
                return true;
            }
            if(Config.PRESERVE_UNLOCK.get(player) == 0) {
                player.dialogue(new MessageDialogue("You have to learn how to use <col=000080>Preserve</col> before activating it."));
                return false;
            }
            return true;
        };
    });

    public final String name;
    
    public final Config config;

    public final int soundId;

    public int level;

    public int drain;

    public double attackBoost, strengthBoost, defenceBoost;

    public double rangedAttackBoost, rangedStrengthBoost;

    public double magicBoost;

    public int headIcon = -1;

    public Predicate<Player> activationCheck;

    Prayer(int varpbitId, int soundId, Consumer<Prayer> consumer) {
        int offset = 0;
        String name = "";
        String[] s = name().split("_");
        name += StringUtils.capitalizeFirst(s[offset++].toLowerCase());
        if(s.length >= 3)
            name += " " + s[offset++].toLowerCase();
        if(s.length >= 2)
            name += " " + StringUtils.capitalizeFirst(s[offset].toLowerCase());
        this.name = name;
        this.config = Config.varpbit(varpbitId, false);
        this.soundId = soundId;
        consumer.accept(this);
    }

    public ArrayList<Prayer> disallowed;

    private void disallow(Prayer... prayers) {
        if(disallowed == null)
            disallowed = new ArrayList<>(1);
        for(Prayer prayer : prayers) {
            if(!disallowed.contains(prayer))
                disallowed.add(prayer);
        }
        disallowed.trimToSize();
    }

    /**
     * Static
     */

    public static final Prayer[] QUICK_PRAYER_ORDER;

    static {
        Prayer[] defencePrayers = new Prayer[]{THICK_SKIN, ROCK_SKIN, STEEL_SKIN};
        Prayer[] strengthPrayers = new Prayer[]{BURST_OF_STRENGTH, SUPERHUMAN_STRENGTH, ULTIMATE_STRENGTH};
        Prayer[] attackPrayers = new Prayer[]{CLARITY_OF_THOUGHT, IMPROVED_REFLEXES, INCREDIBLE_REFLEXES};
        Prayer[] rangedPrayers = new Prayer[]{SHARP_EYE, HAWK_EYE, EAGLE_EYE};
        Prayer[] magicPrayers = new Prayer[]{MYSTIC_WILL, MYSTIC_LORE, MYSTIC_MIGHT};
        Prayer[] mixedPrayers = new Prayer[]{CHIVALRY, PIETY, RIGOUR, AUGURY};
        Prayer[] overheadPrayers = new Prayer[]{PROTECT_FROM_MAGIC, PROTECT_FROM_MISSILES, PROTECT_FROM_MELEE, RETRIBUTION, REDEMPTION, SMITE};

        for(Prayer prayer : defencePrayers) {
            prayer.disallow(defencePrayers);
            prayer.disallow(mixedPrayers);
        }
        for(Prayer prayer : strengthPrayers) {
            prayer.disallow(strengthPrayers);
            prayer.disallow(rangedPrayers);
            prayer.disallow(magicPrayers);
            prayer.disallow(mixedPrayers);
        }
        for(Prayer prayer : attackPrayers) {
            prayer.disallow(attackPrayers);
            prayer.disallow(rangedPrayers);
            prayer.disallow(magicPrayers);
            prayer.disallow(mixedPrayers);
        }
        for(Prayer prayer : rangedPrayers) {
            prayer.disallow(rangedPrayers);
            prayer.disallow(strengthPrayers);
            prayer.disallow(attackPrayers);
            prayer.disallow(magicPrayers);
            prayer.disallow(mixedPrayers);
        }
        for(Prayer prayer : magicPrayers) {
            prayer.disallow(magicPrayers);
            prayer.disallow(strengthPrayers);
            prayer.disallow(attackPrayers);
            prayer.disallow(rangedPrayers);
            prayer.disallow(mixedPrayers);
        }
        for(Prayer prayer : mixedPrayers) {
            prayer.disallow(mixedPrayers);
            prayer.disallow(defencePrayers);
            prayer.disallow(strengthPrayers);
            prayer.disallow(attackPrayers);
            prayer.disallow(rangedPrayers);
            prayer.disallow(magicPrayers);
        }
        for(Prayer prayer : overheadPrayers) {
            prayer.disallow(overheadPrayers);
        }

        Prayer[] prayers = values();
        QUICK_PRAYER_ORDER = new Prayer[prayers.length];
        System.arraycopy(prayers, 0, QUICK_PRAYER_ORDER, 0, 24);
        QUICK_PRAYER_ORDER[25] = CHIVALRY;
        QUICK_PRAYER_ORDER[26] = PIETY;
        QUICK_PRAYER_ORDER[24] = RIGOUR;
        QUICK_PRAYER_ORDER[27] = AUGURY;
        QUICK_PRAYER_ORDER[28] = PRESERVE;
    }

    public static Prayer getQuickPrayer(int slot) {
        if(slot < 0 || slot >= QUICK_PRAYER_ORDER.length)
            return null;
        return QUICK_PRAYER_ORDER[slot];
    }

}
