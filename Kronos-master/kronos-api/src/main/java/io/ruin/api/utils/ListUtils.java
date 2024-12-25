package io.ruin.api.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ListUtils {

    @SafeVarargs
    public static <T> List<T> toList(T... t) {
        if(t.length == 0)
            return null;
        if(t.length == 1)
            return Collections.singletonList(t[0]);
        return Arrays.asList(t);
    }

}
