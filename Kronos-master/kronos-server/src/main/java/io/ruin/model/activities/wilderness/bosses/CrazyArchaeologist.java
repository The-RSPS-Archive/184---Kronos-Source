package io.ruin.model.activities.wilderness.bosses;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;

import java.util.ArrayList;
import java.util.Collections;

public class CrazyArchaeologist extends NPCCombat {

    private static final Projectile RANGED_PROJECTILE = new Projectile(1259, 40, 36, 41, 51, 5, 16, 64);

    private static final Projectile MAGIC_PROJECTILE = new Projectile(1260, 40, 0, 41, 150, 0, 16, 64);
    private static final Projectile SECONDARY_MAGIC_PROJECTILE = new Projectile(1260, 0, 0, 41, 110, 0, 32, 64);

    @Override
    public void init() {
        npc.deathStartListener = (entity, killer, killHit) -> entity.forceText("Ow!");
    }

    @Override
    public void follow() {
        follow(8);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false;
        if (Random.rollDie(8, 1))
            rainOfKnowledge();
        else if (withinDistance(1) && Random.rollDie(2, 1)) {
            basicAttack(423, AttackStyle.CRUSH, info.max_damage);
            npc.forceText(Random.get(SHOUTS));
        } else {
            projectileAttack(RANGED_PROJECTILE, info.attack_animation, AttackStyle.RANGED, info.max_damage);
            npc.forceText(Random.get(SHOUTS));
        }
        return true;
    }

    private void rainOfKnowledge() {
        npc.forceText("Rain of knowledge!");
        npc.animate(info.attack_animation);
        ArrayList<Position> selectedTargets = new ArrayList<>(3);
        ArrayList<Position> possibleTargets = new ArrayList<>(15); // get
        Position secondarySource = target.getPosition().copy();
        selectedTargets.add(secondarySource);
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
        selectedTargets.forEach(pos -> MAGIC_PROJECTILE.send(npc.getAbsX(), npc.getAbsY(), pos.getX(), pos.getY()));
        npc.addEvent(event -> {
            event.delay(3);
            for (int i = 0; i < 3; i++)
                World.sendGraphics(157, 0, 20, selectedTargets.get(i));
            if (!npc.getCombat().isDead())
                npc.localPlayers().forEach(p -> {
                    if (selectedTargets.stream().anyMatch(pos -> pos.equals(p.getPosition()))) {
                        p.hit(new Hit(npc, AttackStyle.MAGIC, null).ignorePrayer().ignoreDefence().randDamage(24));
                    }
                });
            //bounce for 2 more explosions
            selectedTargets.clear();
            selectedTargets.add(possibleTargets.get(2));
            selectedTargets.add(possibleTargets.get(3));
            selectedTargets.forEach(pos -> SECONDARY_MAGIC_PROJECTILE.send(secondarySource.getX(), secondarySource.getY(), pos.getX(), pos.getY()));
            event.delay(3);
            for (int i = 0; i < 2; i++)
                World.sendGraphics(157, 0, 20, selectedTargets.get(i));
            if (!npc.getCombat().isDead())
                npc.localPlayers().forEach(p -> {
                    if (selectedTargets.stream().anyMatch(pos -> pos.equals(p.getPosition()))) {
                        p.hit(new Hit(npc, AttackStyle.MAGIC, null).ignorePrayer().ignoreDefence().randDamage(24));
                    }
                });
        });
    }

    private static final String[] SHOUTS = {
            "I'm Bellock - respect me!",
            "Get off my site!",
            "No-one messes with Bellock's dig!",
            "These ruins are mine!",
            "Taste my knowledge!",
            "You belong in a museum!",
    };

}
