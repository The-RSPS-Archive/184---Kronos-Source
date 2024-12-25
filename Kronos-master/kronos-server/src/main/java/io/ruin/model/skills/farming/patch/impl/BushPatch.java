package io.ruin.model.skills.farming.patch.impl;

import io.ruin.api.utils.TimeUtils;
import io.ruin.model.skills.farming.crop.Crop;
import io.ruin.model.skills.farming.crop.impl.BushCrop;
import io.ruin.model.skills.farming.patch.RegrowPatch;
import io.ruin.model.stat.StatType;

public class BushPatch extends RegrowPatch {

    @Override
    public int getCropVarpbitValue() {
        if (getStage() == getPlantedCrop().getTotalStages()) {
            BushCrop crop = (BushCrop) getPlantedCrop();
            return 250 + crop.ordinal();
        }
        int value = getPlantedCrop().getContainerIndex() + getStage();
        if (isDiseased()) // these technically don't work for poison ivy but they're disease immune, not sure why  they're in the patch containers in the first place
            value += 65;
        else if (isDead())
            value += 129;
        else if (getStage() > getPlantedCrop().getTotalStages())
            value += getProduceCount() - 1;
        return value;
    }

    @Override
    public long getRegrowDelay() {
        return TimeUtils.getMinutesToMillis(3);
    }

    @Override
    public int getMaxProduce() {
        return 4;
    }
    private void checkHealth() {
        player.sendMessage("You examine the bush and find that it is in perfect health.");
        getPlantedCrop().getCounter().increment(player, 1);
        player.getStats().addXp(StatType.Farming, ((BushCrop) getPlantedCrop()).getCheckHealthXP(), true);
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
    public boolean canPlant(Crop crop) {
        return crop instanceof BushCrop;
    }

    @Override
    public boolean isDiseaseImmune() {
        return false;
    }

    @Override
    public int calculateProduceAmount() {
        return 4;
    }

    @Override
    public boolean requiresCure() {
        return false;
    }

    @Override
    public String getPatchName() {
        return "a bush";
    }
}
