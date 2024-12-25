package io.ruin.model.activities.warriorsguild;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.skillcapes.AttackSkillCape;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.CapePerks;
import io.ruin.model.stat.StatType;

public class WarriorsGuild {

    private enum Doors {
        /* double doors */
        CYCLOPS_NORTH(24309, 24308, 2847, 3541, 2, 1, false),
        CYCLOPS_SOUTH(24306, 24311, 2847, 3540, 2, 3, false),
        MAGICAL_ANIMATOR_EAST(24309, 24308, 2855, 3546, 0, 2, true),
        MAGICAL_ANIMATOR_WEST(24306, 24311, 2854, 3546, 0, 0, true),

        /* single doors */
        ENTRANCE(24318, 24320, 2877, 3546, 0, 1, false),
        CYCLOPS_BASEMENT(10043, 10044, 2911, 9968, 0, 3, false);

        public final int id, replacementId, doorX, doorY, doorZ, direction;
        public final boolean horizontal;

        Doors(int id, int replacementId, int doorX, int doorY, int doorZ, int direction, boolean horizontal) {
            this.id = id;
            this.replacementId = replacementId;
            this.doorX = doorX;
            this.doorY = doorY;
            this.doorZ = doorZ;
            this.direction = direction;
            this.horizontal = horizontal;
        }
    }

    public static final Bounds WARRIORS_GUILD = new Bounds(2837, 3530, 2877, 3557, 0);
    public static final Bounds WARRIORS_GUILD_BASEMENT = new Bounds(2905, 9957, 2940, 9973, 0);



    static {
        /**
         * Doors
         */
        for(Doors door : Doors.values()) {
            ObjectAction.register(door.id, door.doorX, door.doorY, door.doorZ, "open", (player, obj) -> player.startEvent(event -> {
                /* if we're entering the guild, check for level requirements */
                if(door == Doors.ENTRANCE) {
                    int combinedLevel = player.getStats().get(StatType.Attack).fixedLevel + player.getStats().get(StatType.Strength).fixedLevel;
                    if(combinedLevel < 130) {
                        player.dialogue(new MessageDialogue("Your non-boosted <col=000080>Attack</col> and <col=000080>Strength</col> level combined " +
                                "must be equal to or greater than 130 to enter the Warriors Guild."));
                        return;
                    }
                }

                /* if we're entering a cyclops area, check for tokens */
                if((player.getAbsX() < obj.x && door == Doors.CYCLOPS_NORTH) || (player.getAbsX() < obj.x && door == Doors.CYCLOPS_SOUTH) || (player.getAbsX() == obj.x && door == Doors.CYCLOPS_BASEMENT)) {
                    Item tokens = player.getInventory().findItem(8851);
                    if(!AttackSkillCape.wearsAttackCape(player) && (tokens == null || tokens.getAmount() < 100)) {
                        player.dialogue(new ItemDialogue().one(8851, "You don't have enough Warrior Guild Tokens to enter the cyclopes " +
                                "enclosure yet, collect at least 100 then come back."));
                        return;
                    } else {
                        int defender = Cyclops.getNext(player, door == Doors.CYCLOPS_BASEMENT);
                        if(door == Doors.CYCLOPS_BASEMENT && defender != Cyclops.DRAGON_DEFENDER) {
                            player.dialogue(new ItemDialogue().one(Cyclops.RUNE_DEFENDER, "You need a Rune Defender or Dragon Defender to access this area."));
                            return;
                        }
                        player.sendFilteredMessage("<col=804080>Cyclops' are currently dropping " + ItemDef.get(defender).name + "s.");
                    }
                }

                player.lock();

                if(door.horizontal) {
                    if(!player.isAt(door.doorX, player.getAbsY() >= door.doorY ? door.doorY : door.doorY - 1)) {
                        player.stepAbs(door.doorX, player.getAbsY() >= door.doorY ? door.doorY : door.doorY - 1, StepType.FORCE_WALK);
                        event.delay(1);
                    }
                } else {
                    if(!player.isAt(door == Doors.CYCLOPS_BASEMENT ? player.getAbsX() <= door.doorX ? door.doorX : door.doorX + 1 :
                            player.getAbsX() >= door.doorX ? door.doorX : door.doorX - 1, door.doorY)) {
                        player.stepAbs(player.getAbsX() >= door.doorX ? door.doorX : door.doorX - 1, door.doorY, StepType.FORCE_WALK);
                        event.delay(1);
                    }
                }
                GameObject opened = GameObject.spawn(door.replacementId, door == Doors.CYCLOPS_BASEMENT ? door.doorX + 1 : door.horizontal ? door.doorX :
                        door.doorX - 1, door.horizontal ? door.doorY - 1 : door.doorY, door.doorZ, obj.type, door.direction);
                obj.skipClipping(true).remove();
                if(door == Doors.CYCLOPS_BASEMENT)
                    player.step(player.getAbsX() == door.doorX ? 1 : -1, 0, StepType.FORCE_WALK);
                else if(door.horizontal)
                    player.step(0, player.getAbsY() == door.doorY ? -1 : 1, StepType.FORCE_WALK);
                else
                    player.step(player.getAbsX() == door.doorX ? -1 : 1, 0, StepType.FORCE_WALK);
                event.delay(2);
                obj.restore().skipClipping(false);
                opened.remove();

                player.unlock();
            }));
        }

        /**
         * Staircases
         */
        ObjectAction.register(16671, 2839, 3537, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2840, 3539, 2));
        ObjectAction.register(24303, 2840, 3538, 2, "climb-down", (player, obj) -> player.getMovement().teleport(2841, 3538, 0));

        /**
         * Ladder
         */
        ObjectAction.register(10042, 2833, 3542, 0, "climb-down", (player, obj) -> player.getMovement().teleport(2907, 9968, 0));
        ObjectAction.register(9742, 2906, 9968, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2834, 3542, 0));
    }

}
