package io.ruin.model.combat.special.melee;

import io.ruin.cache.ItemDef;
import io.ruin.model.activities.duelarena.DuelRule;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.route.routes.DumbRoute;

//Shove: Push your target back and stun them
//for 3 seconds. This attack deals no damage. (25%)
public class DragonSpear implements Special {

    @Override
    public boolean accept(ItemDef def, String name) {
        return name.contains("dragon spear") || name.contains("zamorakian");
    }

    @Override
    public boolean handle(Player player, Entity target, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        if(DuelRule.NO_MOVEMENT.isToggled(player)) {
            player.sendMessage("This weapon's special attack cannot be used in this duel.");
            return false;
        }
        if(target.getSize() > 1) {
            player.sendMessage("That monster is too big to be pushed back!");
            return false;
        }
        if (target.npc.getId() == 2668) {
            player.sendMessage("You cannot push a Combat Dummy");
            return false;
        }
        if(target.isStunned(1)) {
            player.sendMessage("They're already stunned!"); // todo correct message or check if you can actually use the spec on a stunned/immune target but without applying the stun
            return false;
        }
        player.animate(1064);
        player.graphics(253, 100, 0);
        player.publicSound(2544);

        player.getCombat().reset();
        player.getCombat().delayAttack(2);

        int diffX = target.getPosition().getX() - player.getPosition().getX();
        int diffY = target.getPosition().getY() - player.getPosition().getY();
        if(diffX != 0)
            diffX /= Math.abs(diffX);
        if(diffY != 0)
            diffY /= Math.abs(diffY);
        if(DumbRoute.getDirection(target.getAbsX(), target.getAbsY(), target.getHeight(), target.getSize(), target.getAbsX() + diffX, target.getAbsY() + diffY) != null) {
            target.getCombat().reset();
            target.faceNone(false);
            target.step(diffX, diffY, StepType.FORCE_WALK);
        }
        target.stun(3, false);
        target.graphics(245, 124, 5);
        target.hit(new Hit(player).nullify()); // so other players cant pj in single-way zone
        if(target.player != null) //important that this happens here for things that hit multiple targets
            player.getCombat().skull(target.player);
        return true;
    }

    @Override
    public int getDrainAmount() { return 25; }

}