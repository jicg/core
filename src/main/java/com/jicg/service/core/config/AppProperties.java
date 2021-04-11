package com.jicg.service.core.config;

import cn.hutool.core.collection.ListUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author jicg on 2021/4/11
 */
@ConfigurationProperties(prefix = "app")
@Data
public class AppProperties {
    private JobConfig job = new JobConfig();

    private String url = "/";

    @Data
    public static class JobConfig {
        private String savePath = "./job.json";
        private List<String> packages = ListUtil.of();
    }
}
