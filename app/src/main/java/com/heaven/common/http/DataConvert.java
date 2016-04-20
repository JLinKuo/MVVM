/** 
 * Project Name:JxAir 
 * File Name:GsonTools.java 
 * Package Name:com.neusoft.jxair.utils 
 * Date:2015年8月13日下午1:55:05 
 * Copyright (c) 2015, All Rights Reserved. 
 * 
 */

package com.heaven.common.http;

import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;
import com.heaven.common.util.LogUtil;


/**
 * ClassName:GsonTools <br/>
 * Function: 处理gson的工具 Reason: TODO ADD REASON. <br/>
 * Date: 2015年8月13日 下午1:55:05 <br/>
 * 
 * @author liu.hongtao
 * @version
 * @since JDK 1.6
 * @see
 */

public class DataConvert {
    static String TAG = DataConvert.class.getName();
    
    private static Lock lock = new ReentrantLock();// 锁对象  

    /*
     * 查看Gson api其中的fromJson(String json, Class<T> classOfT)方法 public <T> T
     * fromJson(String json, Class<T> classOfT)
     * 用来描述一个特殊的Json对象,使用反射机制可以将JSON字符串中包含的内容赋值给指定的类。这边的T表示的是通过泛型,也就是可以表示任意的类型。
     * 参数说明： json : 指定对象解析过的JSON字符串源,用来转换回Java对象。 classOfT ： 泛型 T 的Class对象。
     */
    public static <T> T getBeanObj(String gsonStr, Class<T> clazz) {
        T t = null;
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        t = gson.fromJson(gsonStr, clazz);
        return t;
    }

    public static synchronized <T> T getBeanObj(Reader reader, Class<T> clazz) {
        T t = null;
        lock.lock();// 得到锁
        try {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        t = gson.fromJson(reader, clazz);
        } finally {
            lock.unlock();
        }
        return t;
    }

    // 当需要特殊反序列化处理
    public static <T> T getBeanObjByDeserializer(String gsonStr, Class<T> clazz, JsonDeserializer<T> deserializer) {
        T t = null;
        // Configure Gson
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(clazz, deserializer);
        Gson gson = gsonBuilder.create();
        // Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        t = gson.fromJson(gsonStr, clazz);
        return t;
    }

    public static synchronized <T> T getBeanObjByDeserializer(Reader reader, Class<T> clazz, JsonDeserializer<T> deserializer) {
        T t = null;
        // Configure Gson
        lock.lock();// 得到锁  
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(clazz, deserializer);
            Gson gson = gsonBuilder.create();
            // Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            t = gson.fromJson(reader, clazz);
        } finally {
            lock.unlock();
        }
        return t;
    }

    /*
     * List 我们使用的Gson中的 public <T> T fromJson(String json, Type typeOfT)
     * 这边我们需要取到List<T>中不同的对象，普通的实现方式就如上一讲中org.Json库来解析JSON的方式一样， 这里我们通过 Gson中的
     * TypeToken类是简便操作：这边typeOfT的用法是通过反射机制把T里面的对象的属性的值映射出来，
     * 然后通过将json字符串取得的值赋值上去就可以了。
     * getType()的意思就是表示将jsonString的字符串解析出来之后封装到List集合中，然后分别从T里面取出类型将其复制。
     */

    public static <T> List<T> getListBeanObj(String gsonStr, Class<T> clazz) {
        List<T> objList = new ArrayList<T>();
        Gson gson = new Gson();
        Type type = new TypeToken<List<T>>() {
        }.getType();
        objList = gson.fromJson(gsonStr, type);
        return objList;
    }

    public static List<String> getStringList(String jsonString) {
        List<String> list = new ArrayList<String>();
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {
        }.getType();
        list = gson.fromJson(jsonString, type);
        return list;
    }

    // 取得mapList
    public static List<Map<String, Object>> getListMaps(String jsonString, Class clazz) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Gson gson = new Gson();
        Type type = new TypeToken<List<Map<String, Object>>>() {
        }.getType();
        list = gson.fromJson(jsonString, type);
        return list;
    }

    // 把对象转换成gson串
    public static synchronized String invertToGsonStr(Object obj) {
        String reqStr = "";
        try {
        Gson gson = new Gson();
        reqStr = gson.toJson(obj);
        LogUtil.v("Heaven", "ReqJson---" + reqStr);
        reqStr = URLEncoder.encode(reqStr, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return reqStr;
    }
}
