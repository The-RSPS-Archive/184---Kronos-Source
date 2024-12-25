package io.ruin.model.achievements.listeners.novice;

import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.entity.player.Player;

public class Lightness implements AchievementListener {

    @Override
    public String name() {
        return "Light as a Feather";
    }

    @Override
    public AchievementStage stage(Player player) {
        return player.bootsOfLightnessTaken ? AchievementStage.FINISHED : AchievementStage.NOT_STARTED;
    }

    @Override
    public String[] lines(Player player, boolean finished) {
        return new String[]{
                Achievement.slashIf("<col=000080>Assignment</col>: Retrieve the Boots of Lightness from the Temple of Ikov.", finished),
                Achievement.slashIf("<col=000080>Reward</col>: 10,000 coins and the Boots of Lightness.", finished),
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
