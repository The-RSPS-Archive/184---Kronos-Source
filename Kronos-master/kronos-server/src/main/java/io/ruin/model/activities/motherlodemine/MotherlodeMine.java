package io.ruin.model.activities.motherlodemine;

import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.Random;
import io.ruin.cache.ItemID;
import io.ruin.model.World;
import io.ruin.model.achievements.Achievement;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.map.*;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.mining.Mining;
import io.ruin.model.skills.mining.Pickaxe;
import io.ruin.model.skills.mining.Rock;
import io.ruin.model.stat.StatType;

import java.util.Optional;

import static io.ruin.model.skills.Tool.HAMMER;

public class MotherlodeMine { //why do we have two motherlode mine classes? Remove the one from skilling and add the actions here


    private static final int MOVING_WATER = 10459;
    private static final int SMALL_WATER_EFFECT = 2018;
    private static final int BIG_WATER_EFFECT = 2016;

    private static final int BROKEN_WHEEL = 26672;
    private static final int WORKING_WHEEL = 26671;

    private static final int BROKEN_STRUT = 26670;
    private static final int WORKING_STRUT = 26669;

    private static final int PAY_DIRT_NPC = 6564;
    private static final int PAY_DIRT = 12011;

    private static final int DARK_TUNNEL = 10047;
    private static final int LADDER = 19044;

    private static final int CRATE = 357;
    public static final Position[] PICKAXE_CRATES = {
            new Position(3753, 5659, 0),
            new Position(3755, 5665, 0),
            new Position(3758, 5671, 0)
    };
    public static final Position[] HAMMER_CRATES = {
            new Position(3752, 5664, 0),
            new Position(3755, 5660, 0),
            new Position(3756, 5668, 0),
            new Position(3752, 5674, 0)
    };


    public static final GameObject WHEEL_1 = Tile.getObject(BROKEN_WHEEL, 3743, 5668, 0);
    public static final GameObject STRUT_1 = Tile.getObject(BROKEN_STRUT, 3742, 5669, 0);

    public static final GameObject WHEEL_2 = Tile.getObject(BROKEN_WHEEL, 3743, 5662, 0);
    public static final GameObject STRUT_2 = Tile.getObject(BROKEN_STRUT, 3742, 5663, 0);

    public static final Bounds[] UPPER_LEVEL_VEINS = {
            new Bounds(3732, 5670, 3763, 5684, 0),
            new Bounds(3763, 5662, 3764, 5666, 0),
            new Bounds(3760, 5655, 3766, 5659, 0),
            new Bounds(3758, 5685, 3758, 5685, 0),
            new Bounds(3764, 5677, 3765, 5677, 0),
    };

