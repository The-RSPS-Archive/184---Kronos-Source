package io.ruin.model.skills.farming.patch.impl;

import io.ruin.model.item.Item;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.impl.SpiritTree;
import io.ruin.model.skills.farming.crop.Crop;
import io.ruin.model.skills.farming.crop.impl.SpiritTreeCrop;
import io.ruin.model.skills.farming.patch.Patch;
import io.ruin.model.stat.StatType;

public class SpiritTreePatch extends Patch {

    public static final Position BRIMHAVEN_TELEPORT = new Position(2800, 3203, 0);
    public static final Position PORT_SARIM_TELEPORT = new Position(3060, 3256, 0);
    public static final Position ETCETERIA_TELEPORT = new Position(2800, 3203, 0);
    public static final Position ZEAH_TELEPORT = new Position(1691, 3542, 0);

    private Position teleportPosition;

    public SpiritTreePatch setTeleportPosition(Position teleportPosition) {
        this.teleportPosition = teleportPosition;
        return this;
    }

    public Position getTeleportPosition() {
        return teleportPosition;
    }

    @Override
    public int getCropVarpbitValue() {
        if (getStage() == getPlantedCrop().getTotalStages()) {
            return 44;
        }
        int val = getPlantedCrop().getContainerIndex() + getStage();
        if (isDiseased()) {
            return val + 13;
        }
        if (isDead()) {
            return val + 24;
        }
        return val;
    }

    private void checkHealth() {
        player.sendMessage("You examine the tree and find that it is in perfect health.");
        getPlantedCrop().getCounter().increment(player, 1);
        player.getStats().addXp(StatType.Farming, ((SpiritTreeCrop)getPlantedCrop()).getCheckHealthXP(), true);
        advanceStage();
        update();
    }

    @Override
    public void cropInteract() {
        if (getStage() == getPlantedCrop().getTotalStages()) {
            checkHealth();
        } else if (getStage() > getPlantedCrop().getTotalStages()) {
            SpiritTree.open(player);
        }
    }

    @Override
    public void objectAction(int option) {
        if (option == 1)
            interact();
        else if (option == 2 && getStage() >= getPlantedCrop().getTotalStages())
            SpiritTree.open(player);
        else if (option == 3)
            player.sendMessage((getPlantedCrop() == null ? "The patch is clear for new crops. " : "The patch has something growing on it. ") + (getCompost() > 0 ? "It has been treated with " + (getCompost() == 1 ? "regular" : "super") + " compost." : ""));
        else if (option == 4)
            ; // TODO open guide
    }


    @Override
    public boolean isDiseaseImmune() {
        return false;
    }

    @Override
    public int calculateProduceAmount() {
        return 0;
    }

    @Override
    public boolean requiresCure() {
        return false;
    }

    @Override
    public boolean canPlant(Crop crop) {
        return crop instanceof SpiritTreeCrop;
    }

    @Override
    public void plant(Item item) {
        if (plantedCount() >= getMaxCount()) {
            player.sendMessage("You cannot have any more spirit trees planted at your current Farming level.");
            return;
        }
        super.plant(item);
    }

    public int plantedCount() {
        int count = 0;
        if (player.getFarming().getBrimhavenSpiritTree().getPlantedCrop() != null) {
            count++;
        }
        if (player.getFarming().getPortSarimSpiritTree().getPlantedCrop() != null) {
            count++;
        }
        if (player.getFarming().getZeahSpiritTree().getPlantedCrop() != null) {
            count++;
        }
        if (player.getFarming().getEtceteriaSpiritTree().getPlantedCrop() != null) {
            count++;
        }
        return count;
    }

    public int getMaxCount() {
        if (player.getStats().get(StatType.Farming).currentLevel == 99) {
            return Integer.MAX_VALUE;
        }
        if (player.getStats().get(StatType.Farming).currentLevel >= 91) {
            return 2;
        }
        return 1;
    }

    public boolean canTeleport() {
        return getPlantedCrop() != null && getStage() >= getPlantedCrop().getTotalStages();
    }

    @Override
    public String getPatchName() {
        return "a spirit tree";
    }
}
