package io.ruin.model.entity.player;

import io.ruin.api.buffer.OutBuffer;
import io.ruin.model.World;
import io.ruin.model.entity.shared.UpdateMask;
import io.ruin.model.map.Region;

import java.util.Iterator;

public class PlayerUpdater {

    //todo - limit player adds per cycle? (going to see exactly how many osrs do per cycle and copy them because Im a copycatter!)

    private Player player;

    private int skipCount = -1;
    private byte[] skipData = new byte[2048];

    private int localCount = 0;
    private int[] localIndexes = new int[2048];
    protected boolean[] local = new boolean[2048];

    private int globalCount = 0;
    private int[] globalIndexes = new int[2048];
    private int[] globalRegionHashes = new int[2048];

    private OutBuffer maskBuffer = new OutBuffer(0xff);

    public boolean updateRegion;

    public PlayerUpdater(Player player) {
        this.player = player;
    }

    public void init(OutBuffer out) {
        out.initBitAccess();
        out.addBits(30, player.getPosition().getTileHash());
        local[player.getIndex()] = true;
        localIndexes[localCount++] = player.getIndex();
        for(int index = 1; index < 2048; index++) {
            if(index == player.getIndex())
                continue;
            Player p = World.getPlayer(index);
            out.addBits(18, globalRegionHashes[index] = (p == null ? 0 : p.getPosition().getRegionHash()));
            globalIndexes[globalCount++] = index;
        }
        out.finishBitAccess();
    }

    public void process() {
        OutBuffer out = new OutBuffer(0xff).sendVarShortPacket(4);
        /**
         * Local
         */
        for(int x = 0; x < 2; x++) {
            out.initBitAccess();
            for(int i = 0; i < localCount; i++) {
                int index = localIndexes[i];
                int skip = skipData[index] & 0x1;
                if(x == 0 ? (skip != 0) : (skip == 0))
                    continue;
                Player localPlayer = World.getPlayer(index);
                int localUpdateHash = getLocalUpdateHash(localPlayer);
                if(localUpdateHash == 0) {
                    skipCount++;
                    skipData[index] |= 0x2;
                } else {
                    writeSkip(out);
                    out.addBits(1, 1);
                    if(!writeLocalUpdate(out, localPlayer, localUpdateHash))
                        local[index] = false;
                }
            }
            writeSkip(out);
            out.finishBitAccess();
        }
        /**
         * Global
         */
        for(int x = 0; x < 2; x++) {
            out.initBitAccess();
            for(int i = 0; i < globalCount; i++) {
                int index = globalIndexes[i];
                int skip = skipData[index] & 0x1;
                if(x == 0 ? (skip == 0) : (skip != 0))
                    continue;
                Player globalPlayer = World.getPlayer(index);
                int updateType = getGlobalUpdateType(globalPlayer);
                if(updateType == -1) {
                    skipCount++;
                    skipData[index] |= 0x2;
                } else {
                    writeSkip(out);
                    out.addBits(1, 1);
                    if(!writeGlobalUpdate(out, globalPlayer, updateType)) {
                        local[index] = true;
                        skipData[index] |= 0x2;
                    }
                }
            }
            writeSkip(out);
            out.finishBitAccess();
        }
        /**
         * Masks
         */
        int maskPosition = maskBuffer.position();
        if(maskPosition > 0) {
            out.addBytes(maskBuffer.payload(), 0, maskPosition);
            maskBuffer.position(0);
        }
        /**
         * Write
         */
        if(updateRegion) {
            updateRegion = false;
            player.getPacketSender().sendRegion(false);
            player.getPacketSender().write(out);
            Region.update(player);

        } else {
            player.getPacketSender().write(out);
            if(player.getPosition().getZ() != player.getLastPosition().getZ())
                Region.update(player);
        }
        /**
         * Recount
         */
        localCount = 0;
        globalCount = 0;
        for(int index = 1; index < 2048; index++) {
            skipData[index] >>= 1;
            if(local[index])
                localIndexes[localCount++] = index;
            else
                globalIndexes[globalCount++] = index;
        }
    }