    static {
        Region region = Region.get(14936);
        for (int x = 0; x < 64; x++) { // the veins default state is depleted, so we have to restore them all
            for (int y = 0; y < 64; y++) {
                Tile tile = region.getTile(region.baseX + x, region.baseY + y, 0, false);
                if (tile == null)
                    continue;
                GameObject obj = tile.getObject(-1, 0, -1);
                if (obj != null && obj.id >= 26665 && obj.id <= 26668)
                    obj.setId(obj.id - 4);
            }
        }

        ObjectAction.register(26654, "enter", (player, obj) -> tunnel(player, 3728, 5692));
        ObjectAction.register(30374, "enter", (player, obj) -> tunnel(player, 3718, 5678));

        ObjectAction.register(26655, "exit", (player, obj) -> tunnel(player, 3060, 9766));
        ObjectAction.register(30375, "exit", (player, obj) -> {
            int miningLevel = player.getStats().get(StatType.Mining).currentLevel;
            if(miningLevel < 60) {
                player.dialogue(
                        new NPCDialogue(7712, "Sorry, but you're not experienced enough to go in there.").animate(588),
                        new MessageDialogue("You need a Mining level of 60 to access the Mining Guild."));
                return;
            }

            tunnel(player, 3054, 9744);
        });


        for (int i = 26661; i < 26665; i++)
            ObjectAction.register(i, "mine", (p, obj) -> mine(p, obj, isUpperLevel(obj)));

        ObjectAction.register(26688, 1, (p, obj) -> lootSack(p));

        Tile.getObject(19044, 3755, 5673, 0); // TODO ladder

        Tile.getObject(26674, 3748, 5672, 0).skipReachCheck = p -> p.equals(3749, 5672) || p.equals(3748, 5673);
        ObjectAction.register(26674, "deposit", (player, obj) -> addToHopper(player));
        ObjectAction.register(STRUT_1, 1, (p, obj) -> strutInteract(p, obj, WHEEL_1));
        ObjectAction.register(STRUT_2, 1, (p, obj) -> strutInteract(p, obj, WHEEL_2));
        ObjectAction.register(Tile.getObject(357, 3752, 5664, 0), 1, (player, obj) -> {
            if (!player.getInventory().contains(HAMMER, 1)) {
               if (player.getInventory().hasRoomFor(HAMMER)) {
                   player.getInventory().add(HAMMER, 1);
                   player.dialogue(new ItemDialogue().one(HAMMER, "You've found a hammer. How handy."));
               } else {
                   player.dialogue(new ItemDialogue().one(HAMMER, "You find a hammer in the crate, but you don't have enough inventory space to take it."));
               }
            } else {
                player.sendMessage("You search the crate but find nothing.");
            }
        });

        ObjectAction.register(26679, "mine", MotherlodeMine::clearRockfall);
        ObjectAction.register(26680, "mine", MotherlodeMine::clearRockfall);

        ObjectAction.register(DARK_TUNNEL, "enter", (player, obj) -> {
            if(!Achievement.DOWN_IN_THE_DIRT.isFinished(player)) {
                if(obj.x == 3760 && obj.y == 5670) {
                    player.dialogue(
                            new NPCDialogue(6562, "Oi, git yerself out of that hole! It's not safe in there."),
                            new MessageDialogue("You must complete the achievement <col=800000>Down in the Dirt</col> before Percy will let you use that tunnel."));
                } else {
                    player.dialogue(new MessageDialogue("You must complete the achievement <col=800000>Down in the Dirt</col> before Percy will let you use that tunnel."));
                }
            } else {
                if(obj.x == 3760 && obj.y == 5670)
                    tunnel(player, 3765, 5671);
                 else
                   tunnel(player, 3759, 5670);
            }
        });

        Tile.getObject(19044, 3755, 5673, 0).walkTo = new Position(3755, 5674, 0);
        Tile.getObject(19044, 3755, 5673, 0).skipReachCheck = p -> p.equals(3755, 5675) || p.equals(3756, 5674) || p.equals(3755, 5673);
        Tile.get(3755, 5673, 0).clipping = 0;

        ObjectAction.register(LADDER, 1, (player, obj) -> {
            if (player.isAt(3755, 5673)) {
                if (player.getStats().get(StatType.Mining).fixedLevel < 72) {
                    player.dialogue(new NPCDialogue(6562, "Ye'll need level 72 Mining first. An' don't think ye can<br>fool me with yer potions and fancy stat-boosts. Get yer<br>level up for real."));
                } else {
                    player.startEvent(event -> {
                        player.lock();
                        player.face(Direction.NORTH);
                        event.delay(1);
                        player.animate(828);
                        event.delay(1);
                        player.getMovement().teleport(3755, 5675, 0);
                        player.unlock();
                    });
                }
            } else if (player.isAt(3756, 5674)) {
                player.startEvent(event -> {
                    event.delay(1);
                    player.step(0, 1, StepType.FORCE_WALK);
                    event.delay(1);
                    player.step(-1, 0, StepType.FORCE_WALK);
                    event.delay(1);
                    player.face(Direction.SOUTH);
                    event.delay(1);
                    player.animate(832);
                    event.delay(1);
                    player.getMovement().teleport(3755, 5673, 0);
                });
            } else {
                player.startEvent(event -> {
                    player.lock();
                    event.delay(1);
                    player.animate(832);
                    event.delay(1);
                    player.getMovement().teleport(3755, 5673, 0);
                    player.unlock();
                });
            }
        });
    }

