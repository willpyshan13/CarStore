package com.car.baselib.cache;


import com.car.baselib.BaseLibCore;
import com.car.baselib.utils.SpUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SpCache {

    private static final String DEFAULT_FILE_NAME = BaseLibCore.getContext().getPackageName();
    private static final Map<String, SpUtils> spUtilsMap = new ConcurrentHashMap<>();

    public static SpUtils get() {
        return get(DEFAULT_FILE_NAME);
    }

    public static SpUtils get(String fileName) {
        SpUtils spUtils = spUtilsMap.get(fileName);
        if (spUtils == null) {
            spUtils = new SpUtils(fileName);
            spUtilsMap.put(fileName, spUtils);
        }
        return spUtils;
    }
}