    /**
     * NSN Skips
     */

    private void writeSkip(OutBuffer out) {
        if(skipCount == -1)
            return;
        out.addBits(1, 0);
        out.addBits(2, skipCount == 0 ? 0 : skipCount > 255 ? 3 : (skipCount > 31 ? 2 : 1));
        if(skipCount > 0)
            out.addBits(skipCount > 255 ? 11 : (skipCount > 31 ? 8 : 5), skipCount);
        skipCount = -1;
    }


    /**
     * Local Updating
     */

    private int getLocalUpdateHash(Player localPlayer) {
        if(localPlayer == null || !isVisible(localPlayer))
            return -1;
        int updateType = 0;
        if(localPlayer.getMovement().hasTeleportUpdate())
            updateType = 3;
        else if(localPlayer.getMovement().getRunDirection() != -1)
            updateType = 2;
        else if(localPlayer.getMovement().getWalkDirection() != -1)
            updateType = 1;
        return localPlayer.getUpdateMaskData(true, false) << 16 | updateType;
    }

    private boolean writeLocalUpdate(OutBuffer out, Player localPlayer, int updateHash) {
        /**
         * Remove player
         */
        if(updateHash == -1) {
            out.addBits(1, 0);
            out.addBits(2, 0);
            if(localPlayer != null) {
                /* left screen, but not game */
                int index = localPlayer.getIndex();
                int oldRegionHash = globalRegionHashes[index];
                int newRegionHash = localPlayer.getPosition().getRegionHash();
                if(oldRegionHash != newRegionHash) {
                    out.addBits(1, 1);
                    moveGlobalPlayer(out, index, oldRegionHash, newRegionHash);
                    return false;
                }
            }
            out.addBits(1, 0);
            return false;
        }
        /**
         * Update masks
         */
        int maskData = (updateHash >> 16) & 0xffff;
        if(maskData != 0) {
            out.addBits(1, 1);
            writeMasks(localPlayer, maskData);
        } else {
            out.addBits(1, 0);
        }
        /**
         * Update movement
         */
        int updateType = updateHash & 0xff;
        if(updateType == 3) {
            out.addBits(2, 3);
            int diffX = localPlayer.getPosition().getX() - localPlayer.getLastPosition().getX();
            int diffY = localPlayer.getPosition().getY() - localPlayer.getLastPosition().getY();
            int diffZ = localPlayer.getPosition().getZ() - localPlayer.getLastPosition().getZ();
            if(Math.abs(diffX) <= 15 && Math.abs(diffY) <= 15) {
                out.addBits(1, 0);
                if(diffX < 0)
                    diffX += 32;
                if(diffY < 0)
                    diffY += 32;
                out.addBits(12, ((diffZ & 0x3) << 10) | ((diffX & 0x1f) << 5) | (diffY & 0x1f));
            } else {
                out.addBits(1, 1);
                out.addBits(30,  ((diffZ & 0x3) << 28) | ((diffX & 0x3fff) << 14) | (diffY & 0x3fff));
            }
            globalRegionHashes[localPlayer.getIndex()] = localPlayer.getPosition().getRegionHash();
        } else if(updateType == 2) {
            out.addBits(2, 2);
            out.addBits(4, localPlayer.getMovement().getRunDirection());
            globalRegionHashes[localPlayer.getIndex()] = localPlayer.getPosition().getRegionHash();
        } else if(updateType == 1) {
            out.addBits(2, 1);
            out.addBits(3, localPlayer.getMovement().getWalkDirection());
            globalRegionHashes[localPlayer.getIndex()] = localPlayer.getPosition().getRegionHash();
        } else {
            /* only masks were updated */
            out.addBits(2, 0);
        }
        return true;
    }

    private boolean isVisible(Player p) {
        return p.getIndex() == player.getIndex() ||
                (p.isOnline() && !p.isHidden() && p.getPosition().isWithinDistance(player.getPosition()));
    }

    /**
     * Global Updating
     */

