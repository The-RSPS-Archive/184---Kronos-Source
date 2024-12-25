package io.ruin.model.entity.npc;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.shared.Movement;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.Tile;

public class NPCMovement extends Movement {

    private NPC npc;

    public NPCMovement(NPC npc) {
        this.npc = npc;
    }

    /**
     * Processing
     */

    public void process() {
        randomWalk();
        //^Interesting spot for this ey?
        npc.updateLastPosition();
        walkDirection = runDirection = -1;
        if(finishTeleport(npc.getPosition())) {
            npc.getPosition().getTile().checkTriggers(npc);
        } else {
            if(!step(npc))
                return;
            boolean forceRun = stepType == StepType.FORCE_RUN;
            boolean ran = forceRun && step(npc);
            int diffX = npc.getPosition().getX() - npc.getLastPosition().getX();
            int diffY = npc.getPosition().getY() - npc.getLastPosition().getY();
            if(ran) {
                runDirection = getDirection(diffX, diffY);
                if(runDirection == -1)
                    walkDirection = getDirection(diffX, diffY);
            } else {
                walkDirection = getDirection(diffX, diffY);
            }
            npc.getPosition().getTile().checkTriggers(npc);
        }
        npc.getPosition().updateRegion();
        npc.checkMulti();
        Tile.occupy(npc);
    }

    private void randomWalk() {
        if(!npc.isRandomWalkAllowed())
            return;
        if(!Random.rollDie(4, 1))
            return;
        NPCCombat combat = npc.getCombat();
        if(combat != null && (combat.isDead() || combat.getTarget() != null))
            return;
        int x = npc.walkBounds.randomX();
        int y = npc.walkBounds.randomY();
        npc.getRouteFinder().routeAbsolute(x,y);
    }

    /**
     * Misc
     */

    private static int getDirection(int dx, int dy) {
        if(dy == 1) {
            if(dx == -1)
                return 0;
            if(dx == 0)
                return 1;
            if(dx == 1)
                return 2;
        } else if(dy == 0) {
            if(dx == -1)
                return 3;
            if(dx == 1)
                return 4;
        } else if(dy == -1) {
            if(dx == -1)
                return 5;
            if(dx == 0)
                return 6;
            if(dx == 1)
                return 7;
        }
        return -1;
    }

}