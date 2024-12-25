package io.ruin.model.activities.raids.xeric;

import io.ruin.cache.Color;
import io.ruin.model.activities.raids.xeric.chamber.Chamber;
import io.ruin.model.activities.raids.xeric.chamber.ChamberDefinition;
import io.ruin.model.activities.raids.xeric.chamber.ChamberType;
import io.ruin.model.activities.raids.xeric.chamber.impl.CheckpointChamber;
import io.ruin.model.activities.raids.xeric.chamber.impl.OlmChamber;
import io.ruin.model.activities.raids.xeric.party.Party;
import io.ruin.model.activities.raids.xeric.party.RecruitingBoard;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.handlers.TabJournal;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.dynamic.DynamicChunk;
import io.ruin.model.map.dynamic.DynamicMap;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;
import io.ruin.services.Loggers;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import static io.ruin.model.activities.raids.xeric.chamber.ChamberDefinition.*;

/**
 * @author Edu
 */
public class ChambersOfXeric {

    static {

        ObjectAction.register(30066, 1, (player, obj) -> { // energy well
            if (player.raidsParty == null || player.raidsParty.getRaid() == null)
                return;
            player.getMovement().restoreEnergy(100);
        });
        ObjectAction.register(29734, 1, (player, obj) -> { // hole to lower level
            if (player.raidsParty == null || player.raidsParty.getRaid() == null)
                return;
            player.raidsParty.getRaid().enterLowerFloor(player);
        });

        ObjectAction.register(29995, 1, (player, obj) -> {
            if (player.raidsParty == null || player.raidsParty.getRaid() == null)
                return;
            player.raidsParty.getRaid().leaveLowerFloor(player);
        });

        ObjectAction.register(29735, 1, (player, obj) -> {
            if (player.raidsParty == null || player.raidsParty.getRaid() == null)

                return;
            player.raidsParty.getRaid().enterOlmFloor(player);
        });

        ObjectAction.register(29996, 1, (player, obj) -> {
            if (player.raidsParty == null || player.raidsParty.getRaid() == null)
                return;
            player.raidsParty.getRaid().leaveOlmFloor(player);
        });

        ObjectAction.register(29789, 1, (player, obj) -> {
            if(player.raidsParty != null && Config.RAIDS_STAGE.get(player) < Party.EXPLORING_UPPER_LEVEL) {
                if(player.raidsParty.getLeader() == player)
                    player.dialogue(new MessageDialogue("Use the controls on the <col=6f0000>side-panel</col><br>when you are ready to begin the raid."));
                else
                    player.dialogue(new MessageDialogue("Your party leader hasn't started the raid."));
                return;
            }
            int x = player.getAbsX();
            int y = player.getAbsY();
            if (obj.direction == 0 || obj.direction == 2) {
                if (x < obj.x)
                    x += 4;
                else
                    x -= 4;
            } else {
                if (y < obj.y)
                    y += 4;
                else
                    y -= 4;
            }
            player.getMovement().teleport(x, y, player.getHeight());
        });
        /**
         * Chambers entrance
         */
        ObjectAction.register(29777, 1, (player, obj) -> {
            if(player.raidsParty == null) {
                player.dialogue(new MessageDialogue("You must be in a party to go on a raid."),
                        new OptionsDialogue("Would you like to create a party now?",
                                new Option("Create a party.", () -> {
                                    RecruitingBoard.createParty(player, false);
                                    player.dialogue(new MessageDialogue("Your party has been created."));
                                }),
                                new Option("Cancel.")));
                return;
            }
            if(!player.raidsEntranceWarning) {
                player.dialogue(
                        new MessageDialogue("WARNING!<br>" +
                                "Items left in a dungeon <col=6f0000>cannot</col> be retrieved.<br>" +
                                "Don't risk dropping items that you want to keep."),
                        new OptionsDialogue(
                                new Option("Accept warning and proceed.", () -> {
                                    player.raidsEntranceWarning = true;
                                    createChamber(player, player.raidsParty);
                                }),
                                new Option("Cancel.", player::closeDialogue)
                        ));
            } else {
                createChamber(player, player.raidsParty);
            }
        });
        /**
         * Raids exit
         */
        ObjectAction.register(29778, 1, (player, obj) -> {
            if (isRaiding(player) && player.raidsParty.getRaid().isComplete()) {
                leaveRaid(player);
            } else {
                player.dialogue(new OptionsDialogue(isRaiding(player) && player.raidsParty.getRaid().isStarted() ?
                        "The raid has already begun, you will not be able to re-enter." : "If the raid begins, you won't be able to re-enter.",
                        new Option("Leave.", () -> leaveRaid(player)),
                        new Option("Stay.", player::closeDialogue)
                ));
            }
        });
    }

