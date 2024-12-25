package io.ruin.model.skills.farming.crop;

import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;

public interface Crop {

	int getSeed();

	int getLevelReq();

	long getStageTime();

	int getTotalStages();

	double getDiseaseChance(int compostType);

	double getPlantXP();

	int getContainerIndex();

	int getProduceId();

	double getHarvestXP();

	PlayerCounter getCounter();

	default Item getPayment() {
		return null;
	}

}