    private int getGlobalUpdateType(Player globalPlayer) {
        if(globalPlayer != null) {
            if(isVisible(globalPlayer))
                return 0;
            int oldRegionHash = globalRegionHashes[globalPlayer.getIndex()];
            int newRegionHash = globalPlayer.getPosition().getRegionHash();
            if(oldRegionHash != newRegionHash)
                return 1;
        }
        return -1;
    }

    private boolean writeGlobalUpdate(OutBuffer out, Player globalPlayer, int updateType) {
        int index = globalPlayer.getIndex();
        int oldRegionHash = globalRegionHashes[index];
        int newRegionHash = globalPlayer.getPosition().getRegionHash();
        if(updateType == 0) {
            out.addBits(2, 0);
            if(oldRegionHash != newRegionHash) {
                out.addBits(1, 1);
                moveGlobalPlayer(out, index, oldRegionHash, newRegionHash);
            } else {
                out.addBits(1, 0);
            }
            out.addBits(13, globalPlayer.getPosition().getX());
            out.addBits(13, globalPlayer.getPosition().getY());
            int maskData = globalPlayer.getUpdateMaskData(true, true);
            if(maskData != 0) {
                out.addBits(1, 1);
                writeMasks(globalPlayer, maskData);
            } else {
                out.addBits(1, 0);
            }
            return false;
        }
        moveGlobalPlayer(out, index, oldRegionHash, newRegionHash);
        return true;
    }

    private void moveGlobalPlayer(OutBuffer out, int index, int oldRegionHash, int newRegionHash) {
        int oldRegionLevel = (oldRegionHash >> 16) & 0x3;
        int oldRegionX = (oldRegionHash >> 8) & 0xff;
        int oldRegionY = oldRegionHash & 0xff;

        int newRegionLevel = (newRegionHash >> 16) & 0x3;
        int newRegionX = (newRegionHash >> 8) & 0xff;
        int newRegionY = newRegionHash & 0xff;

        int diffLevel = newRegionLevel - oldRegionLevel;
        int diffX = newRegionX - oldRegionX;
        int diffY = newRegionY - oldRegionY;

        if(diffX == 0 && diffY == 0) {
            out.addBits(2, 1);
            out.addBits(2, diffLevel);
        } else if(Math.abs(diffX) <= 1 && Math.abs(diffY) <= 1) {
            int direction;
            if(diffX == -1 && diffY == -1)
                direction = 0;
            else if(diffX == 1 && diffY == -1)
                direction = 2;
            else if(diffX == -1 && diffY == 1)
                direction = 5;
            else if(diffX == 1 && diffY == 1)
                direction = 7;
            else if(diffY == -1)
                direction = 1;
            else if(diffX == -1)
                direction = 3;
            else if(diffX == 1)
                direction = 4;
            else
                direction = 6;
            out.addBits(2, 2);
            out.addBits(5, ((diffLevel & 0x3) << 3) | (direction & 0x7));
        } else {
            out.addBits(2, 3);
            out.addBits(18, ((diffLevel & 0x3) << 16) | ((diffX & 0xff) << 8) | (diffY & 0xff));
        }
        globalRegionHashes[index] = newRegionHash;
    }

    /**
     * Masks
     */

    private void writeMasks(Player localPlayer, int maskData) {

        if(maskData > 0xff)
            maskData |= 1;

        maskBuffer.addByte(maskData);

        if(maskData > 0xff)
            maskBuffer.addByte(maskData >> 8);

        for(UpdateMask updateMask : localPlayer.getMasks()) {
            if((maskData & updateMask.get(true)) != 0) {
                updateMask.send(maskBuffer, true);
                updateMask.setSent(true);
            }
        }

    }

    /**
     * Local Iteration
     */

    protected Iterable<Player> localIterator() {
        return LocalIterator::new;
    }

    private class LocalIterator implements Iterator<Player> {

        private int offset;

        @Override
        public boolean hasNext() {
            return offset < localCount;
        }

        @Override
        public Player next() {
            int index = localIndexes[offset++];
            return World.getPlayer(index);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

}