package com.jicg.service.core.config.auth;

import cn.hutool.core.net.Ipv4Util;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.StrUtil;
import com.jicg.service.core.IpUtils;
import com.jicg.service.core.config.AppProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author jicg on 2021/10/27
 */
@Slf4j
public class IpInterceptor implements HandlerInterceptor {
    private final AppProperties.IpFilter ipFilter;

    public IpInterceptor(AppProperties.IpFilter ipFilter) {
        this.ipFilter = ipFilter;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddress = IpUtils.getIpAddr(request);
        log.info(ipAddress);
        if (!ipFilter.isEnable()) {
            return true;
        }
        List<String> cidr = ipFilter.getCidr();
        for (String c : cidr) {
            if (NetUtil.isInRange(ipAddress, c)) {
                return true;
            }
        }

        if (ipFilter.getIps().contains(ipAddress)) {
            return true;
        }
        response.getWriter().append("<h1 style=\"text-align:center;\">Not allowed!</h1>");
        return false;
    }
}
