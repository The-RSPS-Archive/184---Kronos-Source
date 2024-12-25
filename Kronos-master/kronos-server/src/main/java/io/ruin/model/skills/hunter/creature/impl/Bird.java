package io.ruin.model.skills.hunter.creature.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.skills.hunter.Hunter;
import io.ruin.model.skills.hunter.creature.Creature;
import io.ruin.model.skills.hunter.traps.Trap;
import io.ruin.model.skills.hunter.traps.TrapType;
import io.ruin.model.skills.hunter.traps.impl.BirdSnare;
import io.ruin.process.event.Event;
import kilim.Pausable;

import java.util.Arrays;
import java.util.List;

public class Bird extends Creature {

    static {
        Hunter.registerCreature(new Bird("crimson swift", 5549, 1, 34, 9349, 9373, 10088, 0.5, 30, PlayerCounter.CAUGHT_SWIFT));
        Hunter.registerCreature(new Bird("golden warbler", 5551, 5, 47, 9376, 9377, 10090, 0.5, 30, PlayerCounter.CAUGHT_WARBLER));
        Hunter.registerCreature(new Bird("copper longtail", 5552, 9, 61, 9378, 9379, 10091, 0.5, 30, PlayerCounter.CAUGHT_LONGTAIL));
        Hunter.registerCreature(new Bird("cerulean twitch", 5550, 11, 64.5, 9374, 9375, 10089, 0.5, 30, PlayerCounter.CAUGHT_TWITCH));
        Hunter.registerCreature(new Bird("tropical wagtail", 5548, 19, 95.8, 9347, 9348, 10087, 0.5, 30, PlayerCounter.CAUGHT_WAGTAIL));
    }

    private int catchingObject, caughtObject, featherId;

    public Bird(String creatureName, int npcId, int levelReq, double catchXP, int catchingObject, int caughtObject, int featherId, double baseCatchChance, int respawnTicks, PlayerCounter counter) {
        super(creatureName, npcId, levelReq, catchXP, baseCatchChance, respawnTicks, counter);
        this.catchingObject = catchingObject;
        this.caughtObject = caughtObject;
        this.featherId = featherId;
    }

    @Override
    public TrapType getTrapType() {
        return BirdSnare.INSTANCE;
    }

    @Override
    public List<Item> getLoot() {
        return Arrays.asList(new Item(featherId, Random.get(6, 12)), new Item(9978, 1), new Item(526, 1));
    }

    @Override
    protected void failCatch(NPC npc, Trap trap, Event event) throws Pausable {
        trap.getObject().setId(9346);
        npc.animate(5172);
        event.delay(1);
        if (!trap.getOwner().isOnline()) {
            return;
        }
        trap.getObject().setId(trap.getTrapType().getFailedObjectId());
    }

    @Override
    protected void prepareForCatchAttempt(NPC npc, Trap trap, Event event) throws Pausable {
        npc.face(npc.getAbsX(), npc.getAbsY() - 1);
        event.delay(2);
        npc.animate(5171);
        event.delay(1);
    }


    @Override
    protected void succeedCatch(NPC npc, Trap trap, Event event) throws Pausable {
        trap.getObject().setId(catchingObject);
        npc.setHidden(true);
        event.delay(2);
        if (!trap.getOwner().isOnline()) {
            return;
        }
        trap.getObject().setId(caughtObject);
    }


    @Override
    public boolean hasRoomForLoot(Player player) {
        int slots = 4;
        if (player.getInventory().contains(featherId, 1))
            slots--;
        return player.getInventory().getFreeSlots() >= slots;
    }


}
