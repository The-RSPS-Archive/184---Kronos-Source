package io.ruin.model.achievements.listeners.experienced;

import io.ruin.api.utils.NumberUtils;
import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;

public class EssenceExtractor implements AchievementListener {
    @Override
    public String name() {
        return "Essence Extractor";
    }

    @Override
    public AchievementStage stage(Player player) {
        int amount = get(player);
        if (amount == 0)
            return AchievementStage.NOT_STARTED;
        else if (amount >= 500)
            return AchievementStage.FINISHED;
        else
            return AchievementStage.STARTED;
    }

    private static int get(Player player) {
        return PlayerCounter.MINED_RUNE_ESSENCE.get(player) +  PlayerCounter.MINED_PURE_ESSENCE.get(player);
    }

    @Override
    public String[] lines(Player player, boolean finished) {
        return new String[] {
                Achievement.slashIf("Pure or not, a hard day's labor is a hard day's haul.", finished),
                Achievement.slashIf("Mine 500 essence, pure or not.", finished),
                "",
                Achievement.slashIf("<col=000080>Assignment</col>: Mine 500 rune or pure essence.", finished),
                Achievement.slashIf("<col=000080>Reward</col>: Unlocks the medium essence pouch.", finished),
                "",
                "<col=000080>Essence mined: <col=800000>" + NumberUtils.formatNumber(get(player)),
        };
    }

    @Override
    public void started(Player player) {

    }

    @Override
    public void finished(Player player) {

    }
}
