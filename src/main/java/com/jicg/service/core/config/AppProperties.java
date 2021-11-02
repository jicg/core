package com.jicg.service.core.config;

import cn.hutool.core.collection.ListUtil;
import com.jicg.service.core.Utils;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jicg on 2021/4/11
 */
@ConfigurationProperties(prefix = "app")
@Data
public class AppProperties {
    private JobConfig job = new JobConfig();
    private String url = "/";
    private String title = "管理";
    private AuthConfig auth = new AuthConfig();
    private IpFilter ipFilter = new IpFilter();


    @Data
    public static class JobConfig {
        private String savePath = Utils.ApplicationHomePath() + "/data/job.json";
        private List<String> packages = ListUtil.of();
    }

    @Data
    public static class AuthConfig {
        private boolean enable = false;
        private AutoType type = AutoType.Simple;
        private String user = "admin";
        private String pwd = "123456";
        private String findUserSql = "select id,email as user,passwordhash as pwd from users where email = ? ";
    }

    @Data
    public static class IpFilter {
        private boolean enable = false;
        private List<String> ips = new ArrayList<>();
        private List<String> cidr = new ArrayList<>();
        private List<String> excludePathPatterns = new ArrayList<>();
    }

    public enum AutoType {
        DB, Simple
    }
}
