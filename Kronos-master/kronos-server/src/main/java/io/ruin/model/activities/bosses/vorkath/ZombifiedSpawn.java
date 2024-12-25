package io.ruin.model.activities.bosses.vorkath;

import io.ruin.model.World;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.skills.magic.spells.modern.CrumbleUndead;

public class ZombifiedSpawn extends NPCCombat {
    @Override
    public void init() {
        npc.hitListener = new HitListener().postDefend(hit -> {
           if (!hit.isBlocked() && hit.attackSpell == CrumbleUndead.INSTANCE)
               hit.damage = npc.getHp();
        });
    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(1))
            return false;
        npc.addEvent(event -> {
            event.delay(1);
            npc.animate(7890);
            World.sendGraphics(305, 50, 0, npc.getPosition());
            if(target != null)
            target.hit(new Hit().randDamage(60));
            setDead(true);
            event.delay(2);
            npc.setHidden(true);
            npc.remove();
        });
        return true;
    }
}