    private static void leaveRaid(Player player) {
        player.startEvent(event -> {
            player.lock();
            player.getPacketSender().fadeOut();
            Party party = player.raidsParty;
            if(party != null && Config.RAIDS_STAGE.get(player) >= Party.EXPLORING_UPPER_LEVEL) {
                Player leader = party.getLeader();
                if (leader == player) {
                    if (party.getMembers().isEmpty()) {
                        RecruitingBoard.disbandParty(player);
                    } else {
                        Player newLeader = party.getMembers().get(party.getMembers().size() - 1);
                        party.setLeader(newLeader);
                        party.removeMember(player);
                        player.raidsParty = null;
                        RecruitingBoard.resetPartyPreferences(player);
                        Config.RAIDS_PARTY.set(player, -1);
                        party.getMembers().forEach(member -> member.sendMessage(player.getName() + " has left the party. " + newLeader.getName() + " is the new raid leader."));
                        player.sendMessage("You leave your raiding party.");
                    }
                } else {
                    RecruitingBoard.leaveParty(player);
                }
            }
            event.delay(2);
            Config.RAIDS_PARTY.set(player, -1);
            player.getMovement().teleport(1234, 3572, 0);
            Config.RAIDS_TAB_ICON.set(player, -1);
            Party.updatePartyStage(player, -1);
            //TabJournal.restore(player);
            player.getPacketSender().fadeIn();
            event.delay(1);
            player.unlock();
        });
    }

    public static int getPartySize(NPC npc) {
        Chamber chamber = npc.get("RAID_CHAMBER");
        if (chamber == null)
            return 1;
        return chamber.getRaid().getPartySize();
    }

    private void enterLowerFloor(Player player) {
        player.getMovement().teleport(getLowerStartingChamber().getEntrancePosition());
        if (raidStage < 2) {
            raidStage = 2;
            String time = getTimeSinceStart();
            party.forPlayers(p -> p.sendMessage(Color.RAID_PURPLE.wrap("Upper level complete! Duration: " + Color.RED.wrap(time) + ".")));
            Party.updatePartyStage(player, Party.REACHED_LOWER_LEVEL);
        }
    }

    private String getTimeSinceStart() {
        Duration d = Duration.between(startTime, Instant.now());
        return String.format("%02d:%02d", d.toMinutes(), d.getSeconds() % 60);
    }

    private void leaveLowerFloor(Player player) {
        player.getMovement().teleport(getUpperFinishChamber().getEntrancePosition());
    }

    private void enterOlmFloor(Player player) {
        player.getMovement().teleport(map.swRegion.baseX + 32, map.swRegion.baseY + 25, 0);
        if (raidStage < 3) {
            raidStage = 3;
            String time = getTimeSinceStart();
            party.forPlayers(p -> p.sendMessage(Color.RAID_PURPLE.wrap("Lower level complete! Duration: " + Color.RED.wrap(time) + ".")));
            Party.updatePartyStage(player, Party.REACHED_BOTTOM_LEVEL);
        }
    }

    private void leaveOlmFloor(Player player) {
        player.getMovement().teleport(getLowerFinishChamber().getEntrancePosition());
    }

    private Position getRespawnPosition() {
        if (raidStage < 2)
            return getStartingChamber().getRespawnPosition();
        else if (raidStage < 3)
            return getLowerStartingChamber().getRespawnPosition();
        else
            return new Position(map.swRegion.baseX + 32, map.swRegion.baseY + 25, 0);
    }

    private ChambersOfXeric(long seed) {
        this.seed = seed;
        this.rng = new Random(seed);
    }

    public ChambersOfXeric() {
        this(new Random().nextLong());
    }

