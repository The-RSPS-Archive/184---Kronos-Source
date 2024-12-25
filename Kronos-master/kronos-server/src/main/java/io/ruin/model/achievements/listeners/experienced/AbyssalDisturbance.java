package io.ruin.model.achievements.listeners.experienced;

import io.ruin.api.utils.NumberUtils;
import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;

public class AbyssalDisturbance implements AchievementListener {
    @Override
    public String name() {
        return "Abyssal Slayer";
    }

    @Override
    public AchievementStage stage(Player player) {
        int amt = PlayerCounter.ABYSSAL_CREATURES_KC.get(player);
        if (amt >= 200)
            return AchievementStage.FINISHED;
        else if (amt > 0)
            return AchievementStage.STARTED;
        else
            return AchievementStage.NOT_STARTED;
    }

    @Override
    public String[] lines(Player player, boolean finished) {
        return new String[]{
                Achievement.slashIf("Once dangers and threats, now found fodder..", finished),
                "",
                Achievement.slashIf("<col=000080>Assignment</col>: Kill 200 creatures in the abyss.", finished),
                Achievement.slashIf("<col=000080>Reward</col>: Unlocks the large essence pouch.", finished),
                "",
                "<col=000080>Abyssal creatures killed: <col=800000>" + NumberUtils.formatNumber(PlayerCounter.ABYSSAL_CREATURES_KC.get(player)),
        };
    }

    @Override
    public void started(Player player) {

    }

    @Override
    public void finished(Player player) {

    }
}
