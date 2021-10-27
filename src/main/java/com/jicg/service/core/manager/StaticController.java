package com.jicg.service.core.manager;

import cn.hutool.cache.file.LFUFileCache;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.jicg.service.core.config.AppProperties;
import com.jicg.service.core.config.auth.CoreStpUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author jicg on 2021/10/21
 */
@Controller
@Slf4j
public class StaticController {
    final AppProperties.AuthConfig authConfig;
    final static LFUFileCache cache = new LFUFileCache(1000, 1024 * 10, 1);

    public StaticController(AppProperties.AuthConfig authConfig) {
        this.authConfig = authConfig;
    }


    @GetMapping(path = "/manager/")
    public String index() {
        return "redirect:/manager/index.html";
    }

    @GetMapping("/sys/manager/extends.js")
    @ResponseBody
    public String getJS() {
        try {
            byte[] bytes = cache.getFileBytes(FileUtil.file("system/extends.js"));
            return StrUtil.str(bytes, "UTF8");
        } catch (Exception e) {
            return "";
        }
    }

    @PostMapping("/sys/manager/login")
    @ResponseBody
    public String login(@RequestBody UserVo userVo) throws Exception {
        try {
            switch (authConfig.getType()) {
                case DB:
                    if (true)
                        throw new RuntimeException("暂时不支持，数据库登陆");
                    break;
                case Simple:
                    if (StrUtil.equals(authConfig.getUser(), userVo.name) && StrUtil.equals(authConfig.getPwd(), userVo.pwd)) {
                        CoreStpUtil.login(userVo.name);
                    } else {
                        throw new RuntimeException("用户或密码不对");
                    }
                    break;
                default:
                    throw new RuntimeException("占不支持");
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            throw e;
        }
        return "ok";
    }

    @Data
    public static class UserVo {
        private String name;
        private String pwd;
    }
}
