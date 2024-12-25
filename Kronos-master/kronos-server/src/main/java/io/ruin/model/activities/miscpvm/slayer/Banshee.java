package io.ruin.model.activities.miscpvm.slayer;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.skills.slayer.Slayer;
import io.ruin.model.stat.StatType;


public class Banshee extends NPCCombat {

    private static StatType[] DRAIN = { StatType.Attack, StatType.Strength, StatType.Defence, StatType.Ranged, StatType.Magic, StatType.Prayer, StatType.Agility};

    @Override
    public void init() {

    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(1))
            return false;
        npc.animate(info.attack_animation);
        Hit hit = new Hit(npc, info.attack_style);
        if (target.player != null && target.player.getEquipment().getId(Equipment.SLOT_HAT) != 4166 && !Slayer.hasSlayerHelmEquipped(target.player)) {
            hit.randDamage(info.max_damage + 6).ignoreDefence().ignorePrayer();
            for (StatType statType : DRAIN) {
                target.player.getStats().get(statType).drain(5);
            }
            target.player.sendMessage("The banshee's deafening scream drains your stats!");
        } else {
            hit.randDamage(info.max_damage);
        }
        target.hit(hit);
        return true;
    }
}
