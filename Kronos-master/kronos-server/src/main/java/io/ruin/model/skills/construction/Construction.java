package io.ruin.model.skills.construction;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.Tool;
import io.ruin.model.skills.construction.room.Room;
import io.ruin.model.stat.StatType;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Construction {

    /* confiscated items */
    public static final List<Integer> CONFISCATED_ITEMS = Arrays.asList(
            7671, 7673, 7675, 7676, 7679, // combat room
            7688, 7690, 7691, // kettles
            7728, 7702, 7700, 7692, 7694, 7696, 7698, 7730, 7731, // tea stuff
            7732, 7714, 7712, 7704, 7706, 7708, 7710, 7733, 7734, // tea stuff
            7735, 7726, 7724, 7716, 7718, 7720, 7722, 7736, 7737, // tea stuff
            7740, 7752, 7744, 7746, 7748, 7754 // ales
    );

    /* animations */
    public static final int MID_BUILD = 3676;
    public static final int LOW_BUILD = 3683;
    public static final int HIGH_BUILD = 3684;
    public static final int REMOVE_OBJECT = 3685;
    public static final int GROUND_PLANT = 827;
    public static final int MAKE_TABLET = 4067;
    public static final int READ_LECTERN = 3652;

    public static final int SIT = 4103; // back up and sit
    public static final int SIT_DIAGONAL = 4104; // back up and sit, facing diagonally
    public static final int STAND_UP = 4105;
    public static final int STAND_UP_DIAGONAL = 4106;

    /* other ids */
    public static final int OCCUPIED_SEAT = 26232; // 'blank' object that still has clipping, replaces the object a player is seated on



    public static int getMaxRooms(int level) {
        if(level == 99)
            return 33;
        if(level >= 96)
            return 32;
        if(level >= 50)
            return 24 + ((level - 50) / 6);
        return 23;
    }

    public static int getEffectiveLevel(Player player, Buildable toBuild) {
        int level = player.getStats().get(StatType.Construction).currentLevel;
        if (toBuild != null && toBuild.isRequireTools() && player.getInventory().contains(Tool.CRYSTAL_SAW, 1)) {
            level += 3;
        }
        return level;
    }

    public static ObjectAction forCurrentHouse(BiConsumer<Player, House> action) {
        return (p, obj) -> {
            House house = p.getCurrentHouse();
            if (house != null)
                action.accept(p, house);
        };
    }

    public static ObjectAction forCurrentRoom(BiConsumer<Player, Room> action) {
        return (p, obj) -> {
            Room room = p.getCurrentRoom();
            if (room != null)
                action.accept(p, room);
        };
    }

    public static ObjectAction forCurrentRoomElse(BiConsumer<Player, Room> action, Consumer<Player> elseBranch) {
        return (p, obj) -> {
            Room room = p.getCurrentRoom();
            if (room != null)
                action.accept(p, room);
             else
                 elseBranch.accept(p);
        };
    }

    public static ObjectAction forHouseOwnerOnly(BiConsumer<Player, House> action) {
        return (p, obj) -> {
            House house = p.getCurrentHouse();
            if (house != null) {
                if (house.getOwner() != p) {
                    p.dialogue(new MessageDialogue("Only the house owner can do that."));
                    return;
                }
                action.accept(p, house);
            }
        };
    }

    public static void confiscate(Player p) {
        for (int id : CONFISCATED_ITEMS) {
            p.getInventory().remove(id, Integer.MAX_VALUE);
            p.getEquipment().remove(id, Integer.MAX_VALUE);
        }
    }
}
