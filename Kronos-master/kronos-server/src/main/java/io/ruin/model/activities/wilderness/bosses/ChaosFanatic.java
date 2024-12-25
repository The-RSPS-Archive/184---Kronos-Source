package io.ruin.model.activities.wilderness.bosses;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ChaosFanatic extends NPCCombat {

    private static final Projectile GROUND_PROJECTILE = new Projectile(551, 80, 0, 41, 150, 0, 16, 64);
    private static final Projectile MAGIC_ATTACK = new Projectile(554, 80, 43, 51, 56, 10, 16, 64);
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
            return false;
        npc.forceText(Random.get(SHOUTS));
        if (Random.rollDie(8, 1))
            magicAttack();
        else if (target.player != null && target.player.getInventory().hasFreeSlots(1) && Random.rollDie(10,  1))
            disarmAttack();
        else normalAttack();
        return true;
    }

    private void normalAttack() {
        projectileAttack(MAGIC_ATTACK, info.attack_animation, info.attack_style, info.max_damage)
                .postDamage(t -> t.graphics(305));
    }

    private static final int[] DISARM_SLOTS = {Equipment.SLOT_WEAPON, Equipment.SLOT_SHIELD, Equipment.SLOT_CHEST, Equipment.SLOT_HAT, Equipment.SLOT_LEGS, Equipment.SLOT_HANDS, Equipment.SLOT_FEET};

    private void disarmAttack() {
        int slot = -1;
        int[] viable = Arrays.stream(DISARM_SLOTS).filter(s -> target.player.getEquipment().get(s) != null).toArray();
        if (viable.length > 0)
            slot = viable[Random.get(viable.length - 1)];
        npc.animate(info.attack_animation);
        int delay = MAGIC_ATTACK.send(npc, target);
        if(slot != -1)
            target.player.getEquipment().unequip(target.player.getEquipment().get(slot));

        Hit hit = new Hit(npc, AttackStyle.MAGIC, null)
                .randDamage(slot != -1 ? 5 : info.max_damage)
                .clientDelay(delay);
        target.hit(hit);

    }

    private void magicAttack() {
        npc.animate(info.attack_animation);
        ArrayList<Position> selectedTargets = new ArrayList<>(3);
        ArrayList<Position> possibleTargets = new ArrayList<>(15); // get
        selectedTargets.add(target.getPosition().copy());
        Position illegalPosition = npc.getPosition().copy();
        int radius = 2;
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                if ((x == 0 && y == 0) || illegalPosition.equals(target.getAbsX() + x, target.getAbsY() + y)) continue;
                possibleTargets.add(new Position(target.getAbsX() + x, target.getAbsY() + y, target.getHeight())); // triggered
            }
        }
        Collections.shuffle(possibleTargets); // hitler!
        selectedTargets.add(possibleTargets.get(0));
        selectedTargets.add(possibleTargets.get(1));
        selectedTargets.forEach(pos -> GROUND_PROJECTILE.send(npc.getAbsX(), npc.getAbsY(), pos.getX(), pos.getY()));
        npc.addEvent(event -> {
            event.delay(3);
            for (int i = 0; i < selectedTargets.size(); i++)
                World.sendGraphics(i == 0 ? 552 : 157, 0, 20, selectedTargets.get(i));
            if (!npc.getCombat().isDead())
                npc.localPlayers().forEach(p -> {
                    if (selectedTargets.stream().anyMatch(pos -> pos.equals(p.getPosition()))) {
                        p.hit(new Hit().ignorePrayer().ignoreDefence().randDamage(30));
                    }
                });
        });
    }

    private static final String[] SHOUTS = {
            "Burn!",
            "WEUGH!",
            "Develish Oxen Roll!",
            "All your wilderness are belong to them!",
            "AhehHeheuhHhahueHuUEehEahAH",
            "I shall call him squidgy and he shall be my squidgy!",
    };

}
