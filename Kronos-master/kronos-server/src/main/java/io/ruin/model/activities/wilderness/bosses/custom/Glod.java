package io.ruin.model.activities.wilderness.bosses.custom;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Projectile;
import io.ruin.utility.Misc;

public class Glod extends NPCCombat {

    private static final Projectile RANGED_PROJECTILE = new Projectile(856, 0, 0, 50, 65, 6, 0, 255);

    private boolean forceQuake = false;

    @Override
    public void init() {
        npc.hitsUpdate.hpBarType = 2;
        npc.hitListener = new HitListener().postDefend(this::postDefend);
    }

    private void postDefend(Hit hit) {
        if (hit.damage > 100 && !hit.isBlocked())
            hit.damage = 100;
    }

    @Override
    public void updateLastDefend(Entity attacker) {
        super.updateLastDefend(attacker);
        super.updateLastDefend(attacker);
        if(attacker.player != null && !attacker.player.getCombat().isSkulled()) {
            attacker.player.getCombat().skullNormal();
            attacker.player.sendMessage("<col=6f0000>You've been marked with a skull for attacking Glod!");
        }
    }

    @Override
    public void follow() {
        follow(6);
    }

    @Override
    public boolean attack() {
        if(!withinDistance(8))
            return false;
        if(forceQuake || Random.rollDie(5, 1)) {
            earthquake();
            forceQuake = false;
        } else if(withinDistance(1) || Random.rollPercent(80)) {
            rangedAttack();
        } else {
            taunt();
        }
        return true;
    }

    private void earthquake() {
        npc.animate(6501);
        npc.forceText("GLOD SMASH!");
        npc.addEvent(event -> {
            event.delay(1);
            npc.localPlayers().forEach(p -> {
                if(!canAttack(p))
                    return;
                int distance = Misc.getEffectiveDistance(npc, p);
                if(distance >= 8)
                    return;
                int damage = 50 - (distance * 5);
                p.getPrayer().deactivateAll();
                p.sendMessage("Glod's earthquake disables your prayers!");
                p.hit(new Hit(npc, AttackStyle.CRUSH).randDamage(damage / 2, damage).boostAttack(0.35));
                for(int i = 0; i <= 2; i++)
                    p.getPacketSender().shakeCamera(i, damage);
                p.addEvent(camEvent -> {
                    camEvent.delay(2);
                    p.getPacketSender().resetCamera();
                });

            });
        });
    }

    private void rangedAttack() {
        npc.animate(6501);
        npc.localPlayers().forEach(p -> {
            if(!canAttack(p) || Misc.getEffectiveDistance(npc, p) > 8)
                return;
            int delay = RANGED_PROJECTILE.send(npc, p);
            p.hit(new Hit(npc, AttackStyle.RANGED).randDamage(info.max_damage).clientDelay(delay));
        });
    }

    private void taunt() {
        Player player = target.player;
        npc.animate(6524);
        delayAttack(-2);
        forceQuake = true;
        if(target.player != null)
            target.player.sendMessage(Color.RED.wrap("Glod's taunt forces you to mindlessly run towards him!"));
        player.addEvent(event -> {
            player.lock();
            player.getCombat().reset();
            player.face(npc);
            player.getRouteFinder().routeEntity(npc);
            event.delay(2);
            player.unlock();
        });
    }
}
