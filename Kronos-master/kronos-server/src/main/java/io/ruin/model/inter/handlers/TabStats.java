package io.ruin.model.inter.handlers;

import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.Color;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerAction;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.OptionAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.map.Bounds;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;

public class TabStats {

    static {
        InterfaceHandler.register(Interface.SKILLS, h -> {
            h.actions[1] = (OptionAction) (player, option) -> handleStat(player, StatType.Attack);
            h.actions[2] = (OptionAction) (player, option) -> handleStat(player, StatType.Strength);
            h.actions[3] = (OptionAction) (player, option) -> handleStat(player, StatType.Defence);
            h.actions[4] = (OptionAction) (player, option) -> handleStat(player, StatType.Ranged);
            h.actions[5] = (OptionAction) (player, option) -> handleStat(player, StatType.Prayer);
            h.actions[6] = (OptionAction) (player, option) -> handleStat(player, StatType.Magic);
            h.actions[7] = (OptionAction) (player, option) -> handleStat(player, StatType.Runecrafting);
            h.actions[8] = (OptionAction) (player, option) -> handleStat(player, StatType.Construction);
            h.actions[9] = (OptionAction) (player, option) -> handleStat(player, StatType.Hitpoints);
            h.actions[10] = (OptionAction) (player, option) -> handleStat(player, StatType.Agility);
            h.actions[11] = (OptionAction) (player, option) -> handleStat(player, StatType.Herblore);
            h.actions[12] = (OptionAction) (player, option) -> handleStat(player, StatType.Thieving);
            h.actions[13] = (OptionAction) (player, option) -> handleStat(player, StatType.Crafting);
            h.actions[14] = (OptionAction) (player, option) -> handleStat(player, StatType.Fletching);
            h.actions[15] = (OptionAction) (player, option) -> handleStat(player, StatType.Slayer);
            h.actions[16] = (OptionAction) (player, option) -> handleStat(player, StatType.Hunter);
            h.actions[17] = (OptionAction) (player, option) -> handleStat(player, StatType.Mining);
            h.actions[18] = (OptionAction) (player, option) -> handleStat(player, StatType.Smithing);
            h.actions[19] = (OptionAction) (player, option) -> handleStat(player, StatType.Fishing);
            h.actions[20] = (OptionAction) (player, option) -> handleStat(player, StatType.Cooking);
            h.actions[21] = (OptionAction) (player, option) -> handleStat(player, StatType.Firemaking);
            h.actions[22] = (OptionAction) (player, option) -> handleStat(player, StatType.Woodcutting);
            h.actions[23] = (OptionAction) (player, option) -> handleStat(player, StatType.Farming);
        });

        InterfaceHandler.register(Interface.SKILL_GUIDE, h -> {
            h.actions[11] = (SimpleAction) p -> selectCategory(p, 0);
            h.actions[12] = (SimpleAction) p -> selectCategory(p, 1);
            h.actions[13] = (SimpleAction) p -> selectCategory(p, 2);
            h.actions[14] = (SimpleAction) p -> selectCategory(p, 3);
            h.actions[15] = (SimpleAction) p -> selectCategory(p, 4);
            h.actions[16] = (SimpleAction) p -> selectCategory(p, 5);
            h.actions[17] = (SimpleAction) p -> selectCategory(p, 6);
            h.actions[18] = (SimpleAction) p -> selectCategory(p, 7);
            h.actions[19] = (SimpleAction) p -> selectCategory(p, 8);
            h.actions[20] = (SimpleAction) p -> selectCategory(p, 9);
            h.actions[21] = (SimpleAction) p -> selectCategory(p, 10);
            h.actions[22] = (SimpleAction) p -> selectCategory(p, 11);
            h.actions[23] = (SimpleAction) p -> selectCategory(p, 12);
            h.actions[24] = (SimpleAction) p -> selectCategory(p, 13);
        });
    }

    private static void handleStat(Player player, StatType statType) {
//        if(!player.getGameMode().isIronMan() && (statType.ordinal() <= 6))
//            setLevel(player, statType);
//        else
            openGuide(player, statType, 0);
    }

    private static void handleTotal(Player player, int option) {
        if(option == 1)
            player.forceText("!My " + Color.ORANGE.wrap("Total Level") + " is " + NumberUtils.formatNumber(player.getStats().totalLevel) + ".");
        else
            player.forceText("!My " + Color.ORANGE.wrap("Total XP") + " is " + NumberUtils.formatNumber(player.getStats().totalXp) + ".");
    }

    public static void openGuide(Player player, StatType statType, int category) {
        Config.SKILL_GUIDE_STAT.set(player, statType.clientId);
        Config.SKILL_GUIDE_CAT.set(player, category);
        player.getPacketSender().sendClientScript(917, "ii", 4600861, 80);
        player.openInterface(InterfaceType.MAIN, Interface.SKILL_GUIDE);
    }

    private static void selectCategory(Player player, int category) {
        Config.SKILL_GUIDE_CAT.set(player, category);
    }

    public static void setLevel(Player player, StatType statType) {
        PlayerAction action = player.getAction(1);
        if(action == PlayerAction.FIGHT || action == PlayerAction.ATTACK) {
            player.sendMessage("You can't set levels from here.");
            return;
        }
        if(player.getBountyHunter().target != null) {
            player.sendMessage("You can't set levels while you have a target.");
            return;
        }
        if(player.joinedTournament) {
            player.sendMessage("You can't set levels while inside a tournament.");
            return;
        }
        int min = statType == StatType.Hitpoints ? 10 : 1;
        int max = 99;
        player.integerInput("Enter desired " + statType.name() + " level: (" + min + "-" + max + ")", level -> {
            if(level < min || level > max && !player.isAdmin()) {
                player.dialogue(new MessageDialogue("Invalid level, please try again."));
                return;
            }
            if(!player.isNearBank() && !player.getPosition().inBounds(new Bounds(3036, 3478, 3144, 3520, 0))) {
                player.dialogue(new MessageDialogue("You can only set your stats near a bank or around Edgeville."));
                return;
            }
            for(Item item : player.getEquipment().getItems()) {
                if(item == null)
                    continue;
                ItemDef def = item.getDef();
                int[] reqs = def.equipReqs;
                if(reqs == null)
                    continue;
                for(int req : reqs) {
                    StatType type = StatType.values()[req >> 8];
                    int lvl = req & 0xff;
                    if(type == statType && level < lvl) {
                        player.dialogue(new MessageDialogue("Before you can set your " + type.name() + " level to " + level + ",<br>you must first unequip your " + def.name + ".").lineHeight(24));
                        return;
                    }
                }
            }
            Stat stat = player.getStats().get(statType);
            stat.currentLevel = stat.fixedLevel = level;
            stat.experience = Stat.xpForLevel(level);
            stat.updated = true;
            player.getPrayer().deactivateAll();
            player.getCombat().updateLevel();
            player.dialogue(new MessageDialogue("Your " + statType.name() + " level is now " + level + "."));
        });
    }

}
