package io.ruin.model.skills.farming.patch.impl;

import io.ruin.api.utils.TimeUtils;
import io.ruin.model.skills.farming.crop.Crop;
import io.ruin.model.skills.farming.crop.impl.CalquatCrop;
import io.ruin.model.skills.farming.patch.RegrowPatch;
import io.ruin.model.stat.StatType;

public class CalquatTreePatch extends RegrowPatch {

    @Override
    public int getCropVarpbitValue() {
        if (getStage() == getPlantedCrop().getTotalStages()) {
            return 34;
        }
        int val = getPlantedCrop().getContainerIndex() + getStage();
        if (getStage() > getPlantedCrop().getTotalStages()) {
            return val + getProduceCount() - 1;
        }
        if (isDiseased()) {
            return val + 15;
        }
        if (isDead()) {
            return val + 22;
        }
        return val;
    }

    @Override
    public long getRegrowDelay() {
        return TimeUtils.getMinutesToMillis(5);
    }

    @Override
    public int getMaxProduce() {
        return 6;
    }

    private void checkHealth() {
        player.sendMessage("You examine the tree and find that it is in perfect health.");
        getPlantedCrop().getCounter().increment(player, 1);
        player.getStats().addXp(StatType.Farming, ((CalquatCrop)getPlantedCrop()).getCheckHealthXP(), true);
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
        return 6;
    }

    @Override
    public boolean requiresCure() {
        return false;
    }

    @Override
    public boolean canPlant(Crop crop) {
        return crop instanceof CalquatCrop;
    }

    @Override
    public String getPatchName() {
        return "a calquat tree";
    }
}
