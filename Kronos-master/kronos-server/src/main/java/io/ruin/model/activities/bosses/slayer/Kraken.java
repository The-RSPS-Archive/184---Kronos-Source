package io.ruin.model.activities.bosses.slayer;

import io.ruin.cache.NPCDef;
import io.ruin.model.activities.miscpvm.slayer.CaveKraken;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.shared.listeners.RespawnListener;
import io.ruin.model.map.Projectile;

import java.util.ArrayList;
import java.util.List;

public class Kraken extends CaveKraken {

    static {
        NPCDef def = NPCDef.get(496);
        def.attackOption = def.getOption("disturb");
        def.swimClipping = true;
        def = NPCDef.get(494);
        def.swimClipping = true;
    }

    private static final Projectile PROJECTILE = new Projectile(156, 60, 31, 25, 56, 10, 15, 128);
    private List<NPC> tentacles = new ArrayList<>(4);

    @Override
    public void init() {
        super.init();
        tentacles.add(new NPC(5534).spawn(npc.spawnPosition.copy().translate(-3, 0, 0)));
        tentacles.add(new NPC(5534).spawn(npc.spawnPosition.copy().translate(-3, 4, 0)));
        tentacles.add(new NPC(5534).spawn(npc.spawnPosition.copy().translate(6, 0, 0)));
        tentacles.add(new NPC(5534).spawn(npc.spawnPosition.copy().translate(6, 4, 0)));
        npc.deathStartListener = (entity, killer, killHit) -> {
          tentacles.forEach(t -> {
              if (!t.getCombat().isDead())
                  t.getCombat().startDeath(killHit);
          });
        };
        RespawnListener superListener = npc.respawnListener;
        npc.respawnListener = n -> {
            superListener.onRespawn(npc);
            tentacles.forEach(t -> t.getCombat().respawn());
        };
        npc.hitListener.preDefend(this::preDefend);
    }

    private void preDefend(Hit hit) {
        hit.ignorePrayer();
        if (npc.getId() == getWhirlpoolId() && tentacles.stream().anyMatch(t -> t.getId() == 5534)) {
            hit.block();
        }
    }

    @Override
    public boolean attack() {
        tentacles.forEach(t -> {
            if (t.getCombat().getTarget() != target) {
                t.getCombat().setTarget(target);
                t.face(target);
            }
        });
        return super.attack();
    }

    @Override
    protected void preTargetDefend(Hit hit, Entity entity) {
        super.preTargetDefend(hit, entity);
        hit.ignorePrayer();
    }

    @Override
    protected int getWhirlpoolId() {
        return 496;
    }

    @Override
    protected int getSurfaceId() {
        return 494;
    }

    @Override
    protected Projectile getProjectile() {
        return PROJECTILE;
    }

    @Override
    protected int getHitGfx() {
        return 157;
    }
}
