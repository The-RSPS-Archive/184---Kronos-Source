package io.ruin.model.skills.farming.patch.impl;

import com.google.gson.annotations.Expose;
import io.ruin.api.utils.Random;
import io.ruin.model.item.Item;
import io.ruin.model.skills.farming.crop.Crop;
import io.ruin.model.skills.farming.crop.impl.HopsCrop;
import io.ruin.model.skills.farming.patch.Patch;
import io.ruin.model.stat.StatType;

public class HopsPatch extends Patch {

    @Expose
    private boolean watered;

    private void water(Item item, int index) {
        player.addEvent(event -> {
            player.animate(2293);
            event.delay(2);
            watered = true;
            item.setId(AllotmentPatch.WATERING_CAN_IDS.get(index - 1));
            if (index == 1)
                player.sendMessage("Your watering can is now empty.");
            player.getStats().addXp(StatType.Farming, 1, false);
            update();
        });
    }

    @Override
    public void reset(boolean weeds) {
        watered = false;
        super.reset(weeds);
    }

    @Override
    public void handleItem(Item item) { // we only handle an item if it's a watering can, if not just let the superclass handle it
        int can = AllotmentPatch.WATERING_CAN_IDS.indexOf(item.getId());
        if (can != -1) {
            if (can == 0) {
                player.sendMessage("Your watering can has no water in it.");
            } else if (getPlantedCrop() == null) {
                player.sendMessage("There is nothing to water on this patch.");
            } else if (isDiseased() || isDead()) {
                player.sendMessage("Water won't cure your hops.");
            } else if (getStage() >= getPlantedCrop().getTotalStages()) {
                player.sendMessage("Your hops are already fully grown.");
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
        else if (isDiseased())
            value |= 1 << 7;
        else if (isDead())
            value |= 3 << 6;
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
                    player.collectResource(new Item(getPlantedCrop().getProduceId(), 1));
                    player.getInventory().add(getPlantedCrop().getProduceId(), 1);
                    player.getStats().addXp(StatType.Farming, getPlantedCrop().getHarvestXP(), true);
                    player.sendFilteredMessage("You harvest the patch.");
                    getPlantedCrop().getCounter().increment(player, 1);
                    removeProduce();
                    if (getProduceCount() == 0) {
                        reset(false);
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
        int seedAmt = crop.getSeed() == 5306 ? 3 : 4;
        if (!player.getInventory().contains(crop.getSeed(), seedAmt)) {
            player.sendMessage("You'll need at least " + seedAmt + " seeds to plant in this patch.");
            return;
        }
        if (!player.getInventory().contains(5343, 1)) {
            player.sendMessage("You need a seed dibber to plant seeds.");
            return;
        }
        player.startEvent(event -> {
            player.animate(2291);
            event.delay(2);
            player.getInventory().remove(crop.getSeed(), seedAmt);
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
        return crop instanceof HopsCrop;
    }

    @Override
    public boolean isDiseaseImmune() {
        return watered;
    }

    @Override
    public int calculateProduceAmount() {
        int amount = Random.get(3, 25);
        if (getCompost() == 2) { // supercompost bonus
            amount += Random.get(3, 10);
        }
        return amount;
    }

    @Override
    public boolean requiresCure() {
        return true;
    }

    @Override
    public String getPatchName() {
        return "a hops";
    }

}
