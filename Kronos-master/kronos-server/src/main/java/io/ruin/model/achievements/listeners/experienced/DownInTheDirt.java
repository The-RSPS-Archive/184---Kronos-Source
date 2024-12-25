package io.ruin.model.achievements.listeners.experienced;

import io.ruin.api.utils.NumberUtils;
import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.entity.player.Player;

public class DownInTheDirt implements AchievementListener {

    @Override
    public String name() {
        return "Dirty Money";
    }

    @Override
    public AchievementStage stage(Player player) {
        if(player.cleanedPaydirt == 0)
            return AchievementStage.NOT_STARTED;
        if(player.cleanedPaydirt < 250)
            return AchievementStage.STARTED;
        return AchievementStage.FINISHED;
    }

    @Override
    public String[] lines(Player player, boolean finished) {
        return new String[]{
                Achievement.slashIf("Not all opportunities present themselves as cleanly as others.", finished),
                Achievement.slashIf("Sometimes you just gotta get down and dirty to see it.", finished),
                "",
                Achievement.slashIf("<col=000080>Assignment</col>: Clean 250 pay-dirt", finished),
                Achievement.slashIf("<col=000080>Reward</col>: Ability to use Dark tunnel shortcut", finished),
                "",
                "<col=000080>Pay-dirt cleaned: <col=800000>" + NumberUtils.formatNumber(player.cleanedPaydirt)
        };
    }

    @Override
    public void started(Player player) {
    }

    @Override
    public void finished(Player player) {

    }

}