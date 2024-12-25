package io.ruin.model.achievements.listeners.experienced;

import io.ruin.Server;
import io.ruin.api.utils.NumberUtils;
import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.skills.farming.crop.impl.AllotmentCrop;
import io.ruin.model.skills.farming.crop.impl.FlowerCrop;

import java.util.ArrayList;
import java.util.List;

public class NaturesTouch implements AchievementListener {

    private static List<PlayerCounter> counters = new ArrayList<>();

    public void init() {
        Server.afterData.add(() -> {
            for (AllotmentCrop allotment : AllotmentCrop.values()) {
                counters.add(allotment.getCounter());
            }

            for (FlowerCrop flower : FlowerCrop.values()) {
                counters.add(flower.getCounter());
            }
        });
    }

    public static int get(Player player) {
        int total = 0;
        for (PlayerCounter c : counters)
            total += c.get(player);
        return total;
    }

    @Override
    public String name() {
        return "Flower Picker";
    }

    @Override
    public AchievementStage stage(Player player) {
        int amt = get(player);
        if (amt >= 100)
            return AchievementStage.FINISHED;
        else if (amt == 0)
            return AchievementStage.NOT_STARTED;
        else
            return AchievementStage.STARTED;
    }

    @Override
    public String[] lines(Player player, boolean finished) {
        return new String[]{
                Achievement.slashIf("", finished),
                "",
                Achievement.slashIf("<col=000080>Assignment</col>: Harvest 200 items from allotment and flower patches.", finished),
                Achievement.slashIf("<col=000080>Reward</col>: Unlocks the magic secateurs.", finished),
                "",
                "<col=000080>Herbs farmed: <col=800000>" + NumberUtils.formatNumber(get(player)),
        };
    }

    @Override
    public void started(Player player) {

    }

    @Override
    public void finished(Player player) {

    }
}
