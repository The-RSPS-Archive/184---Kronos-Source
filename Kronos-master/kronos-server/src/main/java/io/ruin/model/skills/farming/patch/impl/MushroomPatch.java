package io.ruin.model.skills.farming.patch.impl;

import io.ruin.model.item.Item;
import io.ruin.model.skills.farming.crop.Crop;
import io.ruin.model.skills.farming.crop.impl.MushroomCrop;
import io.ruin.model.skills.farming.patch.Patch;
import io.ruin.model.stat.StatType;

public class MushroomPatch extends Patch {

    @Override
    public int getCropVarpbitValue() {
        int index = getPlantedCrop().getContainerIndex() + getStage();
        if (isDiseased()) {
            index += 12;
        } else if (isDead()) {
            index += 17;
        } else if (getStage() == getPlantedCrop().getTotalStages()) {
            index += 6 - getProduceCount();
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
        if (player.getInventory().getFreeSlots() == 0) {
            player.sendMessage("Not enough space in your inventory.");
            return;
        }
        player.startEvent(event -> {
            while (true) {
                if (player.getInventory().getFreeSlots() == 0) {
                    player.sendMessage("Not enough space in your inventory.");
                    return;
                }
                player.animate(2282);
                event.delay(2);
                player.collectResource(new Item(getPlantedCrop().getProduceId(), 1));
                player.getInventory().add(getPlantedCrop().getProduceId(), 1);
                player.getStats().addXp(StatType.Farming, getPlantedCrop().getHarvestXP(), true);
                player.sendFilteredMessage("You pick a mushroom.");
                getPlantedCrop().getCounter().increment(player, 1);
                removeProduce();
                if (getProduceCount() == 0) {
                    MushroomPatch.this.reset(false);
                    player.sendMessage("You've picked all the mushrooms from this patch.");
                    return;
                }
                update();
                event.delay(1);
            }
        });
    }

    @Override
    public boolean canPlant(Crop crop) {
        return crop instanceof MushroomCrop;
    }

    @Override
    public boolean isDiseaseImmune() {
        return false;
    }

    @Override
    public int calculateProduceAmount() {
        return 6;
    }

    @Override
    public boolean requiresCure() {
        return true;
    }

    @Override
    public String getPatchName() {
        return "a mushroom";
    }

}
