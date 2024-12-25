package io.ruin.model.achievements.listeners.intro;

import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.entity.player.Player;

public class TheBestiary implements AchievementListener {
    @Override
    public String name() {
        return "The Bestiary";
    }

    @Override
    public AchievementStage stage(Player player) {
        return player.bestiaryIntro ? AchievementStage.FINISHED : AchievementStage.NOT_STARTED;
    }

    @Override
    public String[] lines(Player player, boolean finished) {
        return new String[]{
                Achievement.slashIf("The Bestiary can be used to view drop table and stat information", finished),
                Achievement.slashIf("about any monster in the game. To access it, click the yellow icon", finished),
                Achievement.slashIf("in your Journal tab.", finished),
                "",
                Achievement.slashIf("<col=000080>Assignment</col>: View any monster's information in the bestiary.", finished),
                Achievement.slashIf("<col=000080>Reward</col>: 10,000 coins", finished),
        };
    }

    @Override
    public void started(Player player) {

    }

    @Override
    public void finished(Player player) {
        rewardCoins(player, 10000);
    }

    public static void complete(Player player) {
        if (!player.bestiaryIntro) {
            player.bestiaryIntro = true;
            Achievement.THE_BESTIARY.update(player);
        }
    }
}
