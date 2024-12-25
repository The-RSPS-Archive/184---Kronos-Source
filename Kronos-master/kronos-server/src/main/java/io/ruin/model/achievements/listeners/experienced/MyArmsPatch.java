package io.ruin.model.achievements.listeners.experienced;

import io.ruin.Server;
import io.ruin.api.utils.NumberUtils;
import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.skills.farming.crop.impl.HerbCrop;

import java.util.ArrayList;
import java.util.List;

public class MyArmsPatch implements AchievementListener {

    private static List<PlayerCounter> counters = new ArrayList<>();

    public void init() {
        Server.afterData.add(() -> {
            for (HerbCrop herbCrop : HerbCrop.values()) {
                counters.add(herbCrop.getCounter());
            }
        });

        NPCAction.register(740, 1, (player, npc) -> {
            if (npc.getAbsX() > 2830) {
                if (!Achievement.MY_ARMS_PATCH.isFinished(player)) {
                    player.dialogue(new NPCDialogue(742, "Who dis? Human not good farmer."), new MessageDialogue("You must complete the " + Achievement.MY_ARMS_PATCH.name() + " achievement before My Arm will allow you to use his farming patch."));
                } else {
                    player.dialogue(new NPCDialogue(742, "Hello human. Want to go to herb patch?"),
                            new OptionsDialogue(new Option("Yes, please.", () -> player.startEvent(event -> {
                                player.lock();
                                player.getPacketSender().fadeOut();
                                event.delay(2);
                                player.getMovement().teleport(2831, 3678, 0);
                                event.delay(2);
                                player.getPacketSender().fadeIn();
                                player.unlock();
                                player.dialogue(new MessageDialogue("My Arm leads you through the stronghold."));
                            })),
                                    new Option("Not right now.", () -> {
                                    })));
                }
            } else {
                player.dialogue(new NPCDialogue(npc, "We already here human. Go farm."));
            }
        });
    }

    public static int get(Player player) {
        return counters.stream().mapToInt(counter -> counter.get(player)).sum();
    }


    @Override
    public String name() {
        return "My Arm's patch";
    }

    @Override
    public AchievementStage stage(Player player) {
        int amt = get(player);
        if (amt >= 200)
            return AchievementStage.FINISHED;
        else if (amt == 0)
            return AchievementStage.NOT_STARTED;
        return AchievementStage.STARTED;
    }

    @Override
    public String[] lines(Player player, boolean finished) {
        return new String[]{
                Achievement.slashIf("<col=000080>Assignment</col>: Harvest 200 herbs from farming patches.", finished),
                Achievement.slashIf("<col=000080>Reward</col>: My Arm will allow you to use his herb patch.", finished),
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
