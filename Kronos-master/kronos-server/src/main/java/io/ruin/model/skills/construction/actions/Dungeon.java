package io.ruin.model.skills.construction.actions;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Door;
import io.ruin.model.skills.construction.Buildable;
import io.ruin.model.skills.construction.ChallengeMode;
import io.ruin.model.skills.construction.House;
import io.ruin.model.skills.construction.RoomDefinition;
import io.ruin.model.stat.StatType;

public class Dungeon {

    static {
        registerDoor(Buildable.OAK_DOOR, 1);
        registerDoor(Buildable.STEEL_PLATED_DOOR, 0.9);
        registerDoor(Buildable.MARBLE_DOOR, 0.75);
    }

    public static void registerDoor(Buildable b, double chanceMod) {
        for (int id : b.getBuiltObjects()) {
            ObjectAction.register(id, "open", (player, obj) -> {
                House house = player.getCurrentHouse();
                if (house != null && (house.getChallengeMode() != ChallengeMode.OFF || player.getCurrentRoom().getDefinition() == RoomDefinition.OUBLIETTE)) {
                    player.sendMessage("The door is locked. Try picking the lock or forcing it open.");
                } else if (!player.isInOwnHouse()) {
                    player.sendMessage("You can't open this door until challenge mode is enabled.");
                } else {
                    Door.handle(player, obj);
                }
            });
            ObjectAction.register(id, "pick-lock", (player, obj) -> {
                House house = player.getCurrentHouse();
                if (house != null && (house.getChallengeMode() != ChallengeMode.OFF || player.getCurrentRoom().getDefinition() == RoomDefinition.OUBLIETTE)) {
                    unlockDoor(player, obj, StatType.Thieving, chanceMod);
                } else if (!player.isInOwnHouse()) {
                    player.sendMessage("You can't open this door until challenge mode is enabled.");
                } else {
                    Door.handle(player, obj);
                }
            });
            ObjectAction.register(id, "force", (player, obj) -> {
                House house = player.getCurrentHouse();
                if (house != null && (house.getChallengeMode() != ChallengeMode.OFF || player.getCurrentRoom().getDefinition() == RoomDefinition.OUBLIETTE)) {
                    unlockDoor(player, obj, StatType.Strength, chanceMod);
                } else if (!player.isInOwnHouse()) {
                    player.sendMessage("You can't open this door until challenge mode is enabled.");
                } else {
                    Door.handle(player, obj);
                }
            });
        }
    }

    public static void unlockDoor(Player player, GameObject door, StatType skill, double chanceMod) {
        if (skill != StatType.Thieving && skill != StatType.Strength)
            throw new IllegalArgumentException();
        boolean pick = skill == StatType.Thieving;
        player.addEvent(event -> {
            player.lock();
            int animation = pick ? 3692 : 3693;
            if (player.get("TENTACLE_POOL") != null)
                animation += 2;
            player.animate(animation);
            player.sendMessage("You attempt to " + (pick ? "pick" : "force") + " the lock...");
            event.delay(2);
            double chance = chance(player.getStats().get(skill).currentLevel);
            if (skill.defaultXpMultiplier > 10)
                chance *= 0.65;
            chance *= chanceMod;
            if (Random.get() <= chance) {
                player.getStats().addXp(skill, 1, false);
                player.sendMessage("You manage to open the door.");
                Door.handle(player, door);
            } else {
                player.sendMessage("You fail to " + (pick ? "pick" : "force") + " the lock.");
            }
            player.unlock();
        });
    }

    private static double chance(int x) {
        double a = 5.0566460247138519E-01;
        double b = 2.1095481906923817E-03;
        double c = 2.6267797214257673E-05;
        return a + b * x + c * Math.pow(x, 2.0);
    }

}
