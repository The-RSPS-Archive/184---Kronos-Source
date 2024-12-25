package io.ruin.model.map;

public class Chunk { // TODO: add tile/region related stuffs when we need it (if ever?)

    private final int x;

    private final int y;

    private final int z;
    public Chunk(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

}
