package io.ruin.model.activities.godwars.combat.impl.armadyl;

import io.ruin.api.utils.Random;
import io.ruin.model.activities.godwars.combat.General;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.route.routes.DumbRoute;
import io.ruin.model.map.route.routes.ProjectileRoute;
import io.ruin.utility.TickDelay;

public class KreeArra extends General {

    private TickDelay meleeDelay = new TickDelay();
    private static final Projectile RANGED_PROJECTILE = new Projectile(1199, 43, 31, 51, 68, 8, 15, 64);
    private static final Projectile MAGIC_PROJECTILE = new Projectile(1200, 43, 31, 51, 68, 8, 15, 64);

//    {"id": 3162, "x": 2832, "y": 5301, "walkRange": 8, "z": 2}, // Kree'arra

//    {"id": 3165, "x": 2829, "y": 5300, "walkRange": 8, "z": 2}, // Flight Kilisa
//    {"id": 3163, "x": 2834, "y": 5297, "walkRange": 8, "z": 2}, // Wingman Skree
//    {"id": 3164, "x": 2827, "y": 5299, "walkRange": 8, "z": 2}, // Flockleader Geerin

    public KreeArra() {
        super(new Lieutenant(3163, 2, -4, 8),
                new Lieutenant(3164, -5, -2, 8),
                new Lieutenant(3165, -3, -1, 8));
    }

    @Override
    public void init() {
        super.init();
        npc.hitListener = new HitListener().preDefend(hit -> {
            if (hit.attackStyle != null && hit.attackStyle.isMelee()) {
                hit.nullify();
                if (hit.attacker.player != null)
                    hit.attacker.player.sendMessage("Kree'arra is flying too high for you to hit with melee.");
            }
            meleeDelay.delay(5);
        });
    }
    
    @Override
    public void follow() {
        if (meleeDelay.isDelayed())
            follow(8);
        else
            follow(1);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false;
        if (Random.rollDie(6, 1))
            npc.forceText(Random.get(SHOUTS));
        if (!meleeDelay.isDelayed() && withinDistance(1))
            basicAttack();
        else if (Random.rollDie(10, 4))
            magicAttack();
        else
            rangedAttack();
        return true;
    }

    private void magicAttack() {
        npc.animate(6980);
        npc.localPlayers().forEach(p -> {
            if (ProjectileRoute.allow(npc, p)) {
                int delay = MAGIC_PROJECTILE.send(npc, p);
                p.hit(new Hit(npc, AttackStyle.MAGIC).randDamage(21).clientDelay(delay));
            }
        });
    }

    private void rangedAttack() {
        npc.animate(6980);
        npc.localPlayers().forEach(p -> {
            if (ProjectileRoute.allow(npc, p)) {
                int delay = RANGED_PROJECTILE.send(npc, p);
                p.hit(new Hit(npc, AttackStyle.RANGED).randDamage(71).clientDelay(delay));
                int vecX = (p.getAbsX() - getClosestX());
                if (vecX != 0)
                    vecX /= Math.abs(vecX);
                int vecY = (p.getAbsY() - getClosestY());
                if (vecY != 0)
                    vecY /= Math.abs(vecY);
                if (DumbRoute.getDirection(p.getAbsX(), p.getAbsY(), p.getHeight(), p.getSize(), p.getAbsX() + vecX, p.getAbsY() + vecY) != null) {
                    p.getMovement().teleport(p.getAbsX() + vecX, p.getAbsY() + vecY, p.getHeight());
                    p.graphics(245, 124, 0);
                }

            }
        });
    }

    private static final String[] SHOUTS = {
            "Kraaaaw!",
            "Eeeeek!"
    };



    private int getClosestX() { // PUT THIS SOMEWHERE I DONT KNOW WHERE HELKP ME PLEASE
        if (target.getAbsX() < npc.getAbsX())
            return npc.getAbsX();
        else if (target.getAbsX() >= npc.getAbsX() && target.getAbsX() <= npc.getAbsX() + npc.getSize() - 1)
            return target.getAbsX();
        else
            return npc.getAbsX() + npc.getSize() - 1;
    }

    private int getClosestY() { //
        if (target.getAbsY() < npc.getAbsY())
            return npc.getAbsY();
        else if (target.getAbsY() >= npc.getAbsY() && target.getAbsY() <= npc.getAbsY() + npc.getSize() - 1)
            return target.getAbsY();
        else
            return npc.getAbsY() + npc.getSize() - 1;
    }


    @Override
    public int getAttackBoundsRange() {
        return 20;
    }
}
