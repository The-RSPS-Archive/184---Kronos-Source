package io.ruin.model.entity.npc.actions.tzhaar;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.Pet;

public class TzhaarMejJal {

    private static final int TZHAAR_MAJ_JAL = 2180;

    private static void startDialogue(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc, "You want help JalYt-Ket-" + player.getName() + "?").animate(592),
                new OptionsDialogue(
                        new Option("What is this place?", () -> whatIsThisPlace(player, npc)),
                        new Option("No I'm fine, thanks.", () -> imFineThanks(player))
                )
        );
    }

    private static void whatIsThisPlace(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("What is this place?"),
                new NPCDialogue(npc, "This is the fight cave, TzHaar-Xil made it for practice,<br>but many JalYt come here to fight too.<br>" +
                        "Just enter the cave and make sure you're prepared."),
                new OptionsDialogue(
                        new Option("Are there any rules?", () -> player.dialogue(
                                new PlayerDialogue("Are there any rules?"),
                                new NPCDialogue(npc, "Rules? Survival is the only rule in there."),
                                new OptionsDialogue(
                                        new Option("Do I win anything?", () -> player.dialogue(
                                                new PlayerDialogue("Do I win anything?"),
                                                new NPCDialogue(npc, "You ask a lot of questions.<br>Might give you TokKul if you last long enough."),
                                                new PlayerDialogue("..."),
                                                new NPCDialogue(npc, "Before you ask, TokKul is like your Coins."),
                                                new NPCDialogue(npc, "Gold is like you JalYt, soft and easily broken, we use<br>hard rock forged in fire like TzHaar!")
                                        )),
                                        new Option("Sounds good.", () -> player.dialogue(new PlayerDialogue("Sounds good.")))
                                )
                        )),
                        new Option("Ok thanks.", () -> player.dialogue(new PlayerDialogue("Ok thanks.")))
                )
        );
    }

    private static void imFineThanks(Player player) {
        player.dialogue(new PlayerDialogue("No I'm fine thanks."));
    }

    private static void bargain(Player player, NPC npc) {
        if(player.getInventory().hasId(Pet.TZREK_JAD.itemId) || player.getBank().hasId(Pet.TZREK_JAD.itemId) || (player.pet != null && player.pet == Pet.TZREK_JAD)) {
            player.dialogue(new NPCDialogue(npc, "You already have TzRek-Jad!"));
            return;
        }
        Item fireCape = player.getInventory().findItem(6570);
        if(fireCape == null) {
            player.dialogue(new NPCDialogue(npc, "You no have cape!"));
            return;
        }
        player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "Sacrifice your firecape for a chance at TzRek-Jad?", fireCape, () -> {
            fireCape.remove();
            if (Random.rollDie(100, 1)) {
                Pet.TZREK_JAD.unlock(player);
                player.dialogue(new NPCDialogue(npc, "You lucky. Better train him good else TzTok-Jad find you, JalYt."));
            } else {
                player.dialogue(new NPCDialogue(npc, "You not lucky. Maybe next time, JalYt."));
            }
        }));
    }

    static {
        NPCAction.register(TZHAAR_MAJ_JAL, "talk to", TzhaarMejJal::startDialogue);
        NPCAction.register(TZHAAR_MAJ_JAL, "exchange fire cape", (player, npc) -> {
            Item fireCape = player.getInventory().findItem(6570);
            if (fireCape == null) {
                startDialogue(player, npc);
                return;
            }

            player.dialogue(
                    new PlayerDialogue("I have a fire cape here."),
                    new OptionsDialogue(
                            new Option("Yes, sell it for 8,000 TokKul.", () -> {
                                player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "Sacrifice your fire cape for 8,000 TokKul?", fireCape, () -> {
                                    fireCape.remove();
                                    player.getInventory().add(6529, 8000);
                                    player.dialogue(new NPCDialogue(TZHAAR_MAJ_JAL, "Here TokKul. Thanks for cape."));
                                }));
                            }),
                            new Option("No, keep it.", player::closeDialogue),
                            new Option("Bargain for TzRek-Jad.", () -> bargain(player, npc))
                    )
            );
        });
    }

}
