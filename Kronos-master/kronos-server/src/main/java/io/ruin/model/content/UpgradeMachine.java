package io.ruin.model.content;

import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.StringUtils;
import io.ruin.cache.Color;
import io.ruin.cache.ItemDef;
import io.ruin.cache.ObjectID;
import io.ruin.model.World;
import io.ruin.model.content.upgrade.ItemEffect;
import io.ruin.model.content.upgrade.ScrapableItems;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceAction;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.handlers.EquipmentStats;
import io.ruin.model.inter.handlers.TabInventory;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.object.owned.impl.DwarfCannon;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author ReverendDread on 5/27/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class UpgradeMachine {

    @Setter private transient Player player;
    @Getter @Setter private Item selected;

    private static final int[] CURRENT_UPGRADES = { 45, 50, 55 };
    private static final int ITEM_PANE = 56;
    private static final int[] ITEM_BONUSES = { 112, 115, 119, 122, 126, 193, 196, 200, 203, 207, 211, 214, 218, 221 };
    private static final int[] ITEM_REQUIREMENTS = { 238, 239 };
    private static final int[] POSSIBLE_UPGRADES = { 244, 245, 246, 247, 248, 249, 250, 251, 252, 253, 254, 255, 256, 257, 258 };
    private static final int UPGRADE_BUTTON = 240;
    private static final int REQUIREMENT_TEXT = 89;
    private static final double ZOOM_SCALE = 3.0;
    private static final int SCRAP_ID = 30034;

    //sends the main interface with default setup.
    public void sendInterface() {
        player.openInterface(InterfaceType.MAIN, Interface.UPGRADE_MACHINE);
        player.openInterface(InterfaceType.INVENTORY, Interface.UPGRADE_MACHINE_INVENTORY);
        player.getPacketSender().sendAccessMask(Interface.UPGRADE_MACHINE_INVENTORY, 3, 0, 27, 1181694);
        player.getPacketSender().sendAccessMask(Interface.UPGRADE_MACHINE_INVENTORY, 10, 0, 27, 1054);
        player.getPacketSender().sendModel(Interface.UPGRADE_MACHINE, ITEM_PANE, -1);
        player.getPacketSender().sendString(Interface.UPGRADE_MACHINE, REQUIREMENT_TEXT, "");
        for (int slot : ITEM_BONUSES) {
            player.getPacketSender().sendString(Interface.UPGRADE_MACHINE, slot, "0");
        }
        for (int slot : CURRENT_UPGRADES) {
            player.getPacketSender().sendString(Interface.UPGRADE_MACHINE, slot, "");
        }
        for (int slot : POSSIBLE_UPGRADES) {
            player.getPacketSender().sendString(Interface.UPGRADE_MACHINE, slot, "");
        }
        for (int slot : ITEM_REQUIREMENTS) {
            player.getPacketSender().sendItems(Interface.UPGRADE_MACHINE, slot, 0, new Item(-1));
        }
    }

    //select item to upgrade.
    private void selectItem(int slot) {
        Item item = player.getInventory().get(slot);
        if (item == null)
            return;
        ItemDef def = item.getDef();
        if (def != null && canSelect(def)) {
            player.getPacketSender().sendModel(Interface.UPGRADE_MACHINE, ITEM_PANE, def.inventoryModel);
            player.getPacketSender().sendModelInformation(Interface.UPGRADE_MACHINE, ITEM_PANE, (int) (def.zoom2d / ZOOM_SCALE), def.xan2d, def.yan2d);
            setSelected(item);
            sendCurrentUpgrades();
            sendPossibleUpgrades();
            sendStats();
            sendCost();
        } else {
            player.sendMessage("You can't upgrade this item!");
        }
    }

    //send item stats w/ modifications
    private void sendStats() {
//        String[] upgrades = AttributeExtensions.getUpgrades(getSelected());
        if (Objects.nonNull(ITEM_BONUSES)) {
            return;
        }
        for (int index = 0; index < ITEM_BONUSES.length; index++) {
            int slot = ITEM_BONUSES[index];
            int bonus = getSelected().getDef().equipBonuses[index];
//            int upgradeIndex = 1;
//            for (String upgrade : upgrades) {
//                if (upgrade.contains("stat_")) {
//                    String idxName = upgrade.substring(5, 7).replace("=", "");
//                    int idx = Integer.parseInt(idxName);
//                    if (idx >= 0 && idx <= 15) {
//                        if (index == idx) {
//                            String extra_bonus_raw = getSelected().getAttributeString("UPGRADE_" + upgradeIndex, "null");
//                            if (!extra_bonus_raw.equalsIgnoreCase("null") && extra_bonus_raw.contains("=")) {
//                                extra_bonus_raw = extra_bonus_raw.split("=")[1];//.replaceAll("[^0-9]", "");
//                                int extra_bonus = Integer.parseInt(extra_bonus_raw);
//                                bonus += extra_bonus;
//                            }
//                        }
//                    }
//                }
//                upgradeIndex++;
//            }
            player.getPacketSender().sendString(Interface.UPGRADE_MACHINE, slot, EquipmentStats.asBonus(bonus, false));
        }
    }

    private void sendPossibleUpgrades() {
        for (int slot : POSSIBLE_UPGRADES) {
            player.getPacketSender().sendString(Interface.UPGRADE_MACHINE, slot, "");
        }
        ItemEffect[] effects = getPossibleUpgrades();
        for (int component = 0; component < POSSIBLE_UPGRADES.length; component++) {
            if (component > effects.length - 1)
                continue;
            ItemEffect effect = effects[component];
            player.getPacketSender().sendString(Interface.UPGRADE_MACHINE, POSSIBLE_UPGRADES[component], effect.getName());
        }
    }

    private ItemEffect[] getPossibleUpgrades() {
        ItemEffect[] current = AttributeExtensions.getCurrentEffects(getSelected());
        ItemEffect[] effects = Stream.of(ItemEffect.values()).filter(currentEffect -> {
            boolean matchesSlot = Arrays.stream(currentEffect.getSlots()).anyMatch(e -> e == getSelected().getDef().equipSlot);
            for (ItemEffect effect : current) {
                if (currentEffect.equals(effect)) {
                    return false;
                }
            }
            return matchesSlot;
        }).toArray(ItemEffect[]::new);
        return effects;
    }

    private void sendCurrentUpgrades() {
       String[] upgrades = AttributeExtensions.getUpgrades(getSelected());
       for (int index = 0; index < CURRENT_UPGRADES.length; index++) {
           int component = CURRENT_UPGRADES[index];
           String upgrade = upgrades[index];
//           if (upgrade.startsWith("stat_")) { //formatted stat increase/decrease
//               String formatted = upgrade.replace("stat_", "").replace("=", " ");
//               String bonus_name = EquipmentStats.getNameForIndex(Integer.parseInt(formatted.substring(0, 2).replace(" ", "")));
//               upgrade = formatted.substring(formatted.indexOf(" ")).concat(" " + bonus_name);
//           }
           player.getPacketSender().sendString(Interface.UPGRADE_MACHINE, component, StringUtils.getFormattedEnumName(upgrade));
       }
    }

    private void sendCost() {
        int component = 0;
        for (Item item : getCost()) {
            int multiplier = getNextUpgradeSlot();
            player.getPacketSender().sendItems(Interface.UPGRADE_MACHINE, ITEM_REQUIREMENTS[component++], 0, new Item(item.getId(), item.getAmount() * (multiplier == -1 ? 1 : multiplier)));
        }
        player.getPacketSender().sendString(Interface.UPGRADE_MACHINE, REQUIREMENT_TEXT, Color.RED.wrap("These items will be consumed upon upgrading your item."));
    }

    //upgrade the item selected
    private void upgrade() {
        if (getSelected() == null) {
            player.sendMessage("You have to select an item to upgrade first!");
            return;
        }
        int nextSlot = getNextUpgradeSlot();
        if (nextSlot != -1) {
            Item[] cost = Stream.of(getCost()).map(i -> new Item(i.getId(), i.getAmount() * nextSlot)).filter(i -> i.getId() != -1).toArray(Item[]::new);
            if (player.getInventory().containsAll(true, cost)) {
                ItemEffect[] effects = getPossibleUpgrades();
                if (effects.length == 0) {
                    player.sendMessage("There are no upgrades available for this item.");
                    return;
                }
                ItemEffect selected = ItemEffect.rollFrom(effects);
                getSelected().putAttribute("UPGRADE_" + nextSlot, selected);
                player.getInventory().removeAll(true, cost);
                final Item lastSelected = getSelected();
                sendInterface();
                selectItem(lastSelected.getSlot());
                player.getInventory().update(lastSelected.getSlot());
            } else {
                player.sendMessage("You don't have the required items to perform an upgrade.");
            }
        } else {
            player.sendMessage("This item already has max upgrades!");
        }
    }

    private void scrap(Item item) {
        int amount = getScrapForItem(item);
        if (amount > 0) {
            player.dialogue(new YesNoDialogue("Are you sure you want to do this?",
                    Color.RED.wrap("This will destroy your item in the process. You will get " + NumberUtils.formatNumber(amount) + " scrap."), item, () -> {
                if (player.getInventory().hasRoomFor(SCRAP_ID)) {
                    if (item.getAmount() > 1) {
                        player.integerInput("How many " + item.getDef().name + " would you like to scrap?", (input) -> {
                            int amt = player.getInventory().remove(new Item(item.getId(), input));
                            player.getInventory().add(SCRAP_ID, amount * amt);
                            player.sendMessage("You scrap x" + amt + " " + item.getDef().name + "(s)");
                        });
                    } else {
                        player.getInventory().remove(item);
                        player.getInventory().add(SCRAP_ID, amount);
                        player.sendMessage("You scraped " + (StringUtils.vowelStart(item.getDef().name) ? "an" : "a") + " " + item.getDef().name + ".");
                    }
                } else {
                    player.sendMessage("You don't have enough room to scrap anything.");
                }
            }));
        } else {
            player.sendMessage("You can't scrap this item for anything useful.");
        }
    }

    private Item[] getCost() {
        return new Item[] {
                new Item(995, 10_000_000),
                new Item(30034, 250_000)
        };
    }

    private int getNextUpgradeSlot() {
        for (int i = 1; i < 4; i++) {
            String key = "UPGRADE_" + i;
            String value = getSelected().getAttributeString(key, "null");
            if (value.equalsIgnoreCase("null")) {
                return i;
            }
        }
        return -1;
    }

    //If the item clicked can be selected
    private static boolean canSelect(ItemDef def) {
        return (def.inventoryOptions != null && !def.stackable && Stream.of(def.inventoryOptions).filter(Objects::nonNull).anyMatch(s -> s.equalsIgnoreCase("wield") || s.equalsIgnoreCase("wear") || s.equalsIgnoreCase("equip"))) || allowUpgrade(def.id);
    }

    private static final int[] ALLOWED_ITEMS = { 12924,  };

    private static boolean allowUpgrade(int id) {
        return IntStream.of(ALLOWED_ITEMS).anyMatch(i -> i == id);
    }

    private int getScrapForItem(Item item) {
        int amount = 0;
        for (ScrapableItems scrapableItem : ScrapableItems.values()) {
            if (scrapableItem.getId() == item.getId()) {
                amount = scrapableItem.getScrap();
            }
        }
        return amount;
    }

    static {
        ObjectAction.register(ObjectID.UPGRADE_MACHINE, "Upgrade", (player, object) -> player.getUpgradeMachine().sendInterface());
        ItemObjectAction.register(ObjectID.UPGRADE_MACHINE, ((player, item, obj) -> player.getUpgradeMachine().scrap(item)));
        InterfaceHandler.register(Interface.UPGRADE_MACHINE, interfaceHandler -> {
            interfaceHandler.actions[UPGRADE_BUTTON] = (SimpleAction) (player) -> player.getUpgradeMachine().upgrade();
            interfaceHandler.closedAction = (player, i) -> player.getUpgradeMachine().setSelected(null);
        });
        InterfaceHandler.register(Interface.UPGRADE_MACHINE_INVENTORY, (interfaceHandler -> {
            interfaceHandler.actions[3] = new InterfaceAction() {

                @Override
                public void handleClick(Player player, int option, int slot, int itemId) {
                    player.getUpgradeMachine().selectItem(slot);
                }

                @Override
                public void handleDrag(Player player, int fromSlot, int fromItemId, int toInterfaceId, int toChildId, int toSlot, int toItemId) {
                    TabInventory.drag(player, fromSlot, toSlot);
                }
            };
        }));
        ItemItemAction.register(30127, (player, primary, secondary) -> {
            if (canSelect(secondary.getDef()) && AttributeExtensions.hasUpgrades(secondary)) {
                player.dialogue(new YesNoDialogue(Color.RED.wrap("WARNING!"), "This will remove all upgrades on your item.", secondary,
                        () -> AttributeExtensions.removeUpgrades(secondary)));
            } else {
                player.sendMessage("This item doesn't have any upgrades.");
            }
        });
        NPCAction.register(2408, "talk-to", (player, npc) -> {
            World.doCannonReclaim(player.getUserId(), (reclaim) -> {
                if (reclaim) {
                    boolean hasSpace = player.getInventory().hasFreeSlots(DwarfCannon.CANNON_PARTS.length);
                    player.dialogue(
                        new PlayerDialogue("I've lost my cannon, can i have another one?"),
                        !hasSpace ? new NPCDialogue(npc, "Come back when you at least 4 inventory spaces.") : new NPCDialogue(npc, "Yeah sure, here you go. Try not to do it again."),
                        !hasSpace ? new MessageDialogue("You need at least 4 inventory spaces to claim your cannon back.") : new ItemDialogue().one(DwarfCannon.BARRELS, "The Drunken dwarf gives you another cannon.").action(() -> {
                            IntStream.of(DwarfCannon.CANNON_PARTS).forEach(player.getInventory()::add);
                            World.removeCannonReclaim(player.getUserId());
                        })
                    );
                } else {
                    player.dialogue(
                        new PlayerDialogue("Hello there! Are you alright?"),
                        new NPCDialogue(npc, "Of courshe! Why why why hic* why shouldn't I be?"),
                        new PlayerDialogue("I don't know... You look a bit drunk."),
                        new NPCDialogue(npc, "Noooooo, hic* that's the liquor doing the talking."),
                        new PlayerDialogue("Ok... Hey, do you know what this machine next to you does?"),
                        new NPCDialogue(npc, "That old thing? That's jusht my hic* upgrader mashine thingy."),
                        new NPCDialogue(npc, "It makes items do hic* unique things after they've been shoved into it."),
                        new NPCDialogue(npc, "Sometimes you hic* can get pretty cool stuff, but for at a cost."),
                        new PlayerDialogue("What kind of cost are we talking here?"),
                        new NPCDialogue(npc, "Well, it takes a few coins, hic* and couple of item hic* scrap to use it."),
                        new PlayerDialogue("That's pretty cool, how do you get item scrap?"),
                        new NPCDialogue(npc, "All you have to do, is hic* throw any item onto the bench top, " +
                                "and the machine will spit out scrap, though the amount depends on what item you give it."),
                        new PlayerDialogue("What about the effects you were talking about then?"),
                        new NPCDialogue(npc, "You can just look at the hic* bensh, put your items into it, and KABAM! You get cool hic* stuff."),
                        new NPCDialogue(npc, "Their's also a list of effects taped onto the side of it."),
                        new PlayerDialogue("Okay, thanks. I'll give it a try some time.")
                    );
                }
            });
        });
    }

}
