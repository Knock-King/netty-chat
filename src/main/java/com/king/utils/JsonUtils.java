package com.king.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hemiao
 * @Description:json转换类
 * @time:2017年4月13日 上午10:39:49
 */
public class JsonUtils {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 将对象转换成json字符串。
     * <p>Title: pojoToJson</p>
     * <p>Description: </p>
     *
     * @param data
     * @return
     */
    public static String objectToJson(Object data) {

        String str = "";
        try {
            str = MAPPER.writeValueAsString(data);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 将json结果集转化为对象
     *
     * @param jsonData json数据
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            T t = MAPPER.readValue(jsonData, beanType);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json数据转换成pojo对象list
     * <p>Title: jsonToList</p>
     * <p>Description: </p>
     *
     * @param jsonData
     * @param beanType
     * @return
     */
    @SuppressWarnings("deprecation")
    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            List<T> list = MAPPER.readValue(jsonData, javaType);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("f3efe");
        list.add("f3ef3e");
        list.add("f32efe");
        list.add("f311111efe");

        String s = objectToJson(list);
        System.out.println(s);
        List list1 = jsonToPojo(s, List.class);
        System.out.println(list1);
        List<String> list2 = jsonToList(s, String.class);
        System.out.println(list2);

    }
}
