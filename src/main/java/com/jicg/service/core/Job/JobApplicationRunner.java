package com.jicg.service.core.Job;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.jicg.service.core.Job.bean.BaseJobAction;
import com.jicg.service.core.Job.bean.JobInfo;
import com.jicg.service.core.Utils;
import com.jicg.service.core.annos.JobDisable;
import com.jicg.service.core.annos.JobJava;
import com.jicg.service.core.config.AppConfig;
import com.jicg.service.core.config.AppProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author jicg on 2021/3/21
 */
@Slf4j
@Component
@Order(200)
public class JobApplicationRunner implements ApplicationRunner {
    private final AppProperties.JobConfig jobConfig;
    private final JobService jobService;
    public static final String NAME_KEY = "NAME_KEY";
    public static final String DATA_KEY = "DATA_KEY";
    public static final String ORDER_KEY = "ORDER_KEY";

    public static boolean isInted = false;


    public static ApplicationContext applicationContext;

    public JobApplicationRunner(AppProperties.JobConfig jobConfig, JobService jobService, ApplicationContext applicationContext) {
        log.info(JSONUtil.toJsonStr(jobConfig));
        this.jobConfig = jobConfig;
        this.jobService = jobService;
        JobApplicationRunner.applicationContext = applicationContext;
    }

    public static List<JobInfo> getScanBean(String basePackage) {
        List<JobInfo> jobInfos = new ArrayList<>();
        for (Class<?> aClass : ClassUtil.scanPackage(basePackage)) {
            JobDisable jobDisable = aClass.getAnnotation(JobDisable.class);
            if (jobDisable != null) continue;
            Method[] methods = aClass.getDeclaredMethods();
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
                JobJava data = method.getDeclaredAnnotation(JobJava.class);
                if (data == null) continue;
                BaseJobAction action = new BaseJobAction();
                action.setJavaClass(aClass.getName());
                action.setJavaMethod(method.getName());
                action.setJavaArgs(Arrays.asList(method.getParameters().clone()));
                jobInfos.add(
                        JobInfo.builder().name(data.name())
                                .jobName(data.jobName())
                                .groupName(data.groupName())
                                .javaClass(BaseJob.class.getName())
                                .javaData(JSONUtil.toJsonStr(action))
                                .order(data.order())
                                .cron(data.cron())
                                .remark(data.remark()).build()
                );
            }
        }

        return jobInfos;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<JobInfo> jobInfos = new ArrayList<>();
        try {
            jobInfos = Utils.readJsonFile2List(jobConfig.getSavePath(), JobInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getLocalizedMessage(), e);
        }
        List<JobInfo> jobInfoScans = new ArrayList<>();
        if (jobConfig.getPackages().size() > 0) {
            jobConfig.getPackages().forEach(pag -> jobInfoScans.addAll(getScanBean(pag)));
        }

        List<JobInfo> finalJobInfos = jobInfos;
        jobInfos.removeIf(jobInfo -> !jobInfoScans.contains(jobInfo));
        jobInfos.addAll(jobInfoScans.stream().filter(jobInfo -> !finalJobInfos.contains(jobInfo)).collect(Collectors.toList()));
        jobService.addJobs(jobInfos);
        log.info("?????????" + jobInfos.stream().map(JobInfo::getName).collect(Collectors.joining(",")) + "??????????????? ??????");
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                isInted = true;
                log.info("jobs?????????????????????");
            }
        }, 3000);
    }
}
