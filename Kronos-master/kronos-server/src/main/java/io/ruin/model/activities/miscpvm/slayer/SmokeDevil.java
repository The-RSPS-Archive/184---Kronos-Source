package io.ruin.model.activities.miscpvm.slayer;

import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.slayer.Slayer;

public class SmokeDevil extends NPCCombat {

    protected static final Projectile PROJECTILE = new Projectile(643, 65, 31, 15, 56, 10, 0, 0);

    @Override
    public void init() {

    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false;
        if (smokeAttack())
            return true;
        projectileAttack(PROJECTILE, info.attack_animation, info.attack_style, info.max_damage);
        return true;
    }

    protected boolean smokeAttack() {
        if (target.player != null && (target.player.getEquipment().getId(Equipment.SLOT_HAT) != 4164 && !Slayer.hasSlayerHelmEquipped(target.player))) {
            target.hit(new Hit(npc, AttackStyle.MAGIC).fixedDamage(18).ignorePrayer().ignoreDefence());
            target.player.sendMessage("<col=ff0000>The devil's smoke blinds and damages you!");
            target.player.sendMessage("<col=ff0000>A facemask can protect you from this attack.");
            return true;
        }
        return false;
    }
}
