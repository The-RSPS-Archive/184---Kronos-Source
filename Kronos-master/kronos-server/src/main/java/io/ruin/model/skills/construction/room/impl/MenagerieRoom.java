package io.ruin.model.skills.construction.room.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Unlock;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.item.actions.impl.Pet;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.construction.Buildable;
import io.ruin.model.skills.construction.Hotspot;
import io.ruin.model.skills.construction.RoomDefinition;
import io.ruin.model.skills.construction.room.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenagerieRoom extends Room {

    static {
        InterfaceHandler.register(Interface.PET_HOUSE, h -> {
            h.actions[7] = (DefaultAction) (player, option, slot, itemId) -> {
                if (!player.isInOwnHouse())
                    return;
                Room room = player.getCurrentRoom();
                if (room instanceof MenagerieRoom) {
                    MenagerieRoom menagerie = (MenagerieRoom) room;
                    menagerie.withdrawPet(player, slot - 40);
                }
            };
            h.actions[9] = (SimpleAction) (player) -> {
                if (!player.isInOwnHouse())
                    return;
                Room room = player.getCurrentRoom();
                if (room instanceof MenagerieRoom) {
                    MenagerieRoom menagerie = (MenagerieRoom) room;
                    menagerie.toggleRoam(player);
                }
            };
        });
        for (Buildable petHouse : Arrays.asList(Buildable.OAK_HOUSE, Buildable.TEAK_HOUSE, Buildable.MAHOGANY_HOUSE, Buildable.CONSECRATED_HOUSE, Buildable.DESECRATED_HOUSE, Buildable.NATURE_HOUSE)) {
            petHouse.setRemoveTest((p, room) -> {
                if (!room.getHouse().getPetContainer().isEmpty()) {
                    p.sendMessage("You must remove all the pets from the pet house before you can remove it.");
                    return false;
                }
                return true;
            });
        }
    }

    @Override
    public void removeHotspotObject(GameObject obj) {
        if (getDefinition() == RoomDefinition.MENAGERIE_OUTDOORS && obj.id == 26865 && getBuilt(Hotspot.HABITAT) != null) { // arena floor, replace it with habitat floor instead of removing it
            obj.setId(getBuilt(Hotspot.HABITAT).getBuiltObjects()[1]);
        } else {
            super.removeHotspotObject(obj);
        }
    }

    private void toggleRoam(Player player) {
        int newValue = Config.PETS_ROAMING_DISABLED.get(player) == 0 ? 1 : 0;
        Config.PETS_ROAMING_DISABLED.set(player, newValue);
        for (Item item : getHouse().getPetContainer().getItems()) {
            if (item != null && item.getDef().pet != null) {
                if (newValue == 1)
                    despawnPet(item.getDef().pet);
                else
                    spawnPet(item.getDef().pet);
            }
        }
    }

    private void withdrawPet(Player player, int slot) {
        Item pet = getHouse().getPetContainer().getSafe(slot);
        if (pet == null) {
            return;
        }
        if (pet.move(pet.getId(), 1, player.getInventory()) > 0 && Config.PETS_ROAMING_DISABLED.get(player) == 0) {
            despawnPet(pet.getDef().pet); // player removed pet from house, despawn it from the house
            getHouse().getPetContainer().send(player);
        }
    }

    private List<Position> spawnPoints = new ArrayList<>(64);

    @Override
    protected void onBuild() {
        loadSpawnPoints();
        if (getBuilt(Hotspot.PET_HOUSE) != null && Config.PETS_ROAMING_DISABLED.get(getHouse().getOwner()) == 0) {
            for (Item item : getHouse().getPetContainer().getItems()) {
                if (item != null && item.getDef().pet != null) {
                    spawnPet(item.getDef().pet);
                }
            }
        }
        getHotspotObjects(Hotspot.PET_HOUSE).forEach(obj -> {
            ItemObjectAction.register(obj, this::storePet);
            ObjectAction.register(obj, 1, this::viewPets);
        });
    }

    private void viewPets(Player player, GameObject gameObject) {
        if (!player.isInOwnHouse()) {
            player.sendMessage("Only the house owner can do that.");
            return;
        }
        player.openInterface(InterfaceType.MAIN, Interface.PET_HOUSE);
        new Unlock(211,7, 0,80).unlockMultiple(player, 0, 10);
        getHouse().getPetContainer().send(player);
    }

    private void storePet(Player player, Item item, GameObject gameObject) {
        if (!player.isInOwnHouse()) {
            player.sendMessage("Only the house owner can store their pets here.");
            return;
        }
        if (item.getDef().pet == null) {
            player.sendMessage("You can only store pets in the pet house.");
            return;
        }
        int maxPets = getMaxPets(getBuilt(Hotspot.PET_HOUSE));
        if (maxPets <= 0)
            return;
        if (getHouse().getPetContainer().getCount() >= maxPets) {
            player.sendMessage("The pet house is already at its max capacity of " + maxPets + " pets. Upgrade it or remove a pet to make room for more pets.");
            return;
        }
        item.move(item.getId(), 1, getHouse().getPetContainer());
        if (Config.PETS_ROAMING_DISABLED.get(player) == 0)
            spawnPet(item.getDef().pet);
        player.animate(833);
    }

    private void loadSpawnPoints() {
        spawnPoints.clear();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Position pos = getAbsolutePosition(x, y);
                Tile t = Tile.get(pos);
                if (t == null || t.clipping == 0)
                    spawnPoints.add(pos);
            }
        }
    }

    private void spawnPet(Pet pet) {
        NPC npc = new NPC(pet.roamId);
        getHouse().addNPC(npc.spawn(Random.get(spawnPoints)));
        npc.walkBounds = new Bounds(npc.getSpawnPosition(), 16);
        npc.ownerId = getHouse().getOwner().getUserId();
        NPCAction.register(npc, "pick-up", (player, n) -> {
            if (Pet.pickup(player, n, pet)) {
                getHouse().getPetContainer().remove(pet.itemId, 1);
            }
        });
    }

    private void despawnPet(Pet pet) {
        for (NPC npc : getHouse().getMap().getNpcs()) {
            if (npc.getId() == pet.roamId && !npc.isRemoved()) {
                npc.remove();
                return;
            }
        }
    }

    public static int getMaxPets(Buildable house) {
        switch (house) {
            case OAK_HOUSE:
                return 3;
            case TEAK_HOUSE:
                return 5;
            case MAHOGANY_HOUSE:
                return 7;
            case CONSECRATED_HOUSE:
                return 9;
            case DESECRATED_HOUSE:
                return 12;
            case NATURE_HOUSE:
                return 40;
            default:
                return 0;
        }
    }
}