    public ChambersOfXeric(String seed) {
        this(seed.hashCode());
    }


    /*
     * fields
     */

    private Instant startTime = Instant.now();
    private int raidStage = 0;

    private Party party;

    private double combatPointsFactor = 1;

    private RaidStorage storage = new RaidStorage(this);

    private List<GameObject> storageObjects = new ArrayList<>();

    private int storageLevel = 0; // 0 = not built, 1 = small, 2 = medium, 3 = large

    /*
     * Raid generation code
     */
    private static final ChamberDefinition[] COMBAT_ROOMS = {/*VESPULA,*/ TEKTON, VASA_NISTIRIO, GUARDIANS, SKELETAL_MYSTICS, LIZARDMAN_SHAMANS, MUTTADILES, VANGUARDS}; // order is important here!!
    private static final int X_LENGTH = 4;
    private static final int Y_LENGTH = 2;

    private static final double SIXTH_ROOM_CHANCE = 0.05;
    private static final double FIVE_COMBAT_ROOMS_CHANCE = 0.08;
    private static final double THREE_PUZZLE_ROOM_CHANCE = 0.05;


    private final long seed;

    private DynamicMap map;

    private static Chamber[][][] chambers = new Chamber[2][4][2]; // zxy; 2 floors here since the final one is fixed
    private static Chamber[] layout = new Chamber[30];
    private OlmChamber olmChamber;
    private Random rng;
    private CheckpointChamber startingChamber;

    private CheckpointChamber lowerStartingChamber;
    private CheckpointChamber upperFinishChamber;
    private CheckpointChamber lowerFinishChamber;

