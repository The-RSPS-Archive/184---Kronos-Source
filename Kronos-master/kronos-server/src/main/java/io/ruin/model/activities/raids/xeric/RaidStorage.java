package io.ruin.model.activities.raids.xeric;

import io.ruin.cache.ItemDef;
import io.ruin.cache.ItemID;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Unlock;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.item.ItemContainerG;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.Tool;
import io.ruin.model.skills.construction.Buildable;
import io.ruin.model.skills.construction.Material;
import io.ruin.model.stat.StatType;

import java.util.List;
import java.util.Map;

public class RaidStorage extends ItemContainer {

    public static final Buildable[] STORAGE_UNITS = {Buildable.SMALL_STORAGE_UNIT, Buildable.MEDIUM_STORAGE_UNIT, Buildable.LARGE_STORAGE_UNIT};

    static {
        for (Buildable unit : STORAGE_UNITS) {
            ObjectAction.register(unit.getBuiltObjects()[0], "shared", (p, obj) -> {
                if (!ChambersOfXeric.isRaiding(p))
                    return;
                p.raidsParty.getRaid().openStorage(p);
            });
            ObjectAction.register(unit.getBuiltObjects()[0], "private", (p, obj) -> openPrivateStorage(p));
            ObjectAction.register(unit.getBuiltObjects()[0], "upgrade", (p, obj) -> upgradeStorage(p));
        }
        ObjectAction.register(29769, "build", (p, obj) -> openBuildMenu(p));

        ObjectAction.register(30107, "private", (p, obj) -> openPrivateStorage(p)); // storage unit outside - for withdrawing items left in private storage
        InterfaceHandler.register(Interface.RAID_PRIVATE_STORAGE, h -> {
            h.actions[5] = (SimpleAction) p -> {
                if (!ChambersOfXeric.isRaiding(p)) {
                    p.sendMessage("The shared storage can only be used while inside a raid.");
                    return;
                }
                p.raidsParty.getRaid().openStorage(p);
            };
            h.actions[9] = (SimpleAction) p -> {
               for (Item item : p.getPrivateRaidStorage().getItems()) {
                   if (item == null)
                       continue;
                   if (!withdrawFromPrivate(p, item.getSlot(), item.getAmount()))
                       return;
               }
            };
            h.actions[10] = (SimpleAction) p -> {
                if (!ChambersOfXeric.isRaiding(p)) {
                    bankPrivateStorage(p);
                } else {
                    for (Item item : p.getInventory().getItems()) {
                        if (item == null)
                            continue;
                        if (item.move(item.getId(), item.getAmount(), p.getPrivateRaidStorage()) == 0) {
                            p.sendMessage("Not enough space in the storage unit.");
                            return;
                        }
                    }
                    p.getPrivateRaidStorage().sendUpdates();
                }
            };
            h.actions[6] = (DefaultAction) (p, option, slot, id) -> {
                if (!ChambersOfXeric.isRaiding(p))
                    return;
                switch (option) {
                    case 1:
                        withdrawFromPrivate(p, slot, 1);
                        return;
                    case 2:
                        withdrawFromPrivate(p, slot, 5);
                        return;
                    case 3:
                        withdrawFromPrivate(p, slot, 10);
                        return;
                    case 4:
                        withdrawFromPrivate(p, slot, Integer.MAX_VALUE);
                        return;
                    case 5:
                        p.integerInput("Enter amount:", amt -> withdrawFromPrivate(p, slot, amt));
                        return;
                }
            };
        });

        InterfaceHandler.register(Interface.RAID_SHARED_STORAGE, h -> {
            h.actions[5] = (SimpleAction) RaidStorage::openPrivateStorage;
            h.actions[7] = (DefaultAction) (p, option, slot, id) -> { // this interface works in a rather odd way - the slot is actually an index to enum 1666...
                if (!ChambersOfXeric.isRaiding(p))
                    return;
                RaidStorage storage = p.raidsParty.getRaid().getStorage();
                switch (option) {
                    case 1:
                        storage.withdraw(p, id, 1);
                        return;
                    case 2:
                        storage.withdraw(p, id, 5);
                        return;
                    case 3:
                        storage.withdraw(p, id, 10);
                        return;
                    case 4:
                        storage.withdraw(p, id, Integer.MAX_VALUE);
                        return;
                    case 5:
                        p.integerInput("Enter amount:", amt -> storage.withdraw(p, id, amt));
                        return;
                }
            };
        });
        InterfaceHandler.register(Interface.RAID_STORAGE_INVENTORY, h -> {
            h.actions[8] = (SimpleAction) p -> Config.RAIDS_STORAGE_WARNING_DISMISSED.set(p, 1);
            h.actions[1] = (DefaultAction) (p, option, slot, id) -> {
                if (Config.RAIDS_STORAGE_PRIVATE_INVENTORY.get(p) == 0) { // storing into shared storage
                    if (!ChambersOfXeric.isRaiding(p))
                        return;
                    RaidStorage storage = p.raidsParty.getRaid().getStorage();
                    switch (option) {
                        case 1:
                            storage.deposit(p, slot, 1);
                            return;
                        case 2:
                            storage.deposit(p, slot, 5);
                            return;
                        case 3:
                            storage.deposit(p, slot, 10);
                            return;
                        case 4:
                            storage.deposit(p, slot, Integer.MAX_VALUE);
                            return;
                        case 5:
                            p.integerInput("Enter amount:", amt -> storage.deposit(p, slot, amt));
                            return;
                        case 10:
                            if (!ItemDef.get(id).coxItem) {
                                p.sendMessage("This item cannot be placed in the shared storage.");
                            }
                            return;
                    }
                } else { // to private storage
                    if (!ChambersOfXeric.isRaiding(p))
                        return;
                    switch (option) {
                        case 1:
                            depositToPrivate(p, slot, 1);
                            return;
                        case 2:
                            depositToPrivate(p, slot, 5);
                            return;
                        case 3:
                            depositToPrivate(p, slot, 10);
                            return;
                        case 4:
                            depositToPrivate(p, slot, Integer.MAX_VALUE);
                            return;
                        case 5:
                            p.integerInput("Enter amount:", amt -> depositToPrivate(p, slot, amt));
                            return;
                    }
                }
            };
        });
    }

