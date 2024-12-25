package io.ruin.model.activities.godwars.combat;

import io.ruin.cache.NPCDef;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Direction;

import java.util.Arrays;

public abstract class General extends NPCCombat { // TODO something on npc removal? don't think there's a listener for it, will probably need it to clean up lieutenants on instance removal

    private Lieutenant[] lieutenants;

    public General(Lieutenant... lieutenants) {
        if (lieutenants.length != 3)
            throw new IllegalArgumentException("must have exactly 3 lieutenants");
        this.lieutenants = lieutenants;
        Arrays.stream(lieutenants).map(l -> NPCDef.get(l.npc.getId())).forEach(def -> def.ignoreOccupiedTiles = true);
    }

    @Override
    public void init() {
        for (Lieutenant l : lieutenants) {
            l.npc.spawn(npc.spawnPosition.getX() + l.xOffset, npc.spawnPosition.getY() + l.yOffset, npc.spawnPosition.getZ(), Direction.SOUTH, l.walkRange);
            l.npc.aggressionImmunity = entity -> false; // override god item protection
        }
        npc.respawnListener = n -> {
           for (Lieutenant l : lieutenants) {
               if (l.npc.isHidden()) {
                   l.npc.getMovement().teleport(l.npc.spawnPosition);
                   l.npc.getCombat().restore();
                   l.npc.getCombat().setDead(false);
                   l.npc.getCombat().resetKillers();
                   l.npc.setHidden(false);
               }
           }
        };
        npc.aggressionImmunity = entity -> false; // override god item protection
        npc.getDef().ignoreOccupiedTiles = true;
    }


    protected static class Lieutenant {
        NPC npc;
        int xOffset, yOffset;
        int walkRange;

        /**
         *  @param npc lieutenant npc id
         * @param x x coord to spawn npc in, relative to general's spawn x
         * @param y y coord to spawn npc in, relative to general's spawn y
         * @param walkRange
         */
        public Lieutenant(int npc, int x, int y, int walkRange) {
            this.npc = new NPC(npc);
            this.xOffset = x;
            this.yOffset = y;
            this.walkRange = walkRange;
        }
    }
}
