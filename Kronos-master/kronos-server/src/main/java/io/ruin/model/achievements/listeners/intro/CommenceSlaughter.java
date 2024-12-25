package io.ruin.model.achievements.listeners.intro;

import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.entity.player.Player;

public class CommenceSlaughter implements AchievementListener {
    @Override
    public String name() {
        return "First Time Slayer";
    }

    @Override
    public AchievementStage stage(Player player) {
        return player.slayerTasksCompleted >= 1 ? AchievementStage.FINISHED : AchievementStage.NOT_STARTED;
    }

    @Override
    public String[] lines(Player player, boolean finished) {
        return new String[]{
                Achievement.slashIf("Completing Slayer tasks is a great way to train your combat stats", finished),
                Achievement.slashIf("and start gaining some wealth.", finished),
                "",
                Achievement.slashIf("<col=000080>Assignment</col>: Complete a Slayer task.", finished),
                Achievement.slashIf("<col=000080>Reward</col>: 10,000 coins.", finished),
        };
    }

    @Override
    public void started(Player player) {

    }

    @Override
    public void finished(Player player) {
        rewardCoins(player, 10000);
    }

}
