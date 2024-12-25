package io.ruin.model.activities.bosses.necromancer;

import io.ruin.model.World;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.skills.magic.spells.modern.CrumbleUndead;

/**
 * @author ReverendDread on 6/16/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class Necrominion extends NPCCombat {

    @Override
    public void init() {
        npc.hitListener = new HitListener().postDefend(hit -> {
            if (!hit.isBlocked() && hit.attackSpell == CrumbleUndead.INSTANCE)
                hit.damage = npc.getHp();
        });
        npc.hitsUpdate.hpBarType = 8;
        npc.startEvent(e -> {
            int progress = 1;
            while (progress <= 10 && !(target != null && target.getPosition().isWithinDistance(npc.getPosition(), 1))) {
                npc.hitsUpdate.forceSend(progress++, 10);
                e.delay(1);
            }
            if (target != null) {
                World.sendGraphics(5009, 0, 0, npc.getPosition());
                target.hit(new Hit().randDamage(60));
                target.resetFreeze();
                setDead(true);
                e.delay(2);
                npc.setHidden(true);
                npc.remove();
            }
        });
    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        return false;
    }

}
