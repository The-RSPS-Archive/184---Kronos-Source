package io.ruin.model.activities.wilderness.bosses;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.route.routes.DumbRoute;
import io.ruin.process.event.Event;

public class Callisto extends NPCCombat {

    public static final Projectile SHOCK_PROJECTILE = new Projectile(395, 43, 31, 51, 36, 10, 15, 127);

    @Override
    public void init() {
    }

    @Override
    public void follow() {
        follow(8);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false; // do i need this??
        if (withinDistance(1) && Random.rollPercent(80)) {
                basicAttack();
        } else if (Random.rollPercent(80)) {
                shockwaveAttack();
        } else {
                roarAttack(); // CAN HE USE THIS OUTSIDE MELEE RANGE? I CAN'T FIND OUT!!!
        }
        if (Random.rollDie(12, 1)) {
            heal();
        }
        return true;
    }

    private void heal() {
        npc.graphics(157);
        npc.incrementHp(Random.get(4, 10)); //Heal: A blast will appear under Callisto. Even though the game states that Callisto will prepare to heal himself based on the damage dealt to him, he actually heals himself during this time for a small amount. -- source: wiki
    }

    private void shockwaveAttack() {
        npc.animate(info.attack_animation);
        int delay = SHOCK_PROJECTILE.send(npc, target);
        target.hit(new Hit(npc, AttackStyle.MAGIC, null).randDamage(60).ignoreDefence().clientDelay(delay));
        target.player.sendMessage("Callisto's fury sends an almighty shockwave through you.");
    }

    private void roarAttack() {
        npc.animate(info.attack_animation);
        int vecX = (target.getAbsX() - getClosestX());
        if (vecX != 0)
            vecX /= Math.abs(vecX); // determines X component for knockback
        int vecY = (target.getAbsY() - getClosestY());
        if (vecY != 0)
            vecY /= Math.abs(vecY); // determines Y component for knockback
        int endX = target.getAbsX();
        int endY = target.getAbsY();
        for (int i = 0; i < 4; i++) {
            if (DumbRoute.getDirection(endX, endY, npc.getHeight(), target.getSize(), endX + vecX, endY + vecY) != null) { // we can take this step!
                endX += vecX;
                endY += vecY;
            } else
                break; // cant take the step, stop here
        }
        Direction dir;
        if (vecX == -1)
            dir = Direction.EAST;
        else if (vecX == 1)
            dir = Direction.WEST;
        else if (vecY == -1)
            dir = Direction.NORTH;
        else
            dir = Direction.SOUTH;

        if (endX != target.getAbsX() || endY != target.getAbsY()) { // only do movement if we can take at least one step
            if (target.player != null) {// npcs don't have support for force move? why :(
                int finalEndX = endX;
                int finalEndY = endY;
                World.startEvent(e -> {
                    final Player p = target.player;
                    p.lock();
                    p.animate(1157);
                    p.graphics(245, 5, 124);
                    p.hit(new Hit().fixedDamage(3));
                    p.stun(2, true);
                    int diffX = finalEndX - target.getAbsX();
                    int diffY = finalEndY - target.getAbsY();
                    p.getMovement().teleport(finalEndX, finalEndY, npc.getHeight());
                    p.getMovement().force(diffX, diffY, 0, 0, 10, 60, dir);
                    p.sendMessage("Callisto's roar throws you backwards.");
                    p.unlock();
                });
            }
        } else {
            target.hit(new Hit().fixedDamage(3));
            target.animate(1157);
            target.graphics(245, 5, 124);
            target.stun(2, true);
            if (target.player != null)
                target.player.sendMessage("Callisto's roar throws you backwards.");
        }
    }

    private int getClosestX() { // is this already added somewhere else? it's very useful, should be available somewhere public
        if (target.getAbsX() < npc.getAbsX())
            return npc.getAbsX();
        else if (target.getAbsX() >= npc.getAbsX() && target.getAbsX() <= npc.getAbsX() + npc.getSize() - 1)
            return target.getAbsX();
        else
            return npc.getAbsX() + npc.getSize() - 1;
    }

    private int getClosestY() { // is this already added somewhere else? it's very useful, should be available somewhere public
        if (target.getAbsY() < npc.getAbsY())
            return npc.getAbsY();
        else if (target.getAbsY() >= npc.getAbsY() && target.getAbsY() <= npc.getAbsY() + npc.getSize() - 1)
            return target.getAbsY();
        else
            return npc.getAbsY() + npc.getSize() - 1;
    }

/*    @Override
    public void dropItems(Killer killer) {
        super.dropItems(killer);
        Wilderness.rewardBossDamage(npc, 1);
    }*/
}
