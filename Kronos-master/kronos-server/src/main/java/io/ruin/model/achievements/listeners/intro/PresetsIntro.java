package io.ruin.model.achievements.listeners.intro;

import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;

import static io.ruin.model.achievements.Achievement.counterStage;

public class PresetsIntro implements AchievementListener {
    @Override
    public String name() {
        return "Presets";
    }

    @Override
    public AchievementStage stage(Player player) {
        return counterStage(PlayerCounter.PRESETS_LOADED.get(player), 0, 1);
    }

    @Override
    public String[] lines(Player player, boolean finished) {
        return new String[]{
                Achievement.slashIf("Presets are a great way to speed up banking or save loadouts.", finished),
                Achievement.slashIf("Open the Journal tab and click the <col=ff0000>red</col> icon to view your presets,", finished),
                Achievement.slashIf("then click on a slot to create a custom preset. After the preset", finished),
                Achievement.slashIf("has been created, click on it again and choose", finished),
                Achievement.slashIf("\"Select Preset\" to load it!", finished),
                "",
                Achievement.slashIf("<col=000080>Assignment</col>: Create and load a preset.", finished),
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

}
