package io.ruin.model.activities.raids.tob.dungeon.room;

import com.google.common.collect.Lists;
import io.ruin.cache.Color;
import io.ruin.cache.NpcID;
import io.ruin.model.activities.raids.tob.dungeon.RoomType;
import io.ruin.model.activities.raids.tob.dungeon.boss.TheMaidenOfSugadinti;
import io.ruin.model.activities.raids.tob.party.TheatreParty;
import io.ruin.model.activities.raids.tob.party.TheatrePartyManager;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

import java.util.List;

/**
 * @author ReverendDread on 7/24/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class MaidenRoom extends TheatreRoom {

    public MaidenRoom(TheatreParty party) {
        super(party);
    }

    @Override
    public void onLoad() {
        buildSw(12613, 1);
        buildSe(12869, 1);
        GameObject.spawn(32756, convertX(3192), convertY(4448), 0, 11, 4);
        boss = new TheMaidenOfSugadinti(NpcID.THE_MAIDEN_OF_SUGADINTI, party);
        boss.spawn(convertX(3162), convertY(4444), 0, Direction.EAST, 0);
        boss.getCombat().setAllowRespawn(false);
        boss.postEffects();
    }

    @Override
    public void registerObjects() {
        for (int y = 4445; y < 4449; y++) {
            ObjectAction.register(32755, convertX(3185), convertY(y), 0, "pass", (player, obj) -> {
                boolean west = player.getAbsX() > convertX(3185);
                Direction dir = west ? Direction.WEST : Direction.EAST;
                TheatrePartyManager.instance().getPartyForPlayer(player.getUserId()).ifPresent(party -> {
                    if (party.getDungeon().isStarted(RoomType.MAIDEN)) {
                        if (dir == Direction.EAST && !boss.dead()) {
                            player.sendMessage("You can't leave in the middle of a fight.");
                        } else {
                            moveIntoRoom(player, dir);
                        }
                    } else {
                        if (party.isLeader(player)) {
                            player.dialogue(new OptionsDialogue(Color.MAROON.wrap("Is your party ready to fight?"),
                                new Option("Yes, let's begin.", () -> {
                                    moveIntoRoom(player, dir);
                                }),
                                new Option("No, don't start yet.")
                            ));
                        } else {
                            player.dialogue(new MessageDialogue("You need to wait for the party leader to start the fight."));
                        }
                    }
                });
            });
        }
        ObjectAction.register(33113, convertX(3177), convertY(4422), 0, "enter", ((player, obj) -> {
            if (boss.dead()) {
                TheatrePartyManager.instance().getPartyForPlayer(player.getUserId()).ifPresent(party ->
                        party.getDungeon().enterRoom(player, RoomType.BLOAT));
            } else {
                player.sendMessage("You can't proceed until the challenge is complete.");
            }
        }));
        ObjectAction.register(33113, convertX(3177), convertY(4422), 0, "quick-enter", ((player, obj) -> {
            if (boss.dead()) {
                TheatrePartyManager.instance().getPartyForPlayer(player.getUserId()).ifPresent(party ->
                        party.getDungeon().enterRoom(player, RoomType.BLOAT));
            } else {
                player.sendMessage("You can't proceed until the challenge is complete.");
            }
        }));
    }

    @Override
    public List<Position> getSpectatorSpots() {
        return Lists.newArrayList(
            Position.of(convertX(3167), convertY(4460)),
            Position.of(convertX(3166), convertY(4460)),
            Position.of(convertX(3166), convertY(4433)),
            Position.of(convertX(3167), convertY(4433))
        );
    }

    @Override
    public Position getEntrance() {
        return Position.of(3219, 4458);
    }

    public void moveIntoRoom(Player player, Direction direction) {
        int x = player.getAbsX();
        player.stepAbs(direction == Direction.WEST ? x - 2 : x + 2, player.getAbsY(), StepType.FORCE_WALK);
        player.deathEndListener = (entity, killer, killHit) -> {
            Config.THEATRE_OF_BLOOD.set(player, 3); //set to spectator
            //TODO finish this, so when party is all dead they lose the dungeon
            handlePlayerDeath(player);
        };
    }

}
