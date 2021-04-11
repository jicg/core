package  com.jicg.service.core;

import cn.hutool.cache.CacheUtil;
import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.convert.ConverterRegistry;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import  com.jicg.service.core.annos.TableName;
import sun.nio.ch.IOUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author jicg on 2021/3/16
 */

public class Utils {


    final static Map<Class<?>, String> tableNameCache = new HashMap<>();

    public static String getTableName(Class<?> beanClass) {
        if (tableNameCache.containsKey(beanClass)) return tableNameCache.get(beanClass);
//        System.out.println("" + AnnotationUtil.getAnnotationValue(beanClass, TableName.class));
        return AnnotationUtil.getAnnotationValue(beanClass, TableName.class);
    }

    public static <T> List<T> readJsonFile2List(String filePath, Class<T> tClass) {
        String str = ResourceUtil.readUtf8Str(filePath);
        return JSONUtil.toList(str, tClass);
    }

    public static <T> T readJsonFile2Bean(String filePath, Class<T> tClass) {
        String str = ResourceUtil.readUtf8Str(filePath);
        return JSONUtil.toBean(str, tClass);
    }


    public static <T> void writeJsonFile2Bean(String filePath, Object data) {
        FileUtil.writeUtf8String(JSONUtil.toJsonStr(data), filePath);
    }


    public static <T> List<T> json2ListByJackson(String str, Class<T> tClass) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, tClass);
        return mapper.readValue(str, listType);
    }

    public static String jsonObj2StrByJackson(Object data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(data);
    }



}
