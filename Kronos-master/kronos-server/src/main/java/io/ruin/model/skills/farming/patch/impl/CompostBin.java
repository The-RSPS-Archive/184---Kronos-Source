package io.ruin.model.skills.farming.patch.impl;

import com.google.gson.annotations.Expose;
import io.ruin.api.utils.TimeUtils;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.skills.farming.crop.Crop;
import io.ruin.model.skills.farming.patch.Patch;

public class CompostBin extends Patch {

    private static final int REGULAR = 0, SUPER = 1, TOMATOES = 2;
    private static final int[] PRODUCTS = {6032, 6034, 2518};
    private static final int BUCKET = 1925;
    @Expose private int currentType = 0;

    @Override
    public int getVarpbitValue() {
        if (getProduceCount() == 0)
            return 0;
        if (stage == 0) {
            int value = 0;
            if (currentType == SUPER)
                value |= 1 << 5;
            else if (currentType == TOMATOES)
                value |= 1 << 7;
            value |= getProduceCount();
            return value;
        } else if (stage == 1) {
            return 31;
        } else if (stage == 2) {
            if (currentType != TOMATOES)
                return (1 << 5) | (1 << 4) | (getProduceCount() - 1);
            else
                return 144 | (getProduceCount() - 1);
        }
        return 0;
    }

    @Override
    public void interact() {
        if (stage == 0 && getProduceCount() == 15) {
            player.startEvent(event -> {
                player.animate(810);
                event.delay(1);
                advanceStage();
                setTimePlanted(System.currentTimeMillis());
                update();
            });
        } else if (stage == 1) {
            player.sendMessage("The vegetation hasn't finished rotting yet.");
        } else if (stage == 2) {
            if (currentType == TOMATOES) {
                player.startEvent(event -> {
                    while (getProduceCount() > 0 && player.getInventory().hasRoomFor(PRODUCTS[TOMATOES])) {
                        player.animate(832);
                        event.delay(1);
                        player.getInventory().add(PRODUCTS[TOMATOES], 1);
                        removeProduce();
                        update();
                    }
                    if (getProduceCount() == 0) {
                        reset(false);
                        update();
                        player.sendMessage("The compost bin is now empty.");
                    }
                });
            }
        }
    }

    @Override
    public void objectAction(int option) {
        if (option == 1)
            interact();
        else if (option == 5)
            dump();
    }

    public void dump() {
        player.dialogue(
                new OptionsDialogue("Dump the entire contents of the bin?",
                        new Option("Yes, throw it away.", () -> player.startEvent(event -> {
                            player.animate(832);
                            event.delay(1);
                            reset(false);
                        })),
                        new Option("No, keep it.", player::closeDialogue))
        );
    }

    @Override
    public void handleItem(Item item) {
        if (stage == 0) {
            if (getProduceCount() >= 15) {
                player.sendMessage("The compost bin is too full to put anything else in it.");
                return;
            }
            int type;
            if (item.getId() == 1982) { // tomato
                type = TOMATOES;
            } else if (item.getDef().produceOf != null) {
                type = item.getDef().produceOf.getLevelReq() >= 47 ? SUPER : REGULAR;
            } else {
                switch (item.getId()) {
                    case 6055:
                        type = 0;
                        break;
                    default:
                        type = -1;
                        break;
                }
            }
            if (type == -1) {
                player.sendMessage("There's no reason to put that in a compost bin.");
                return;
            }
            if (currentType != 0 && type != currentType) {
                player.dialogue(
                        new MessageDialogue("With the contents currently in this bin, it will produce " + (currentType == 1 ? "supercompost" : "rotten tomatoes") + ".<br>Adding " + item.getDef().name + " will change the product to regular compost.<br>Are you sure you want to continue?"),
                        new OptionsDialogue(
                                new Option("Yes.", () -> add(item, 0)),
                                new Option("No.", player::closeDialogue))
                );
            } else {
                add(item, getProduceCount() == 0 ? type : currentType);
            }
        } else if (stage == 2) {
            if (item.getId() != BUCKET) {
                player.sendMessage("You'll need a bucket instead.");
                return;
            }
            if (currentType == TOMATOES) {
                player.sendMessage("You don't need a bucket to take the rotten tomatoes, just use your hands instead.");
                return;
            }
            player.startEvent(event -> {
                while (getProduceCount() > 0) {
                    Item bucket = player.getInventory().findItem(BUCKET);
                    if(bucket == null)
                        break;
                    player.lock();
                    player.animate(832);
                    event.delay(1);
                    bucket.setId(PRODUCTS[currentType]);
                    removeProduce();
                    update();
                    player.unlock();
                }
                if (getProduceCount() == 0) {
                    reset(false);
                    update();
                    player.sendMessage("The compost bin is now empty.");
                }
            });
        }
    }

    public void add(Item item, int type) {
        player.startEvent(event -> {
            player.lock();
            player.animate(832);
            event.delay(1);
            int amount = Math.min(15 - getProduceCount(), player.getInventory().getAmount(item.getId()));
            currentType = type;
            setProduceCount(getProduceCount() + amount);
            player.getInventory().remove(item.getId(), amount);
            update();
            player.unlock();
        });
    }

    @Override
    public void tick() {
        if (isNextStageReady()) {
            advanceStage();
            setNeedsUpdate(true);
        }
    }

    @Override
    protected boolean isNextStageReady() {
        if (getTimePlanted() <= 0 || stage != 1)
            return false;
        return getTimeElapsed() >= TimeUtils.getMinutesToMillis(30);
    }

    @Override
    public void reset(boolean weeds) {
        currentType = 0;
        stage = 0;
        setProduceCount(0);
        setTimePlanted(0);
        update();
    }

    @Override
    public int getCropVarpbitValue() {
        throw new IllegalStateException();
    }

    @Override
    public void cropInteract() {

    }

    @Override
    public boolean canPlant(Crop crop) {
        return false;
    }

    @Override
    public boolean isDiseaseImmune() {
        return false;
    }

    @Override
    public int calculateProduceAmount() {
        return 15;
    }

    @Override
    public boolean requiresCure() {
        return false;
    }

    @Override
    public String getPatchName() {
        return "";
    }
}
