package io.ruin.model.skills.farming.patch.impl;

import com.google.gson.annotations.Expose;
import io.ruin.api.utils.Random;
import io.ruin.model.item.Item;
import io.ruin.model.skills.farming.crop.Crop;
import io.ruin.model.skills.farming.crop.impl.AllotmentCrop;
import io.ruin.model.skills.farming.crop.impl.FlowerCrop;
import io.ruin.model.skills.farming.patch.Patch;
import io.ruin.model.skills.farming.patch.PatchData;
import io.ruin.model.stat.StatType;

import java.util.Arrays;
import java.util.List;

public class AllotmentPatch extends Patch {

    private FlowerPatch associatedFlowerPatch;
    @Expose private boolean watered;

    public static final List<Integer> WATERING_CAN_IDS = Arrays.asList(5331, 5333, 5334, 5335, 5336, 5337, 5338, 5339, 5340);

    public Patch set(PatchData data, FlowerPatch associatedFlowerPatch) {
        super.set(data);
        this.associatedFlowerPatch = associatedFlowerPatch;
        return this;
    }

    private void water(Item item, int index) {
        player.addEvent(event -> {
            player.animate(2293);
            event.delay(2);
            watered = true;
            update();
            item.setId(WATERING_CAN_IDS.get(index - 1));
            if (index == 1)
                player.sendMessage("Your watering can is now empty.");
            player.getStats().addXp(StatType.Farming, 1, false);

        });
    }

    @Override
    public void reset(boolean weeds) {
        watered = false;
        super.reset(weeds);
    }

    @Override
    public void handleItem(Item item) { // we only handle an item if it's a watering can, if not just let the superclass handle it
        int can = WATERING_CAN_IDS.indexOf(item.getId());
        if (can != -1) {
            if (can == 0) {
                player.sendMessage("Your watering can has no water in it.");
            } else if (getPlantedCrop() == null) {
                player.sendMessage("There is nothing to water on this patch.");
            } else if (isDiseased() || isDead()) {
                player.sendMessage("Water won't cure your crops.");
            } else if (getStage() >= getPlantedCrop().getTotalStages()) {
                player.sendMessage("Your crops are already fully grown.");
            } else {
                water(item, can);
            }
            return;
        } else {
            super.handleItem(item);
        }
    }

    @Override
    protected void advanceStage() {
        super.advanceStage();
        watered = false;
    }


    @Override
    public int getCropVarpbitValue() {
        int value = getPlantedCrop().getContainerIndex() + getStage();
        if (watered)
            value |= 1 << 6;
        else if (getDiseaseStage() > 0)
            value |= (getDiseaseStage() + 1) << 6;
        return value;
    }

    @Override
    public void cropInteract() {
        if (getStage() >= getPlantedCrop().getTotalStages()) {
            player.startEvent(event -> {
                while (true) {
                    if (player.getInventory().getFreeSlots() == 0) {
                        player.sendMessage("Not enough space in your inventory.");
                        return;
                    }
                    if (!player.getInventory().contains(952, 1)) {
                        player.sendMessage("You'll need a spade to harvest your crops.");
                        return;
                    }
                    player.animate(830);
                    event.delay(2);
                    player.getInventory().add(getPlantedCrop().getProduceId(), 1);
                    player.collectResource(new Item(getPlantedCrop().getProduceId(), 1));
                    player.getStats().addXp(StatType.Farming, getPlantedCrop().getHarvestXP(), true);
                    player.sendFilteredMessage("You harvest the patch.");
                    getPlantedCrop().getCounter().increment(player, 1);
                    removeProduce();
                    if (getProduceCount() == 0) {
                        AllotmentPatch.this.reset(false);
                        player.sendMessage("You've harvested the patch completely.");
                        return;
                    }
                    event.delay(1);
                }
            });
        }
    }

    @Override
    public void plant(Item item) {
        Crop crop = item.getDef().seedType;
        if (!canPlant(crop))
            return;
        if (!player.getStats().check(StatType.Farming, crop.getLevelReq(), "plant that seed")) {
            return;
        }
        if (!isRaked()) {
            player.sendMessage("You must clear the patch of any weeds before planting a seed.");
            return;
        }
        if (!player.getInventory().contains(crop.getSeed(), 3)) {
            player.sendMessage("You'll need at least 3 seeds to plant in this patch.");
            return;
        }
        if (!player.getInventory().contains(5343, 1)) {
            player.sendMessage("You need a seed dibber to plant seeds.");
            return;
        }
        player.startEvent(event -> {
            player.animate(2291);
            event.delay(2);
            player.getInventory().remove(crop.getSeed(), 3);
            setPlantedCrop(crop);
            setProduceCount(calculateProduceAmount());
            player.getStats().addXp(StatType.Farming, crop.getPlantXP(), true);
            setTimePlanted(System.currentTimeMillis());
            player.sendFilteredMessage("You plant the seed in the patch.");
            send();
        });
    }


    @Override
    public boolean canPlant(Crop crop) {
        return crop instanceof AllotmentCrop;
    }

    @Override
    public boolean isDiseaseImmune() {
        if (watered)
            return true;
        if (associatedFlowerPatch == null || associatedFlowerPatch.getDiseaseStage() > 0)
            return false;
        if (getPlantedCrop().getProtectionFlower() == null)
            return false;
        FlowerCrop flower = associatedFlowerPatch.getPlantedCrop();
        if (flower == null)
            return false;
        return getPlantedCrop().getProtectionFlower() == flower;
    }

    @Override
    public int calculateProduceAmount() {
        int amount = Random.get(8, 12);
        if (getCompost() == 2) { // supercompost bonus
            amount += Random.get(4, 8);
        }
        return amount;
    }

    @Override
    public boolean requiresCure() {
        return true;
    }

    @Override
    public AllotmentCrop getPlantedCrop() {
        return super.getPlantedCrop() == null ? null : (AllotmentCrop) super.getPlantedCrop();
    }

    @Override
    public String getPatchName() {
        return "an allotment";
    }


}
