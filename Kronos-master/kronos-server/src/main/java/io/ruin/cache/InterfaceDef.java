package io.ruin.cache;

import io.ruin.Server;
import io.ruin.api.buffer.InBuffer;
import io.ruin.api.filestore.IndexFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InterfaceDef {

    public static int[] COUNTS;

    public static void load() {
        IndexFile idx = Server.fileStore.get(3);
        COUNTS = new int[idx.getLastArchiveId() + 1];
        for(int i = 0; i < COUNTS.length; i++) {
            int lastChildId = idx.getLastFileId(i);
            if(lastChildId != -1)
                COUNTS[i] = lastChildId + 1;
        }
        COUNTS[259] += 7;
    }

    public static void printConfigs(int interfaceId, boolean recursiveSearch) {
        Set<ScriptDef> scripts = getScripts(interfaceId, recursiveSearch);
        if(scripts == null) {
            System.out.println("Interface " + interfaceId + " uses no scripts! [recursiveSearch=" + recursiveSearch + "]");
            return;
        }
        HashSet<Integer> varpIds = new HashSet<>();
        for(ScriptDef script : scripts) {
            for(int i = 0; i < script.instructions.length; i++) {
                int instruction = script.instructions[i];
                if(instruction == 1)
                    varpIds.add(script.intArgs[i]);
                else if(instruction == 25) //get
                    varpIds.add(Varpbit.get(script.intArgs[i]).varpId);
            }
        }
        if(varpIds.isEmpty()) {
            System.out.println("Interface " + interfaceId + " uses no configs! [recursiveSearch=" + recursiveSearch + "]");
            System.out.println("    scripts=" + scripts.stream().map(def -> def.id).collect(Collectors.toList()).toString());
            return;
        }
        System.out.println("Interface " + interfaceId + " uses the following configs: [recursiveSearch=" + recursiveSearch + "]");
        System.out.println("    scripts=" + scripts.stream().map(def -> def.id).collect(Collectors.toList()).toString());
        for(int varpId : varpIds) {
            System.out.println("  varp: " + varpId);
            Varp varp = Varp.get(varpId);
            if(varp == null || varp.bits == null)
                continue;
            for(Varpbit bit : varp.bits)
                System.out.println("      bit: " + bit.id + "  shift: " + bit.leastSigBit);
        }
    }

    public static Set<ScriptDef> getScripts(int interfaceId, boolean recursiveSearch) {
        IndexFile idx = Server.fileStore.get(3);
        int lastFileId = idx.getLastFileId(interfaceId);
        if(lastFileId == -1)
            return null;
        Set<ScriptDef> scripts = Collections.newSetFromMap(new ConcurrentHashMap<>());
        for(int fileId = 0; fileId <= lastFileId; fileId++) {
            byte[] data = idx.getFile(interfaceId, fileId);
            if(data == null || data[0] != -1)
                continue;
            InBuffer in = new InBuffer(data);
            ArrayList<ScriptDef> childScripts = new ArrayList<>();
            int scriptListeners = 18, triggers = 3; //count based on client reads
            c: for(int offset = 0; offset < data.length; offset++) {
                in.position(offset);
                childScripts.clear();
                /**
                 * Read scripts
                 */
                for(int x = 0; x < scriptListeners; x++) {
                    try {
                        int length = in.readUnsignedByte();
                        if(length == 0)
                            continue;
                        Object[] objects = new Object[length];
                        int intCount = 0, strCount = 0;
                        for(int i = 0; i < length; i++) {
                            int type = in.readUnsignedByte();
                            if(type == 0) {
                                intCount++;
                                objects[i] = in.readInt();
                                continue;
                            }
                            if(type == 1) {
                                strCount++;
                                objects[i] = in.readString();
                                continue;
                            }
                            continue c;
                        }
                        int scriptId = (int) objects[0];
                        ScriptDef script = ScriptDef.get(scriptId);
                        if(script == null || intCount - 1 != script.anInt1364 || strCount != script.anInt1365)
                            continue c;
                        childScripts.add(script);
                    } catch(Throwable t) {
                        continue c;
                    }
                }
                /**
                 * Read triggers
                 */
                for(int x = 0; x < triggers; x++) {
                    int length = in.readUnsignedByte();
                    in.skip(length * 4);
                }
                /**
                 * Validate offset
                 */
                if(in.position() == data.length) {
                    if(!childScripts.isEmpty())
                        scripts.addAll(childScripts);
                    break;
                }
            }
        }
        boolean[] searched = new boolean[ScriptDef.LOADED.length];
        for(int a = 0; a < 100; a++)
        for(ScriptDef script : scripts) {
            if(searched[script.id])
                continue;
            searched[script.id] = true;
            s: for(int i = 0; i < script.instructions.length; i++) {
                int instruction = script.instructions[i];
                if(instruction == 40) {
                    /* include instruction */
                    if(recursiveSearch)
                        scripts.add(ScriptDef.get(script.intArgs[i]));
                } else if(instruction >= 1400 && instruction < 1500 || instruction >= 2400 && instruction < 2500) {
                    /* hook instruction */
                    int offset = i - 1;
                    if(instruction >= 2000)
                        offset--; //grabs interface
                    String string = script.stringArgs[offset--];
                    if(string == null)
                        string = "";
                    if(string.endsWith("Y")) {
                        int valueCount = script.intArgs[offset--];
                        offset -= valueCount;
                        string = string.substring(0, string.length() - 1);
                    }
                    int argCount = string.length();
                    for(int x = 0; x < argCount; x++) {
                        switch(script.instructions[offset]) {
                            case 0:     //push new int
                            case 3:     //push new string
                            case 33:    //push local int
                            case 35:    //push local string
                                break;
                            case 1702:  //???
                            case 2502:  //???
                            case 2505:  //???
                                break;
                            case 4000:  //???
                                offset -= 2;
                                break;
                            default:
                                System.out.println("Script (" + script.id + ") has Unhandled Instruction: [" + (offset+1) + "]=" + script.instructions[offset]);
                                continue s;
                        }
                        offset--;
                    }
                    int scriptId = script.intArgs[offset];
                    if(scriptId != -1)
                        scripts.add(ScriptDef.get(scriptId));
                }
            }
        }
        return scripts;
    }
}