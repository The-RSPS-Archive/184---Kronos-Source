package io.ruin.api.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class NumberUtils {

    private static final DecimalFormat onePlace = new DecimalFormat("0.0");

    private static final DecimalFormat twoPlaces = new DecimalFormat("0.00");

    public static String formatOnePlace(double d) {
        return onePlace.format(d);
    }

    public static String formatTwoPlaces(double d) {
        return twoPlaces.format(d);
    }

    public static String formatNumber(long value) {
        return NumberFormat.getInstance().format(value);
    }

    public static int toInt(int b1, int b2, int b3, int b4) {
        int number = 0;
        number |= (((byte) b1) & 0xff) << 24;
        number |= (((byte) b2) & 0xff) << 16;
        number |= (((byte) b3) & 0xff) << 8;
        number |= (((byte) b4) & 0xff);
        return number;
    }

    public static long toLong(int b1, int b2, int b3, int b4, int b5, int b6, int b7, int b8) {
        long number = 0;
        number |= (long) (((byte) b1) & 0xff) << 56;
        number |= (long) (((byte) b2) & 0xff) << 48;
        number |= (long) (((byte) b3) & 0xff) << 40;
        number |= (long) (((byte) b4) & 0xff) << 32;
        number |= (((byte) b5) & 0xff) << 24;
        number |= (((byte) b6) & 0xff) << 16;
        number |= (((byte) b7) & 0xff) << 8;
        number |= (((byte) b8) & 0xff);
        return number;
    }

    public static int getByte(int hash, int pos) {
        int shift = 24 - (pos * 8);
        long value = hash >> shift;
        return ((byte) value) & 0xff;
    }

    public static int getByte(long hash, int pos) {
        int shift = 56 - (pos * 8);
        long value = hash >> shift;
        return ((byte) value) & 0xff;
    }

    public static int intValue(String s) {
        long value;
        char c = s.toLowerCase().charAt(s.length() - 1);
        if(Character.isLetter(c)) {
            value = Long.valueOf(s.substring(0, s.length() - 1));
            if(c == 'k')
                value *= 1000;
            else if(c == 'm')
                value *= 1000000;
            else
                value *= 1000000000;
        } else {
            value = Long.valueOf(s);
        }
        if(value < Integer.MIN_VALUE)
            value = Integer.MIN_VALUE;
        else if(value > Integer.MAX_VALUE)
            value = Integer.MAX_VALUE;
        return (int) value;
    }

    public static int[] toIntArray(String s) {
        String[] split = s.split(",");
        if(split.length <= 1)
            return new int[]{Integer.valueOf(s)};
        ArrayList<Integer> idList = new ArrayList<>(split.length);
        for(String id : split) {
            if((id = id.trim()).isEmpty())
                continue;
            idList.add(Integer.valueOf(id));
        }
        int[] ids = new int[idList.size()];
        for(int i = 0; i < ids.length; i++)
            ids[i] = idList.get(i);
        return ids;
    }

    public static boolean contains(int[] array, int target) {
        for(int value : array) {
            if(value == target)
                return true;
        }
        return false;
    }

    public static int intSum(long a, long b) {
        return (int) Math.min(a + b, Integer.MAX_VALUE);
    }

    public static int roundUpPowerOf2(int num) {
        --num;
        num |= num >>> 1;
        num |= num >>> 2;
        num |= num >>> 4;
        num |= num >>> 8;
        num |= num >>> 16;
        return num + 1;
    }

}