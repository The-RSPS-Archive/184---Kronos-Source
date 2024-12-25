package io.ruin.model.activities.raids.xeric.chamber.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.activities.raids.xeric.chamber.Chamber;
import io.ruin.model.activities.raids.xeric.chamber.combat.LargeMuttadile;
import io.ruin.model.activities.raids.xeric.chamber.combat.SmallMuttadile;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.Direction;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.skills.woodcutting.Hatchet;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Misc;

public class MuttadilesChamber extends Chamber {

    private static final int[][] treeSpawns = {
            {4, 8},
            {5, 8},
            {22, 5},
    };

    private static final int[][] smallSpawns = {
            {12, 19},
            {12, 14},
            {14, 11},
    };

    private static final int[][] swimmerSpawns = {
            {18, 19, 1},
            {18, 19, 1},
            {11, 18, 0},
    };

    private static final int[][] blockSpawns = {
            {6, 16},
            {12, 23},
            {24, 15},
    };

    public static final int SMALL_MUTTADILE = 7562;
    public static final int SWIMMING_MUTTADILE = 7561;
    private static Direction[] directions = {Direction.SOUTH, Direction.WEST, Direction.NORTH, Direction.EAST};
    private static final int LIVE_TREE_OBJ = 30012;
    private static final int DEAD_TREE_OBJ = 30013;
    private static final int BLOCK_CRYSTAL = 30018;
    public static final int TREE_NPC = 7564;

    private int treeHealth;
    private int maxHealth;

    public int getTreeHealth() {
        return treeHealth;
    }

    public NPC getTree() {
        return tree;
    }

    public void damageTree(int damage) {
        treeHealth -= damage;
        if (treeHealth <= 0) {
            GameObject.forObj(LIVE_TREE_OBJ, tree.getAbsX(), tree.getAbsY(), tree.getHeight(), obj -> obj.setId(DEAD_TREE_OBJ));
            tree.remove();
        }
    }

    private NPC tree;

    @Override
    public void onRaidStart() {
        int[] spawn = swimmerSpawns[getLayout()];
        NPC swimmer = spawnNPC(SWIMMING_MUTTADILE, spawn[0], spawn[1], directions[spawn[2]], 0);
        swimmer.attackNpcListener = (player, npc, message) -> {
            if (message) player.sendMessage("The Muttadile is underwater, your attacks can't reach it!");
            return false;
        };
        spawn = treeSpawns[getLayout()];
        spawnObject(LIVE_TREE_OBJ, spawn[0], spawn[1], 10, 0);
        tree = spawnNPC(TREE_NPC, spawn[0], spawn[1], Direction.SOUTH, 0);
        maxHealth =  5 + (getRaid().getPartySize() * 3);
        treeHealth = maxHealth;
        NPCAction.register(tree, 1, this::chop);
        spawn = smallSpawns[getLayout()];
        NPC smallMuttadile = spawnNPC(SMALL_MUTTADILE, spawn[0], spawn[1], directions[getLayout()], 0); // VERY important to spawn the smaller one last because it needs references to the tree npc
        SmallMuttadile mutta = (SmallMuttadile) smallMuttadile.getCombat();
        mutta.chamber = this;
        smallMuttadile.deathEndListener = (entity, killer, killHit) -> {
            smallMuttadile.remove();
            swimmer.startEvent(event -> {
                swimmer.lock();
                Direction dir = rotatedDir(directions[swimmerSpawns[getLayout()][2]]);
                swimmer.transform(7563);
                swimmer.animate(7423);
                swimmer.step(dir.deltaX * 6, dir.deltaY * 6, StepType.FORCE_WALK);
                event.delay(7);
                LargeMuttadile largeMuttadile = (LargeMuttadile) swimmer.getCombat();
                largeMuttadile.chamber = this;
                swimmer.unlock();
                swimmer.attackNpcListener = null;
                if (killHit != null && killHit.attacker != null) {
                    swimmer.getCombat().setTarget(killHit.attacker);
                    swimmer.face(killHit.attacker);
                }
            });
        };
        spawn = blockSpawns[getLayout()];
        GameObject blocking = spawnObject(BLOCK_CRYSTAL, spawn[0], spawn[1], 10, 0);
        swimmer.deathEndListener = (entity, killer, killHit) -> {
            swimmer.remove();
            World.startEvent(event -> {
               blocking.animate(7506);
               event.delay(3);
               blocking.remove();
            });
        };
    }

    private void chop(Player player, NPC npc) {
        Hatchet hatchet = Hatchet.find(player);
        if (hatchet == null) {
            player.sendMessage("You'll need a hatchet to chop down this tree.");
            return;
        }
        player.addEvent(event -> {
            int ticks = 0;
            while (treeHealth > 0 && !npc.isRemoved() && !npc.isHidden() && Misc.getEffectiveDistance(npc, player) <= 1) {
                player.face(npc);
                player.animate(hatchet.animationId);
                if (++ticks >= 5) {
                    ticks = 0;
                    if (Random.rollDie(100, 35 + hatchet.points)) {
                        player.animate(-1);
                        npc.hitsUpdate.forceSend(treeHealth, maxHealth);
                        player.getStats().addXp(StatType.Woodcutting, 5, true);
                        damageTree(1);
                    }
                }
                event.delay(1);
            }
            if (player.getCombat().getTarget() != null)
                player.face(player.getCombat().getTarget());
        });
    }
}
