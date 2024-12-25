package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;
import io.ruin.model.stat.StatType;

public class Teleblock extends TargetSpell {

    private static final Projectile LAND_PROJECTILE = new Projectile(1299, 43, 31, 46, 85, 0, 16, 64);

    private static final Projectile MISS_PROJECTILE = new Projectile(1300, 43, 31, 46, 85, 0, 16, 64);

    public Teleblock() {
        setLvlReq(85);
        setAnimationId(1819);
        setCastSound(202, 0, 0);
        setHitGfx(345, 0);
        setHitSound(203);
        setRunes(Rune.LAW.toItem(1), Rune.DEATH.toItem(1), Rune.CHAOS.toItem(1));
    }

    @Override
    public boolean cast(Entity entity, Entity target) {
        if(target.npc != null) {
            if(entity.player != null)
                entity.player.sendMessage("You can only cast that on other players.");
            return false;
        }
        if(target.player.getCombat().tbImmunityTicks > 0) {
            if(entity.player != null)
                entity.player.sendMessage("This player is currently immune to this spell.");
            return false;
        }
        if(target.player.getCombat().tbTicks > 0) {
            entity.player.sendMessage("That player is already being affected by this spell.");
            return false;
        }
        return super.cast(entity, target);
    }

    @Override
    public void beforeHit(Hit hit, Entity target) {
        hit.ignorePrayer(); //don't want protect from mage always blocking!
    }

    @Override
    public void afterHit(Hit hit, Entity target) {
        if(hit.isBlocked()) {
            hit.clientDelay(MISS_PROJECTILE.send(hit.attacker, target));
            hit.attacker.player.getStats().addXp(StatType.Magic, 84, false);
        } else {
            hit.clientDelay(LAND_PROJECTILE.send(hit.attacker, target));
            hit.attacker.player.getStats().addXp(StatType.Magic, 95, false);
            target.player.getCombat().teleblock();
        }
        hit.hide();
    }

}