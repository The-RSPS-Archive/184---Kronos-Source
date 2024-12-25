package io.ruin.model.activities.godwars.combat.armadyl;

import io.ruin.model.entity.npc.NPCCombat;

public class ArmadylSpiritualWarrior extends NPCCombat {
    @Override
    public void init() {
        npc.attackNpcListener = (player, n, message) -> {
            if (player.getCombat().getAttackStyle().isMelee()) {
                if (message)
                    player.sendMessage("The aviansie is flying too high for you to hit with melee.");
                return false;
            }
            return true;
        };
    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(1))
            return false;
        basicAttack();
        return true;
    }
}
