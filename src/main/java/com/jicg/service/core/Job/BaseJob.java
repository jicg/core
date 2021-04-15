package com.jicg.service.core.Job;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.jicg.service.core.Job.bean.BaseJobAction;
import com.jicg.service.core.Job.bean.JobInfo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author jicg on 2021/3/21
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Slf4j
public class BaseJob implements Job, InterruptableJob {

    @SneakyThrows
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        if (!JobApplicationRunner.isInted) {
            String name = context.getMergedJobDataMap().getString(JobApplicationRunner.NAME_KEY);
            log.error("任务【" + name + "】----程序还未初始化成功，不执行---");
            return;
        }
        String name = context.getMergedJobDataMap().getString(JobApplicationRunner.NAME_KEY);
        String json = context.getMergedJobDataMap().getString(JobApplicationRunner.DATA_KEY);
        if (StrUtil.isNotEmpty(json) && JSONUtil.isJson(json)) {
            BaseJobAction actionInfo = JSONUtil.toBean(json, BaseJobAction.class);
            if (StrUtil.isAllNotEmpty(actionInfo.getJavaClass(), actionInfo.getJavaMethod())) {
                log.info("任务【" + name + "】开始");
                invoker(actionInfo);
                log.info("任务【" + name + "】结束");
            } else {
                log.error("任务【" + name + "】非法，没有指定类或class");
            }
        }

    }

    private void invoker(BaseJobAction actionInfo) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ApplicationContext wac = JobApplicationRunner.applicationContext;
        if (wac != null) {
            Class<?> classType = ClassUtil.loadClass(actionInfo.getJavaClass());
            Object data = wac.getBean(classType);
            Method method = classType.getDeclaredMethod(actionInfo.getJavaMethod());
            Object call = method.invoke(data, actionInfo.getJavaArgs().toArray());
        } else {
            ClassUtil.invoke(actionInfo.getJavaClass(), actionInfo.getJavaMethod(), actionInfo.getJavaArgs().toArray());
        }
    }


    @Override
    public void interrupt() throws UnableToInterruptJobException {

    }
}
