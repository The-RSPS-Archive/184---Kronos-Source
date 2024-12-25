package io.ruin.model.combat.special.melee;

import io.ruin.api.utils.Random;
import io.ruin.cache.ItemDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;

/**
 * First, a normal attack, which usually deals a high amount of damage;
 * Then half of that first hit, and then finally two attacks tha add up to the second hit. For example, 300-150-70-80.
 * If the first hit misses and the second one hits, the 3rd and 4th hits will each deal half of the damage of the second hit. For example, 0-300-150-150.
 * If the first two attacks hit 0-0, the 3rd and 4th will perform as regular hits. For example, 0-0-300-300.
 * However, the last 2 hits can only be up to 75% of your max hit.
 * If the claws hit 3 initial zeros, the last hit (if it hits) will have a 50% damage boost; e.g.: 0-0-0-450.
 */
//Slice and Dice: Hit 4 times in quick
//succession, with great accuracy. (50%)
public class DragonClaws implements Special {

    @Override
    public boolean accept(ItemDef def, String name) {
        return name.contains("dragon claws");
    }

    @Override
    public boolean handle(Player player, Entity victim, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        player.animate(7514, 10);
        player.graphics(1171, 0, 10);
        player.publicSound(2537);
        Hit hit1 = new Hit(player, attackStyle, attackType).boostAttack(0.25),
                hit2 = new Hit(player, attackStyle, attackType).boostAttack(0.25),
                hit3 = new Hit(player, attackStyle, attackType).boostAttack(0.25).delay(2),
                hit4 = new Hit(player, attackStyle, attackType).boostAttack(0.25).delay(2);
        int minDamage; //Never lower than 4 because anything less causes unwanted 0s.
        if(Random.rollDie(6))
            minDamage = 4;
        else
            minDamage = Math.max(4, maxDamage / 2);
        int firstDamage = victim.hit(hit1.randDamage(minDamage, maxDamage));
        if(firstDamage > 0) {
            int secondDamage = victim.hit(hit2.fixedDamage(firstDamage / 2).ignoreDefence());
            int thirdDamage = secondDamage / 2;
            int fourthDamage = thirdDamage + (secondDamage % 2);
            victim.hit(hit3.fixedDamage(thirdDamage).ignoreDefence(), hit4.fixedDamage(fourthDamage).ignoreDefence());
        } else {
            int secondDamage = victim.hit(hit2.randDamage(minDamage, maxDamage));
            if(secondDamage > 0) {
                int damage = secondDamage / 2;
                victim.hit(hit3.fixedDamage(damage).ignoreDefence(), hit4.fixedDamage(damage).ignoreDefence());
            } else {
                int damage = (int) (maxDamage * 0.75);
                int thirdDamage = victim.hit(hit3.randDamage(damage));
                if(thirdDamage == 0)
                    victim.hit(hit4.randDamage((int) (maxDamage * 1.5)));
                else
                    victim.hit(hit4.randDamage(damage));
            }
        }
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 50;
    }

}