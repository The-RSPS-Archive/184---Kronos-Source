package io.ruin.model.activities.barrows;

import io.ruin.cache.NPCDef;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.Position;
import io.ruin.model.map.route.RouteFinder;

public enum BarrowsBrother {

    AHRIM(
            1672,
            Config.AHRIM_KILLED,
            4708, 4710, 4712, 4714
    ),
    DHAROK(
            1673,
            Config.DHAROK_KILLED,
            4716, 4718, 4720, 4722
    ),
    GUTHAN(
            1674,
            Config.GUTHAN_KILLED,
            4724, 4726, 4728, 4730
    ),
    KARIL(
            1675,
            Config.KARIL_KILLED,
            4732, 4734, 4736, 4738
    ),
    TORAG(
            1676,
            Config.TORAG_KILLED,
            4745, 4747, 4749, 4751
    ),
    VERAC(
            1677,
            Config.VERAC_KILLED,
            4753, 4755, 4757, 4759
    );

    public final int npcId;
    public final Config config;
    public final Integer[] pieces;

    BarrowsBrother(int npcId, Config config, Integer... pieces) {
        this.npcId = npcId;
        this.config = config;
        this.pieces = pieces;
    }

    public void spawn(Player player) {
        if(player.npcTarget) {
            player.sendMessage("You are under attack!");
            return;
        }
        Position pos = RouteFinder.findWalkable(player.getPosition());
        NPC npc = new NPC(npcId).spawn(pos).targetPlayer(player, true);
        npc.forceText("You dare disturb my rest!");
        npc.attackTargetPlayer(() -> !player.getPosition().isWithinDistance(npc.getPosition()));
        npc.deathEndListener = (DeathListener.SimpleKiller) killer -> {
            if(killer != null)
                config.set(killer.player, 1);
            npc.remove();
        };
    }

    static {
        for(BarrowsBrother brother : BarrowsBrother.values()) {
            NPCDef.get(brother.npcId).ignoreOccupiedTiles = true;
        }
    }

    public boolean isKilled(Player player) {
        return config.get(player) == 1;
    }

}
