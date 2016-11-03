package com.melody.education.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by K53SV on 10/7/2016.
 */

public class DataCache {
    public static final int KEY_CONTRACT = 0;
    private static DataCache instance;

    public static DataCache getInstance() {
        if (instance == null) {
            synchronized (DataCache.class) {
                if (instance == null) {
                    instance = new DataCache();
                }
            }
        }
        return instance;
    }

    private Map<Class<?>, Object> dataMap;
    public Map<Integer, Object> dataListMap = new HashMap<>();

    public <T> void push(T data) {
        if (dataMap == null) {
            dataMap = new HashMap<>();
        }

        dataMap.put(data.getClass(), data);
    }

    public <T> T pop(Class classType) {
        T value = (T) dataMap.get(classType);
        dataMap.remove(classType);
        return value;
    }

    public <T> void push(int position, T data) {
        if (dataListMap == null) {
            dataListMap = new HashMap<>();
        }

        dataListMap.put(position, data);
    }

    public <T> T pop(int position) {
        if (dataListMap.size() == 0)
            return null;
        T value = (T) dataListMap.get(position);
        return value;
    }
}
