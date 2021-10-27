package com.jicg.service.core.config;

import cn.hutool.core.collection.ListUtil;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jicg.service.core.Job.JobApplicationRunner;
import com.jicg.service.core.Job.JobController;
import com.jicg.service.core.Job.JobService;
import com.jicg.service.core.config.auth.CoreStpUtil;
import com.jicg.service.core.config.auth.SaTokenConfigure;
import com.jicg.service.core.config.auth.SaTokenDaoRocksdb;
import com.jicg.service.core.exts.OracleClobSerializer;
import com.jicg.service.core.manager.GlobalErrorHandler;
import com.jicg.service.core.manager.ManagerApplicationRunner;
import com.jicg.service.core.manager.ManagerController;
import com.jicg.service.core.manager.StaticController;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.sql.Clob;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author jicg on 2021/4/1
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(AppProperties.class)
@Import({
        JobService.class,
        JobController.class, JobApplicationRunner.class, GlobalErrorHandler.class,
        ManagerController.class, ManagerApplicationRunner.class,
        StaticController.class, SaTokenDaoRocksdb.class, SaTokenConfigure.class, CoreStpUtil.class,
        WebMvcConfiguration.class,

})


public class AppConfig {
    final AppProperties appProperties;

    public AppConfig(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    //    @Bean
//    public ObjectMapper getJson() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        SimpleModule module = new SimpleModule();
//        module.addSerializer(Clob.class, new OracleClobSerializer());
//        objectMapper.registerModule(module);
//        DateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
//        objectMapper.setDateFormat(df);
//        return objectMapper;
//    }
    @Bean
    public Module dynamoClobDeserializer() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Clob.class, new OracleClobSerializer());
        return module;
    }

    @Bean
    @ConditionalOnMissingBean
    public AppProperties.JobConfig getJob() {
        return appProperties.getJob();
    }

    @Bean
    @ConditionalOnMissingBean
    public AppProperties.AuthConfig getAuth() {
        return appProperties.getAuth();
    }

    @Bean
    @ConditionalOnMissingBean
    public AppProperties.IpFilter getIpFilter() {
        return appProperties.getIpFilter();
    }

}
