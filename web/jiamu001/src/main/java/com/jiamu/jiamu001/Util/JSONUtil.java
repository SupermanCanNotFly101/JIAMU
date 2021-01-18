package com.jiamu.jiamu001.Util;

import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * JSON工具类
 */
@Component
public class JSONUtil {

    /**
     * 工具类： 从json字符串获取Url数组
     * @param url
     * @return
     */
    public static List<String> getUrlList(String url){
        JSONArray urlList = JSONArray.fromObject(url);
        List tempList = new LinkedList();
        for(int i = 0;i<urlList.size();i++){
            tempList.add(urlList.getJSONObject(i).get("url"));
        }
        return tempList;
    }

    /**
     * 获取利用反射获取类里面的值和名称，返回map
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = obj.getClass();
        //System.out.println(clazz);
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            map.put(fieldName, value);
        }
        return map;
    }

}
