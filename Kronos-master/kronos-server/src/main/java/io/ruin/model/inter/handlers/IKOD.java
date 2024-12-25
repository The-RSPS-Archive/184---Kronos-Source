package io.ruin.model.inter.handlers;

import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.EnumMap;
import io.ruin.cache.ItemDef;
import io.ruin.model.combat.Killer;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.item.actions.impl.ItemBreaking;
import io.ruin.model.item.actions.impl.ItemUpgrading;
import io.ruin.model.item.actions.impl.Pet;
import io.ruin.model.item.actions.impl.chargable.Blowpipe;
import io.ruin.model.item.actions.impl.chargable.CorruptedJavelin;
import io.ruin.model.item.actions.impl.chargable.CorruptedStaff;
import io.ruin.model.item.actions.impl.combine.ItemCombining;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.attributes.AttributeTypes;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.services.Loggers;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static io.ruin.cache.ItemID.BLOOD_MONEY;
import static io.ruin.cache.ItemID.COINS_995;

public class IKOD {

    public static void open(Player player) {
        if(player.isVisibleInterface(Interface.ITEMS_KEPT_ON_DEATH))
            player.closeInterface(InterfaceType.MAIN);
        player.openInterface(InterfaceType.MAIN, Interface.ITEMS_KEPT_ON_DEATH);
        player.getPacketSender().sendAccessMask(Interface.ITEMS_KEPT_ON_DEATH, 18, 0, 4, 2);
        player.getPacketSender().sendAccessMask(Interface.ITEMS_KEPT_ON_DEATH, 21, 0, 42, 2);

        boolean skulled = player.getCombat().isSkulled();
        boolean ultimateIronMan = player.getGameMode().isUltimateIronman(); //todo
        int keepCount = getKeepCount(skulled, ultimateIronMan, player.getPrayer().isActive(Prayer.PROTECT_ITEM));

        ArrayList<Item> items = getItems(player);
        ArrayList<Item> keepItems = new ArrayList<>(keepCount);
        int keepCountRemaining = keepCount;
        long value = 0;
        for(Iterator<Item> it = items.iterator(); it.hasNext(); ) {
            Item item = it.next();
            ItemDef def = item.getDef();
            if(!def.neverProtect && keepCountRemaining > 0) {
                int keepAmount = Math.min(item.getAmount(), keepCountRemaining);
                keepItems.add(new Item(item.getId(), keepAmount));
                keepCountRemaining -= keepAmount;
                item.incrementAmount(-keepAmount);
                if(item.getAmount() == 0) {
                    it.remove();
                    continue;
                }
            }
            value += getValue(item);
        }
        player.getPacketSender().sendItems(-1, 63834, 468, items.toArray(new Item[items.size()]));
        player.getPacketSender().sendItems(-1, 63718, 584, keepItems.toArray(new Item[keepItems.size()]));
        player.getPacketSender().sendClientScript(118, "isii1s", 0, "", keepCount, skulled ? 1 : 0, ultimateIronMan ? 1 : 0, NumberUtils.formatNumber(value) + " " + ("gp"));
    }

    private static final List<Integer> CHARGED_UNTRADEABLES = Arrays.asList(
            12809, 12006, 20655, 20657, 19710,
            12926, 22550, 11283, 21633, 22002,
            12931, 13197, 13199, 22555, 20714,
            12904, 11907, 12899, 22545
    );

    private static boolean allowProtect(Player player, Item item) {  // should this item be allowed to be 'saved'?
        if (item.getDef().neverProtect)
            return false;
        if (!item.getDef().tradeable && item.getDef().breakTo != null)
            return false;
        if (CHARGED_UNTRADEABLES.contains(item.getId()))
            return true;
        if (item.getDef().breakId != 0)
            return true;
        if(item.getDef().combinedFrom != null)
            return true;
        if (item.getDef().upgradedFrom != null) {
            ItemDef broken = ItemDef.get(item.getDef().upgradedFrom.regularId);
            return broken.tradeable;
        }
        return item.getDef().tradeable || player.wildernessLevel > 20;
    }

