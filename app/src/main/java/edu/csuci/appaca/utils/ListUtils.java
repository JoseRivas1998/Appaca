package edu.csuci.appaca.utils;

import com.badlogic.gdx.math.MathUtils;

import java.util.List;
import java.util.Map;

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

    public static <K, V> V getOrDefault(Map<K, V> map, K key, V defaultValue) {
        if(map.containsKey(key)) {
            return map.get(key);
        }
        return defaultValue;
    }

    public static <T> T choose(T... choices) {
        if(choices.length == 0) return null;
        return choices[MathUtils.random(choices.length - 1)];
    }

    public static <T> T choose(List<T> choices) {
        if(choices.size() == 0) return null;
        return choices.get(MathUtils.random(choices.size() - 1));
    }

    public interface MapToInt<T> {
        int toInt(T value);
    }

    // Yes, I am implementing JDK 8 stuff sue me
    public interface Consumer<T> {
        void accept(T t);
    }

    public interface DuelConsumer<K, V> {
        void accept(K k, V v);
    }

}
