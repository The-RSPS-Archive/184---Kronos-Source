package io.ruin.model.achievements.listeners.experienced;

import io.ruin.api.utils.NumberUtils;
import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;

public class DemonSlayer implements AchievementListener {
    @Override
    public String name() {
        return "Demon Slayer";
    }

    @Override
    public AchievementStage stage(Player player) {
        int amount = PlayerCounter.DEMON_KILLS.get(player);
        if (amount == 0)
            return AchievementStage.NOT_STARTED;
        else if (amount >= 300)
            return AchievementStage.FINISHED;
        else
            return AchievementStage.STARTED;
    }

    @Override
    public String[] lines(Player player, boolean finished) {
        return new String[]{
                Achievement.slashIf("Prove yourself worthy of the Darklight sword by slaying demons. ", finished),
                "",
                Achievement.slashIf("<col=000080>Assignment</col>: Kill 300 demons of any kind.", finished),
                Achievement.slashIf("<col=000080>Reward</col>: Ability to purchase and equip Darklight.", finished),
                "",
                Achievement.slashIf("<col=000080>Demons slain</col>: " + NumberUtils.formatNumber(PlayerCounter.DEMON_KILLS.get(player)), finished),
        };
    }

    @Override
    public void started(Player player) {

    }

    @Override
    public void finished(Player player) {

    }

    public static void check(Player player, NPC killed) {
        if (killed.getDef().demon)
            PlayerCounter.DEMON_KILLS.increment(player, 1);
    }
}
