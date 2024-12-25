package io.ruin.model.achievements.listeners.novice;

import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.skills.farming.patch.PatchData;

import java.util.Arrays;
import java.util.List;

public class MorytaniaFarming implements AchievementListener {

    public void init() {
        ItemAction.registerInventory(4251, "empty", (player, item) -> {
            player.getMovement().startTeleport(event -> {
                player.animate(878);
                player.graphics(1273);
                event.delay(3);
                player.getMovement().teleport(3603, 3532, 0);
                event.delay(1);
            });
        });
    }

    @Override
    public String name() {
        return "Morytania Farming";
    }

    private List<PatchData> morytaniaPatches = Arrays.asList(
            PatchData.CANIFIS_NORTH, PatchData.CANIFIS_SOUTH,
            PatchData.CANIFIS_FLOWER, PatchData.CANIFIS_HERB,
            PatchData.CANIFIS_MUSHROOM);

    public void check(Player player, PatchData patch) {
        if (stage(player) != AchievementStage.FINISHED && morytaniaPatches.contains(patch)) {
            player.morytaniaFarmAchievement = true;
            finished(player);
        }
    }

    @Override
    public AchievementStage stage(Player player) {
        return player.morytaniaFarmAchievement ? AchievementStage.FINISHED : AchievementStage.NOT_STARTED;
    }

    @Override
    public String[] lines(Player player, boolean finished) {
        return new String[]{
                Achievement.slashIf("", finished), //TODO: Event message
                "",
                Achievement.slashIf("<col=000080>Assignment</col>: Harvest from any farming patch in Morytania.", finished),
                Achievement.slashIf("<col=000080>Reward</col>: Unlocks the ectophial.", finished),
        };
    }

    @Override
    public void started(Player player) {

    }

    @Override
    public void finished(Player player) {

    }
}
