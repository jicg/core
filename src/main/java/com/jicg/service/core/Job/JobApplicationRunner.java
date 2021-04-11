package  com.jicg.service.core.Job;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.json.JSONUtil;
import  com.jicg.service.core.Job.bean.BaseJobAction;
import  com.jicg.service.core.Job.bean.JobInfo;
import  com.jicg.service.core.Utils;
import  com.jicg.service.core.annos.JobDisable;
import  com.jicg.service.core.annos.JobJava;
import com.jicg.service.core.config.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author jicg on 2021/3/21
 */
@Slf4j
@Component
public class JobApplicationRunner implements ApplicationRunner {
    private final AppConfig appConfig;
    private final JobService jobService;
    public static final String NAME_KEY = "NAME_KEY";
    public static final String DATA_KEY = "DATA_KEY";
    public static final String ORDER_KEY = "ORDER_KEY";

    public static boolean isInted = false;


    public static ApplicationContext applicationContext;

    public JobApplicationRunner(AppConfig appConfig, JobService jobService, ApplicationContext applicationContext) {
        this.appConfig = appConfig;
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
            jobInfos = Utils.readJsonFile2List(appConfig.getJob().getSavePath(), JobInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getLocalizedMessage(), e);
        }
        if (jobInfos.size() <= 0 && appConfig.getJob().getPackages().size() > 0) {
            List<JobInfo> finalJobInfos = jobInfos;
            appConfig.getJob().getPackages().forEach(pag -> finalJobInfos.addAll(getScanBean(pag)));
        }
        jobService.addJobs(jobInfos);
        log.info("jobs初始化完成！！");
        isInted = true;
    }
}