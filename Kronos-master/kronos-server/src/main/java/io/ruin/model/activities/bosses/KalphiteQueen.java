package io.ruin.model.activities.bosses;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Projectile;
import io.ruin.utility.Misc;

import java.util.LinkedList;
import java.util.List;

public class KalphiteQueen extends NPCCombat {

    private static final Projectile MAGIC_PROJECTILE = new Projectile(280, 65, 43, 45, 90, 0, 20, 240);
    private static final Projectile MAGIC_CHAIN_PROJECTILE = new Projectile(280, 43, 43, 30, 90, 0, 20, 11);
    private static final Projectile RANGED_PROJECTILE = new Projectile(289, 25, 43, 45, 50, 10, 20, 240);

    private enum Form {
        FIRST(963, 6240, 6240, 278),
        SECOND(965, 6235, 6234, 279)
        ;
        Form(int npcId, int rangedAnimation, int magicAnimation, int magicGfx) {
            this.npcId = npcId;
            this.rangedAnimation = rangedAnimation;
            this.magicAnimation = magicAnimation;
            this.magicGfx = magicGfx;
        }


        private int npcId;
        private int rangedAnimation;
        private int magicAnimation;
        private int magicGfx;
    }

    private static final int TRANSFORM_ANIM = 6270;
    private static final int TRANSFORM_GFX = 1055;

    @Override
    public void startDeath(Hit killHit) {
        if (currentForm() == Form.FIRST) {
            npc.resetAnimation();
            npc.startEvent(event -> {
                npc.lock();
                event.delay(1);
                npc.setHp(npc.getMaxHp());
                npc.transform(Form.SECOND.npcId);
                npc.lock(LockType.FULL_NULLIFY_DAMAGE);
                npc.animate(TRANSFORM_ANIM);
                npc.graphics(TRANSFORM_GFX);
                event.delay(11);
                npc.unlock();
            });
        } else {
            super.startDeath(killHit);
        }
    }

    @Override
    public void init() {
        npc.hitListener = new HitListener().preDefend(this::preDefend);
        npc.deathEndListener = (entity, killer, killHit) -> npc.transform(Form.FIRST.npcId);
        npc.attackNpcListener = (player, npc1, message) -> !npc1.isLocked();
    }

    private void preDefend(Hit hit) {
        if (hit.attackStyle == null)
            return;
        if (currentForm() == Form.FIRST && (hit.attackStyle.isRanged() || hit.attackStyle.isMagic())) {
            hit.boostDefence(1.5);
        } else if (currentForm() == Form.SECOND && (hit.attackStyle.isMelee())) {
            hit.boostDefence(1.5);
        }
    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(10))
            return false;
        if (Random.rollDie(3, 1))
            aggroMinions();
        if (withinDistance(1) && Random.rollDie(3))
            basicAttack();
        else if (Random.rollDie(3))
            magicAttack();
         else if (Random.rollDie(2))
            rangedAttack();
        return true;
    }

    private Form currentForm() {
        return npc.getId() == Form.FIRST.npcId ? Form.FIRST : Form.SECOND;
    }

    private void aggroMinions() {
        npc.localNpcs().forEach(n -> {
            if (n.getCombat() == null)
                return;
            if (n.getCombat().getTarget() == null && Random.rollDie(6)) {
                Player p = Random.get(npc.localPlayers());
                n.getCombat().setTarget(p);
                n.face(p);
            }
        });
    }


    private void magicAttack() {
        npc.animate(currentForm().magicAnimation);
        npc.graphics(currentForm().magicGfx);
        npc.addEvent(event -> {
            Entity source = npc;
            Entity dest = target;
            Projectile proj = MAGIC_PROJECTILE;
            int bounces = 0;
            while (dest != null && bounces < 4) {
                int delay = proj.send(source, dest);
                dest.hit(new Hit(npc, AttackStyle.MAGIC).ignoreDefence().randDamage(31).clientDelay(delay)); // hit our target
                dest.graphics(281, 0, delay);
                event.delay(2);
                if (isDead() || npc.isRemoved())
                    return;
                //looking for a target to bounce to
                List<Entity> newTargets = new LinkedList<>(); // for O(1) removal
                newTargets.addAll(npc.localPlayers());
                Entity finalDest = dest;
                newTargets.removeIf(e -> e == finalDest || !canAttack(e) || Misc.getDistance(finalDest.getPosition(), e.getPosition()) > 2); // invalid targets
                if (newTargets.size() == 0)
                    return; // no targets found, abort
                source = dest;
                dest = Random.get(newTargets);
                proj = MAGIC_CHAIN_PROJECTILE;
                bounces++;
            }
        });
    }

    private void rangedAttack() {
        npc.animate(currentForm().rangedAnimation);
        int delay = RANGED_PROJECTILE.send(npc, target);
        target.hit(new Hit(npc, AttackStyle.RANGED).randDamage(31).ignoreDefence().clientDelay(delay));
        int ticks = (delay * 25) / 600;
        npc.addEvent(event -> {
            event.delay(ticks);
            if (target == null || isDead())
                return;
            target.localPlayers().forEach(p -> {
                if (p == target)
                    return;
                if (canAttack(p)) {
                    p.hit(new Hit(npc, AttackStyle.RANGED).randDamage(31).ignoreDefence());
                }
            });
        });
    }

    @Override
    public int getAttackBoundsRange() {
        return 20;
    }
}
