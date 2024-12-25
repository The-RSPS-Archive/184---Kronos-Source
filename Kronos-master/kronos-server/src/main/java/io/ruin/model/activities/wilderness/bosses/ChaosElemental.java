package io.ruin.model.activities.wilderness.bosses;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.route.routes.DumbRoute;

import java.util.Arrays;

public class ChaosElemental extends NPCCombat {

    private static final Projectile DISARM_PROJECTILE = new Projectile(551, 80, 32, 40, 60, 6, 16, 64);
    private static final Projectile TELEPORT_PROJECTILE = new Projectile(554, 80, 32, 40, 60, 6, 16, 64);
    private static final Projectile MAGIC_PROJECTILE = new Projectile(557, 80, 32, 40, 60, 6, 16, 64);

    @Override
    public void init() {
    }

    @Override
    public int getAttackBoundsRange() {
        return 20;
    }

    @Override
    public void follow() {
        follow(8);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false;
        if (target.player != null && target.player.getInventory().hasFreeSlots(1) && Random.rollDie(10,  1))
            disarmAttack();
        else if (Random.rollDie(12, 1))
            teleportAttack();
        else
            triAttack();
        return true;
    }

    private void triAttack() {
        AttackStyle style;
        if (Random.rollDie(10, 6)) {
            style = AttackStyle.MAGIC;
        } else if (Random.rollDie(2, 1)) {
            style = AttackStyle.RANGED;
        } else {
            style = AttackStyle.CRUSH;
        }
        projectileAttack(MAGIC_PROJECTILE, info.attack_animation, style, info.max_damage);
    }


    private static final int[] DISARM_SLOTS = {Equipment.SLOT_WEAPON, Equipment.SLOT_SHIELD, Equipment.SLOT_CHEST, Equipment.SLOT_HAT, Equipment.SLOT_LEGS, Equipment.SLOT_HANDS, Equipment.SLOT_FEET};

    private void disarmAttack() {
        int slot = -1;
        int[] viable = Arrays.stream(DISARM_SLOTS).filter(s -> target.player.getEquipment().get(s) != null).toArray();
        if (viable.length > 0)
            slot = viable[Random.get(viable.length - 1)];
        npc.animate(info.attack_animation);
        int delay = DISARM_PROJECTILE.send(npc, target);
        int finalSlot = slot;
        Hit hit = new Hit(npc, AttackStyle.MAGIC, null)
                .randDamage(slot != -1 ? 5 : info.max_damage)
                .clientDelay(delay);
        if(finalSlot != -1 && target.player.getEquipment().get(finalSlot) != null) {
            target.player.getEquipment().unequip(target.player.getEquipment().get(finalSlot));
            target.player.sendMessage("The Chaos Elemental unequips one of your items!");
        }
        target.hit(hit);

    }

    private void teleportAttack() {
        npc.animate(info.attack_animation);
        int vecX = (target.getAbsX() - getClosestX());
        if (vecX != 0)
            vecX /= Math.abs(vecX); // determines X component for knockback
        int vecY = (target.getAbsY() - getClosestY());
        if (vecY != 0)
            vecY /= Math.abs(vecY); // determines Y component for knockback
        int endX = target.getAbsX();
        int endY = target.getAbsY();
        int maxKnockback = Random.get(1, 4);
        for (int i = 0; i < maxKnockback; i++) {
            if (DumbRoute.getDirection(endX, endY, npc.getHeight(), target.getSize(), endX + vecX, endY + vecY) != null) { // we can take this step!
                endX += vecX;
                endY += vecY;
            } else
                break; // cant take the step, stop here
        }
        npc.animate(info.attack_animation);
        int delay = (int) ((TELEPORT_PROJECTILE.send(npc, target) * 25.0) / 600) - 1;
        int finalEndY = endY;
        int finalEndX = endX;
        npc.addEvent(event -> {
            if(target != null) {
                final Player p = target.player;
                event.delay(delay);
                if (target == p) {
                    p.getMovement().teleport(finalEndX, finalEndY, npc.getHeight());
                    p.sendMessage("The Chaos Elemental teleports you!");
                }
            }
        });

    }


    private int getClosestX() {
        if (target.getAbsX() < npc.getAbsX())
            return npc.getAbsX();
        else if (target.getAbsX() >= npc.getAbsX() && target.getAbsX() <= npc.getAbsX() + npc.getSize() - 1)
            return target.getAbsX();
        else
            return npc.getAbsX() + npc.getSize() - 1;
    }

    private int getClosestY() {
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


