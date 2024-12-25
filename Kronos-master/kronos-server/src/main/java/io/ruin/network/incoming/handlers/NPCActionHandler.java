package io.ruin.network.incoming.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.cache.NPCDef;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.map.route.routes.TargetRoute;
import io.ruin.network.incoming.Incoming;
import io.ruin.utility.DebugMessage;
import io.ruin.utility.IdHolder;

import java.util.Arrays;
import java.util.HashSet;

@IdHolder(ids = {89, 96, 35, 79, 37, 9})
public class NPCActionHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        if(player.isLocked())
            return;
        player.resetActions(true, true, true);

        int option = OPTIONS[opcode];
        if(option == 1) {
            int ctrlRun = in.readByteS();
            int targetIndex = in.readLEShort();
            handleAction(player, option, targetIndex, ctrlRun);
            return;
        }
        if(option == 2) {
            int ctrlRun = in.readByteS();
            int targetIndex = in.readShortA();
            handleAction(player, option, targetIndex, ctrlRun);
            return;
        }
        if(option == 3) {
            int ctrlRun = in.readByte();
            int targetIndex = in.readLEShort();
            handleAction(player, option, targetIndex, ctrlRun);
            return;
        }
        if(option == 4) {
            int ctrlRun = in.readByte();
            int targetIndex = in.readLEShort();
            handleAction(player, option, targetIndex, ctrlRun);
            return;
        }
        if(option == 5) {
            int ctrlRun = in.readByteS();
            int targetIndex = in.readLEShort();
            handleAction(player, option, targetIndex, ctrlRun);
            return;
        }
        if(option == 6) {
            int id = in.readShortA();
            NPCDef def = NPCDef.get(id);
            if(def == null)
                return;
            player.sendMessage(def.name);
            if(player.debug)
                debug(player, null, def, -1);
            return;
        }
        player.sendFilteredMessage("Unhandled npc action: option=" + option + " opcode=" + opcode);
    }

    private static void handleAction(Player player, int option, int npcIndex, int ctrlRun) {
        NPC npc = World.getNpc(npcIndex);
        if(npc == null)
            return;
        NPCDef def = npc.getDef();
        if(player.debug)
            debug(player, npc, def, option);
        player.face(npc);
        player.getMovement().setCtrlRun(ctrlRun == 1);
        if(option == def.attackOption) {
            player.getCombat().setTarget(npc);
            return;
        }
        if(npc.skipMovementCheck) {
            player.face(npc);
            int i = option - 1;
            if(i < 0 || i >= 5)
                return;
            NPCAction action = null;
            NPCAction[] actions = npc.actions;
            if(actions != null)
                action = actions[i];
            if(action == null && (actions = def.defaultActions) != null)
                action = actions[i];
            if(action != null) {
                action.handle(player, npc);
                return;
            }
            return;
        }
        TargetRoute.set(player, npc, () -> {
            int i = option - 1;
            if(i < 0 || i >= 5)
                return;
            NPCAction action = null;
            NPCAction[] actions = npc.actions;
            if(actions != null)
                action = actions[i];
            if(def.cryptic != null && def.cryptic.advance(player))
                return;
            if(def.anagram != null && def.anagram.advance(player))
                return;
            if(action == null && (actions = def.defaultActions) != null)
                action = actions[i];
            if(action != null) {
                action.handle(player, npc);
                player.face(npc);
                return;
            }
            /* default to a dialogue */
            player.dialogue(
                    new NPCDialogue(npc, "Beautiful day today, isn't it?").onDialogueOpened(() -> npc.faceTemp(player)),
                    new PlayerDialogue("Uhh.. yeah I guess.")
            );
        });
    }

    private static void debug(Player player, NPC npc, NPCDef def, int option) {
        HashSet<Integer> showIds = new HashSet<>();
        if(def.showIds != null) {
            for(int id : def.showIds)
                showIds.add(id);
            showIds.remove(-1);
        }
        DebugMessage debug = new DebugMessage();
        if(option != -1)
            debug.add("option", option);
        debug.add("id", def.id + (showIds.isEmpty() ? "" : (" " + showIds.toString())));
        debug.add("name", def.name);
        if(npc != null) {
            debug.add("index", npc.getIndex());
            debug.add("x", npc.getAbsX());
            debug.add("y", npc.getAbsY());
            debug.add("z", npc.getHeight());
        }
        debug.add("options", Arrays.toString(def.options));
        debug.add("varpbitId", def.varpbitId);
        debug.add("varpId", def.varpId);
        if (def.varpbitId != -1 || def.varpId != -1)
            debug.add("variants", Arrays.toString(def.showIds));
        player.sendFilteredMessage("[NpcAction] " + debug.toString());
    }

}