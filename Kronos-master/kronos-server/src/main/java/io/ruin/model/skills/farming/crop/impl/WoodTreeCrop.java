package io.ruin.model.skills.farming.crop.impl;

import io.ruin.api.utils.TimeUtils;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.skills.farming.crop.TreeCrop;
import io.ruin.model.skills.woodcutting.Tree;

import static io.ruin.cache.ItemID.COINS_995;

public enum WoodTreeCrop implements TreeCrop {
	OAK(5312, 6043, 5370, 5358, 5364, Tree.OAK, 15, 14, 467.3, 4, new Item(COINS_995,  4000), 8, PlayerCounter.GROWN_OAK),
	WILLOW(5313, 6045, 5371, 5359, 5365, Tree.WILLOW, 30, 25, 1456.5, 6, new Item(COINS_995,  8000), 15, PlayerCounter.GROWN_WILLOW),
	MAPLE(5314, 6047, 5372, 5360, 5366, Tree.MAPLE, 45, 45, 3403.4, 8, new Item(COINS_995,  20000), 24, PlayerCounter.GROWN_MAPLE),
	YEW(5315, 6049, 5373, 5361, 5367, Tree.YEW, 60, 81, 7069, 10, new Item(COINS_995,  70000), 35, PlayerCounter.GROWN_YEW),
	MAGIC(5316,  6051, 5374, 5362, 5368, Tree.MAGIC, 75, 145.5, 13768.3, 12, new Item(COINS_995,  120000), 48, PlayerCounter.GROWN_MAGIC);
	
	
	WoodTreeCrop(int seedId, int roots, int sapling, int seedling, int wateredSeedling, Tree treeType, int levelReq, double plantXP, double harvestXP, int totalStages, Item payment, int containerIndex, PlayerCounter counter) {
		this.plantXP = plantXP;
		this.checkHealthXP = harvestXP;
		this.seedId = seedId;
		this.treeType = treeType;
		this.levelReq = levelReq;
		this.containerIndex = containerIndex;
		this.totalStages = totalStages;
		this.roots = roots;
		this.counter = counter;
		this.payment = payment;
		this.sapling = sapling;
		this.seedling = seedling;
		this.wateredSeedling = wateredSeedling;
	}

	private final double plantXP, checkHealthXP;
	private final Tree treeType;
	private final int seedId, levelReq, containerIndex, sapling, seedling, wateredSeedling;
	private final int totalStages;
	private final int roots;
	private final PlayerCounter counter;
	
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
		return TimeUtils.getMinutesToMillis(40);
	}

	@Override
	public int getTotalStages() {
		return totalStages;
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
				return 0.4 / getTotalStages();
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
		return treeType.log;
	}

	@Override
	public double getHarvestXP() {
		return checkHealthXP;
	}
	
	public Tree getTreeType() {
		return treeType;
	}

	public int getRoots() {
		return roots;
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
}
