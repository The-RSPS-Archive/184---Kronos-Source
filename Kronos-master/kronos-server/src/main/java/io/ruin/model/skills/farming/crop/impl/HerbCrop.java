package io.ruin.model.skills.farming.crop.impl;

import io.ruin.api.utils.TimeUtils;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.skills.farming.crop.Crop;


public enum HerbCrop implements Crop {
	GUAM(5291, 199, 9, 11.0, 12.5, 4, PlayerCounter.HARVESTED_GUAM),
	MARRENTILL(5292, 201, 14, 13.5, 15, 4, PlayerCounter.HARVESTED_MARRENTILL),
	TARROMIN(5293, 203, 19, 16, 18, 4, PlayerCounter.HARVESTED_TARROMIN),
	HARRALANDER(5294, 205, 26, 21.5, 24, 4, PlayerCounter.HARVESTED_HARRALANDER),
	RANARR(5295, 207, 32, 27, 30.5, 4, PlayerCounter.HARVESTED_RANARR),
	TOADFLAX(5296, 3049, 38, 34, 38.5, 4, PlayerCounter.HARVESTED_TOADFLAX),
	IRIT(5297, 209, 44, 43, 48.5, 4, PlayerCounter.HARVESTED_IRIT),
	AVANTOE(5298, 211, 50, 54.5, 61.5, 4, PlayerCounter.HARVESTED_AVANTOE),
	KWUARM(5299, 213, 56, 69, 78, 4, PlayerCounter.HARVESTED_KWUARM),
	SNAPDRAGON(5300, 3051, 62, 87.5, 98.5, 4, PlayerCounter.HARVESTED_SNAPDRAGON),
	CADANTINE(5301, 215, 67, 106.5, 120.0, 4, PlayerCounter.HARVESTED_CADANTINE),
	LANTADYME(5302, 2485, 73, 134.5, 151.5, 4, PlayerCounter.HARVESTED_LANTADYME),
	DWARF_WEED(5303, 217, 79, 170.5, 192, 4, PlayerCounter.HARVESTED_DWARF_WEED),
	TORSTOL(5304, 219, 85, 199.5, 224.5, 4, PlayerCounter.HARVESTED_TORSTOL);

	private HerbCrop(int seed, int herbId, int levelReq, double plantXP, double pickXP, int containerIndex, PlayerCounter counter) {
		this.seed = seed;
		this.levelReq = levelReq;
		this.containerIndex = containerIndex;
		this.herbId = herbId;
		this.plantXP = plantXP;
		this.pickXP = pickXP;
		this.counter = counter;
	}

	int seed, levelReq, containerIndex, herbId;
	
	double plantXP, pickXP;

    @Override
    public PlayerCounter getCounter() {
        return counter;
    }

    PlayerCounter counter;

	@Override
	public int getSeed() {
		return seed;
	}

	@Override
	public int getLevelReq() {
		return levelReq;
	}

	@Override
	public long getStageTime() {
		return TimeUtils.getMinutesToMillis(20);
	}

	@Override
	public int getTotalStages() {
		return 4;
	}

	@Override
	public double getDiseaseChance(int compostType) {
		switch(compostType) {
			case 2:
				return 0.2 / getTotalStages();
			case 1:
				return 0.3 / getTotalStages();
			case 0:
			default:
				return 0.35 / getTotalStages();
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
		return herbId;
	}

	@Override
	public double getHarvestXP() {
		return pickXP;
	}

}
