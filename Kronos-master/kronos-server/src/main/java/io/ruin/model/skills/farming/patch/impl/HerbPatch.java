package io.ruin.model.skills.farming.patch.impl;


import io.ruin.api.utils.Random;
import io.ruin.cache.ItemDef;
import io.ruin.model.item.Item;
import io.ruin.model.skills.CapePerks;
import io.ruin.model.skills.farming.crop.Crop;
import io.ruin.model.skills.farming.crop.impl.HerbCrop;
import io.ruin.model.skills.farming.patch.Patch;
import io.ruin.model.stat.StatType;

public class HerbPatch extends Patch {

    @Override
    public int getCropVarpbitValue() {
        int index = 0;
        if (isDiseased()) {
            index = (getStage() - 1) | (1 << 7);
        } else if (isDead()) {
            index = 170 + Math.min(getStage() - 1, 2);
        } else {
            index = getPlantedCrop().getContainerIndex() + getStage();
        }
        return index;
    }

    @Override
    public void cropInteract() {
        if (isDead()) {
            clear();
            return;
        }
        if (getProduceCount() == 0) {
            reset(false);
            return;
        }
        player.startEvent(event -> {
            while (true) {
                if (player.getInventory().getFreeSlots() == 0) {
                    player.sendMessage("Not enough space in your inventory.");
                    return;
                }
                if (getProduceCount() == 0) {
                    reset(false);
                    player.sendMessage("You've picked all the herbs from this patch.");
                    return;
                }
                player.animate(2282);
                event.delay(2);
                player.collectResource(new Item(getPlantedCrop().getProduceId(), 1));
                player.getInventory().add(getPlantedCrop().getProduceId(), 1);
                player.getStats().addXp(StatType.Farming, getPlantedCrop().getHarvestXP(), true);
                player.sendFilteredMessage("You pick a " + ItemDef.get(getPlantedCrop().getProduceId()).name + ".");
                getPlantedCrop().getCounter().increment(player, 1);
                removeProduce();
                event.delay(1);
            }
        });
    }

    @Override
    public boolean canPlant(Crop crop) {
        return crop instanceof HerbCrop;
    }

    @Override
    public boolean isDiseaseImmune() {
        return getObjectId() == 18816; // trollheim patch no disease
    }

    @Override
    public int calculateProduceAmount() {
        int amount = Random.get(8, 10);
        if (getCompost() == 2) { // supercompost bonus
            amount += Random.get(3, 5);
        }
        if (CapePerks.wearsFarmingCape(player)) {
            amount += Random.get(1, 2);
        }
        return amount;
    }

    @Override
    public boolean requiresCure() {
        return true;
    }

    @Override
    public String getPatchName() {
        return "a herb";
    }

}

