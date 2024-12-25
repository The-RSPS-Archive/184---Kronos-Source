package io.ruin.model.activities.inferno.monsters;

import io.ruin.model.World;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;

import java.util.Collections;
import java.util.List;

public class JalMejJak extends NPCCombat {

    private static final Projectile LAVA_PROJECTILE = new Projectile(660, 0, 0, 0, 75, 0, 45, 32).regionBased();


    @Override
    public void init() {

    }

    @Override
    public void follow() {

    }

    @Override
    public boolean attack() {
        if (!withinDistance(6))
            return false;
        npc.animate(info.attack_animation);
        List<Position> positions = new Position(npc.getAbsX(), target.getAbsY(), npc.getHeight()).area(3, p -> p.getTile().clipping == 0);
        Collections.shuffle(positions);
        for (int i = 0; i < 3; i++) {
            Position pos = positions.get(i);
            LAVA_PROJECTILE.send(npc, pos);
            World.sendGraphics(659, 0, 75, pos);
            npc.addEvent(event -> {
                event.delay(3);
                if (target == null)
                    return;
                if (target.getPosition().equals(pos)) {
                    target.hit(new Hit(npc).ignoreDefence().ignorePrayer().randDamage(info.max_damage));
                }
            });
        }
        return true;
    } // Zuk healer


}
