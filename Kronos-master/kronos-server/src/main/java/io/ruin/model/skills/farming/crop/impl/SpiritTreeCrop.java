package io.ruin.model.skills.farming.crop.impl;

import io.ruin.api.utils.TimeUtils;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.skills.farming.crop.TreeCrop;

import static io.ruin.cache.ItemID.COINS_995;

public class SpiritTreeCrop implements TreeCrop {

    public static final SpiritTreeCrop INSTANCE = new SpiritTreeCrop();

    private SpiritTreeCrop() {

    }


    @Override
    public int getSeed() {
        return 5317;
    }

    @Override
    public int getLevelReq() {
        return 83;
    }

    @Override
    public long getStageTime() {
        return TimeUtils.getMinutesToMillis(320);
    }

    @Override
    public int getTotalStages() {
        return 11;
    }

    @Override
    public double getDiseaseChance(int compostType) {
        switch(compostType) {
            case 2:
                return 0.05 / getTotalStages();
            case 1:
                return 0.1 / getTotalStages();
            case 0:
            default:
                return 0.15 / getTotalStages();
        }
    }

    @Override
    public double getPlantXP() {
        return 199;
    }

    @Override
    public int getContainerIndex() {
        return 8;
    }

    @Override
    public int getProduceId() {
        return -1;
    }

    @Override
    public double getHarvestXP() {
        return 0;
    }

    public double getCheckHealthXP() {
        return 19301;
    }

    @Override
    public PlayerCounter getCounter() {
        return PlayerCounter.GROWN_SPIRIT_TREE;
    }

    @Override
    public int getSapling() {
        return 5375;
    }

    @Override
    public int getSeedling() {
        return 5363;
    }

    @Override
    public int getWateredSeedling() {
        return 5369;
    }

    @Override
    public Item getPayment() {
        return new Item(COINS_995, 1500000);
    }
}
