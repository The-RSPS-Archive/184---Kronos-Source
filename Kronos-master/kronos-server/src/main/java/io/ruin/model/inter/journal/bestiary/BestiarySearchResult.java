package io.ruin.model.inter.journal.bestiary;

import io.ruin.Server;
import io.ruin.api.utils.StringUtils;
import io.ruin.cache.Color;
import io.ruin.cache.NPCDef;
import io.ruin.model.achievements.listeners.intro.TheBestiary;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.XpMode;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.journal.JournalEntry;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BestiarySearchResult extends JournalEntry {

    public final int id;

    public String name;

    public BestiarySearchResult(int npcId) {
        this.id = npcId;
        this.name = NPCDef.get(npcId).name.replaceAll("<col=\\w{6}>|</col>", "");
    }

    private void showInfo(Player player) {
        NPCDef def = NPCDef.get(id);
        if(def.combatInfo == null) {
            player.sendMessage("<img=108>" + Color.DARK_GREEN.tag() + " Bestiary: " + Color.OLIVE.tag() + name + " has no combat information.");
            return;
        }
        String stats = (Color.ORANGE.tag() + "Stats") + "<br>" +
                "Combat Level: " + def.combatLevel + "<br>" +
                "Hitpoints: " + def.combatInfo.hitpoints + "<br>" +
                "Attack: " + def.combatInfo.attack + "<br>" +
                "Strength: " + def.combatInfo.strength + "<br>" +
                "Defence: " + def.combatInfo.defence + "<br>" +
                "Ranged: " + def.combatInfo.ranged + "<br>" +
                "Magic: " + def.combatInfo.magic + "<br>" +
                "Max Standard Hit: " + def.combatInfo.max_damage + "<br>" +
                "Main Attack Style: " + StringUtils.capitalizeFirst(def.combatInfo.attack_style.name().toLowerCase());
        String aggressiveStats = (Color.ORANGE.tag() + "Aggressive Stats") + "<br>" +
                "Stab: " + def.combatInfo.stab_attack + "<br>" +
                "Slash: " + def.combatInfo.slash_attack + "<br>" +
                "Crush: " + def.combatInfo.crush_attack + "<br>" +
                "Magic: " + def.combatInfo.magic_attack + "<br>" +
                "Ranged: " + def.combatInfo.ranged_attack;
        String defensiveStats = (Color.ORANGE.tag() + "Defensive Stats") + "<br>" +
                "Stab: " + def.combatInfo.stab_defence + "<br>" +
                "Slash: " + def.combatInfo.slash_defence + "<br>" +
                "Crush: " + def.combatInfo.crush_defence + "<br>" +
                "Magic: " + def.combatInfo.magic_defence + "<br>" +
                "Ranged: " + def.combatInfo.ranged_defence;
        double attackSeconds = ((def.combatInfo.attack_ticks * (double) Server.tickMs()) / 1000D);
        double deathSeconds = ((def.combatInfo.death_ticks * (double) Server.tickMs()) / 1000D);
        double respawnSeconds = ((def.combatInfo.respawn_ticks * (double) Server.tickMs()) / 1000D);
        String other = (Color.ORANGE.tag() + "Other Information") + "<br>" +
                "Attack Delay: " + (attackSeconds % 1 == 0 ? Integer.toString((int) attackSeconds) : attackSeconds) + " seconds<br>" +
                "Death Delay: " + (deathSeconds % 1 == 0 ? Integer.toString((int) deathSeconds) : deathSeconds) + " seconds<br>" +
                "Respawn Delay: " + (respawnSeconds % 1 == 0 ? Integer.toString((int) respawnSeconds) : respawnSeconds) + " seconds<br>" +
                "Immune to Poison: " + (def.combatInfo.poison_immunity ? "Yes" : "No") + "<br>" +
                "Immune to Venom: " + (def.combatInfo.venom_immunity ? "Yes" : "No");
        if(def.combatInfo.slayer_tasks != null && def.combatInfo.slayer_tasks.length > 0) {
            other += "<br>Slayer Level: " + def.combatInfo.slayer_level + "<br>" +
                    "Slayer Experience: " + (def.combatInfo.slayer_xp % 1 == 0 ? Integer.toString((int) def.combatInfo.slayer_xp) : def.combatInfo.slayer_xp) + "<br>" +
                    "Slayer Tasks: " + Arrays.toString(def.combatInfo.slayer_tasks).replace("[", "").replace("]", "");
        }
        if (def.combatInfo.combat_xp_modifier != 1.0) {
            int mod = (int) (def.combatInfo.combat_xp_modifier * 100);
            other += "<br>Combat XP Modifier: " + mod + "%<br>";
        }
        player.getPacketSender().sendString(522, 3, name);
        player.getPacketSender().sendClientScript(1179, "s", stats + "|" + aggressiveStats + "|" + defensiveStats + "|" + other);
        //todo - generate this string in the constructor! ^^^ :)
        player.openInterface(InterfaceType.INVENTORY, 522);
        TheBestiary.complete(player);
    }

    private void showDrops(Player player) {
        NPCDef def = NPCDef.get(id);
        if(def.lootTable == null) {
            player.sendMessage("<img=108>" + Color.DARK_GREEN.tag() + " Bestiary: " + Color.OLIVE.tag() + name + " has no drops.");
            return;
        }
        int petId, petAverage;
        if(def.combatInfo != null && def.combatInfo.pet != null) {
            petId = def.combatInfo.pet.itemId;
            petAverage = def.combatInfo.pet.dropAverage;
        } else {
            petId = -1;
            petAverage = 0;
        }
        List<Integer[]> drops = new ArrayList<>();
        double totalTablesWeight = def.lootTable.totalWeight;
        if(def.lootTable.guaranteed != null) {
            for(LootItem item : def.lootTable.guaranteed) {
                Integer[] drop = new Integer[5];
                drop[0] = item.id;
                drop[1] = item.broadcast == null ? -1 : item.broadcast.ordinal();
                drop[2] = item.min;
                drop[3] = item.max;
                drop[4] = 1; //average 1/1
                drops.add(drop);
            }
        }
        if(def.lootTable.tables != null) {
            for(LootTable.ItemsTable table : def.lootTable.tables) {
                if(table != null) {
                    double tableChance = table.weight / totalTablesWeight;
                    if(table.items.length == 0) {
                        //Nothing!
                        //nothingPercentage = tableChance * 100D;
                    } else {
                        for(LootItem item : table.items) {
                            Integer[] drop = new Integer[5];
                            drop[0] = item.id;
                            drop[1] = item.broadcast == null ? -1 : item.broadcast.ordinal();
                            drop[2] = item.min;
                            drop[3] = item.max;
                            if (player.xpMode == XpMode.HARD) {
                                if (item.weight == 0)
                                    drop[4] = (int) ((1D / tableChance) * .9);
                                else
                                    drop[4] = (int) ((1D / (tableChance * (item.weight / table.totalWeight))) * .9);
                            } else {
                                if (item.weight == 0)
                                    drop[4] = (int) (1D / tableChance);
                                else
                                    drop[4] = (int) (1D / (tableChance * (item.weight / table.totalWeight)));
                            }
                            drops.add(drop);
                        }
                    }
                }
            }
        }
        //todo - some how generate this string in the constructor! ^^^ :)
        player.openInterface(InterfaceType.MAIN, 383);
        player.getPacketSender().sendDropTable(name, petId, petAverage, drops);
    }

    @Override
    public void send(Player player) {
        send(player, "<img=109> " + name, Color.BRONZE);
    }

    @Override
    public void select(Player player) {
        showInfo(player);
        showDrops(player);
    }

    static {
        InterfaceHandler.register(522, h -> {
            h.actions[17] = (SimpleAction) p -> p.closeInterface(InterfaceType.INVENTORY_OVERLAY);
        });
    }

}