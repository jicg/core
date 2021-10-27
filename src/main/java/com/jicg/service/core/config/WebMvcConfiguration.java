package com.jicg.service.core.config;

import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
import cn.hutool.core.util.ArrayUtil;
import com.jicg.service.core.manager.ManagerController;
import com.jicg.service.core.manager.StaticController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author jicg on 2021/4/11
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebMvcConfiguration implements WebMvcRegistrations {
    @Autowired
    AppProperties appProperties;
    public WebMvcConfiguration() {
    }


    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new WebRequestMappingHandlerMapping(appProperties);
    }

    public static class WebRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
        AppProperties appProperties;
        public WebRequestMappingHandlerMapping(AppProperties appProperties) {
            this.appProperties = appProperties;
        }

        @Override
        public void afterPropertiesSet() {
            super.afterPropertiesSet();
        }

        @Override
        protected RequestMappingInfo getMappingForMethod(Method method, Class handlerType) {
            RequestMappingInfo info = super.getMappingForMethod(method, handlerType);
            if (handlerType == StaticController.class) {
                if (method.getName().equals("index")) {
                    Set<String> paths = info.getDirectPaths();
                    paths.add(appProperties.getUrl());
                    return RequestMappingInfo
                            .paths(ArrayUtil.toArray(paths, String.class)).build();//.combine(info);
                }
            }
            return info;
        }
    }
}


