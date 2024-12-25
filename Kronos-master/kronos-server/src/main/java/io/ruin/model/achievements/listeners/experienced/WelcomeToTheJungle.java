package io.ruin.model.achievements.listeners.experienced;

import io.ruin.api.utils.NumberUtils;
import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.entity.player.Player;

public class WelcomeToTheJungle implements AchievementListener {

    @Override
    public String name() {
        return "Welcome to the Jungle";
    }

    @Override
    public AchievementStage stage(Player player) {
        return player.jungleDemonKills.getKills() > 0 ? AchievementStage.FINISHED : AchievementStage.NOT_STARTED;
    }

    @Override
    public String[] lines(Player player, boolean finished) {
        return new String[]{
                Achievement.slashIf("Jungle demons are powerful demons,", finished),
                Achievement.slashIf("capable of using both melee and magic.", finished),
                Achievement.slashIf("", finished),
                Achievement.slashIf("The demons can be found in the banana plantation beneath Ape Atoll.", finished),
                "",
                Achievement.slashIf("<col=000080>Assignment</col>: Kill a jungle demon.", finished),
                Achievement.slashIf("<col=000080>Reward</col>: " + (player.getGameMode().isIronMan() ? "Unlocks the ability to purchase and equip the Dragon Scimitar." : "100,000 coins."), finished),
                "",
                "<col=000080>Jungle demons slain: <col=800000>" + NumberUtils.formatNumber(player.jungleDemonKills.getKills()),
        };
    }

    @Override
    public void started(Player player) {

    }

    @Override
    public void finished(Player player) {
        if (!player.getGameMode().isIronMan())
            rewardCoins(player, 100000);
    }
}
