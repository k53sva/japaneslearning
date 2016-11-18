package com.melody.education.utils;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private HashMap<Integer, Boolean> mapConversation = new HashMap<>();
    public HashMap<String, List<Integer>> tempKanji = new HashMap<>();
    public HashMap<String, Integer> tempRomakj = new HashMap<>();

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

    public boolean getCacheConversation(int id) {
        if (mapConversation.get(id) != null) {
            return mapConversation.get(id);
        } else return false;
    }

    public void pushConversation(int id, boolean expand) {
        mapConversation.put(id, expand);
    }

    public void pushTempKanji(String id, List<Integer> position) {
        tempKanji.put(id, position);
    }

    public void pushTempRomaji(String id, Integer position) {
        tempRomakj.put(id, position);
    }

    public List<Integer> getTempKanji(String id) {
        if (tempKanji.get(id) != null)
            return tempKanji.get(id);
        else {
            List<Integer> l = new ArrayList<>();
            Stream.range(0, 4).forEach(i -> l.add(0));
            return l;
        }
    }

    public int getTempRomaji(String id) {
        if (tempRomakj.get(id) != null)
            return tempRomakj.get(id);
        else
            return 0;
    }

}
