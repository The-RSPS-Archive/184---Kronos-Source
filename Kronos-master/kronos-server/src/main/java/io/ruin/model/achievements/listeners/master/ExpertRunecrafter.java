package io.ruin.model.achievements.listeners.master;

import io.ruin.api.utils.NumberUtils;
import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;

import java.util.Arrays;
import java.util.List;

import static io.ruin.model.entity.player.PlayerCounter.*;

public class ExpertRunecrafter implements AchievementListener {


    private static int get(Player player) {
        List<PlayerCounter> COUNTERS = Arrays.asList(
                CRAFTED_AIR, CRAFTED_ASTRAL, CRAFTED_BLOOD,
                CRAFTED_BODY, CRAFTED_CHAOS, CRAFTED_COSMIC,
                CRAFTED_DEATH, CRAFTED_EARTH, CRAFTED_FIRE,
                CRAFTED_LAW, CRAFTED_MIND, CRAFTED_NATURE,
                CRAFTED_SOUL, CRAFTED_WATER);

        return COUNTERS.stream().mapToInt(c -> c.get(player)).sum();
    }

    @Override
    public String name() {
        return "Master of Runes";
    }

    @Override
    public AchievementStage stage(Player player) {
        int amt = get(player);
        if (amt >= 30000)
            return AchievementStage.FINISHED;
        else if (amt > 0)
            return AchievementStage.STARTED;
        return AchievementStage.NOT_STARTED;
    }

    @Override
    public String[] lines(Player player, boolean finished) {
        return new String[]{
                Achievement.slashIf("A little touch of magic, a whole lot of walking,", finished),
                Achievement.slashIf("and unfathomable patience will see this achievement met.", finished),
                "",
                Achievement.slashIf("<col=000080>Assignment</col>: Craft a total of 30,000 runes.", finished),
                Achievement.slashIf("<col=000080>Reward</col>: Unlocks the giant essence pouch.", finished),
                "",
                "<col=000080>Runes crafted: <col=800000>" + NumberUtils.formatNumber(get(player)),
        };
    }

    @Override
    public void started(Player player) {

    }

    @Override
    public void finished(Player player) {

    }
}
