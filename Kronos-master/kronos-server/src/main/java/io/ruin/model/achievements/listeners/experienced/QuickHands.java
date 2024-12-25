package io.ruin.model.achievements.listeners.experienced;

import io.ruin.api.utils.NumberUtils;
import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.entity.player.Player;

public class QuickHands implements AchievementListener {

    @Override
    public String name() {
        return "Stealthy";
    }

    @Override
    public AchievementStage stage(Player player) {
        if(player.wallSafesCracked == 0)
            return AchievementStage.NOT_STARTED;
        if(player.wallSafesCracked < 250)
            return AchievementStage.STARTED;
        return AchievementStage.FINISHED;
    }

    @Override
    public String[] lines(Player player, boolean finished) {
        return new String[]{
                Achievement.slashIf("Memorization through repetition, master the traps of the wall safe", finished),
                "",
                Achievement.slashIf("<col=000080>Assignment</col>: Crack 250 wall safes", finished),
                Achievement.slashIf("<col=000080>Reward</col>: 10% chance of double loot when thieving from wall safes", finished),
                "",
                "<col=000080>Wall Safes Cracked: <col=800000>" + NumberUtils.formatNumber(player.wallSafesCracked)
        };
    }

    @Override
    public void started(Player player) {
        player.sendMessage("<col=000080>You have started the achievement: <col=800000>" + name());
    }

    @Override
    public void finished(Player player) {
        player.sendMessage("<col=000080>You have completed the achievement: <col=800000>" + name());
    }

}