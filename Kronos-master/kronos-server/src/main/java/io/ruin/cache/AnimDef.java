package io.ruin.cache;

import io.ruin.Server;
import io.ruin.api.buffer.InBuffer;
import io.ruin.api.filestore.IndexFile;

import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;

public class AnimDef {

    public static AnimDef[] LOADED;

    public static void load() {
        IndexFile index = Server.fileStore.get(2);
        LOADED = new AnimDef[index.getLastFileId(12) + 1];
        for(int id = 0; id < LOADED.length; id++) {
            byte[] data = index.getFile(12, id);
            AnimDef def = new AnimDef();
            def.id = id;
            def.method4576(new InBuffer(data));
            def.method4578();
            LOADED[id] = def;
        }
    }

    public static AnimDef get(int id) {
        if(id < 0 || id >= LOADED.length)
            return null;
        return LOADED[id];
    }

    /**
     * Cache data
     */

    public int id;
    public int[] frameLengths;
    public int[] frameData;
    public int[] anIntArray3611;
    public int forcedPriority = 5;
    public int frameStep = -1;
    public boolean oneSquareAnimation = false;
    public int leftHandItem = -1;
    public int rightHandItem = -1;
    public int maxLoops = 99;
    public int resetWhenWalk = -1;
    public int priority = -1;
    public int delayType = 2;
    public int[] animationFlowControl;
    public int[] anIntArray3609;

    void method4576(InBuffer var1) {
        for(; ; ) {
            int var3 = var1.readUnsignedByte();
            if(var3 == 0)
                break;
            decode(var1, var3);
        }
    }

    void decode(InBuffer buffer, int opcode) {
        if(opcode == 1) {
            int frameCount = buffer.readUnsignedShort();
            frameLengths = new int[frameCount];
            for(int var5 = 0; var5 < frameCount; var5++)
                frameLengths[var5] = buffer.readUnsignedShort();
            frameData = new int[frameCount];
            for(int var5 = 0; var5 < frameCount; var5++)
                frameData[var5] = buffer.readUnsignedShort();
            for(int var5 = 0; var5 < frameCount; var5++)
                frameData[var5] += buffer.readUnsignedShort() << 16;
        } else if(opcode == 2)
            frameStep = buffer.readUnsignedShort();
        else if(opcode == 3) {
            int var4 = buffer.readUnsignedByte();
            animationFlowControl = new int[var4 + 1];
            for(int var5 = 0; var5 < var4; var5++)
                animationFlowControl[var5] = buffer.readUnsignedByte();
            animationFlowControl[var4] = 9999999;
        } else if(opcode == 4)
            oneSquareAnimation = true;
        else if(opcode == 5)
            forcedPriority = buffer.readUnsignedByte();
        else if(opcode == 6)
            leftHandItem = buffer.readUnsignedShort();
        else if(opcode == 7)
            rightHandItem = buffer.readUnsignedShort();
        else if(opcode == 8)
            maxLoops = buffer.readUnsignedByte();
        else if(opcode == 9)
            resetWhenWalk = buffer.readUnsignedByte();
        else if(opcode == 10)
            priority = buffer.readUnsignedByte();
        else if(opcode == 11)
            delayType = buffer.readUnsignedByte();
        else if(opcode == 12) {
            int var4 = buffer.readUnsignedByte();
            anIntArray3609 = new int[var4];
            for(int var5 = 0; var5 < var4; var5++)
                anIntArray3609[var5] = buffer.readUnsignedShort();
            for(int var5 = 0; var5 < var4; var5++)
                anIntArray3609[var5] += buffer.readUnsignedShort() << 16;
        } else if(opcode == 13) {
            int var4 = buffer.readUnsignedByte();
            anIntArray3611 = new int[var4];
            for(int var5 = 0; var5 < var4; var5++)
                anIntArray3611[var5] = buffer.readMedium();
        }
    }

    public static SortedSet<Integer> findAnimationsWithSameRigging(int... ids) { // idk about this name LOL
        HashSet<Integer> skeletons = new HashSet<>();
        for (int srcAnim : ids) {
            if (srcAnim != -1) {
                AnimDef anim = AnimDef.get(srcAnim);
                if (anim == null)
                    continue;
                if (anim.frameData == null)
                    continue;
                for (int frame : anim.frameData)
                    if (frame != -1)
                        skeletons.add(frame >> 16);
            }
        }
        if (skeletons.isEmpty()) {
            return null;
        }
        SortedSet<Integer> results = new TreeSet<>();
        anim_loop:
        for (AnimDef anim : AnimDef.LOADED) {
            if (anim == null || anim.frameData == null)
                continue;
            for (int frame : anim.frameData)
                if (skeletons.contains(frame >> 16)) {
                    results.add(anim.id);
                    continue anim_loop;
                }
        }
        return results;
    }

    void method4578() {
        if(resetWhenWalk == -1) {
            if(animationFlowControl != null)
                resetWhenWalk = 2;
            else
                resetWhenWalk = 0;
        }
        if(priority == -1) {
            if(animationFlowControl != null)
                priority = 2;
            else
                priority = 0;
        }
    }

    /**
     * Gets the duration of this animation in milliseconds.
     * @return The duration.
     */
    public int getDuration() {
        if (frameLengths == null) {
            return 0;
        }
        int duration = 0;
        for (int i : frameLengths) {
            if (i > 100) {
                continue;
            }
            duration += i * 20;
        }
        return duration;
    }

    public int getTickDelay() {
        return (int) (this.getDuration() * 0.001 * 1.6);
    }


}
