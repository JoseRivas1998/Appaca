package edu.csuci.appaca.utils;

import java.util.List;

public final class ListUtils {

    public static int getMax(List<Integer> list) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) > max) {
                max = list.get(i);
            }
        }
        return max;
    }

    public static <T> T getMax(List<T> list, MapToInt<T> map) {
        int max = Integer.MIN_VALUE;
        int maxIndex = -1;
        for (int i = 0; i < list.size(); i++) {
            int val = map.toInt(list.get(i));
            if(val > max) {
                max = val;
                maxIndex = i;
            }
        }
        return list.get(maxIndex);
    }

    public interface MapToInt<T> {
        int toInt(T value);
    }

    // Yes, I am implementing JDK 8 stuff sue me
    public interface Consumer<T> {
        void accept(T t);
    }

}
