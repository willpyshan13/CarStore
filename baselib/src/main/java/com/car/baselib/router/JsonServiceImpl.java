package com.car.baselib.router;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.SerializationService;
import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * JsonServiceImpl
 * <p>
 * Description
 * Used for json converter
 * 添加JsonServiceImpl 实现ARouter之间传递list<Bean>
 * ARouter调用withObject();
 * @author mashizhao304
 * @version 1.0
 * <p>
 * Ver 1.0, 2018/8/2, mashizhao304, Create file
 */

@Route(path = "/service/json")
public class JsonServiceImpl implements SerializationService {

    @Override
    public void init(Context context) {

    }

    @Override
    public <T> T json2Object(String text, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(text,clazz);
    }

    @Override
    public String object2Json(Object instance) {
        Gson gson = new Gson();
        return gson.toJson(instance);

    }

    @Override
    public <T> T parseObject(String input, Type clazz) {
        Gson gson = new Gson();
        return gson.fromJson(input,clazz);
    }

}