    private static final void lootSack(Player player) {
        int inSack = Config.PAY_DIRT_IN_SACK.get(player);
        if (inSack == 0) {
            player.dialogue(new MessageDialogue("The sack is empty."));
            return;
        }
        if (player.getInventory().isFull()){
            player.sendMessage("Not enough space in your inventory.");
            return;
        }
        int lvl = player.getStats().get(StatType.Mining).fixedLevel;
        while (!player.getInventory().isFull() && Config.PAY_DIRT_IN_SACK.get(player) > 0) {
            PaydirtOre ore = PaydirtOre.get(lvl);
            player.getInventory().add(ore.getItemId(), 1);
            player.getStats().addXp(StatType.Mining, ore.getXp(), true);
            Config.PAY_DIRT_IN_SACK.set(player, Config.PAY_DIRT_IN_SACK.get(player) - 1);
            PlayerCounter.CLEANED_PAYDIRT.increment(player, 1);
        }

        if(Config.PAY_DIRT_IN_SACK.get(player) <= 0)
            player.dialogue(new ItemDialogue().one(PAY_DIRT, "You collect your ore from the sack. The sack is now empty."));
        else
            player.dialogue(new ItemDialogue().one(PAY_DIRT, "You collect your ore from the sack. You have " + Config.PAY_DIRT_IN_SACK.get(player) + " ores remaining."));
    }

    public static void tunnel(Player player, int x, int y) {
        player.startEvent(event -> {
            player.lock();
            player.animate(2796);
            event.delay(3);
            player.getMovement().teleport(x, y);
            player.resetAnimation();
            player.unlock();
        });
    }

    private static final boolean isUpperLevel(GameObject obj) {
        for (Bounds b : UPPER_LEVEL_VEINS)
            if (b.inBounds(obj.x, obj.y, obj.z, 0))
                return true;
        return false;
    }

    private static void mine(Player player, GameObject obj, boolean upperLevel) {
        Pickaxe pickaxe = Pickaxe.find(player);
        if (pickaxe == null) {
            player.dialogue(new MessageDialogue("You need a pickaxe to mine this vein. You do not have a pickaxe which " +
                    "you have the Mining level to use."));
            player.privateSound(2277);
            return;
        }
        if (!player.getStats().check(StatType.Mining, 30, "mine this vein")) {
            return;
        }
        if (player.getInventory().isFull()) {
            player.privateSound(2277);
            player.sendMessage("Your inventory is too full to hold any more pay-dirt.");
            return;
        }
        player.startEvent(event -> {
            int attempts = 0;
            while (true) {
                if (player.debug) {
                    double chance = Mining.chance(player.getStats().get(StatType.Mining).currentLevel, Rock.MITHRIL, pickaxe);
                    double dirtPerTick = chance / 2.0;
                    double dirtPerHour = dirtPerTick * 100 * 60;
                    double xpPerTick = dirtPerTick * 60;
                    double xpPerHour = xpPerTick * 100 * 60;
                    player.sendMessage("chance=" + NumberUtils.formatTwoPlaces(chance) + ", xp/tick=" + NumberUtils.formatNumber((long) xpPerTick) + "");
                    player.sendMessage("dirtPerHour=" + NumberUtils.formatTwoPlaces(dirtPerHour) + ", xpPerHour=" + NumberUtils.formatNumber((long) xpPerHour));
                }
                if (obj.id >= 26665) { // depleted
                    player.resetAnimation();
                    return;
                }
                if (upperLevel && !obj.depleting) {
                    obj.depleting = true;
                    World.startEvent(worldEvent -> {
                        worldEvent.delay(Random.get(25, 45));
                        obj.setId(obj.originalId);
                        worldEvent.delay(Random.get(10, 25));
                        obj.setId(obj.id - 4);
                        obj.depleting = false;
                    });
                }
                if(attempts == 0) {
                    player.sendFilteredMessage("You swing your pick at the rock.");
                    player.animate(pickaxe.crystalAnimationID);
                    attempts++;
                } else if (attempts % 2 == 0 && Random.get(100) <= Mining.chance(player.getStats().get(StatType.Mining).currentLevel, Rock.MITHRIL, pickaxe)) {// 300 "difficulty" seems to be a good spot.
                    player.collectResource(new Item(PAY_DIRT, 1));
                    player.getInventory().add(PAY_DIRT, 1);
                    PlayerCounter.MINED_PAYDIRT.increment(player, 1);
                    player.getStats().addXp(StatType.Mining, 60 * Mining.xpBonus(player), true);
                    player.sendMessage("You manage to mine some pay-dirt.");
                    player.resetAnimation();
                    if (!upperLevel && Random.rollDie(3, 1)) {
                        obj.setId(obj.originalId);
                        World.startEvent(worldEvent -> {
                            worldEvent.delay(Random.get(10, 25));
                            obj.setId(obj.id - 4);
                        });
                    }
                }
                if (player.getInventory().isFull()) {
                    player.sendFilteredMessage("Your inventory is too full to continue mining.");
                    return;
                }
                if(attempts++ % 4 == 0)
                    player.animate(pickaxe.crystalAnimationID);
                event.delay(1);
            }
        });
    }

