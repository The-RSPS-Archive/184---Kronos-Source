package io.ruin.model.skills.farming.patch.impl;

import io.ruin.api.utils.TimeUtils;
import io.ruin.model.skills.farming.crop.Crop;
import io.ruin.model.skills.farming.crop.impl.CactusCrop;
import io.ruin.model.skills.farming.patch.RegrowPatch;
import io.ruin.model.stat.StatType;

public class CactusPatch extends RegrowPatch {

    @Override
    public int getCropVarpbitValue() {
        if (getStage() == getPlantedCrop().getTotalStages()) {
            return 31;
        }
        int val = getPlantedCrop().getContainerIndex() + getStage();
        if (getStage() > getPlantedCrop().getTotalStages()) {
            return val + getProduceCount() - 1;
        }
        if (isDiseased()) {
            return val + 11;
        }
        if (isDead()) {
            return val + 17;
        }
        return val;
    }

    @Override
    public long getRegrowDelay() {
        return TimeUtils.getMinutesToMillis(25);
    }

    @Override
    public int getMaxProduce() {
        return 3;
    }

    private void checkHealth() {
        player.sendMessage("You examine the cactus and find that it is in perfect health.");
        getPlantedCrop().getCounter().increment(player, 1);
        player.getStats().addXp(StatType.Farming, ((CactusCrop)getPlantedCrop()).getCheckHealthXP(), true);
        advanceStage();
        update();
    }

    @Override
    public void cropInteract() {
        if (getStage() == getPlantedCrop().getTotalStages()) {
            checkHealth();
        } else if (getStage() == getPlantedCrop().getTotalStages() + 1) {
            if (getProduceCount() == 0) {
                clear();
            } else {
                pick();
            }
        }
    }

    @Override
    public boolean isDiseaseImmune() {
        return false;
    }

    @Override
    public int calculateProduceAmount() {
        return 3;
    }

    @Override
    public boolean requiresCure() {
        return false;
    }

    @Override
    public boolean canPlant(Crop crop) {
        return crop instanceof CactusCrop;
    }

    @Override
    public String getPatchName() {
        return "a cactus";
    }
}
