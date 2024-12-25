package io.ruin.model.skills.farming.crop.impl;

import io.ruin.api.utils.TimeUtils;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.skills.farming.crop.TreeCrop;

import static io.ruin.cache.ItemID.COINS_995;

public class CalquatCrop implements TreeCrop {

    public static final CalquatCrop INSTANCE = new CalquatCrop();

    private CalquatCrop() {

    }

    @Override
    public int getSeed() {
        return 5290;
    }

    @Override
    public int getLevelReq() {
        return 72;
    }

    @Override
    public long getStageTime() {
        return TimeUtils.getMinutesToMillis(160);
    }

    @Override
    public int getTotalStages() {
        return 8;
    }

    @Override
    public double getDiseaseChance(int compostType) {
        switch(compostType) {
            case 2:
                return 0.125 / 6;
            case 1:
                return 0.175 / 6;
            case 0:
            default:
                return 0.25 / 6;
        }
    }

    @Override
    public double getPlantXP() {
        return 129.5;
    }

    @Override
    public int getContainerIndex() {
        return 4;
    }

    @Override
    public int getProduceId() {
        return 5980;
    }

    public double getCheckHealthXP() {
        return 12096;
    }

    @Override
    public double getHarvestXP() {
        return 48.5;
    }

    @Override
    public PlayerCounter getCounter() {
        return PlayerCounter.GROWN_CALQUAT;
    }

    private static final Item PAYMENT = new Item(COINS_995, 8000);

    @Override
    public Item getPayment() {
        return PAYMENT;
    }

    @Override
    public int getSapling() {
        return 5503;
    }

    @Override
    public int getSeedling() {
        return 5487;
    }

    @Override
    public int getWateredSeedling() {
        return 5495;
    }
}
