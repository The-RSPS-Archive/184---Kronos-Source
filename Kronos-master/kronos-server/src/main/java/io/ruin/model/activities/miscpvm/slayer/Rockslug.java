package io.ruin.model.activities.miscpvm.slayer;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.actions.ItemNPCAction;
import io.ruin.model.item.containers.Equipment;

import java.util.Arrays;

public class Rockslug extends NPCCombat {

    private boolean salted = false;

    static {
        for (int i : Arrays.asList(421, 422))
            ItemNPCAction.register(4161, i, (player, item, npc) -> {
                item.incrementAmount(-1);
                if (npc.getHp() <= 4) {
                    ((Rockslug)npc.getCombat()).salted = true;
                    npc.getCombat().startDeath(null);
                } else
                    player.sendMessage("You use the bag of salt, but the rockslug is not weak enough.");
            });
    }

    @Override
    public void startDeath(Hit killHit) {
        if(salted) {
            super.startDeath(killHit);
        }
        if (killHit != null && killHit.attacker != null && killHit.attacker.player != null && killHit.attacker.player.getEquipment().getId(Equipment.SLOT_WEAPON) == 11037) { // brine sabre bypasses need for salt
            super.startDeath(killHit);
        } else if (killHit != null && killHit.attacker != null && killHit.attacker.player != null && Config.SLUG_SALTER.get(killHit.attacker.player) == 1 && killHit.attacker.player.getInventory().contains(4161, 1)) { // autosalt unlock
            super.startDeath(killHit);
            killHit.attacker.player.getInventory().remove(4161, 1);
        } else {
            npc.setHp(1);
            npc.getCombat().reset();
        }
    }

    @Override
    public boolean allowRetaliate(Entity attacker) {
        return npc.getHp() > 1;
    }

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
        basicAttack();
        return true;
    }
}