    private static void bankPrivateStorage(Player p) {
        for (Item item : p.getPrivateRaidStorage().getItems()) {
            if (item == null)
                continue;
            int moved = item.move(item.getId(), item.getAmount(), p.getBank());
            if (moved != item.getAmount()) {
                p.sendMessage("Not enough space in your bank.");
                p.getPrivateRaidStorage().sendUpdates();
                return;
            }
        }
        p.sendMessage("Your items have been deposited.");
        p.getPrivateRaidStorage().sendUpdates();
    }

    private static boolean depositToPrivate(Player p, int slot, int amount) {
        Item item = p.getInventory().get(slot);
        if (item == null)
            return false;
        int moved = item.move(item.getId(), amount, p.getPrivateRaidStorage());
        p.getPrivateRaidStorage().sendUpdates();
        if (moved == 0) {
            p.sendMessage("Not enough space in the storage unit.");
            return false;
        }
        return true;
    }


    public static boolean withdrawFromPrivate(Player p, int slot, int amount) {
        Item item = p.getPrivateRaidStorage().get(slot);
        if (item == null)
            return false;
        int moved = item.move(item.getId(), amount, p.getInventory());
        p.getPrivateRaidStorage().sendUpdates();
        if (moved == 0) {
            p.sendMessage("Not enough space in your inventory.");
            return false;
        }
        return true;
    }

    private static void upgradeStorage(Player p) {
        if (!ChambersOfXeric.isRaiding(p))
            return;
        p.closeInterfaces();
        ChambersOfXeric raid = p.raidsParty.getRaid();
        int currentLevel = raid.getStorageLevel();
        if (currentLevel == 0)
            return;
        else if (currentLevel == 3) {
            p.sendMessage("The storage unit is already at maximum level.");
            return;
        }
        Buildable toBuild = STORAGE_UNITS[currentLevel];
        if (!p.getStats().check(StatType.Construction, toBuild.getLevelReq(), "build the next level storage unit")) {
            return;
        }
        if (!p.getInventory().contains(Material.MALLIGNUM_ROOT_PLANK.item(2))) {
            p.sendMessage("You will need 2 mallignum root planks to upgrade the storage unit.");
            return;
        }
        if (!p.getInventory().contains(Tool.HAMMER, 1) && !p.getInventory().contains(ItemID.DRAGON_WARHAMMER, 1)) {
            p.sendMessage("You'll need a hammer to upgrade the storage unit.");
            return;
        }
        p.startEvent(event -> {
            p.lock();
            p.animate(toBuild.getAnimation());
            event.delay(2);
            p.getInventory().remove(Material.MALLIGNUM_ROOT_PLANK.getItemId(), 2);
            p.getStats().addXp(StatType.Construction, toBuild.getXP(), true);
            raid.setStorageLevel(currentLevel + 1);
            p.unlock();
        });

    }

    public static void openBuildMenu(Player player) {
        if (!ChambersOfXeric.isRaiding(player))
            return;
        player.openInterface(InterfaceType.MAIN, Interface.CONSTRUCTION_FURNITURE_CREATION);
        int count = 1;
        for (Buildable b : STORAGE_UNITS)
            player.getPacketSender().sendClientScript(1404, "iiisi", count++, b.getItemId(), b.getLevelReq(), b.getCreationMenuString(), b.hasAllMaterials(player) ? 1 : 0);
        player.getPacketSender().sendClientScript(1406, "ii", count - 1, 1);
        player.getPacketSender().sendAccessMask(458, 2, 1, count - 1, 1);
        player.getPacketSender().sendClientScript(2157, "");
    }