    public void generate() {
        int layoutCount = 0;
        LinkedList<ChamberDefinition> puzzlePool = new LinkedList<>();
        Arrays.stream(values()).filter(def -> def.getType() == ChamberType.PUZZLE).forEach(puzzlePool::add);
        Collections.shuffle(puzzlePool);
        int combatIndex = rng.nextInt(COMBAT_ROOMS.length); // first boss
        int combatIndexDirection = rng.nextDouble() < 0.5 ? -1 : 1; // clockwise or counter clockwise?
        int budget = 8;
        int combatBudget = getCombatBudget(budget);
        budget -= combatBudget;
        int puzzleBudget = getPuzzleBudget(budget);
        budget -= puzzleBudget;
        int scavengerBudget = budget;
        for (int floorNumber = 0; floorNumber < 2; floorNumber++) {
            int forcedResourceRoom;
            if (floorNumber == 0) {
                forcedResourceRoom = 1 + rng.nextInt(4);
            } else {
                forcedResourceRoom = 4 + rng.nextInt(1);
            }
            Chamber[][] floor = chambers[floorNumber];
            //pick a starting place
            int x = rng.nextInt(floor.length);
            int y = rng.nextInt(floor[x].length);
            Chamber start = floorNumber == 1 ? START.newChamber() : LOWER_LEVEL_START.newChamber();
            floor[x][y] = start;
            if (floorNumber == 1)
                startingChamber = (CheckpointChamber) floor[x][y];
            else
                lowerStartingChamber = (CheckpointChamber) floor[x][y];
            boolean allowVertical = false;
            Direction previousMove = Direction.NORTH; // this is fixed
            Direction nextMove = getNextMove(floorNumber, x, y, previousMove, allowVertical, true);
            layout[layoutCount++] = start;
            int randomRooms = 0;
            while (true) { // add random rooms
                int rotation = getRotation(previousMove);
                floor[x][y].setRotation(rotation);
                floor[x][y].setLayout(getLayout(rotation, nextMove)); // set layout/rotation for previous room
                if (nextMove == null) {
                    chambers = new Chamber[2][4][2];
                    layout = new Chamber[30];
                    generate();
                    return;
                }
                x += nextMove.deltaX;
                y += nextMove.deltaY;
                if (x == 0 || x == X_LENGTH - 1)
                    allowVertical = true;
                previousMove = nextMove;
                randomRooms++;
                nextMove = getNextMove(floorNumber, x, y, previousMove, allowVertical, false);
                if (nextMove == null || randomRooms == 6 && rng.nextDouble() > SIXTH_ROOM_CHANCE) { // no moves left, or made 5 rooms and failed roll for 6th
                    if (floorNumber == 1) {
                        upperFinishChamber = (CheckpointChamber) (floor[x][y] = UPPER_FLOOR_FINISH.newChamber());
                    } else {
                        lowerFinishChamber = (CheckpointChamber) (floor[x][y] = LOWER_FLOOR_FINISH.newChamber());
                    }
                    break;
                } else {
                    if (randomRooms == forcedResourceRoom) {
                        floor[x][y] = getResourceChamber();
                        layout[layoutCount++] = floor[x][y];
                    } else if (floorNumber == 1 && scavengerBudget > 0 && rng.nextDouble() < 0.9) {
                        floor[x][y] = getScavengerChamber();
                        scavengerBudget--;
                        layout[layoutCount++] = floor[x][y];
                    } else {
                        List<ChamberType> possibilities = new ArrayList<>(3);
                        boolean allSpent = combatBudget <= 0 && puzzleBudget <= 0 && scavengerBudget <= 0;
                        if (allSpent || combatBudget > 0)
                            possibilities.add(ChamberType.COMBAT);
                        if (allSpent || puzzleBudget > 0)
                            possibilities.add(ChamberType.PUZZLE);
                        if (allSpent || scavengerBudget > 0)
                            possibilities.add(ChamberType.SCAVENGER);
                        ChamberType roll = possibilities.get(rng.nextInt(possibilities.size()));
                        if (roll == ChamberType.COMBAT) {
                            floor[x][y] = COMBAT_ROOMS[combatIndex].newChamber();
                            layout[layoutCount++] = floor[x][y];
                            combatIndex += combatIndexDirection;
                            if (combatIndex < 0)
                                combatIndex = COMBAT_ROOMS.length - 1;
                            else
                                combatIndex = combatIndex % COMBAT_ROOMS.length;
                            combatBudget--;
                        } else if (roll == ChamberType.PUZZLE) {
                            ChamberDefinition poll = puzzlePool.poll();

                            if (poll == null) {
                                chambers = new Chamber[2][4][2];
                                layout = new Chamber[30];
                                generate();
                                return;
                            }

                            floor[x][y] = poll.newChamber();
                            layout[layoutCount++] = floor[x][y];
                            puzzleBudget--;
                        } else {
                            floor[x][y] = getScavengerChamber();
                            layout[layoutCount++] = floor[x][y];
                            scavengerBudget--;
                        }
                    }
                }
            }
            layout[layoutCount++] = floor[x][y];
            floor[x][y].setRotation(getRotation(previousMove)); // set rotation for final room
            floor[x][y].setLayout(0); // final rooms only have one layout
        }
        // now time to actually generate the regions
        List<DynamicChunk> westChunks = new ArrayList<>(16*8);
        List<DynamicChunk> eastChunks = new ArrayList<>(16*8);
        map = new DynamicMap();
        map.setObjectConsumer(obj -> {
            if (obj.id == 29769)
                storageObjects.add(obj);
        });
        for (int z = 2; z > 0; z--) {
            for (int x = 0; x < X_LENGTH; x++) {
                for (int y = 0; y < Y_LENGTH; y++) {
                    int basePointX;
                    List<DynamicChunk> targetRegion;
                    if (x > 1) {
                        targetRegion = eastChunks;
                        basePointX = (x - 2) * 4;
                    } else {
                        targetRegion = westChunks;
                        basePointX = x * 4;
                    }
                    int basePointY = y * 4;
                    Chamber chamber = chambers[z - 1][x][y];
                    if (chamber == null) { // nothing generated here
                        // need to fill out empty spots so all the upper floor rooms display at the same visual height
                        for (int i = 0; i < 4; i++)
                            for (int j = 0; j < 4; j++)
                                targetRegion.add(new DynamicChunk(400, 712, 0).pos(basePointX + i, basePointY + j, z));

                        continue;
                    }
                    chamber.setBasePosition(new Position(map.swRegion.baseX + (x * 32), map.swRegion.baseY + (y * 32), z));
                    chamber.setLocation(basePointX, basePointY, z);
                    chamber.setRaid(this);
                    targetRegion.addAll(chamber.getChunks());
                }
            }
        }
        olmChamber = new OlmChamber(this);
        westChunks.addAll(olmChamber.getChunks());
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                eastChunks.add(new DynamicChunk(400, 712, 0).pos(i, j, 0)); // fill out bottom floor of east region
        map.build(westChunks).buildSe(eastChunks);

        olmChamber.onBuild();
        for (int z = 2; z > 0; z--) {
            for (int x = 0; x < X_LENGTH; x++) {
                for (int y = 0; y < Y_LENGTH; y++) {
                    Chamber chamber = chambers[z - 1][x][y];
                    if (chamber != null)
                        chamber.onBuild(); // this has to be AFTER everything is built so the scripts can find objects they need
                }
            }
        }
        olmChamber.onRaidStart();
    }

