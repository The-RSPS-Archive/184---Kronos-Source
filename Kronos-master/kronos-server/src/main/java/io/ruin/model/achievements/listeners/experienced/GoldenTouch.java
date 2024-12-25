package io.ruin.model.achievements.listeners.experienced;

import io.ruin.api.utils.NumberUtils;
import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;

public class GoldenTouch implements AchievementListener {

    @Override
    public String name() {
        return "Golden Touch";
    }

    @Override
    public AchievementStage stage(Player player) {
        int bars = PlayerCounter.SMELTED_GOLD_BARS.get(player);
        int ores = PlayerCounter.MINED_GOLD.get(player);
        if (bars == 0 && ores == 0)
            return AchievementStage.NOT_STARTED;
        else if (bars >= 500 && ores >= 500)
            return AchievementStage.FINISHED;
        else
            return AchievementStage.STARTED;
    }

    @Override
    public String[] lines(Player player, boolean finished) {
        return new String[]{
                Achievement.slashIf("", finished),
                "",
                Achievement.slashIf("<col=000080>Assignment</col>: Mine and smelt 500 gold ore.", finished),
                Achievement.slashIf("<col=000080>Reward</col>: Unlocks the goldsmith gauntlets, which increase", finished),
                Achievement.slashIf("<col=00080>gold smelting XP by 150%.", finished),
                "",
                "<col=000080>Gold ore mined: <col=800000>" + NumberUtils.formatNumber(PlayerCounter.MINED_GOLD.get(player)),
                "<col=000080>Gold bars smelted: <col=800000>" + NumberUtils.formatNumber(PlayerCounter.SMELTED_GOLD_BARS.get(player)),
        };
    }

    @Override
    public void started(Player player) {

    }

    @Override
    public void finished(Player player) {

    }
}
