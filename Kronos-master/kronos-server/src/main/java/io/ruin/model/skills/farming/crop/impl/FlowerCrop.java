package io.ruin.model.skills.farming.crop.impl;

import io.ruin.api.utils.TimeUtils;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.skills.farming.crop.Crop;

public enum FlowerCrop implements Crop {
	MARIGOLDS(5096, 6010, 2, 8.5, 47.0, TimeUtils.getMinutesToMillis(5), 8, PlayerCounter.HARVESTED_MARIGOLDS),
	ROSEMARY(5097, 6014, 11, 12, 66.5, TimeUtils.getMinutesToMillis(5), 13, PlayerCounter.HARVESTED_ROSEMARY),
	NASTURTIUM(5098, 6012, 24, 19.5, 111, TimeUtils.getMinutesToMillis(5), 18, PlayerCounter.HARVESTED_NASTURTIUM),
	WOAD(5099, 1793, 25, 20.5, 115.5, TimeUtils.getMinutesToMillis(5), 23, PlayerCounter.HARVESTED_WOAD),
	LIMPWURT(5100, 225, 26, 21.5, 120.0, TimeUtils.getMinutesToMillis(5), 28, PlayerCounter.HARVESTED_LIMPWURT);

	FlowerCrop(int seedId, int produceId, int levelReq, double plantXP, double harvestXP, long stageTime,
			   int containerIndex, PlayerCounter counter) {
		this.plantXP = plantXP;
		this.harvestXP = harvestXP;
		this.seedId = seedId;
		this.produceId = produceId;
		this.levelReq = levelReq;
		this.containerIndex = containerIndex;
		this.stageTime = stageTime;
		this.counter = counter;
	}

	private double plantXP, harvestXP;
	private int seedId, produceId, levelReq, containerIndex;
	private long stageTime;
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
		return stageTime;
	}

	@Override
	public int getTotalStages() {
		return 4;
	}

	@Override
	public double getDiseaseChance(int compostType) {
		switch(compostType) {
			case 2:
				return 0.055;
			case 1:
				return 0.065;
			case 0:
			default:
				return 0.10;
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

	@Override
	public double getHarvestXP() {
		return harvestXP;
	}

	@Override
	public PlayerCounter getCounter() {
		return counter;
	}
}
