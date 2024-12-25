package io.ruin.model.activities.godwars.combat.armadyl;

import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Projectile;

import java.util.Arrays;
import java.util.List;

public class Aviansie extends NPCCombat {

    private static final Projectile SPEAR = new Projectile(1192, 80, 30, 40, 50, 5, 15, 16);
    private static final Projectile AXE = new Projectile(1193, 80, 30, 40, 50, 5, 15, 16);
    private static final List<Integer> SPEAR_IDS = Arrays.asList(3170, 3171, 3172, 3173, 3175, 3178, 3179, 3180, 3181, 3182, 3167);
    private Projectile projectile;

    @Override
    public void init() {
        projectile = SPEAR_IDS.contains(npc.getId()) ? SPEAR : AXE;
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
        follow(8);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false;
        projectileAttack(projectile, info.attack_animation, info.attack_style, info.max_damage);
        return true;
    }
}