    public static void clearRockfall(Player player, GameObject obj) {
        Pickaxe pick = Pickaxe.find(player);
        if (pick == null) {
            player.dialogue(new MessageDialogue("You need a pickaxe to mine this rockfall. You do not have a pickaxe which " +
                    "you have the Mining level to use."));
            player.privateSound(2277);
            return;
        }
        player.startEvent(event -> {
            player.animate(pick.regularAnimationID);
            event.delay(2);
            player.resetAnimation();
            if (obj.isRemoved()) // someone else mined
                return;
            obj.remove();
            player.getStats().addXp(StatType.Mining, 10, true);
            World.startEvent(worldEvent -> {
                worldEvent.delay(25);
                new Projectile(645, 250, 6, 0, 30, 0, 0, 0).send(obj.x - 1, obj.y + 2, obj.x, obj.y);
                new Projectile(645, 225, 6, 10, 30, 0, 0, 0).send(obj.x - 1, obj.y + 1, obj.x, obj.y);
                obj.graphics(305, 0, 30);
                worldEvent.delay(1);
                obj.restore();
                obj.tile.region.players.forEach(p -> {
                    if (p.isAt(obj.x, obj.y)) {
                        p.hit(new Hit().randDamage(4));
                        p.getRouteFinder().routeSelf();
                        //todo message
                    }
                });
            });
        });
    }

    public static void createMovingWater() {
        GameObject.spawn(SMALL_WATER_EFFECT, 3744, 5672, 0, 10, 2);
        GameObject.spawn(SMALL_WATER_EFFECT, 3748, 5671, 0, 10, 3);
        GameObject.spawn(SMALL_WATER_EFFECT, 3748, 5660, 0, 10, 0);
        GameObject.spawn(SMALL_WATER_EFFECT, 3744, 5660, 0, 10, 0);
        //starting top left corner and going clockwise
        int x = 3743;
        int y = 5672;
        for (int i = 0; i < 5; i++)
            GameObject.spawn(MOVING_WATER, x++, y, 0, 22, 3);
        for (int i = 0; i < 12; i++)
            GameObject.spawn(MOVING_WATER, x, y--, 0, 22, 0);
        for (int i = 0; i < 5; i++)
            GameObject.spawn(MOVING_WATER, x--, y, 0, 22, 1);
        for (int i = 0; i < 12; i++)
            GameObject.spawn(MOVING_WATER, x, y++, 0, 22, 2);
    }

    public static void removeMovingWater() {
        Optional.ofNullable(Tile.getObject(SMALL_WATER_EFFECT, 3744, 5672, 0)).ifPresent(obj -> obj.setId(-1));
        Optional.ofNullable(Tile.getObject(SMALL_WATER_EFFECT, 3748, 5671, 0)).ifPresent(obj -> obj.setId(-1));
        Optional.ofNullable(Tile.getObject(SMALL_WATER_EFFECT, 3748, 5660, 0)).ifPresent(obj -> obj.setId(-1));
        Optional.ofNullable(Tile.getObject(SMALL_WATER_EFFECT, 3744, 5660, 0)).ifPresent(obj -> obj.setId(-1));
        //starting top left corner and going clockwise
        int x = 3743;
        int y = 5672;
        for (int i = 0; i < 5; i++)
            Optional.ofNullable(Tile.getObject(MOVING_WATER, x++, y, 0)).ifPresent(obj -> obj.setId(-1));
        for (int i = 0; i < 12; i++)
            Optional.ofNullable(Tile.getObject(MOVING_WATER, x, y--, 0)).ifPresent(obj -> obj.setId(-1));
        for (int i = 0; i < 5; i++)
            Optional.ofNullable(Tile.getObject(MOVING_WATER, x--, y, 0)).ifPresent(obj -> obj.setId(-1));
        for (int i = 0; i < 12; i++)
            Optional.ofNullable(Tile.getObject(MOVING_WATER, x, y++, 0)).ifPresent(obj -> obj.setId(-1));
    }

    public static void fixWheel(GameObject wheel, GameObject strut) {
        if (wheel.id == WORKING_WHEEL)
            return;
        boolean wasActive = isActive();
        wheel.setId(WORKING_WHEEL);
        strut.setId(WORKING_STRUT);
        GameObject.spawn(BIG_WATER_EFFECT, wheel.x, wheel.y + 3, wheel.z, 10, 1);
        if (isActive() && !wasActive) {
            World.startEvent(event -> {
                event.delay(1);
                createMovingWater();
            });
        }
    }

