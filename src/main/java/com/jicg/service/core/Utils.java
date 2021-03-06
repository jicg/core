package com.jicg.service.core;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.jicg.service.core.annos.TableName;
import org.springframework.boot.system.ApplicationHome;

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
        String tableName = AnnotationUtil.getAnnotationValue(beanClass, TableName.class);
        return tableName.toLowerCase();
    }

    public static <T> List<T> readJsonFile2List(String filePath, Class<T> tClass) {
        String str = ResourceUtil.readUtf8Str(filePath);
        return JSONUtil.toList(str, tClass);
    }

    public static <T> T readJsonFile2Bean(String filePath, Class<T> tClass) {
        String str = ResourceUtil.readUtf8Str(filePath);
        return JSONUtil.toBean(str, tClass);
    }

    static String path = "";

    public static String ApplicationHomePath() {
        if (StrUtil.isNotEmpty(path)) return path;
        path= System.getProperty("user.dir");
//        path = new ApplicationHome(Utils.class).getSource().getParentFile().getAbsolutePath();
        return path;
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
