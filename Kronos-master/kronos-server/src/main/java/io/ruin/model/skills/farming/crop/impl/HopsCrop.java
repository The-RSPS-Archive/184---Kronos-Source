package io.ruin.model.skills.farming.crop.impl;

import io.ruin.api.utils.TimeUtils;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.skills.farming.crop.Crop;

import static io.ruin.cache.ItemID.COINS_995;

public enum HopsCrop implements Crop {
    BARLEY(5305, 6006, 3, 4, 8.5, 9.5, 49, new Item(COINS_995,  100), PlayerCounter.GROWN_REDBERRY),
    HAMMERSTONE(5307, 5994, 4, 4, 9, 10, 4, new Item(COINS_995,  200), PlayerCounter.GROWN_REDBERRY),
    ASGARNIAN(5308, 5996, 8, 5, 10.5, 12, 11, new Item(COINS_995,  300), PlayerCounter.GROWN_REDBERRY),
    JUTE(5306, 5931, 13, 5, 13, 14.5, 56, new Item(COINS_995,  500), PlayerCounter.GROWN_REDBERRY),
    YANILLIAN(5309, 5998, 16, 6, 14.5, 16, 19, new Item(COINS_995,  750), PlayerCounter.GROWN_REDBERRY),
    KRANDORIAN(5310, 6000, 21, 7, 17.5, 19.5, 28, new Item(COINS_995,  1000), PlayerCounter.GROWN_REDBERRY),
    WILDBLOOD(5311, 6002, 28, 8, 23, 26, 38, new Item(COINS_995,  1200), PlayerCounter.GROWN_REDBERRY);

    private final int seed, levelReq, totalStages, containerIndex, produceId;
    private final double plantXP, harvestXP;
    private final PlayerCounter counter;

    HopsCrop(int seed, int produceId, int levelReq, int totalStages, double plantXP, double harvestXP, int containerIndex, Item payment, PlayerCounter counter) {
        this.seed = seed;
        this.levelReq = levelReq;
        this.totalStages = totalStages;
        this.containerIndex = containerIndex;
        this.produceId = produceId;
        this.plantXP = plantXP;
        this.harvestXP = harvestXP;
        this.counter = counter;
        this.payment = payment;
    }

    @Override
    public Item getPayment() {
        return payment;
    }

    private Item payment;

    @Override
    public int getSeed() {
        return seed;
    }

    @Override
    public int getLevelReq() {
        return levelReq;
    }

    @Override
    public int getTotalStages() {
        return totalStages;
    }

    @Override
    public int getContainerIndex() {
        return containerIndex;
    }

    @Override
    public int getProduceId() {
        return produceId;
    }

    @Override
    public double getPlantXP() {
        return plantXP;
    }

    @Override
    public double getHarvestXP() {
        return harvestXP;
    }

    @Override
    public PlayerCounter getCounter() {
        return counter;
    }


    @Override
    public long getStageTime() {
        return TimeUtils.getMinutesToMillis(10);
    }

    @Override
    public double getDiseaseChance(int compostType) {
        switch(compostType) {
            case 2:
                return 0.0225;
            case 1:
                return 0.0275;
            case 0:
            default:
                return 0.04;
        }
    }

}
