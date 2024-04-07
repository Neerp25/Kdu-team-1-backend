package com.kdu.ibebackend.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapUtils {
    public static <K, V> Map<K, V> trimMap(Map<K, V> map, int maxSize) {
        if (map.size() <= maxSize) {
            return map;
        }

        LinkedHashMap<K, V> trimmedMap = new LinkedHashMap<>(maxSize);

        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (trimmedMap.size() >= maxSize) {
                break;
            }
            trimmedMap.put(entry.getKey(), entry.getValue());
        }

        return trimmedMap;
    }
}