    public static void selectStorageToBuild(Player p, int level) {
        if (!ChambersOfXeric.isRaiding(p))
            return;
        p.closeInterfaces();
        ChambersOfXeric raid = p.raidsParty.getRaid();
        int currentlyBuilt = raid.getStorageLevel();
        if (level < 1 || level > STORAGE_UNITS.length)
            return;
        if (currentlyBuilt == level) {
            p.sendMessage("You already have a storage of that type built.");
            return;
        } else if (currentlyBuilt > level) {
            p.sendMessage("You already have a bigger storage built.");
            return;
        }
        Buildable toBuild = STORAGE_UNITS[level - 1];
        if (!p.getStats().check(StatType.Construction, toBuild.getLevelReq(), "build that storage unit")) {
            return;
        }
        if (!toBuild.hasAllMaterials(p)) {
            p.sendMessage("You will need at least " + toBuild.getMaterials().get(0).getAmount() + " mallignum root planks to build that storage unit.");
            return;
        }
        if (!p.getInventory().contains(Tool.HAMMER, 1) && !p.getInventory().contains(ItemID.DRAGON_WARHAMMER, 1) && !p.getInventory().contains(23620, 1)) {
            p.sendMessage("You'll need a hammer to build a storage unit.");
            return;
        }
        p.startEvent(event -> {
            p.lock();
            p.animate(toBuild.getAnimation());
            event.delay(2);
            toBuild.removeMaterials(p);
            p.getStats().addXp(StatType.Construction, toBuild.getXP(), true);
            raid.setStorageLevel(level);
            p.unlock();
        });

    }

    private void withdraw(Player p, int id, int amount) {
        Item item = findItem(id);
        if (item == null)
            return;
        if (p.getGameMode().isIronMan()) {
            p.sendMessage("Ironmen cannot take items from the shared storage.");
            return;
        }
        int moved = item.move(id, amount, p.getInventory());
        if (moved > 0)
            sendUpdates();
        else
            p.sendMessage("Not enough space in your inventory.");
    }

    private void deposit(Player p, int slot, int amount) {
        Item item = p.getInventory().get(slot);
        if (item == null)
            return;
        if (!item.getDef().coxItem) {
            p.sendMessage("You may only store raid items into the shared storage.");
            return;
        }
        int moved = item.move(item.getId(), amount, this);
        if (moved > 0)
            sendUpdates();
        else
            p.sendMessage("Not enough space in the storage unit.");
    }


    private ChambersOfXeric raid;

    public RaidStorage(ChambersOfXeric raid) {
        this.raid = raid;
        init(null, 250, -1, -1, 32768 + 582, false);
    }

    public void setSize(int size) {
        if (size != items.length)
            init(null, size, -1, -1, 32768 + 582, false);
    }

    @Override
    public boolean sendUpdates(ItemContainerG mirrorContainer) {
        if(updatedCount == 0 && !sendAll)
            return false;
        for (Player player : raid.getParty().getMembers()) {
            if(sendAll) {
                if(player != null) {
                    player.getPacketSender().sendItems(interfaceHash, containerId, items, items.length);
                }
            } else {
                if(player != null) {
                    player.getPacketSender().updateItems(interfaceHash, containerId, items, updatedSlots, updatedCount);
                }
            }
        }
        for(int i = 0; i < updatedSlots.length; i++)
            updatedSlots[i] = false;
        updatedCount = 0;
        sendAll = false;
        return true;
    }

    public void send(Player player) {
        player.getPacketSender().sendItems(interfaceHash, containerId, items, items.length);
    }

    @Override
    public int add(int id, int amount, Map<String, String> attributes) {
        ItemDef def = ItemDef.get(id);
        if (def == null || !def.coxItem)
            return 0;
        return super.add(id, amount, attributes);
    }

    public void open(Player player) {
        player.openInterface(InterfaceType.MAIN, Interface.RAID_SHARED_STORAGE);
        player.openInterface(InterfaceType.INVENTORY, Interface.RAID_STORAGE_INVENTORY);
        Config.RAIDS_STORAGE_PRIVATE_INVENTORY.set(player, 0);
        player.getPacketSender().sendString(Interface.RAID_SHARED_STORAGE, 4, String.valueOf(items.length));
        new Unlock(551, 1, 0, 27).unlockMultiple(player, 0,1,2,3,4,9);
        new Unlock(550, 7, 0, 600).unlockMultiple(player, 0,1,2,3,4,9);
    }

    public static void openPrivateStorage(Player player) {
        player.openInterface(InterfaceType.MAIN, Interface.RAID_PRIVATE_STORAGE);
        if (ChambersOfXeric.isRaiding(player)) {
            player.openInterface(InterfaceType.INVENTORY, Interface.RAID_STORAGE_INVENTORY);
            Config.RAIDS_STORAGE_PRIVATE_INVENTORY.set(player, 1);
        }
        player.getPacketSender().sendString(Interface.RAID_PRIVATE_STORAGE, 4, String.valueOf(player.getPrivateRaidStorage().getItems().length));
        new Unlock(551, 1, 0, 27).unlockMultiple(player, 0,1,2,3,4,9);
        new Unlock(271, 6, 0, 600).unlockMultiple(player, 0,1,2,3,4,9);
        player.getPrivateRaidStorage().sendUpdates();
    }
}
