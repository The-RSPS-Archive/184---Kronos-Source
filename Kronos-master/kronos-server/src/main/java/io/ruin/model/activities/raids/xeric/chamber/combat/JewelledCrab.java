package io.ruin.model.activities.raids.xeric.chamber.combat;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.skills.Tool;
import io.ruin.utility.TickDelay;

import java.util.Arrays;

import static io.ruin.model.activities.raids.xeric.chamber.impl.JewelledCrabsChamber.Color.*;
public class JewelledCrab extends NPCCombat {

    private TickDelay revertDelay = new TickDelay();
    static {
        for (int id : Arrays.asList(7576, 7577, 7578, 7579))
        NPCAction.register(id, "smash", (player, npc) -> {
            boolean dragon = player.getInventory().hasId(13576) || player.getEquipment().hasId(13576);
            if (!player.getInventory().hasId(Tool.HAMMER) && !dragon) {
                player.sendMessage("You'll need a hammer to smash the crab.");
                return;
            }
            npc.stun(45, true);
            npc.transform(RED.crabId);
            player.animate(dragon ? 7215 : 7214);
            JewelledCrab crab = (JewelledCrab) npc.getCombat();
            crab.revertDelay.delay(8);
        });
    }
    @Override
    public void init() {
        npc.hitListener = new HitListener().preDefend(this::preDefend).preDamage(this::preDamage);
        npc.addEvent(event -> {
            while (true) {
                if (!revertDelay.isDelayed() && npc.getId() != WHITE.crabId)
                    npc.transform(WHITE.crabId);
                event.delay(2);
            }
        });
    }

    private void preDamage(Hit hit) {
        hit.block();
    }

    private void preDefend(Hit hit) {
        if (hit.attackStyle != null) {
            if (hit.attackStyle.isMelee())
                npc.transform(RED.crabId);
            else if (hit.attackStyle.isRanged())
                npc.transform(GREEN.crabId);
            else if (hit.attackStyle.isMagic())
                npc.transform(BLUE.crabId);
            revertDelay.delay(20);
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
        basicAttack();
        return true;
    }
}
