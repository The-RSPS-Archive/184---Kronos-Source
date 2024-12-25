package io.ruin.model.activities.miscpvm;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.jewellery.BraceletOfEthereum;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Projectile;

import java.util.function.Predicate;

public class Revenant extends NPCCombat {

    private static final Projectile RANGED_ATTACK = new Projectile(206, 30, 31, 41, 51, 10, 15, 11);

    private static final Projectile MAGIC_ATTACK = new Projectile(1415, 43, 31, 41, 56, 10, 15, 64);

    @Override
    public void init() {
        npc.aggressionImmunity = isProtected(npc);
    }

    @Override
    public void follow() {
        follow(5);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(5))
            return false;
        if ((npc.getHp() < npc.getMaxHp() / 2) && Random.rollDie(5, 1)) {
            npc.graphics(1221);
            npc.incrementHp(npc.getMaxHp() / 4);
            return true;
        }
        if (withinDistance(1) && Random.rollDie(2, 1))
            meleeAttack();
        else if (Random.rollDie(2, 1))
            rangedAttack();
        else
            magicAttack();
        return true;
    }

    private void meleeAttack() {
        npc.animate(info.attack_animation);
        Hit hit = new Hit(npc, info.attack_style, null).randDamage(info.max_damage);
        if (braceletOfEthereumEffect(target))
            hit.block();
        target.hit(hit);
    }

    private void magicAttack() {
        npc.animate(info.attack_animation);
        int delay = MAGIC_ATTACK.send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.MAGIC).randDamage(info.max_damage).clientDelay(delay);
        if (braceletOfEthereumEffect(target))
            hit.block();
        hit.postDamage(t -> {
            if (hit.damage > 0) {
                t.graphics(1454, 124, 0);
            } else {
                t.graphics(85, 124, 0);
                hit.hide();
            }
        });
        target.hit(hit);
    }

    private void rangedAttack() {
        npc.animate(info.attack_animation);
        int delay = RANGED_ATTACK.send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.RANGED).randDamage(info.max_damage).clientDelay(delay);
        if (braceletOfEthereumEffect(target))
            hit.block();
        target.hit(hit);
    }

    private boolean braceletOfEthereumEffect(Entity target) {
        Item bracelet = target.player.getEquipment().get(Equipment.SLOT_HANDS);
        if (bracelet != null && bracelet.getId() == BraceletOfEthereum.CHARGED) {
            BraceletOfEthereum.consumeCharge(target.player, bracelet);
            return true;
        }
        return false;
    }

    private static Predicate<Entity> isProtected(NPC npc) {
        return entity -> {
           if(entity.player == null)
            return false;
           if(!entity.player.getPosition().isWithinDistance(npc.getPosition(), 4) || entity.player.getCombat().isDefending(6))
               return true;
            Item bracelet = entity.player.getEquipment().get(Equipment.SLOT_HANDS);
            return bracelet != null && bracelet.getId() == BraceletOfEthereum.CHARGED;
        };
    }

}
