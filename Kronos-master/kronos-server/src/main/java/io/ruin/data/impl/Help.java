package io.ruin.data.impl;

import com.google.gson.annotations.Expose;
import io.ruin.api.utils.JsonUtils;
import io.ruin.data.DataFile;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Help extends DataFile {

    private static OptionScroll SCROLL;

    private static Map<String, Option> SCROLL_MAP = new HashMap<>();

    @Override
    public String path() {
        return "help_" + World.type.name().toLowerCase() + ".json";
    }

    @Override
    public Object fromJson(String fileName, String json) {
        List<Entry> entries = JsonUtils.fromJson(json, List.class, Entry.class);
        Option[] options = new Option[entries.size()];
        for(int i = 0; i < entries.size(); i++) {
            Entry entry = entries.get(i);
            Option option = new Option("<col=735a28>" + (i + 1) + ".</col> " + entry.title, p -> p.sendScroll("<col=880000>" + entry.title, entry.message));
            if(entry.key != null && !entry.key.isEmpty())
                SCROLL_MAP.put(entry.key, option);
            options[i] = option;
        }
        SCROLL = new OptionScroll(World.type.getWorldName() + " Help", false, options);
        return entries;
    }

    public static void open(Player player) {
        SCROLL.open(player);
    }

    public static void open(Player player, String key) {
        Option option = SCROLL_MAP.get(key);
        if(option == null) {
            player.sendMessage("Help '" + key + "' does not exist.");
            return;
        }
        option.consumer.accept(player);
    }

    public static void open(Player player, int id) {
        int index = id - 1;
        if(index < 0 || index >= SCROLL.options.length) {
            player.sendMessage("Help #" + id + " does not exist.");
            return;
        }
        SCROLL.options[index].consumer.accept(player);
    }

    private static final class Entry {
        @Expose public String key;
        @Expose public String title;
        @Expose public String[] message;
    }

}