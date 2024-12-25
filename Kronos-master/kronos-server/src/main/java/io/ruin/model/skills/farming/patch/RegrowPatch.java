package io.ruin.model.skills.farming.patch;

import com.google.gson.annotations.Expose;
import io.ruin.cache.ItemDef;
import io.ruin.model.item.Item;
import io.ruin.model.stat.StatType;

public abstract class RegrowPatch extends Patch { // for patches that "restore" produce after a certain delay, like fruit trees

    public abstract long getRegrowDelay();
    @Expose private long lastRegrow;
    public abstract int getMaxProduce();

    @Override
    public void tick() {
        super.tick();
        if (getPlantedCrop() == null || getStage() <= getPlantedCrop().getTotalStages())
            return;
        if (canRegrow()) { // regrow a fruit
            regrow();
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
        while (canRegrow())
            regrow();
    }

    void regrow() {
        setProduceCount(getProduceCount() + 1);
        lastRegrow += getRegrowDelay(); // increment by the delay so it works correctly when multiple regrows are 'queued'
        update();
    }

    public boolean canRegrow() {
        return getPlantedCrop() != null &&
                getStage() == getPlantedCrop().getTotalStages() + 1 && getProduceCount() < getMaxProduce() && System.currentTimeMillis() - lastRegrow >= getRegrowDelay();
    }

    public void pick() {
        player.startEvent(event -> {
            while (true) {
                if (player.getInventory().getFreeSlots() == 0) {
                    player.sendMessage("Not enough space in your inventory.");
                    return;
                }
                player.animate(2280);
                event.delay(1);
                player.getInventory().add(getPlantedCrop().getProduceId(), 1);
                player.collectResource(new Item(getPlantedCrop().getProduceId(), 1));
                player.getStats().addXp(StatType.Farming, getPlantedCrop().getHarvestXP(), true);
                player.sendFilteredMessage("You pick a " + ItemDef.get(getPlantedCrop().getProduceId()).name.toLowerCase() + ".");
                removeProduce();
                update();
                if (getProduceCount() == 0) {
                    return;
                } else if (getProduceCount() == 5) {
                    lastRegrow = System.currentTimeMillis();
                }
                event.delay(2);
            }
        });
    }


}
