package io.ruin.model.activities.godwars.combat.impl.bandos;

import io.ruin.api.utils.Random;
import io.ruin.model.activities.godwars.combat.General;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.route.routes.ProjectileRoute;

public class GeneralGraardor extends General {

    //  {"id": 2215, "x": 2870, "y": 5358, "walkRange": 3, "z": 2}, // General Graardor
    //  {"id": 2216, "x": 2871, "y": 5359, "walkRange": 2, "z": 2}, // Sergeant Strongstack
    //  {"id": 2217, "x": 2872, "y": 5354, "walkRange": 2, "z": 2}, // Sergeant Steelwill
    //  {"id": 2218, "x": 2868, "y": 5362, "walkRange": 6, "z": 2}, // Sergeant Grimspike

    private static final Projectile RANGED_PROJECTILE = new Projectile(1202, 70, 43, 31, 90, 5, 10, 191);

    public GeneralGraardor() {
        super(new Lieutenant(2216, 1, 1, 2),
                new Lieutenant(2217, 2, -4, 2),
                new Lieutenant(2218, -2, 4, 6));
    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false;
        if (Random.rollDie(6, 1))
            npc.forceText(Random.get(SHOUTS));
        if (withinDistance(1) && Random.rollPercent(65))
            basicAttack();
        else
            rangedAttack();
        return true;
    }

    private void rangedAttack() {
        npc.animate(7021);
        npc.localPlayers().forEach( p -> {
            if (ProjectileRoute.allow(npc, p)) {
                int delay = RANGED_PROJECTILE.send(npc, p);
                Hit hit = new Hit(npc, AttackStyle.RANGED)
                        .randDamage(35)
                        .clientDelay(delay);
                hit.postDamage(t -> {
                    if(hit.damage > 0) {
                        t.graphics(160, 124, 0);
                    } else {
                        t.graphics(85, 124, 0);
                        hit.hide();
                    }
                });
                p.hit(hit);
            }
        });
    }

    private static final String[] SHOUTS = {
            "Death to our enemies!",
            "Brargh!",
            "Break their bones!",
            "For the glory of the Big High War God!",
            "Split their skulls!",
            "We feast on the bones of our enemies tonight!",
            "CHAAARGE!",
            "Crush them underfoot!",
            "All glory to Bandos!",
            "GRAAAAAAAAAR!",
    };


    @Override
    public int getAttackBoundsRange() {
        return 20;
    }

}