    private int getPuzzleBudget(int budget) {
        if (budget <= 2)
            return budget;
        if (rng.nextDouble() <= THREE_PUZZLE_ROOM_CHANCE)
            return 3;
        else
            return 2;
    }

    private int getCombatBudget(int budget) {
        if (budget <= 4)
            return budget;
        if (rng.nextDouble() < FIVE_COMBAT_ROOMS_CHANCE)
            return 5;
        else
            return 4;
    }

    private Chamber getResourceChamber() {
        return rng.nextDouble() < 0.5 ? FISHING_RESOURCE.newChamber() : HUNTER_RESOURCE.newChamber();
    }

    private Chamber getScavengerChamber() {
        return rng.nextDouble() < 0.5 ? SCAVENGERS.newChamber() : SCAVENGER_RUINS.newChamber();
    }

    private Direction getNextMove(int currentZ, int currentX, int currentY, Direction previousMove, boolean allowVeritcal, boolean startingChamber) {
        List<Direction> directions = new ArrayList<>(4);
        if (currentX > 0 && getChamberInDirection(currentZ, currentX, currentY, Direction.WEST) == null)
            directions.add(Direction.WEST);
        if (currentX < X_LENGTH - 1 && getChamberInDirection(currentZ, currentX, currentY, Direction.EAST) == null)
            directions.add(Direction.EAST);
        if (allowVeritcal) {
            if (!startingChamber && currentY > 0 && getChamberInDirection(currentZ, currentX, currentY, Direction.SOUTH) == null)
                directions.add(Direction.SOUTH);
            if (currentY < Y_LENGTH - 1 && getChamberInDirection(currentZ, currentX, currentY, Direction.NORTH) == null)
                directions.add(Direction.NORTH);
        }
        if (directions.size() == 0)
            return null;
        return directions.get(rng.nextInt(directions.size()));
    }

    private Chamber getChamberInDirection(int currentZ, int currentX, int currentY, Direction move) {
        return chambers[currentZ][currentX + move.deltaX][currentY + move.deltaY];
    }

    private static int getRotation(Direction lastMove) {
        switch (lastMove) {
            case NORTH:
                return 0;
            case EAST:
                return 1;
            case SOUTH:
                return 2;
            case WEST:
                return 3;
            default:
                throw new IllegalArgumentException("Invalid last move");
        }
    }

    private static int getLayout(int rotation, Direction nextMove) {
        if (rotation == 0) {
            if (nextMove == Direction.WEST) {
                return 0;
            } else if (nextMove == Direction.NORTH) {
                return 1;
            } else {
                return 2;
            }
        } else if (rotation == 1) {
            if (nextMove == Direction.NORTH) {
                return 0;
            } else if (nextMove == Direction.EAST) {
                return 1;
            } else {
                return 2;
            }
        } else if (rotation == 2) {
            if (nextMove == Direction.EAST) {
                return 0;
            } else if (nextMove == Direction.SOUTH) {
                return 1;
            } else {
                return 2;
            }
        } else if (rotation == 3) {
            if (nextMove == Direction.SOUTH) {
                return 0;
            } else if (nextMove == Direction.WEST) {
                return 1;
            } else {
                return 2;
            }
        } else {
            throw new IllegalArgumentException("invalid");
        }
    }

    private static final double BONUS_HP_PER_PLAYER = 0.75; // %increased hp for each players beyond the first

