package io.ruin.model.activities.wilderness.bosses;

import io.ruin.api.utils.Random;
import io.ruin.cache.NPCDef;
import io.ruin.model.World;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.utility.Misc;
import io.ruin.utility.TickDelay;

import java.util.ArrayList;
import java.util.Collections;

public class Vetion extends NPCCombat {

    private static final Projectile MAGIC_PROJECTILE = new Projectile(280, 40, 2, 41, 150, 0, 40, 11); // duration increment is 0 because they have to all land simultaneously
    private static final int SKELETON_HELLHOUND = 6613;
    private static final int GREATER_SKELETON_HELLHOUND = 6614;

    private static final int VETION = 6611;
    private static final int VETION_REBORN = 6612;
    private TickDelay earthquakeCooldown;
    private boolean dogsSpawned = false;
    private NPC[] dogs = new NPC[2];

    @Override
    public void init() {
        earthquakeCooldown = new TickDelay();
        npc.hitListener = new HitListener()
                .preDefend(this::block)
                .postDefend(this::spawnDogs);
        npc.deathEndListener = (entity, killer, killHit) -> entity.npc.transform(VETION);
        NPCDef.get(SKELETON_HELLHOUND).ignoreOccupiedTiles = true;
        NPCDef.get(GREATER_SKELETON_HELLHOUND).ignoreOccupiedTiles = true; // they can do this i saw it in a vid
    }

    @Override
    public void startDeath(Hit killHit) {
        if (npc.getId() == VETION) {
            npc.transform(VETION_REBORN);
            restore();
            npc.forceText("Now do it again!!");
            dogsSpawned = false;
        } else {
            super.startDeath(killHit);
        }
    }

    private void spawnDogs(Hit hit) {
        if (!dogsSpawned && npc.getHp() < (npc.getMaxHp() / 2)) {
            dogsSpawned = true;
            if (npc.getId() == VETION)
                npc.forceText("Kill, my pets!");
            else
                npc.forceText("Bahh! Go, dogs!!");
            for (int i = 0; i < 2; i++) {
                dogs[i] = new NPC(npc.getId() == VETION ? SKELETON_HELLHOUND : GREATER_SKELETON_HELLHOUND).spawn(npc.getAbsX() + 1 + Random.get(-1, 1), npc.getAbsY() + 1 + Random.get(-1, 1), npc.getHeight());
                dogs[i].getCombat().setTarget(target);
                dogs[i].face(target);
            }
            dogs[0].forceText("GRRRRRRRRRRRRRRRRRRR");

        }
    }

    private void block(Hit hit) {
        for (NPC dog : dogs) {
            if (dog != null && !dog.isRemoved() && !dog.getCombat().isDead()) {
                hit.block();
                break;
            }
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
        if (withinDistance(1) && Random.rollPercent(85)) {
            basicAttack();
            return true;
        }
        if (!earthquakeCooldown.isDelayed() && Random.rollPercent(20)) {
            earthquakeAttack();
            return true;
        }
        magicAttack();
        return true;
    }

    private void magicAttack() {
        npc.animate(info.attack_animation);
        ArrayList<Position> selectedTargets = new ArrayList<>(3);
        ArrayList<Position> possibleTargets = new ArrayList<>(15); // get
        selectedTargets.add(target.getPosition().copy());
        Bounds illegalBounds = new Bounds(npc.getPosition().copy().translate(1, 1, 0), npc.getSize() - 1);
        int radius = 2;
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                if ((x == 0 && y == 0) || illegalBounds.inBounds(x, y, npc.getHeight(),0)) continue;
                possibleTargets.add(new Position(target.getAbsX() + x, target.getAbsY() + y, target.getHeight())); // triggered
            }
        }
        Collections.shuffle(possibleTargets); // hitler!
        selectedTargets.add(possibleTargets.get(0));
        selectedTargets.add(possibleTargets.get(1));
        selectedTargets.forEach(pos -> MAGIC_PROJECTILE.send(npc.getAbsX() + 1, npc.getAbsY() + 1, pos.getX(), pos.getY()));
        npc.addEvent(event -> {
            event.delay(3);
            selectedTargets.forEach(pos -> World.sendGraphics(281, 0, 0, pos));
            if (!npc.getCombat().isDead())
                npc.localPlayers().forEach(p -> {
                    if (selectedTargets.stream().anyMatch(pos -> pos.equals(p.getPosition()))) {
                        p.hit(new Hit().ignorePrayer().randDamage(30));
                    }
                });
        });
    }

    private void earthquakeAttack() {
        earthquakeCooldown.delay(10); // 6s cooldown
        npc.animate(5507);
        Position attackSource = npc.getPosition().copy().translate(1, 1, 0);
        npc.localPlayers().forEach(p -> {
            if (Misc.getDistance(attackSource, p.getPosition()) <= 12) {
                p.hit(new Hit().ignoreDefence().ignorePrayer().randDamage(45));
                p.sendMessage("Vet'ion pummels the ground sending a shattering earthquake shockwave through you.");
            }
        });
    }

/*    @Override
    public void dropItems(Killer killer) {
        super.dropItems(killer);
        Wilderness.rewardBossDamage(npc, 1.1);
    }*/
}
