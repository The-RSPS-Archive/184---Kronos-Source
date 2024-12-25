package io.ruin.model.activities.miscpvm.slayer;

import io.ruin.cache.NPCDef;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.Tile;
import io.ruin.utility.Misc;

public class CaveKraken extends NPCCombat {

    private static final Projectile PROJECTILE = new Projectile(162, 25, 31, 25, 56, 10, 15, 64);

    static {
        NPCDef def = NPCDef.get(493);
        def.attackOption = def.getOption("disturb");
        def.swimClipping = true;
        def = NPCDef.get(492);
        def.swimClipping = true;
    }

    @Override
    public void init() {
        npc.hitListener = new HitListener().preDefend(this::preDefend).postDefend(this::postDefend).postDamage(this::postDamage).preTargetDefend(this::preTargetDefend);
        npc.respawnListener = n -> n.transform(getWhirlpoolId()); // back to whirlpool
    }

    private void preDefend(Hit hit) {
        hit.ignorePrayer();
    }

    protected void preTargetDefend(Hit hit, Entity entity) {
        hit.ignorePrayer();
    }

    private void postDamage(Hit hit) {
        if (hit.damage > 0 && npc.getId() == getWhirlpoolId()) { // whirlpool, surface
            npc.transform(getSurfaceId());
            npc.animate(getSurfacingAnimation());
        }
    }

    protected int getSurfacingAnimation() {
        return 7135;
    }

    protected int getWhirlpoolId() {
        return 493;
    }

    protected int getSurfaceId() {
        return 492;
    }

    protected Projectile getProjectile() {
        return PROJECTILE;
    }

    protected int getHitGfx() {
        return 163;
    }

    private void postDefend(Hit hit) {
        if (hit.attackStyle != null && hit.attackStyle.isRanged())
            hit.damage *= 0.8;
    }

    @Override
    public void follow() {
        follow(10); // we use max distance 10 to match player magic. if the player outranges the kraken it would be very easy to safespot them since their movement is limited to water
    }

    @Override
    public boolean attack() {
        if (!withinDistance(10)) {
            return false;
        }
        if (npc.getId() == getWhirlpoolId()) {
            return true; // whirlpool, dont attack
        }
        npc.animate(info.attack_animation);
        int delay = getProjectile().send(npc, target);
        Hit hit = new Hit(npc, info.attack_style)
                .randDamage(info.max_damage)
                .clientDelay(delay);
        hit.postDamage(t -> {
            if(hit.damage > 0) {
                t.graphics(getHitGfx(), 124, 0);
            } else {
                t.graphics(85, 124, 0);
                hit.hide();
            }
        });
        target.hit(hit);
        return true;
    }

    @Override
    public Position getDropPosition() {
        Player pKiller = getKiller().player;
        if (pKiller == null || Misc.getDistance(npc.getPosition(), pKiller.getPosition()) > 16)
            return npc.getPosition();
        Position pos = Misc.getClosestPosition(npc, pKiller);
        while (true) { // dont get spooked this will always have a max of 16 iterations in the absolute worst case, average will probably be 3-4
            int x = pos.unitVectorX(pKiller.getPosition());
            int y = pos.unitVectorY(pKiller.getPosition());
            pos.translate(x, y, 0);
            Tile tile = Tile.get(pos);
            if (pos.equals(pKiller.getPosition()) || tile == null || tile.clipping == 0) {
                return pos;
            }
        }
    }

    @Override
    protected boolean canAggro(Player player) {
        return npc.getId() == getSurfaceId() && super.canAggro(player);
    }

    @Override
    public int getDefendAnimation() {
        return npc.getId() == getWhirlpoolId() ? -1 : super.getDefendAnimation();
    }
}