    private static final StatType[] SCALED_STATS = {StatType.Attack, StatType.Strength, StatType.Defence, StatType.Ranged, StatType.Magic, StatType.Hitpoints};
    public void scaleNPC(NPC npc) {
        if (npc.getCombat() == null) {
            return;
        }
        int partySize = getPartySize();
        int partyCombat = getParty().getPartyCombatLevel();
        npc.getCombat().getStat(StatType.Hitpoints).fixedLevel *= 1 + (BONUS_HP_PER_PLAYER * (partySize - 1));
        npc.getCombat().getStat(StatType.Hitpoints).restore();
        double factor;
        if (partyCombat >= 115)
            factor = 1;
        else if (partyCombat > 90)
            factor = 0.6 + ((partyCombat / 114d) * 0.25);
        else
            factor = 0.5;
        if (factor != 1) {
            for (StatType type : SCALED_STATS) { // scale stats. note that this also scales the hp on top of the per-plater bonus already added above. as these are both multiplicative modifiers it does no matter which one is applied first
                npc.getCombat().getStat(type).fixedLevel *= factor;
                npc.getCombat().getStat(type).restore();
            }
            BiConsumer<Hit, Entity> postTargetDefend = (hit, entity) -> { // scale damage dealt
                if (hit.attackStyle != null && hit.damage > 0)
                    hit.damage *= factor;
            };
            if (npc.hitListener != null) { // awkward but it's how the system works ... need to be 100% sure no listeners are being overwritten
                if (npc.hitListener.postTargetDefend != null)
                    npc.hitListener.postTargetDefend.andThen(postTargetDefend);
                else
                    npc.hitListener.postTargetDefend(postTargetDefend);
            } else {
                npc.hitListener = new HitListener().postTargetDefend(postTargetDefend);
            }
        }

    }

    public static void createChamber(Player player, Party party) {
        if (!player.getPrivateRaidStorage().isEmpty()) {
            RaidStorage.openPrivateStorage(player);
            player.sendMessage(Color.RED.wrap("You must retrieve all your items from the private storage before you can re-enter the Chambers of Xeric."));
            return;
        }
        if(party.getRaid() != null) {
            party.getRaid().enterRaid(player);
            return;
        }

        if(party.getLeader() != player) {
            player.dialogue(new MessageDialogue("Only the leader of your raid party can begin the raid."));
            return;
        }

        player.startEvent(event -> {
            player.lock();
            ChambersOfXeric raid = new ChambersOfXeric();
            raid.setParty(party);
            raid.generate();
            player.getPacketSender().fadeOut();
            event.delay(2);
            player.getMovement().teleport(party.getRaid().getStartingChamber().getEntrancePosition());
            raid.entered(player);
            raid.assignListener(player);
            player.getPacketSender().fadeIn();
            party.getMembers().forEach(member -> {
                if (member != party.getLeader())
                    member.sendMessage("<col=ef20ff>Your party has entered the dungeons! Come join them now.");
            });
            player.sendMessage("<col=ef20ff>Inviting party...</col>");
            player.sendMessage("--Layout--");
            String[] layout = getLayout();
            player.sendMessage("Upper Level - " + layout[0]);
            player.sendMessage("Lower Level - " + layout[1]);
            player.sendMessage("--End Layout--");
            event.delay(1);
            player.unlock();
        });
    }

    private static String[] getLayout() {
        StringJoiner lowerLevel = new StringJoiner (", ", "[", "]");
        StringJoiner upperLevel = new StringJoiner (", ", "[", "]");
        boolean isLowerLevel = false;
        boolean isUpperLevel = false;
        for (Chamber room : layout) {
            if (room == null) {
                continue;
            }
            ChamberDefinition chamberDefinition = room.getDefinition();
            if (chamberDefinition.equals(LOWER_LEVEL_START) || chamberDefinition.equals(LOWER_FLOOR_FINISH)) {
                isLowerLevel = !isLowerLevel;
                continue;
            }
            if (chamberDefinition.equals(START) || chamberDefinition.equals(UPPER_FLOOR_FINISH)) {
                isUpperLevel = !isUpperLevel;
                continue;
            }
            if (isLowerLevel) {
                lowerLevel.add(chamberDefinition.getName());
            } else if (isUpperLevel) {
                upperLevel.add(chamberDefinition.getName());
            }
        }
        String[] levels = new String[2];
        levels[0] = upperLevel.toString();
        levels[1] = lowerLevel.toString();
        return levels;
    }

