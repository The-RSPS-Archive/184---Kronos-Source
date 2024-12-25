package io.ruin.api.utils;

public class ArrayUtils {

    @SafeVarargs
    public static <T> T[] of(T... values) {
        return values;
    }

    public static int indexOf(String rightClickOption, String... array) {
        for(int index = 0;index<array.length;index++){
            if(array[index] != null && array[index].equals(rightClickOption))
                return index;
        }
        return -1;
    }

    public static int indexOfIgnoreCase(String rightClickOption, String... array) {
        for(int index = 0;index<array.length;index++){
            if(array[index] != null && array[index].equalsIgnoreCase(rightClickOption))
                return index;
        }
        return -1;
    }
}
