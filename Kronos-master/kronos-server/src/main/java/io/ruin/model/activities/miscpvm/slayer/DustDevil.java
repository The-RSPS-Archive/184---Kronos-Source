package io.ruin.model.activities.miscpvm.slayer;

import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.skills.slayer.Slayer;

public class DustDevil extends NPCCombat {

    @Override
    public void init() {
        npc.hitListener = new HitListener().preDefend(this::preDefend);
    }

    private void preDefend(Hit hit) {
        if (hit.attacker != null && hit.attacker.player != null && (hit.attacker.player.getEquipment().getId(Equipment.SLOT_HAT) != 4164 && !Slayer.hasSlayerHelmEquipped(hit.attacker.player))) {
            hit.block();
            hit.attacker.player.sendMessage("Blinded by the monster's dust, you miss your attack!");
        }
    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(1))
            return false;
        if (target.player != null && (target.player.getEquipment().getId(Equipment.SLOT_HAT) != 4164 && !Slayer.hasSlayerHelmEquipped(target.player))) {
            target.hit(new Hit(npc, AttackStyle.MAGIC).fixedDamage(14).ignorePrayer().ignoreDefence());
            target.player.sendMessage("<col=ff0000>The devil's dust blinds and damages you!");
            target.player.sendMessage("<col=ff0000>A facemask can protect you from this attack.");
        }
        basicAttack();
        return true;
    }
}
