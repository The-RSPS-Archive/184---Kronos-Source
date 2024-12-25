package io.ruin.utility;

import com.google.gson.annotations.Expose;
import io.ruin.api.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TimedList {

    @Expose private List<Entry> list;

    public void add(int key, long ms) {
        if(list == null)
            list = new ArrayList<>();
        Entry e = new Entry();
        e.key = key;
        e.ms = ms;
        list.add(e);
    }

    public boolean contains(int key, long ms, long timeoutMinutes) {
        if(list == null)
            return false;
        long timeoutMs = TimeUtils.getMinutesToMillis(timeoutMinutes);
        boolean logged = false;
        for(Iterator<Entry> it = list.iterator(); it.hasNext(); ) {
            Entry log = it.next();
            if(ms - log.ms >= timeoutMs)
                it.remove();
            else if(log.key == key)
                logged = true;
        }
        if(list.isEmpty())
            list = null;
        return logged;
    }

    private static final class Entry {
        @Expose public int key;
        @Expose public long ms;
    }

}
