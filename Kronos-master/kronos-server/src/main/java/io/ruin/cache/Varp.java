package io.ruin.cache;

public class Varp {

    public static Varp[] LOADED;

    public static Varp get(int id) {
        if(id < 0 || id >= LOADED.length)
            return null;
        return LOADED[id];
    }

    /**
     * Separator
     */

    public final int id;

    public final Varpbit[] bits;

    public Varp(int id, Varpbit[] bits) {
        this.id = id;
        this.bits = bits;
        LOADED[id] = this;
    }

}
