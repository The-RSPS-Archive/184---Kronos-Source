package io.ruin.model.activities.raids.xeric.chamber.impl;

import io.ruin.model.activities.raids.xeric.ChambersOfXeric;
import io.ruin.model.activities.raids.xeric.chamber.Chamber;
import io.ruin.model.activities.raids.xeric.chamber.ChamberDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Chunk;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.dynamic.DynamicChunk;
import io.ruin.model.map.object.actions.ObjectAction;

import java.util.LinkedList;
import java.util.List;

public class OlmChamber extends Chamber {

    static {
        ObjectAction.register(29879, "pass", (player, obj) -> {
            if (player.getAbsY() > obj.y) {
                player.sendMessage("You cannot pass the barrier from this side.");
                return;
            }
            player.dialogue(new MessageDialogue("This is a one-way passage to the Great Olm's chamber.<br><br>Are you sure you wish to go through?"),
                    new OptionsDialogue(
                            new Option("Yes.", () -> {
                                passBarrier(player);
                            }),
                            new Option("No.")));
        });
        ObjectAction.register(29879, "quick-pass", (player, obj) -> {
            if (player.getAbsY() > obj.y) {
                player.sendMessage("You cannot pass the barrier from this side.");
                return;
            }
            passBarrier(player);
        });
    }

    private static void passBarrier(Player player) {
        player.lock();
        player.addEvent(event -> {
            player.step(0, 2, StepType.FORCE_WALK);
            event.delay(2);
            player.unlock();
        });
    }

    private static final Chunk firstChunk = ChamberDefinition.GREAT_OLM.getBaseChunk(0);
    private static List<DynamicChunk> chunks = null;

    public OlmChamber() {
        //this should only be used when testing with raidroom command
    }

    public OlmChamber(ChambersOfXeric raid) {
        setDefinition(ChamberDefinition.GREAT_OLM);
        setRotation(0);
        setLayout(0);
        setLocation(0,0,0);
        setRaid(raid);
        setBasePosition(new Position(raid.getMap().swRegion.baseX, raid.getMap().swRegion.baseY, 0));
    }

    @Override
    public void onRaidStart() {
        //always start on the east side
        spawnNPC(7554, 38, 42, Direction.WEST, 0); // olm head
    }

    @Override
    public List<DynamicChunk> getChunks() {
        //olm's room has no variations, so override this to use a lazy init singleton pattern
        if (chunks != null)
            return chunks;
        LinkedList<DynamicChunk> list = new LinkedList<>();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                list.add(new DynamicChunk(firstChunk.getX() + x, firstChunk.getY() + y, 0).pos(x, y, 0));
            }
        }
        chunks = list;
        return list;
    }

    @Override
    protected int getTileSize() {
        return 64;
    }

    @Override
    protected int getChunkSize() {
        return 8;
    }
}
