package io.ruin.model.activities.bosses.zulrah;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Projectile;

public class ZulrahSnakeling extends NPCCombat {

    private AttackStyle style;

    @Override
    public void init() {
        style = Random.get() < 0.5 ? AttackStyle.MAGIC : AttackStyle.SLASH;
        npc.attackBounds = npc.walkBounds = new Bounds(npc.getSpawnPosition(), 32);
    }

    @Override
    public void follow() {
        follow(style == AttackStyle.MAGIC ? 8 : 1);
    }

    private static final Projectile PROJECTILE = new Projectile(1044, 25, 31, 8, 45, 10, 15, 11);


    @Override
    public boolean attack() {
        if (!withinDistance(style == AttackStyle.MAGIC ? 8 : 1))
            return false;
        if (style == AttackStyle.MAGIC) {
            npc.animate(info.attack_animation);
            int duration = PROJECTILE.send(npc, target);
            target.hit(new Hit(npc, AttackStyle.MAGIC).randDamage(info.max_damage).clientDelay(duration));
        } else {
            basicAttack();
        }
        if (Random.get() < 0.15)
            target.envenom(6);
        return true;
    }
}
