package com.jicg.service.core.config;

import cn.hutool.core.collection.ListUtil;
import com.jicg.service.core.Utils;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

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
        private String savePath = Utils.ApplicationHomePath() + "/data/job.json";
        private List<String> packages = ListUtil.of();
    }
}
