package io.ruin.model.skills.hunter.creature.impl;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.skills.hunter.Hunter;
import io.ruin.model.skills.hunter.creature.Creature;
import io.ruin.model.skills.hunter.traps.Trap;
import io.ruin.model.skills.hunter.traps.TrapType;
import io.ruin.model.skills.hunter.traps.impl.BoxTrap;
import io.ruin.process.event.Event;
import kilim.Pausable;

import java.util.Collections;
import java.util.List;

public class Chinchompa extends Creature {

    static {
        Hunter.registerCreature(new Chinchompa("chinchompa", 2910, 53, 10033, 198.4, 0.55, new int[]{9386, 9387, 9388, 9389}, 30, PlayerCounter.CAUGHT_GREY_CHINCHOMPA));
        Hunter.registerCreature(new Chinchompa("red chinchompa", 2911, 63, 10034, 265, 0.55, new int[]{9390, 9391, 9392, 9393}, 30, PlayerCounter.CAUGHT_RED_CHINCHOMPA));
        Hunter.registerCreature(new Chinchompa("black chinchompa", 2912, 73, 11959, 315.4, 0.6, new int[]{2025, 2026, 2028, 2029}, 30, PlayerCounter.CAUGHT_BLACK_CHINCHOMPA));
    }

    private final int itemId;
    private final int[] trappingObjects;
    private final List<Item> loot;

    public Chinchompa(String creatureName, int npcId, int levelReq, int itemId, double catchXP, double baseCatchChance, int[] trappingObjects, int respawnTicks, PlayerCounter counter) {
        super(creatureName, npcId, levelReq, catchXP, baseCatchChance, respawnTicks, counter);
        this.itemId = itemId;
        this.loot = Collections.singletonList(new Item(itemId, 1));
        this.trappingObjects = trappingObjects;
    }

    @Override
    public TrapType getTrapType() {
        return BoxTrap.INSTANCE;
    }

    @Override
    public List<Item> getLoot() {
        return loot;
    }

    @Override
    public void pathToTrap(NPC npc, GameObject trap) {
        npc.getRouteFinder().routeObject(trap);
        npc.face(trap);
    }

    @Override
    protected void prepareForCatchAttempt(NPC npc, Trap trap, Event event) throws Pausable {
        npc.face(trap.getObject());
        event.delay(1);
    }

    @Override
    protected void succeedCatch(NPC npc, Trap trap, Event event) throws Pausable {
        npc.animate(5184);
        event.delay(1);
        if (!trap.getOwner().isOnline()) {
            return;
        }
        npc.setHidden(true);
        trap.getObject().setId(trappingObjects[getDirection(npc, trap)]);
        event.delay(2);
        if (!trap.getOwner().isOnline()) {
            return;
        }
        trap.getObject().setId(9384);

    }

    @Override
    protected void failCatch(NPC npc, Trap trap, Event event) throws Pausable {
        npc.animate(5185);
        trap.getObject().setId(9381);
        event.delay(2);
        if (!trap.getOwner().isOnline()) {
            return;
        }
        trap.getObject().setId(9385);
    }

   private static int getDirection(NPC npc, Trap trap) {
       int diffX = npc.getPosition().getX() - trap.getObject().x;
       int diffY = npc.getPosition().getY() - trap.getObject().y;
       if (diffY == 1)
           return 0;
       else if (diffY == -1)
           return 2;
       else if (diffX == 1)
           return 1;
       else
           return 3;

   }

   @Override
   public boolean hasRoomForLoot(Player player) {
        return player.getInventory().hasRoomFor(itemId);
   }

}
