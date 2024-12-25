package io.ruin.model.skills.farming.crop.impl;

import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.skills.farming.crop.Crop;

import static io.ruin.cache.ItemID.COINS_995;

public enum BushCrop implements Crop {
    REDBERRY(5101, 1951, 10, 5, 11.5, 64, 4.5, 5, new Item(COINS_995,  100), PlayerCounter.GROWN_REDBERRY),
    CADAVABERRY(5102, 753, 22, 6, 18, 102.5, 7, 15, new Item(COINS_995,  250), PlayerCounter.GROWN_CADAVABERRY),
    DWELLBERRY(5103, 2126, 36, 7, 31.5, 177.5, 12, 26, new Item(COINS_995,  500), PlayerCounter.GROWN_DWELLBERRY),
    JANGERBERRY(5104, 247, 48, 8, 50.5, 284.5, 19, 38, new Item(COINS_995,  1000), PlayerCounter.GROWN_JANGERBERRY),
    WHITEBERRY(5105, 239, 59, 8, 78, 437.5, 4.5, 51, new Item(COINS_995,  2500), PlayerCounter.GROWN_WHITEBERRY),
    POISON_IVY(5106, 6018, 70, 8, 120, 675, 4.5, 197, null, PlayerCounter.GROWN_POISON_IVY);

    private final int seed, levelReq, totalStages, containerIndex, produceId;
    private final double plantXP, harvestXP, checkHealthXP;
    private final PlayerCounter counter;

    BushCrop(int seed, int produceId, int levelReq, int totalStages, double plantXP, double checkHealthXP, double harvestXP, int containerIndex, Item payment, PlayerCounter counter) {
        this.seed = seed;
        this.levelReq = levelReq;
        this.totalStages = totalStages;
        this.containerIndex = containerIndex;
        this.produceId = produceId;
        this.plantXP = plantXP;
        this.harvestXP = harvestXP;
        this.checkHealthXP = checkHealthXP;
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

    public double getCheckHealthXP() {
        return checkHealthXP;
    }

    @Override
    public PlayerCounter getCounter() {
        return counter;
    }


    @Override
    public long getStageTime() {
        return 3000;// TimeUtils.getMinutesToMillis(20);
    }

    @Override
    public double getDiseaseChance(int compostType) {
        if (this == POISON_IVY)
            return 0;
        switch(compostType) {
            case 2:
                return 0.05;
            case 1:
                return 0.065;
            case 0:
            default:
                return 0.1;
        }
    }

}
