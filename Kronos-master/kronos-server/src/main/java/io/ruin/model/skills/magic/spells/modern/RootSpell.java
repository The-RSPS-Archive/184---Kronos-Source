package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.skills.hunter.Impling;
import io.ruin.model.skills.magic.rune.RuneRemoval;
import io.ruin.model.skills.magic.spells.TargetSpell;
import io.ruin.model.stat.StatType;

public class RootSpell extends TargetSpell {

    private int duration;

    RootSpell(int duration) {
        this.duration = duration;
        entityAction = (p, e) -> {
            if (e.getCombat() != null) {
                p.getCombat().queueSpell(this, e);
            } else if (isRootable(e)) { //non-combat npc
                castOnNonCombat(p, e);
            }
        };
    }

    @Override
    public boolean cast(Entity entity, Entity target) {
        return allowHold(entity, target) && super.cast(entity, target);
    }

    @Override
    public void afterHit(Hit hit, Entity target) {
        hold(hit, target, duration, false);
    }

    private boolean castOnNonCombat(Player player, Entity target) {
        RuneRemoval r = null;
        if(!player.getStats().check(StatType.Magic, lvlReq, "cast this spell"))
            return false;
        if(runeItems != null && (r = RuneRemoval.get(player, runeItems)) == null) {
            player.sendMessage("You don't have enough runes to cast this spell.");
            return false;
        }
        if(castCheck != null && !castCheck.test(player, target))
            return false;
        player.animate(animationId);
        if(castGfx != null)
            player.graphics(castGfx[0], castGfx[1], castGfx[2]);
        if(castSound != null)
            player.publicSound(castSound[0], castSound[1], castSound[2]);
        int delay = 0;
        if(projectile != null)
            delay = projectile.send(player, target);
        if (hitGfx != null)
            target.graphics(hitGfx[0], hitGfx[1], delay);
        if (hitSoundId != -1)
            player.publicSound(hitSoundId, 1, delay);
        if(r != null)
            r.remove();

        target.freeze(duration, player);
        target.getMovement().reset();
        return true;
    }

    private boolean isRootable(Entity e) {
        return e.npc != null && Impling.get(e.npc.getId()) != null;
    }


}
