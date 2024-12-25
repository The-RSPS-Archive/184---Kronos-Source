package io.ruin.model.inter.utils;

import io.ruin.model.entity.player.Player;

public class Unlock {

    private int interfaceId;

    private int childParentId;

    private int minChildId, maxChildId;

    private int mask;

    public Unlock(int interfaceId, int childParentId) {
        this.interfaceId = interfaceId;
        this.childParentId = childParentId;
    }

    public Unlock(int interfaceId, int childParentId, int minChildId, int maxChildId) {
        this.interfaceId = interfaceId;
        this.childParentId = childParentId;
        children(minChildId, maxChildId);
    }

    public Unlock children(int minChildId, int maxChildId) {
        this.minChildId = minChildId;
        this.maxChildId = maxChildId;
        return this;
    }

    public void unlock(Player player, int slot) {
        mask = 2 << slot;
        send(player);
    }

    public void unlockWith(Player player, int mask) {
        this.mask = mask;
        send(player);
        //System.out.println("player.getPacketSender().sendAccessMask(" + interfaceId + ", " + childParentId + ", " + minChildId + ", " + maxChildId + ", " + mask + ");");
    }

    public void unlockFirst(Player player) {
        unlock(player, 0);
    }

    public void unlockSingle(Player player) {
        mask = 1;
        send(player);
    }

    public void unlockRange(Player player, int startSlot, int finishSlot) {
        for(int i = startSlot; i <= finishSlot; i++)
            mask |= 2 << i;
        send(player);
    }

    public void unlockMultiple(Player player, int... slots) {
        for(int slot : slots)
            mask |= 2 << slot;
        send(player);
    }

    private void send(Player player) {
        player.getPacketSender().sendAccessMask(interfaceId, childParentId, minChildId, maxChildId, mask);
    }

}