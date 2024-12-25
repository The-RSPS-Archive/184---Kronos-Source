package io.ruin.model.skills.farming.patch.impl;

import io.ruin.api.utils.Random;
import io.ruin.api.utils.TimeUtils;
import io.ruin.model.skills.farming.crop.Crop;
import io.ruin.model.skills.farming.crop.impl.FruitTreeCrop;
import io.ruin.model.skills.farming.patch.RegrowPatch;
import io.ruin.model.skills.woodcutting.Hatchet;
import io.ruin.model.stat.StatType;

public class FruitTreePatch extends RegrowPatch {

    @Override
    public int getCropVarpbitValue() {
        int cid = getPlantedCrop().getContainerIndex();
        if (getStage() < getPlantedCrop().getTotalStages()) {
            if (getDiseaseStage() == 0)
                return cid + getStage();
            else if (isDiseased())
                return cid + 13 + getStage() - 1;
            else if (isDead())
                return cid + 19 + getStage() - 1;
        } else if (getStage() == getPlantedCrop().getTotalStages()) { // check-health stage
            return cid + 26;
        } else if (getStage() == getPlantedCrop().getTotalStages() + 1) { // picking fruit/chopdown
            return cid + 12 - (6 - getProduceCount());
        } else if (getStage() == getPlantedCrop().getTotalStages() + 2) { // stump
            return cid + 25;
        }
        return 0; // should never happen
    }

    private void checkHealth() {
        player.sendMessage("You examine the tree and find that it is in perfect health.");
        getPlantedCrop().getCounter().increment(player, 1);
        player.getStats().addXp(StatType.Farming, getPlantedCrop().getCheckHealthXP(), true);
        advanceStage();
        update();
    }

    private void chop() {
        Hatchet axe = Hatchet.find(player);
        if (axe == null) {
            player.sendMessage("You'll need an axe to chop down this tree.");
            return;
        }
        player.startEvent(event -> {
            player.animate(axe.animationId);
            event.delay(Random.get(4, 7));
            advanceStage();
            update();
            player.getStats().addXp(StatType.Woodcutting, 1, false);
            player.sendMessage("You chop down the tree.");
        });
    }

    @Override
    public long getRegrowDelay() {
        return TimeUtils.getMinutesToMillis(5);
    }

    @Override
    public int getMaxProduce() {
        return 6;
    }

    @Override
    public void tick() {
        super.tick();
        if (getPlantedCrop() != null && getStage() == getPlantedCrop().getTotalStages() + 2) { // regrow tree
            setStage(getStage() - 1);
            update();
        }
    }

    @Override
    public void cropInteract() {
        if (getStage() == getPlantedCrop().getTotalStages()) {
            checkHealth();
        } else if (getStage() == getPlantedCrop().getTotalStages() + 1) {
            if (getProduceCount() == 0) {
                chop();
            } else {
                pick();
            }
        } else if (getStage() == getPlantedCrop().getTotalStages() + 2) {
            clear();
        }
    }

    @Override
    public boolean canPlant(Crop crop) {
        return crop instanceof FruitTreeCrop;
    }

    @Override
    public boolean isDiseaseImmune() {
        return false;
    }

    @Override
    public int calculateProduceAmount() {
        return 6; // fixed
    }

    @Override
    public boolean requiresCure() {
        return false;
    }

    @Override
    public FruitTreeCrop getPlantedCrop() {
        return (FruitTreeCrop) super.getPlantedCrop();
    }

    @Override
    public String getPatchName() {
        return "a fruit tree";
    }

}
