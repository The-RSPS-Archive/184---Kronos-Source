package io.ruin.cache;

import io.ruin.Server;
import io.ruin.api.buffer.InBuffer;
import io.ruin.api.filestore.IndexFile;

import java.io.PrintStream;
import java.util.HashMap;

public class ScriptDef {

    public static ScriptDef[] LOADED;

    public static void load() {
        IndexFile index = Server.fileStore.get(12);
        LOADED = new ScriptDef[index.getLastArchiveId() + 1];
        for(int id = 0; id < LOADED.length; id++) {
            byte[] data = index.getFile(id, 0);
            if(data == null) {
              //  System.err.println("CS2 " + id + " has null data!");
                continue;
            }
            ScriptDef def = new ScriptDef();
            def.id = id;
            def.decode(data);
            LOADED[id] = def;
        }
    }

    public static ScriptDef get(int id) {
        if(id < 0 || id >= LOADED.length)
            return null;
        return LOADED[id];
    }

    public void print(PrintStream ps) {
        ps.println("Script " + id + " Instructions:");
        for(int i = 0; i < instructions.length; i++) {
            int instructionId = instructions[i];
            String instructionName = INSTRUCTIONS.get(instructionId);
            if(instructionName == null)
                instructionName = "?" + instructionId;
            StringBuilder sb = new StringBuilder(instructionName);
            if(stringArgs[i] != null)
                sb.append("(s:\"").append(stringArgs[i]).append("\")");
            else
                sb.append("(i:").append(intArgs[i]).append(")");
            ps.println("[" + i + "] " + sb.toString());
        }
    }

    /**
     * Separator
     */

    public int id;
    int intArgumentCount;
    public String[] stringArgs;
    public int[] instructions;
    int stringArgumentCount;
    int anInt1364;
    int anInt1365;
    public int[] intArgs;

    private void decode(byte[] data) {
        InBuffer buffer = new InBuffer(data);
        buffer.position(data.length - 2);
        int i_13_ = buffer.readUnsignedShort();
        int i_14_ = data.length - 2 - i_13_ - 12;
        buffer.position(i_14_);
        int i_32_ = buffer.readInt();
        intArgumentCount = buffer.readUnsignedShort();
        stringArgumentCount = buffer.readUnsignedShort();
        anInt1364 = buffer.readUnsignedShort();
        anInt1365 = buffer.readUnsignedShort();
        int someCount = buffer.readUnsignedByte();
        if(someCount > 0) {
            for(int i = 0; i < someCount; i++) {
                int i_18_ = buffer.readUnsignedShort();
                while(i_18_-- > 0) {
                    buffer.readInt();
                    buffer.readInt();
                }
            }
        }
        buffer.position(0);
        buffer.readSafeString();
        instructions = new int[i_32_];
        intArgs = new int[i_32_];
        stringArgs = new String[i_32_];
        int i_33_ = 0;
        while(buffer.position() < i_14_) {
            int i_34_ = buffer.readUnsignedShort();
            if(i_34_ == 3)
                stringArgs[i_33_] = buffer.readString();
            else if(i_34_ < 100 && i_34_ != 21 && i_34_ != 38 && i_34_ != 39)
                intArgs[i_33_] = buffer.readInt();
            else
                intArgs[i_33_] = buffer.readUnsignedByte();
            instructions[i_33_++] = i_34_;
        }
    }

    /**
     * Identified instructions
     */

    private static HashMap<Integer, String> INSTRUCTIONS = new HashMap<Integer, String>() {
        {
            put(0, "push_int");
            put(1, "push_config");
            put(2, "pop_config");
            put(3, "push_string");
            put(6, "jump_relative");
            put(7, "jump_ne");
            put(8, "jump_eq");
            put(9, "jump_lt");
            put(10, "jump_gt");
            put(21, "return");
            put(25, "push_varbit");
            put(27, "set_varbit");
            put(31, "jump_lte");
            put(32, "jump_gte");
            put(33, "push_intvar");
            put(34, "pop_intvar");
            put(35, "push_stringvar");
            put(36, "pop_stringvar");
            put(37, "strcat");
            put(38, "popint");
            put(39, "popstring");
            put(40, "run_script");
            put(42, "push_script_int");
            put(43, "pop_script_int");
            put(47, "push_script_str");
            put(48, "pop_script_str");
            put(2003, "set_hidden");
            put(2112, "set_interface_string");
            put(2419, "set_keypress_script");
            put(2423, "set_open_script");
            put(2504, "push_hidden");
            put(3301, "get_container_item");
            put(3302, "get_container_amount");
            put(3313, "get_alt_container_item");
            put(3314, "get_alt_container_amount");
            put(3400, "get_enum_str");
            put(3408, "get_enum_val");
            put(4014, "bit_and");
            put(4010, "check_bit");
        }
    };

}
