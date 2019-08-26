package edu.xalead.taobao.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author: HuYi.Zhang
 * @create: 2018-04-24 17:20
 **/
public class JsonUtils {

    public static final ObjectMapper mapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    @Nullable
    public static String serialize(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj.getClass() == String.class) {
            return (String) obj;
        }
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("json序列化出错：" + obj, e);
            return null;
        }
    }

    @Nullable
    public static <T> T parse(String json, Class<T> tClass) {
        try {
            return mapper.readValue(json, tClass);
        } catch (IOException e) {
            logger.error("json解析出错：" + json, e);
            return null;
        }
    }

    @Nullable
    public static <E> List<E> parseList(String json, Class<E> eClass) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, eClass));
        } catch (IOException e) {
            logger.error("json解析出错：" + json, e);
            return null;
        }
    }

    @Nullable
    public static <K, V> Map<K, V> parseMap(String json, Class<K> kClass, Class<V> vClass) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructMapType(Map.class, kClass, vClass));
        } catch (IOException e) {
            logger.error("json解析出错：" + json, e);
            return null;
        }
    }

    @Nullable
    public static <T> T nativeRead(String json, TypeReference<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            logger.error("json解析出错：" + json, e);
            return null;
        }
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class User{
        String name;
        Integer age;
    }
    public static void main(String[] args) {
        User u = new User("张三",22);
        System.out.println(serialize(u));
        String json = "{\"name\":\"张三\",\"age\":22}";
        System.out.println(parse(json,User.class));
        json = "[{\"name\":\"张三\",\"age\":22},{\"name\":\"张三\",\"age\":22},{\"name\":\"张三\",\"age\":22}]";
        List<User> list = parseList(json, User.class);
        System.out.println(list);
        json = "{\"name\":\"李四\",\"age\":22}";
        Map<String, Object> map = parseMap(json, String.class, Object.class);
        System.out.println("map:" + map);
        json = "[{\"name\":\"李四\",\"age\":22},{\"name\":\"李四\",\"age\":22}]";
        List<Map<String, Object>> maps = nativeRead(json, new TypeReference<List<Map<String, Object>>>() {
        });
        for (Map<String, Object> m : maps) {
            System.out.println(m);
        }
    }
}
