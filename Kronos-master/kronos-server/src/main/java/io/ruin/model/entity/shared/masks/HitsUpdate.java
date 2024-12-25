package io.ruin.model.entity.shared.masks;

import io.ruin.Server;
import io.ruin.api.buffer.OutBuffer;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.shared.UpdateMask;

import java.util.ArrayList;

public class HitsUpdate extends UpdateMask {

    private final ArrayList<Splat> splats = new ArrayList<>(4);

    public int hpBarType;

    private int hpBarRatio;

    public int curHp, maxHp;

    public long removeAt;

    private boolean forceSend = false;

    public void add(Hit hit, int curHp, int maxHp) {
        if(splats.size() < 6)
            splats.add(new Splat(hit.type.ordinal(), hit.damage, 0));
        this.hpBarRatio = toRatio(hpBarType, curHp, maxHp);
        this.curHp = curHp;
        this.maxHp = maxHp;
        this.removeAt = Server.getEnd(10);
    }


    /**
     * For showing HP bar without any hits
     */
    public void forceSend(int curHp, int maxHp) {
        hpBarRatio = toRatio(hpBarType, curHp, maxHp);
        forceSend = true;
    }

    @Override
    public void reset() {
        splats.clear();
        forceSend = false;
    }

    @Override
    public boolean hasUpdate(boolean added) {
        return forceSend || !splats.isEmpty();
    }

    @Override
    public void send(OutBuffer out, boolean playerUpdate) {
        if(playerUpdate)
            out.addByteA(splats.size());
        else
            out.addByteC(splats.size());
        for(Splat splat : splats) {
            out.addSmart(splat.type);
            out.addSmart(splat.damage);
            out.addSmart(splat.delay);
        }
        if(playerUpdate)
            out.addByteS(1); //hp bar count
        else
            out.addByteS(1); //hp bar count
        out.addSmart(hpBarType);
        out.addSmart(0); //second bar type
        out.addSmart(0); //hp bar delay
        if(playerUpdate)
            out.addByteA(hpBarRatio);
        else
            out.addByteC(hpBarRatio);
    }

    @Override
    public int get(boolean playerUpdate) {
        return playerUpdate ? 4 : 1;
    }

    private static final class Splat {

        private int type, damage;

        private int delay;

        public Splat(int type, int damage, int delay) {
            this.type = type;
            this.damage = damage;
            this.delay = delay;
        }

    }

    /**
     * Ratio calc
     */
    private static final int[][] RATIO_DATA = {
            {30, 30},       //0
            {100, 100},     //1
            {120, 120},     //2
            {160, 160},     //3
            {120, 120},     //4
            {60, 60},       //5
            {100, 100},     //6
            {100, 100},     //7
            {120, 120},     //8
    };

    private static int toRatio(int type, int min, int max) {
        int ratio = (Math.min(min, max) * RATIO_DATA[type][0]) / max;
        if(ratio == 0 && min > 0)
            ratio = 1;
        return ratio;
    }

}