    private void enterRaid(Player player) {
        if (isStarted()) {
            player.dialogue(new MessageDialogue("The raid has already begun. You can no longer enter."));
            return;
        }
        player.startEvent(event -> {
            player.lock();
            player.getPacketSender().fadeOut();
            event.delay(2);
            player.getMovement().teleport(party.getRaid().getStartingChamber().getEntrancePosition());
            player.getPacketSender().fadeIn();
            entered(player);
            assignListener(player);
            event.delay(2);
            player.unlock();
        });
    }

    private void assignListener(Player player) {
        map.assignListener(player)
                .onEnter(this::entered)
                .onExit(this::exited);
    }

    private void entered(Player player) {
        Config.RAIDS_TAB_ICON.set(player, 1);
        //TODO CoX quest tab interface
        Party.sendRaidJournal(player, party);
        Party.updatePartyStage(player, getRaidStageConfig());
        player.deathEndListener = (entity, killer, killHit) -> handleDeath(player);
        player.openInterface(InterfaceType.PRIMARY_OVERLAY, Interface.RAID_POINTS);
        if (startTime != null)
            Config.RAIDS_TIMER.set(player, (int) (Duration.between(startTime, Instant.now()).toMillis() / 600L));
    }

    private void exited(Player player, boolean logout) {
        party.removeMember(player);
        player.raidsParty = null;
        if (logout) {
            player.getMovement().teleport(1233, 3566, 0);
        } else {
            //TODO CoX quest tab interface
            TabJournal.restore(player);
            player.getPacketSender().resetCamera();
            player.closeInterface(InterfaceType.PRIMARY_OVERLAY);
        }
        player.deathEndListener = null;
        if (party.getSize() == 0) {
            destroy();
        }
        else {
            party.refreshAll();
        }
        confiscateItems(player);
    }

    private static void confiscateItems(Player player) {
        for (Item item : player.getInventory().getItems()) {
            if (item != null && item.getDef().coxItem) {
                item.remove();
            }
        }
        for (Item item : player.getPrivateRaidStorage().getItems()) {
            if (item != null && item.getDef().coxItem) {
                item.remove();
            }
        }
    }

    private void handleDeath(Player player) {
        for (Item item : player.getInventory().getItems()) {
            if (item != null && item.getDef().coxItem) {
                item.remove();
                new GroundItem(item).position(player.getPosition()).spawnPublic();
            }
        }
        player.getCombat().restore();
        player.getMovement().teleport(getRespawnPosition());
        player.getPacketSender().resetCamera();
        int pointsLost = (int) (Config.RAIDS_PERSONAL_POINTS.get(player) * 0.4);
        if (pointsLost > 0)
            addPoints(player, -pointsLost);
        player.openInterface(InterfaceType.PRIMARY_OVERLAY, Interface.RAID_POINTS);
        if (startTime != null)
            Config.RAIDS_TIMER.set(player, (int) (Duration.between(startTime, Instant.now()).toMillis() / 600L));
    }

    private int getRaidStageConfig() {
        switch (raidStage) {
            case 0:
                return Party.WAITING;
            case 1:
                return Party.EXPLORING_UPPER_LEVEL;
            case 2:
                return Party.REACHED_LOWER_LEVEL;
            case 3:
                return Party.REACHED_BOTTOM_LEVEL;
            case 4:
                return Party.GET_OUT;
        }
        return Party.WAITING;
    }

    private void destroy() {
        map.destroy();
        party.destroy();
        party = null;
        map = null;
    }

    public static boolean isRaiding(Player player) {
        return player.raidsParty != null && player.raidsParty.getRaid() != null;
    }

    public int getPartySize() {
        return getParty() != null ? getParty().getSize() : 1;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
        party.setRaid(this);
    }

    public DynamicMap getMap() {
        return map;
    }

    public void setMap(DynamicMap map) {
        this.map = map;
    }

