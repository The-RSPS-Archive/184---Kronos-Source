package io.ruin.model.activities.raids.xeric.chamber.impl;

import io.ruin.model.World;
import io.ruin.model.activities.raids.xeric.ChambersOfXeric;
import io.ruin.model.activities.raids.xeric.chamber.Chamber;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.route.routes.DumbRoute;

import java.util.LinkedList;
import java.util.List;

public class JewelledCrabsChamber extends Chamber {

    public enum Color {
        WHITE(7580, 7576, 29758, 119), // black crystal
        RED(7581, 7577, 29759, 128), // cyan crystal
        GREEN(7582, 7578, 29760, 125), // magenta crystal
        BLUE(7583, 7579, 29761, 502), // yellow crystal
        ;
        private int energyId; // npc id of the energy thing of this color
        public int crabId; // the crab of this color
        private int crystalId; // the correct crystal the energy thing of this color should go to (opposite color)
        private int gfx;
        Color(int energyId, int crabId, int crystalId, int gfx) {
            this.energyId = energyId;
            this.crabId = crabId;
            this.crystalId = crystalId;
            this.gfx = gfx;
        }

        static Color getByCrab(int crabId) {
            for (Color c : values())
                if (c.crabId == crabId)
                    return c;
            return null;
        }

        static Color getByCrystal(int id) {
            for (Color c : values())
                if (c.crystalId == id)
                    return c;
            return null;
        }
    }

    private static final int BLOCKING_CRYSTAL = 29757;
    private static final int WHITE_CRYSTAL = 29762;

    private static int[][] startingPositions = {
            {13, 9}, // layout 0
            {22, 19}, // layout 1
            {18, 14}, // layout 2
    };

    private static int[][] blockingCrystals = {
            {8, 21}, // layout 0
            {12, 22}, // layout 1
            {21, 22}, // layout 2
    };

    private static int[][][] crabSpawns = {
            {
                    {16, 19},
                    {20, 22},
                    {21, 18},
                    {17, 17}
            },
            {
                    {13, 16},
                    {11, 12},
                    {14, 10},
                    {19, 14}
            },
            {
                    {9, 13},
                    {11, 11},
                    {14, 19},
                    {10, 21}
            },
    };

    private static Direction[] directions = {Direction.SOUTH, Direction.WEST, Direction.NORTH, Direction.EAST};
    private static int[] startingDirections = { // index to ^
            3, // layout 0
            0, // layout 1
            1, // layout 2
    };

    private static Direction nextDirection(Direction current) {
        for (int i = 0; i < directions.length; i++) {
            if (directions[i] == current) {
                return directions[(i + 1) % directions.length];
            }
        }
        return directions[0];
    }


    @Override
    public void onRaidStart() {
        GameObject blockingCrystal = spawnObject(BLOCKING_CRYSTAL, blockingCrystals[getLayout()][0], blockingCrystals[getLayout()][1], 10, 0);
        List<NPC> crabs = new LinkedList<>();
        for (int i = 0; i < 4; i++) { // spawn crabs
            NPC crab = spawnNPC(7576, crabSpawns[getLayout()][i][0], crabSpawns[getLayout()][i][1], Direction.SOUTH, 3);
            crab.attackBounds = new Bounds(crab.getSpawnPosition(), 16);
            crabs.add(crab);
        }
        NPC energy = spawnNPC(7580, startingPositions[getLayout()][0], startingPositions[getLayout()][1], directions[startingDirections[getLayout()]], 0);
        energy.addEvent(event -> { // main logic of this room
            int converted = 0; // crystals converted
            Color currentColor = Color.WHITE;
            Direction initialDirection = rotatedDir(directions[startingDirections[getLayout()]]);
            Direction direction = initialDirection;
            Position start = getPosition(startingPositions[getLayout()][0], startingPositions[getLayout()][1]);
            while (true) {
                int nextX = energy.getAbsX() + direction.deltaX;
                int nextY = energy.getAbsY() + direction.deltaY;
                energy.stepAbs(nextX, nextY, StepType.FORCE_WALK);
                Player playerOnTile = getPlayerOnTile(energy, nextX, nextY);
                NPC npcOnTile = getNPCOnTile(crabs, nextX, nextY, energy.getHeight());
                if (playerOnTile == null && npcOnTile == null && DumbRoute.getDirection(energy.getAbsX(), energy.getAbsY(), energy.getHeight(), energy.getSize(), nextX, nextY) == direction) { // we can take this step!
                    event.delay(1);
                    continue;
                } else { // we can't take this step... why?
                    GameObject obj = Tile.getObject(-1, nextX, nextY, energy.getHeight(), 10, -1);
                    if (obj != null && currentColor.crystalId == obj.id) { // we hit the correct crystal, change it to white!
                        energy.localPlayers().forEach(p -> ChambersOfXeric.addPoints(p, 100));
                        obj.setId(WHITE_CRYSTAL);
                        if (++converted == 4) {
                            World.sendGraphics(currentColor.gfx, 90, 0, nextX, nextY, energy.getHeight());
                            energy.remove();
                            break;
                        }
                    } else if (playerOnTile != null) { // we hit a player
                        playerOnTile.hit(new Hit().randDamage(15).delay(0));
                    } else if (npcOnTile != null) { // we hit a crab...
                        Color crabColor = Color.getByCrab(npcOnTile.getId());
                        if (crabColor != null) { // should never be false but who knows..
                            if (crabColor != Color.WHITE) { // change color if not white crab
                                currentColor = crabColor;
                                energy.transform(currentColor.energyId);
                            }
                            direction = nextDirection(direction);
                            continue; // no delay for smoother movement
                        }
                    }
                    World.sendGraphics(currentColor.gfx, 90, 0, nextX, nextY, energy.getHeight());
                    energy.getMovement().teleport(start);
                    direction = initialDirection;
                    currentColor = Color.WHITE;
                    energy.transform(currentColor.energyId);
                    event.delay(1);
                }
            }
            blockingCrystal.remove();
            crabs.forEach(npc -> npc.getCombat().startDeath(null));
        });
    }

    private static Player getPlayerOnTile(NPC energy, int x, int y) {
        for (Player player : energy.localPlayers())
            if (player.isAt(x, y) && player.getHeight() == energy.getHeight())
                return player;
        return null;
    }

    private static NPC getNPCOnTile(List<NPC> crabs, int x, int y, int z) { // we don't need to check size here because the only npcs that could be in the way are the crabs which are size 1, but keep that in mind in case anything about this room changes in the future
        for (NPC npc : crabs)
            if (npc.isAt(x, y) && npc.getHeight() == z)
                return npc;
        return null;
    }
}
