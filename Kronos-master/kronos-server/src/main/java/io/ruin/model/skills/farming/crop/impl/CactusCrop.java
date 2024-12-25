package io.ruin.model.skills.farming.crop.impl;

import io.ruin.api.utils.TimeUtils;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.skills.farming.crop.Crop;

import static io.ruin.cache.ItemID.COINS_995;

public class CactusCrop implements Crop {

    public static final CactusCrop INSTANCE = new CactusCrop();

    private CactusCrop() {

    }

    @Override
    public int getSeed() {
        return 5280;
    }

    @Override
    public int getLevelReq() {
        return 55;
    }

    @Override
    public long getStageTime() {
        return TimeUtils.getMinutesToMillis(80);
    }

    @Override
    public int getTotalStages() {
        return 7;
    }

    @Override
    public double getDiseaseChance(int compostType) {
        switch(compostType) {
            case 2:
                return 0.15 / 6;
            case 1:
                return 0.2 / 6;
            case 0:
            default:
                return 0.3 / 6;
        }
    }

    @Override
    public double getPlantXP() {
        return 66.5;
    }

    @Override
    public int getContainerIndex() {
        return 8;
    }

    @Override
    public int getProduceId() {
        return 6016;
    }

    @Override
    public double getHarvestXP() {
        return 25;
    }

    public double getCheckHealthXP() {
        return 374;
    }

    @Override
    public PlayerCounter getCounter() {
        return PlayerCounter.GROWN_CACTUS;
    }

    private static final Item PAYMENT = new Item(COINS_995, 5000);

    @Override
    public Item getPayment() {
        return PAYMENT;
    }
}