    public void startRaid() {
        raidStage = 1;
        startTime = Instant.now();
        party.forPlayers(p -> Config.RAIDS_TIMER.set(p, 1));
        for (int z = 2; z > 0; z--) {
            for (int x = 0; x < X_LENGTH; x++) {
                for (int y = 0; y < Y_LENGTH; y++) {
                    Chamber chamber = chambers[z - 1][x][y];
                    if (chamber != null)
                        chamber.onRaidStart();
                }
            }
        }
        map.addEvent(event -> {
            while (true) {
                party.sendTotalPoints();
                event.delay(10);
            }
        });
        int averageCombatLevel = party.getPartyCombatLevel();
        if (averageCombatLevel < 90)
            combatPointsFactor = 0.6;
        else if (averageCombatLevel < 115)
            combatPointsFactor = 0.8;
    }


    public CheckpointChamber getStartingChamber() {
        return startingChamber;
    }

    public CheckpointChamber getLowerStartingChamber() {
        return lowerStartingChamber;
    }

    public CheckpointChamber getUpperFinishChamber() {
        return upperFinishChamber;
    }

    public CheckpointChamber getLowerFinishChamber() {
        return lowerFinishChamber;
    }

    public static void addPoints(Player player, int points) {
        if (!isRaiding(player))
            return;
        Config.RAIDS_PERSONAL_POINTS.set(player, Config.RAIDS_PERSONAL_POINTS.get(player) + points);
        Config.RAIDS_PARTY_POINTS.set(player, Config.RAIDS_PARTY_POINTS.get(player) + points);
        player.raidsParty.addPoints(points);
    }

    public static void addDamagePoints(Player player, NPC target, int points) {
        if (!isRaiding(player))
            return;
        if (target.get("RAID_NO_POINTS") != null)
            return;
        if (target.getId() == 7548 || target.getId() == 7549) // scavengers
            points /= 4;
        points *= 5;
        points *= player.raidsParty.getRaid().combatPointsFactor;
        addPoints(player, points);
    }

    public void openStorage(Player player) {
        if (storageLevel == 0)
            return;
        storage.open(player);
    }

    public RaidStorage getStorage() {
        return storage;
    }

    public int getStorageLevel() {
        return storageLevel;
    }

    public void setStorageLevel(int level) {
        if (level != storageLevel) {
            storageLevel = level;
            storageObjects.forEach(obj -> obj.setId(RaidStorage.STORAGE_UNITS[level - 1].getBuiltObjects()[0]));
            if (storageLevel == 1) {
                storage.setSize(250);
                updatePrivateStorageCapacity(30);
            } else if (storageLevel == 2) {
                storage.setSize(500);
                updatePrivateStorageCapacity(60);
            } else {
                storage.setSize(1000);
                updatePrivateStorageCapacity(90);
            }
        }
    }

    public void updatePrivateStorageCapacity(int capacity) {
        party.getMembers().forEach(p -> {
            p.getPrivateRaidStorage().init(p, capacity, -1, -1, 583, false);
        });
    }

    public void completeRaid() {
        raidStage = 4;
        String time = getTimeSinceStart();
        String[] playerNames = party.getMembers().stream().map(player -> player.getName()).collect(Collectors.toList()).toArray(new String[1]);
        party.forPlayers(p -> {
            p.sendMessage(Color.RAID_PURPLE.wrap("Congratulations - your raid is complete! Duration: " + Color.RED.wrap(time) + "."));
            p.chambersofXericKills.increment(p);
            Party.updatePartyStage(p, Party.GET_OUT);
        });
        Loggers.logRaidsCompletion(playerNames, time, party.getPoints());
        XericRewards.giveRewards(this);
        party.forPlayers(p -> {
            p.sendMessage(String.format("Total points: " + Color.RAID_PURPLE.wrap("%,d") + ", Personal points: " + Color.RAID_PURPLE.wrap("%,d") + " (" + Color.RAID_PURPLE.wrap("%.2f") + "%%)",
                    party.getPoints(), Config.RAIDS_PERSONAL_POINTS.get(p), (double) (Config.RAIDS_PERSONAL_POINTS.get(p) / party.getPoints()) * 100));
        });
    }

    private boolean isStarted() {
        return raidStage > 0;
    }

    private boolean isComplete() {
        return raidStage == 4;
    }
}
