package io.ruin.model.skills;

import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.map.route.routes.DumbRoute;
import io.ruin.services.Punishment;

import static io.ruin.cache.ItemID.COINS_995;

public class BotPrevention {

    private static final int PILLORY_GUARD = 380;

    public static void spawn(Player player) {
        NPC npc = new NPC(PILLORY_GUARD);
        player.botPreventionNPC = npc;
        player.botPreventionJailDelay.delaySeconds(120);
        player.botPreventionNpcShoutDelay.delaySeconds(30);
        player.sendMessage(Color.COOL_BLUE.wrap("A guard has suspicion you've been stealing!"));
        npc.ownerId = player.getUserId();
        npc.spawn(player.getAbsX(), player.getAbsY(), player.getHeight());
        npc.face(player);
        npc.forceText(player.getName() + " stop right there!");
        npc.addEvent(e -> {
            while (player.isOnline()) {
                npc.face(player);
                if (player.getCombat().isDead() || player.getMovement().isTeleportQueued()) {
                    e.delay(1);
                    continue;
                }
                if(!player.botPreventionNpcShoutDelay.isDelayed()) {
                    int seconds = player.botPreventionJailDelay.remaining() / 10 * 6;
                    if(seconds <= 40)
                        npc.forceText("This is your last chance " + player.getName() + "!");
                        else
                        npc.forceText("Hey " + player.getName() + ", I'm talking to you!");
                    player.botPreventionNpcShoutDelay.delaySeconds(30);
                    e.delay(1);
                    continue;
                }
                if(!player.botPreventionJailDelay.isDelayed()) {
                    npc.forceText("You're coming with me, " + player.getName() + ".");
                    npc.lock();
                    player.lock();
                    npc.animate(864);
                    player.face(npc);
                    resetBlock(player);
                    e.delay(3);
                    World.sendGraphics(86, 60, 0, npc.getPosition());
                    World.sendGraphics(86, 60, 0, player.getPosition());
                    Punishment.jail(player, npc, 250);
                    e.delay(1);
                    npc.remove();
                    player.unlock();
                    break;
                }
                if(player.dismissBotPreventionNPC) {
                    npc.lock();
                    npc.animate(863);
                    resetBlock(player);
                    e.delay(3);
                    World.sendGraphics(86, 60, 0, npc.getPosition());
                    int randomGold = Random.get(10000, 25000);
                    player.getInventory().addOrDrop(COINS_995, randomGold);
                    player.sendMessage(Color.COOL_BLUE.wrap("The guard hands you " + NumberUtils.formatNumber(randomGold) + " gold coins for the trouble."));
                    e.delay(1);
                    npc.remove();
                    break;
                }
                if (!npc.getPosition().isWithinDistance(player.getPosition(), 14)) {
                    player.dismissBotPreventionNPC = true;
                    e.delay(1);
                    continue;
                }
                if (!npc.getPosition().isWithinDistance(player.getPosition(), 7)) {
                    npc.getMovement().teleport(player.getAbsX(), player.getAbsY(), player.getHeight());
                    e.delay(1);
                    continue;
                }
                int destX, destY;
                if (player.getMovement().hasMoved()) {
                    destX = player.getMovement().lastFollowX;
                    destY = player.getMovement().lastFollowY;
                } else {
                    destX = player.getMovement().followX;
                    destY = player.getMovement().followY;
                }
                if(player.getPosition() == npc.getPosition())
                    DumbRoute.step(npc, player, 1);
                else if (destX == -1 || destY == -1)
                    DumbRoute.step(npc, player, 1);
                else if (!npc.isAt(destX, destY))
                    DumbRoute.step(npc, destX, destY);
                e.delay(1);
            }
            npc.remove();
            resetBlock(player);
        });
    }

    public static void attemptBlock(Player player) {
        if(Random.rollDie(300, 1)) {
            if(player.botPreventionNPC == null)
                spawn(player);
        }
    }

    private static void resetBlock(Player player) {
        player.botPreventionNPC = null;
        player.dismissBotPreventionNPC = false;
        player.botPreventionJailDelay.reset();
        player.botPreventionNpcShoutDelay.reset();
    }

    public static boolean isBlocked(Player player) {
        return player.botPreventionNPC != null;
    }

    static {
        NPCAction.register(PILLORY_GUARD, "talk-to", (player, npc) -> {
            if(npc.ownerId != player.getUserId()) {
                player.dialogue(new NPCDialogue(PILLORY_GUARD, "I'm not interested in talking with you " + player.getName() + "."));
            } else {
                player.dialogue(new NPCDialogue(PILLORY_GUARD, "I've gotten reports that you've been stealing, "
                                + player.getName() + ". Is that true?"),
                        new PlayerDialogue("That doesn't sound like something I'd do!"),
                        new NPCDialogue(PILLORY_GUARD, "Very well.. carry on."),
                        new ActionDialogue(() -> {
                            if(player.botPreventionNPC != null)
                                player.dismissBotPreventionNPC = true;
                        }));
            }
        });
        NPCAction.register(PILLORY_GUARD, "dismiss", ((player, npc) ->  {
            if(npc.ownerId != player.getUserId()) {
                player.dialogue(new NPCDialogue(PILLORY_GUARD, "You're no use to me, " + player.getName() + "."));
                return;
            }
            player.dismissBotPreventionNPC = true;
        }));
    }
}