    //todo - still want to clean this up
    public static void forLostItem(Player player, Killer killer, Consumer<Item> dropConsumer) {
        ArrayList<Item> items = getItems(player);
        if(items.isEmpty())
            return;
        player.getInventory().clear();
        player.getEquipment().clear();
        Item currency = new Item(COINS_995, 0);
        ArrayList<Item> loseItems = new ArrayList<>(items.size());
        ArrayList<Item> keepItems = new ArrayList<>();
        int keepCountRemaining = getKeepCount(player.getCombat().isSkulled(), false, player.getPrayer().isActive(Prayer.PROTECT_ITEM));
        for(Item item : items) {
            boolean lootingBag = isLootingBag(item);
            /* attempt to protect */
            if(keepCountRemaining > 0 && allowProtect(player, item)) {
                int keepAmount = Math.min(item.getAmount(), keepCountRemaining);
                keepItems.add(new Item(item.getId(), keepAmount, item.copyOfAttributes()));
                keepCountRemaining -= keepAmount;
                item.incrementAmount(-keepAmount);
                if(item.getAmount() == 0)
                    continue;
            }

            //Auto keep god d'hides on death, when not in pvp
            if (player.wildernessLevel < 1 && !player.pvpAttackZone && item.getDef().name.contains("d'hide") && (item.getDef().name.contains("Armadyl") ||
                    item.getDef().name.contains("Ancient") ||
                    item.getDef().name.contains("Bandos") ||
                    item.getDef().name.contains("Saradomin") ||
                    item.getDef().name.contains("Guthix") ||
                    item.getDef().name.contains("Zamorak"))) {
                keepItems.add(item);
                continue;
            }

            // Ferocious Gloves
            if (item.getId() == 22981) {
                item.setId(22983);
                loseItems.add(item);
                continue;
            }
            /* rune pouch */
            if(item.getId() == 12791) {
                for(Item rune : player.getRunePouch().getItems()) {
                    if(rune != null)
                        loseItems.add(rune);
                }

                player.getRunePouch().clear();
                continue;
            }
            if (player.wildernessLevel > 0 || player.pvpAttackZone) {
                /* saradomin's blessed sword */
                if (item.getId() == 12809) {
                    item.setId(12804);
                    AttributeExtensions.setCharges(item, 0);
                    loseItems.add(item);
                    continue;
                }
                /* tentacle whip */
                if (item.getId() == 12006) {
                    item.setId(12004);
                    AttributeExtensions.setCharges(item, 0);
                    loseItems.add(item);
                    continue;
                }
                /* ring of suffering */
                if (item.getId() == 20655 || item.getId() == 20657 || item.getId() == 19710) {
                    item.setId(19550);
                    AttributeExtensions.setCharges(item, 0);
                    loseItems.add(item);
                    continue;
                }
                /* serpentine helm */
                if (item.getDef().breakId == 12929) {
                    int scalesAmount = AttributeExtensions.getCharges(item);
                    if (scalesAmount > 0)
                        loseItems.add(new Item(12934, scalesAmount));
                    item.setId(12929);
                    AttributeExtensions.setCharges(item, 0);
                    loseItems.add(item);
                    continue;
                }
                /* viggora's chainmace */
                if (item.getId() == 22545) {
                    int etherAmount = AttributeExtensions.getCharges(item);
                    if (etherAmount > 0)
                        loseItems.add(new Item(21820, etherAmount));
                    item.setId(22542);
                    AttributeExtensions.setCharges(item, 0);
                    loseItems.add(item);
                    continue;
                }
                /* corrupted staff */
                if (item.getId() == CorruptedStaff.CHARGED) {
                    int essenceAmt = AttributeExtensions.getCharges(item);
                    if (essenceAmt > 0)
                        loseItems.add(new Item(CorruptedStaff.ESSENCE, essenceAmt));
                    item.setId(CorruptedStaff.UNCHARGED);
                    AttributeExtensions.setCharges(item, 0);
                    loseItems.add(item);
                    continue;
                }
                /* corrupted staff */
                if (item.getId() == CorruptedJavelin.CHARGED) {
                    int essenceAmt = AttributeExtensions.getCharges(item);
                    if (essenceAmt > 0)
                        loseItems.add(new Item(CorruptedJavelin.ESSENCE, essenceAmt));
                    item.setId(CorruptedJavelin.UNCHARGED);
                    AttributeExtensions.setCharges(item, 0);
                    loseItems.add(item);
                    continue;
                }
                /* craw's bow */
                if (item.getId() == 22550) {
                    int etherAmount = AttributeExtensions.getCharges(item);
                    if (etherAmount > 0)
                        loseItems.add(new Item(21820, etherAmount));
                    item.setId(22547);
                    AttributeExtensions.setCharges(item, 0);
                    loseItems.add(item);
                    continue;
                }
                /* thammarons's sceptre */
                if (item.getId() == 22555) {
                    int etherAmount = AttributeExtensions.getCharges(item);
                    if (etherAmount > 0)
                        loseItems.add(new Item(21820, etherAmount));
                    item.setId(22552);
                    AttributeExtensions.setCharges(item, 0);
                    loseItems.add(item);
                    continue;
                }
                /* dragonfire shield */
                if (item.getDef().id == 11283) {
                    item.setId(11284);
                    AttributeExtensions.setCharges(item, 0);
                    loseItems.add(item);
                    continue;
                }
                /*ancient wyvern shield */
                if (item.getDef().id == 21633) {
                    item.setId(21634);
                    AttributeExtensions.setCharges(item, 0);
                    loseItems.add(item);
                    continue;
                }
                /*dragonfire ward */
                if (item.getDef().id == 22002) {
                    item.setId(22003);

                    loseItems.add(item);
                    continue;
                }
                /* toxic staff of the dead */
                if (item.getDef().breakId == 12902) {
                    int scalesAmount = AttributeExtensions.getCharges(item);
                    if (scalesAmount > 0)
                        loseItems.add(new Item(12934, scalesAmount));
                    item.setId(12902);
                    AttributeExtensions.setCharges(item, 0);
                    loseItems.add(item);
                    continue;
                }
                /* toxic blowpipe */
                if (item.getId() == 12926) {
                    Blowpipe.Dart dart = Blowpipe.getDart(item);
                    if (dart != Blowpipe.Dart.NONE)
                        loseItems.add(new Item(dart.id, Blowpipe.getDartAmount(item)));
                    int scales = Blowpipe.getScalesAmount(item);
                    if (scales > 0)
                        loseItems.add(new Item(12934, scales));
                    AttributeExtensions.setCharges(item, 0);
                    item.setId(12924);
                    loseItems.add(item);
                    continue;
                }
                /* blood money pouches */
                if (item.getId() >= 22521 && item.getId() <= 22524) {
                    loseItems.add(item);
                    continue;
                }
                /* bracelet of ethereum */
                if (item.getDef().breakId == 21817) {
                    int etherAmount = AttributeExtensions.getCharges(item);
                    if (etherAmount > 0)
                        loseItems.add(new Item(21820, etherAmount));
                    AttributeExtensions.setCharges(item, 0);
                    item.setId(21817);
                    loseItems.add(item);
                    continue;
                }
                /* tome of fire */
                if (item.getId() == 20714) {
                    int charges = AttributeExtensions.getCharges(item);
                    if (charges > 0)
                        loseItems.add(new Item(20718, Math.max(1, charges / 20)));
                    AttributeExtensions.setCharges(item, 0);
                    item.setId(20716);
                    loseItems.add(item);
                    continue;
                }
            }
            
            /* pet */
            Pet pet = item.getDef().pet;
            if(pet != null) {
                keepItems.add(item);
                continue;
            }


            /* breakable items */
            ItemBreaking breakable = item.getDef().breakTo;
            if(breakable != null && !breakable.freeFromShops) {
                //if not in wilderness keep the item breakable
                if (player.wildernessLevel < 1 && !player.pvpAttackZone) {
                    keepItems.add(item);
                    continue;
                }
                ItemDef brokenDef = ItemDef.get(breakable.brokenId);
                if (brokenDef == null) {
                    System.out.println("Broken Def is null: " + breakable.brokenId);
                    continue;
                }

                //if killed by player add coins to drop
                if (!Objects.isNull(killer)) {
                    currency.incrementAmount(breakable.coinRepairCost);
                }

                //if in wilderness and broken item not tradeable, keep that item broken version
                if(!brokenDef.tradeable) {
                    item.setId(brokenDef.id);
                    keepItems.add(item);
                    continue;
                }
                //if in wilderness and item is tradeable set item to broken id
                if (player.wildernessLevel > 0 || player.pvpAttackZone) {
                    item.setId(brokenDef.id);
                }
            }
            /* upgraded items */
            ItemUpgrading upgrade = item.getDef().upgradedFrom;
            if(upgrade != null) {
                //if not in wilderness keep the item upgradeable
                if (player.wildernessLevel < 1 && !player.pvpAttackZone) {
                    keepItems.add(item);
                    continue;
                }
                ItemDef regularDef = ItemDef.get(upgrade.regularId);
                if (regularDef == null) {
                    System.out.println("Regular Def is null: " + upgrade.regularId);
                    continue;
                }

                //Always keep these upgrades in wilderness
                if (upgrade.name().toLowerCase().contains("slayer")
                        || upgrade.name().toLowerCase().contains("salve")
                        || upgrade.equals(ItemUpgrading.GUTHIX_CAPE)
                        || upgrade.equals(ItemUpgrading.ZAMORAK_CAPE)
                        || upgrade.equals(ItemUpgrading.SARADOMIN_CAPE)) {
                    keepItems.add(item);
                    continue;
                }

                //if killed by player add coins to drop
                if (!Objects.isNull(killer)) {
                    currency.incrementAmount((long) (upgrade.coinUpgradeCost * .05));
                }

                //if in wilderness and below lvl 20 and regular item not tradeable, keep that item
                if(!regularDef.tradeable) {
                    if(player.wildernessLevel > 0 && player.wildernessLevel < 30  || player.pvpAttackZone) {
                        keepItems.add(new Item(upgrade.regularId, 1, item.copyOfAttributes()));
                    }
                    continue;
                }

                //if in wilderness and item is tradeable set item to regular id
                if (player.wildernessLevel > 0 || player.pvpAttackZone) {
                    item.setId(regularDef.id);
                }
            }
            /* combined items */
            ItemCombining combined = item.getDef().combinedFrom;
            if(combined != null) {
                loseItems.add(new Item(combined.primaryId, item.getAmount()));
                loseItems.add(new Item(combined.secondaryId, item.getAmount()));
                continue;
            }

            /* keep untradeables */ // Ignore auto-keeping looting bag.
            if (!isLootingBag(item) && !item.getDef().tradeable) {
                keepItems.add(item);
                continue;
            }

            // If the player is carrying a looting bag then we dump all of its contents into the lost items collection.
            if (lootingBag) {
                ItemContainer contents = player.getLootingBag();
                if (!contents.isEmpty()) {
                    List<Item> lost = Arrays.stream(contents.getItems()).filter(Objects::nonNull).collect(Collectors.toList());
                    loseItems.addAll(lost);
                    player.getLootingBag().clear();
                }
            }

            loseItems.add(item);
        }
        if(currency.getAmount() > 0)
            loseItems.add(currency);
        int size = Math.min(keepItems.size(), player.getInventory().getItems().length);
        for(int i = 0; i < size; i++)
            player.getInventory().set(i, keepItems.get(i));
        for(Item dropItem : loseItems)
            dropConsumer.accept(dropItem);
        if(killer == null)
            Loggers.logDangerousDeath(player.getUserId(), player.getName(), player.getIp(), -1, "", "", keepItems, loseItems);
        else
            Loggers.logDangerousDeath(player.getUserId(), player.getName(), player.getIp(), killer.player.getUserId(), killer.player.getName(), killer.player.getIp(), keepItems, loseItems);
    }

