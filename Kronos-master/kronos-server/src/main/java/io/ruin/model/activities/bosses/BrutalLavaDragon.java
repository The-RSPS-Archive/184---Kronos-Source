package io.ruin.model.activities.bosses;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Graphic;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.route.routes.DumbRoute;
import io.ruin.process.event.Event;

/**
 * @author ReverendDread on 6/30/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class BrutalLavaDragon extends NPCCombat {

    private static final int FLYING_NPC = 15016;
    private static final int LANDED_NPC = 15019;
    private static final int FLYING_DRAGONFIRE = 7871;
    private static final int LANDING = 7872;
    private static final int HEADBUTT = 91;
    private static final int GROUND_DRAGONFIRE_ANIM = 84;
    private static final int GROUND_MELEE_ANIM = 80;

    private static final Projectile FIRE_PROJ = new Projectile(1465, 43, 31, 51, 56, 10, 16, 64).tileOffset(3);
    private static final Projectile FIRE_PROJ_FLYING = new Projectile(1465, 160, 31, 51, 56, 10, 16, 64).tileOffset(3);
    private static final Graphic FIRE_HIT_GFX = Graphic.builder().id(1466).height(124).soundId(163).build();
    private static final Graphic SPLASH_GFX = Graphic.builder().id(85).height(124).soundId(227).build();
    private static final Projectile DRAGONFIRE_PROJ = new Projectile(54, 37, 32, 50, 60, 5, 24, 200).tileOffset(3);
    private static final Projectile DRAGONFIRE_PROJ_FLYING = new Projectile(54, 150, 32, 50, 60, 5, 24, 200).tileOffset(3);
    private static final Graphic NONE = Graphic.builder().id(-1).build();
    private boolean headbutt; //prevents headbutt twice in a row
    private boolean transformed;

    @Override
    public void init() {
        npc.hitListener = new HitListener().preDefend(this::transform);
    }

    @Override
    public void follow() {
        follow(8);
    }

    @Override
    public boolean attack() {
        boolean close = withinDistance(2);
        int random = Random.get(close && transformed ? 0 : 3, 5);
        if (target == null)
            return false;
        if (random >= 0 && random < 4) {
            if (Random.rollDie(4, 1) && !headbutt && transformed) {
                npc.animate(HEADBUTT);
                knockback();
                headbutt = true;
            } else if (Random.rollDie(2, 1)){
                npc.animate(!transformed ? FLYING_DRAGONFIRE : GROUND_MELEE_ANIM);
                Hit hit = new Hit(npc, AttackStyle.STAB, null).randDamage(18).ignorePrayer();
                target.player.hit(hit);
            }
        } else {
            npc.localPlayers().forEach(player -> {
                if (Random.rollDie(4, 1) && close) {
                    npc.graphics(5023, 100, 0);
                    npc.animate(!transformed ? FLYING_DRAGONFIRE : GROUND_DRAGONFIRE_ANIM);
                    Hit hit = new Hit(npc, AttackStyle.DRAGONFIRE, null).randDamage(58).ignorePrayer();
                    player.hit(hit);
                } else {
                    boolean dragon_fire = Random.rollDie(2, 1);
                    (dragon_fire ? (!transformed ? DRAGONFIRE_PROJ_FLYING : DRAGONFIRE_PROJ) : (!transformed ? FIRE_PROJ_FLYING : FIRE_PROJ)).send(npc, player);
                    npc.animate((!transformed ? FLYING_DRAGONFIRE : GROUND_DRAGONFIRE_ANIM));
                    Hit hit = new Hit(npc, dragon_fire ? AttackStyle.DRAGONFIRE : AttackStyle.MAGIC, null).randDamage(dragon_fire ? 58 : 21);
                    if (!dragon_fire) hit.ignorePrayer();
                    player.hit(hit);
                }
                headbutt = false;
            });
        }
        return true;
    }

    private void transform(Hit hit) {
        double ratio = ((double) npc.getHp() / npc.getMaxHp());
        if (ratio < 0.50 && !transformed) {
            npc.resetAnimation();
            delayAttack(4);
            npc.transform(LANDED_NPC);
            npc.startEvent(e -> {
                e.delay(1);
                npc.animate(LANDING);
                e.delay(2);
            });
            transformed = true;
        }
    }

    private void knockback() {
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
                    p.hit(new Hit().fixedDamage(20));
                    p.stun(2, true);
                    int diffX = finalEndX - target.getAbsX();
                    int diffY = finalEndY - target.getAbsY();
                    p.getMovement().teleport(finalEndX, finalEndY, npc.getHeight());
                    p.getMovement().force(diffX, diffY, 0, 0, 10, 60, dir);
                    p.sendMessage("The brutal lava dragon roars and throws you backwards.");
                    p.unlock();
                });
            }
        } else {
            target.hit(new Hit().fixedDamage(20));
            target.animate(1157);
            target.graphics(245, 5, 124);
            target.stun(2, true);
            if (target.player != null)
                target.player.sendMessage("The brutal lava dragon roars and throws you backwards.");
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

}
