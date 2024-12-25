package io.ruin.model.activities.godwars.combat.impl.zamorak;

import io.ruin.api.utils.Random;
import io.ruin.model.activities.godwars.combat.General;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.route.routes.ProjectileRoute;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.model.stat.StatType;

public class KrilTsutsaroth extends General {

    private static final Projectile MAGIC_PROJECTILE = new Projectile(1225, 0, 0, 50, 51, 5, 16, 155);

//    {"id": 3129, "x": 2920, "y": 5326, "walkRange": 2, "z": 2}, // K'ril Tsutsaroth
//    {"id": 3130, "x": 2929, "y": 5327, "walkRange": 5, "z": 2}, // Tstanon Karlak
//    {"id": 3132, "x": 2923, "y": 5324, "walkRange": 5, "z": 2}, // Balfrug
//    {"id": 3131, "x": 2921, "y": 5327, "walkRange": 3, "z": 2}, // Zakl'n Gritch

    public KrilTsutsaroth() {
        super(new Lieutenant(3130, 9, 1, 5),
                new Lieutenant(3131, 1, 1, 3),
                new Lieutenant(3132, 3, -2, 5));
    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false;
        if (withinDistance(1)) {
            if (target.player != null && target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MELEE) && Random.rollDie(10, 2))
                pierceAttack();
            else {
                shout();
                basicAttack();
            }
        } else {
            shout();
            magicAttack();
        }
        if (Random.rollDie(8, 1))
            target.poison(16);
        return true;
    }


    private void pierceAttack() {
        npc.forceText("YARRRRRRR!");
        npc.animate(info.attack_animation);
        target.hit(new Hit(npc, AttackStyle.SLASH, null).randDamage(49).ignoreDefence().ignorePrayer());
        if (target.player != null) {
            target.player.getPrayer().drain(target.player.getStats().get(StatType.Prayer).currentLevel / 2);
            target.player.sendMessage("K'ril Tsutsaroth slams through your protection prayer, leaving you feeling drained.");
        }
    }

    private void magicAttack() {
        npc.graphics(1224, 30, 0);
        npc.localPlayers().forEach(p -> {
            if (ProjectileRoute.allow(npc, p)) {
                projectileAttack(MAGIC_PROJECTILE, 6950, AttackStyle.MAGIC, 30);
            }
        });
    }

    private void shout() {
        if (Random.rollDie(6, 1))
            npc.forceText(Random.get(SHOUTS));
    }

    private static final String[] SHOUTS = {
            "Attack them, you dogs!",
            "Forward!",
            "Death to Saradomin's dogs!",
            "Kill them, you cowards!",
            "The Dark One will have their souls!",
            "Zamorak curse them!",
            "Rend them limb from limb!",
            "No retreat!",
    };

    @Override
    public int getAttackBoundsRange() {
        return 20;
    }

}