    public static int getKeepCount(boolean skulled, boolean ultimateIronman, boolean protectingItem) {
        if(ultimateIronman)
            return 0;
        int keepCount = skulled ? 0 : 3;
        if(protectingItem)
            keepCount++;
        return keepCount;
    }

    public static ArrayList<Item> getItems(Player player) {
        int count = player.getInventory().getCount() + player.getEquipment().getCount();
        ArrayList<Item> list = new ArrayList<>(count);
        if(count > 0) {
            for(Item item : player.getInventory().getItems()) {
                if(item != null)
                    list.add(item.copy());
            }
            for(Item item : player.getEquipment().getItems()) {
                if(item != null)
                    list.add(item.copy());
            }

            list.sort((i1, i2) -> Integer.compare(i2.getDef().protectValue, i1.getDef().protectValue));
        }
        return list;
    }

    private static long getValue(Item item) {
        if(item.getId() == BLOOD_MONEY)
            return item.getAmount();
        if(item.getId() == COINS_995)
            return item.getAmount();
        long price = item.getDef().protectValue;
        if(price <= 0 && ((price = item.getDef().highAlchValue)) <= 0)
            return 0;
        return item.getAmount() * price;
    }

    static {
        EnumMap map = EnumMap.get(879);
        for(int id : map.keys)
            ItemDef.get(id).neverProtect = id != 13190 && id != 13192; //true when not bonds

        /**
         * Custom protect items
         */
        ItemDef.get(12931).protectValue = (int) Math.min(Integer.MAX_VALUE, 20000 * 1000L); // Serpentine helm (charged)
        ItemDef.get(13197).protectValue = (int) Math.min(Integer.MAX_VALUE, 30000 * 1000L); // Tanzanite helm (charged)
        ItemDef.get(13199).protectValue = (int) Math.min(Integer.MAX_VALUE, 30000 * 1000L); // Magma helm (charged)
        ItemDef.get(12926).protectValue = (int) Math.min(Integer.MAX_VALUE, 20000 * 1000L); //Charged blowpipe (charged)
        ItemDef.get(22550).protectValue = (int) Math.min(Integer.MAX_VALUE, 30000 * 1000L); //Craws bow (charged)
        ItemDef.get(22547).protectValue = (int) Math.min(Integer.MAX_VALUE, 30000 * 1000L); //Craws bow (uncharged)
        ItemDef.get(22545).protectValue = (int) Math.min(Integer.MAX_VALUE, 30000 * 1000L); //Viggora's chainmace (charged)
        ItemDef.get(22542).protectValue = (int) Math.min(Integer.MAX_VALUE, 30000 * 1000L); //Viggora's chainmace (uncharged)
        ItemDef.get(22555).protectValue = (int) Math.min(Integer.MAX_VALUE, 30000 * 1000L); //Thammaron's sceptre (charged)
        ItemDef.get(22552).protectValue = (int) Math.min(Integer.MAX_VALUE, 30000 * 1000L); //Thammaron's sceptre (uncharged)
        ItemDef.get(11283).protectValue = (int) Math.min(Integer.MAX_VALUE, 20000 * 1000L); //Dragonfire shield (charged)
        ItemDef.get(21633).protectValue = (int) Math.min(Integer.MAX_VALUE, 25000 * 1000L); //Ancient wyvern (charged)
        ItemDef.get(22002).protectValue = (int) Math.min(Integer.MAX_VALUE, 25000 * 1000L); //Dragonfire ward (charged)
        ItemDef.get(12899).protectValue = (int) Math.min(Integer.MAX_VALUE, 25000 * 1000L); //Trident of the swamp (charged)
        ItemDef.get(11907).protectValue = (int) Math.min(Integer.MAX_VALUE, 5000 * 1000L); //Trident of the seas (charged)
        ItemDef.get(11905).protectValue = (int) Math.min(Integer.MAX_VALUE, 5000 * 1000L); //Trident of the seas (fully charged)
        ItemDef.get(12904).protectValue = (int) Math.min(Integer.MAX_VALUE, 10000 * 1000L); //Toxic staff of the dead
        ItemDef.get(20714).protectValue = (int) Math.min(Integer.MAX_VALUE, 6000 * 1000L); //Tome of fire
        ItemDef.get(19550).protectValue = (int) Math.min(Integer.MAX_VALUE, 15000 * 1000L); //Ring of suffering
        ItemDef.get(22613).protectValue = (int) Math.min(Integer.MAX_VALUE, 120000 * 1000L); //Vesta long
    }

    private static boolean isLootingBag(Item item) {
        return item.getId() == 11941 || item.getId() == 22586;
    }
}