    public static void breakWheel(GameObject wheel, GameObject strut) {
        if (wheel.id == BROKEN_WHEEL)
            return;
        boolean wasActive = isActive();
        wheel.setId(BROKEN_WHEEL);
        strut.setId(BROKEN_STRUT);
        Optional.ofNullable(Tile.getObject(BIG_WATER_EFFECT, wheel.x, wheel.y + 3, wheel.z, 10, 1)).ifPresent(obj -> obj.setId(-1));
        if (!isActive() && wasActive) {
            World.startEvent(event -> {
                event.delay(1);
                removeMovingWater();
            });
        }
    }

    public static boolean isActive() {
        return WHEEL_1.id == WORKING_WHEEL || WHEEL_2.id == WORKING_WHEEL;
    }

    public static void addToHopper(Player player) {
        if (!player.getInventory().contains(PAY_DIRT, 1)) {
            player.dialogue(new ItemDialogue().one(PAY_DIRT, "You don't have any pay-dirt to put in the hopper."));
            return;
        }
        if (Config.PAY_DIRT_IN_SACK.get(player) >= (player.motherlodeBiggerSackUnlocked ? 162 : 81)) {
            player.sendMessage("The sack cannot hold any more ore.");
            return;
        }

        if(!isActive())
            player.dialogue(new ItemDialogue().one(PAY_DIRT, "The machine will need to be repaired before your pay-dirt can be cleaned."));

        player.startEvent(event -> {
            player.lock();
            player.animate(832);
            event.delay(1);
            int amount = player.getInventory().remove(PAY_DIRT, 28);
            player.paydirtInWater += amount;
            sendPaydirt(player, amount);
            player.unlock();
        });
    }

    private static void sendPaydirt(Player player, int amount) {
        NPC paydirt = new NPC(PAY_DIRT_NPC).spawn(3748, 5671, 0);
        paydirt.startEvent(event -> {
            event.delay(2);
            int steps = 0;
            while (steps < 11) {
                if (!player.isOnline()) {
                    paydirt.remove();
                    return;
                }
                if (isActive()) {
                    steps++;
                    paydirt.step(0, -1, StepType.FORCE_WALK);
                }
                event.delay(1);
            }
            Config.PAY_DIRT_IN_SACK.set(player, Config.PAY_DIRT_IN_SACK.get(player) + amount);
            player.paydirtInWater -= amount;
            paydirt.remove();
            if (Config.PAY_DIRT_IN_SACK.get(player) >= (player.motherlodeBiggerSackUnlocked ? 162 : 81)) {
                player.sendMessage("The sack is getting full.");
            }
        });
    }

    private static void strutInteract(Player player, GameObject strut, GameObject wheel) {
        if (strut.id == WORKING_STRUT)
            return;
        if (!player.getInventory().contains(HAMMER, 1) && !player.getInventory().hasId(ItemID.DRAGON_WARHAMMER)) {
            player.sendMessage("You'll need a hammer to fix it.");
            return;
        }
        player.startEvent(event -> {
            int cycles = 0;
            while (true) {
                /* another player must have fixed it */
                if (strut.id == WORKING_STRUT) {
                    player.resetAnimation();
                    return;
                }
                if (cycles++ % 3 == 0)
                    player.animate(3971, 10);
                if (cycles > 2 && Random.get(6) == 1) {
                    break;
                }
                event.delay(1);
            }
            player.getStats().addXp(StatType.Smithing, player.getStats().get(StatType.Smithing).fixedLevel * 1.5, true);
            fixWheel(wheel, strut);
            /* add event for it to break again later */
            World.startEvent(worldEvent -> {
                worldEvent.delay(100);
                breakWheel(wheel, strut);
            });
        });
    }

    public static void entered(Player player) {
        player.openInterface(InterfaceType.PRIMARY_OVERLAY, 382);
    }

    public static void exited(Player player, boolean logout) {
        if (logout)
            return;
        player.closeInterface(InterfaceType.PRIMARY_OVERLAY);
    }

    static {
        MapListener.registerRegion(14936)
                .onEnter(io.ruin.model.activities.motherlodemine.MotherlodeMine::entered)
                .onExit(io.ruin.model.activities.motherlodemine.MotherlodeMine::exited);
    }

}
