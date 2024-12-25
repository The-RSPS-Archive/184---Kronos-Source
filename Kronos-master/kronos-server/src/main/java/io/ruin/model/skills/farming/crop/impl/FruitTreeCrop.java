package io.ruin.model.skills.farming.crop.impl;

import io.ruin.api.utils.TimeUtils;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.skills.farming.crop.TreeCrop;

import static io.ruin.cache.ItemID.COINS_995;


public enum FruitTreeCrop implements TreeCrop {
	APPLE(5283, 1955, 5496, 5480, 5488, 27, 22, 1199.5, 8.5, 8, PlayerCounter.GROWN_APPLE, new Item(COINS_995,  2000)),
	BANANA(5284, 1963, 5497, 5481, 5489, 33, 28, 1750, 10.5, 35, PlayerCounter.GROWN_BANANA, new Item(COINS_995,  3000)),
	ORANGE(5285, 2108, 5498, 5482, 5490, 39, 35.5, 2470.2, 13.5, 72, PlayerCounter.GROWN_ORANGE, new Item(COINS_995,  5000)),
	CURRY(5286, 5970, 5499, 5483, 5491, 42, 40, 2906.9, 99, 15, PlayerCounter.GROWN_CURRY, new Item(COINS_995,  15000)),
	PINEAPPLE(5287, 2114, 5500, 5484, 5492, 51, 57, 4605.7, 21.5, 136, PlayerCounter.GROWN_PINEAPPLE, new Item(COINS_995,  30000)),
	PAPAYA(5288, 5972, 5501, 5485, 5493, 57, 72, 6146.4, 27, 163, PlayerCounter.GROWN_PAPAYA, new Item(COINS_995,  50000)),
	PALM(5289, 5974, 5502, 5486, 5494, 68, 110.5, 10150.1, 41.5, 200, PlayerCounter.GROWN_PALM, new Item(COINS_995,  80000));
	
	FruitTreeCrop(int seedId, int fruitId, int sapling, int seedling, int wateredSeedling, int levelReq, double plantXP, double checkHealthXP, double harvestXP,
				  int containerIndex, PlayerCounter counter, Item payment) {
		this.plantXP = plantXP;
		this.checkHealthXP = checkHealthXP;
		this.harvestXP = harvestXP;
		this.seedId = seedId;
		this.produceId = fruitId;
		this.levelReq = levelReq;
		this.containerIndex = containerIndex;
		this.counter = counter;
		this.payment = payment;
		this.sapling = sapling;
		this.seedling = seedling;
		this.wateredSeedling = wateredSeedling;
	}

	private double plantXP, checkHealthXP, harvestXP;
	private int seedId;
	private int produceId;
	private int levelReq;
	private int containerIndex;

	@Override
	public int getSapling() {
		return sapling;
	}

	@Override
	public int getSeedling() {
		return seedling;
	}

	@Override
	public int getWateredSeedling() {
		return wateredSeedling;
	}

	private int sapling;
	private int seedling;
	private int wateredSeedling;

	private PlayerCounter counter;



	@Override
	public int getSeed() {
		return seedId;
	}

	@Override
	public int getLevelReq() {
		return levelReq;
	}

	@Override
	public long getStageTime() {
		return TimeUtils.getMinutesToMillis(160);
	}

	@Override
	public int getTotalStages() {
		return 6;
	}

	@Override
	public double getDiseaseChance(int compostType) {
		switch(compostType) {
			case 2:
				return 0.125 / getTotalStages();
			case 1:
				return 0.175 / getTotalStages();
			case 0:
			default:
				return 0.25 / getTotalStages();
		}
	}

	@Override
	public double getPlantXP() {
		return plantXP;
	}

	@Override
	public int getContainerIndex() {
		return containerIndex;
	}

	@Override
	public int getProduceId() {
		return produceId;
	}

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
	public Item getPayment() {
		return payment;
	}

	private Item payment;
}