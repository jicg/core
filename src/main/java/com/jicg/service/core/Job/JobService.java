package com.jicg.service.core.Job;


import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.jicg.service.core.Job.bean.JobInfo;
import com.jicg.service.core.Utils;
import com.jicg.service.core.config.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.jicg.service.core.Job.JobApplicationRunner.*;

@Slf4j
@Service

public class JobService {
    private final AppConfig appConfig;


    private final Scheduler scheduler;

    @Autowired
    public JobService(Scheduler scheduler, AppConfig appConfig) {
        this.scheduler = scheduler;
        this.appConfig = appConfig;
    }

    public void addJob(JobInfo jobInfo) throws Exception {
        // 构建Job信息
        addJobHelp(jobInfo);
        saveToFile();
    }

    public void addJobs(List<JobInfo> jobInfos) throws Exception {
        for (JobInfo jobInfo : jobInfos) {// 构建Job信息
            addJobHelp(jobInfo);
        }
        saveToFile();
    }

    private void addJobHelp(JobInfo jobInfo) throws Exception {
        JobDetail jobDetail =
                JobBuilder.newJob(ClassUtil.loadClass(jobInfo.getJavaClass()))
                        .withIdentity(jobInfo.getJobName(), jobInfo.getGroupName())
                        .withDescription(jobInfo.getRemark())
                        .build();
        jobDetail.getJobDataMap().put(NAME_KEY, jobInfo.getName());
        jobDetail.getJobDataMap().put(DATA_KEY, jobInfo.getJavaData());
        jobDetail.getJobDataMap().put(ORDER_KEY, jobInfo.getOrder());
        // Cron表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder cron = CronScheduleBuilder.cronSchedule(jobInfo.getCron());
        //根据Cron表达式构建一个Trigger
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobInfo.getJobName(), jobInfo.getGroupName())
                .withSchedule(cron).build();


        if (StrUtil.isNotEmpty(jobInfo.getStatus())
                && Trigger.TriggerState.PAUSED == Trigger.TriggerState.valueOf(jobInfo.getStatus())) {
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.interrupt(trigger.getJobKey());
            scheduler.pauseJob(trigger.getJobKey());

        } else {
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.resumeJob(trigger.getJobKey());
        }
    }

    private void saveToFile() throws SchedulerException {
        Utils.writeJsonFile2Bean(appConfig.getJob().getSavePath(), getAll());
    }


    public void deleteJob(String jobName, String jobGroup) throws SchedulerException {
        scheduler.pauseTrigger(TriggerKey.triggerKey(jobName, jobGroup));
        scheduler.unscheduleJob(TriggerKey.triggerKey(jobName, jobGroup));
        scheduler.deleteJob(JobKey.jobKey(jobName, jobGroup));
        saveToFile();
    }


    public void pauseJob(String jobName, String jobGroup) throws SchedulerException {
        scheduler.pauseJob(JobKey.jobKey(jobName, jobGroup));
        saveToFile();
    }

    public void triggerJob(String jobName, String jobGroup) throws SchedulerException {
        scheduler.triggerJob(JobKey.jobKey(jobName, jobGroup));
    }

    public void resumeJob(String jobName, String jobGroup) throws SchedulerException {
        log.info(jobName + "  " + jobGroup);
        scheduler.resumeJob(JobKey.jobKey(jobName, jobGroup));
        saveToFile();
    }


    public void cronJob(String jobName, String jobGroup, String cron) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        // 表达式调度构建器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        // 根据Cron表达式构建一个Trigger
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
        scheduler.rescheduleJob(triggerKey, trigger);
        saveToFile();
    }


    public List<JobInfo> getAll() throws SchedulerException {
        Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.anyJobGroup());
        List<JobInfo> jobInfos = new ArrayList<>();
        for (JobKey jobKey : jobKeys) {
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            List<CronTrigger> triggers = scheduler
                    .getTriggersOfJob(jobKey)
                    .stream().map(trigger -> {
                        if (trigger instanceof CronTrigger) return (CronTrigger) trigger;
                        return null;
                    }).collect(Collectors.toList());
            for (CronTrigger trigger : triggers) {
                if (trigger == null) continue;
                Trigger.TriggerState state = scheduler.getTriggerState(trigger.getKey());
                JobInfo jobInfo = new JobInfo();
                jobInfo.setGroupName(jobKey.getGroup());
                jobInfo.setJavaClass(jobDetail.getJobClass().getName());
                jobInfo.setName(jobDetail.getJobDataMap().getString(NAME_KEY));
                jobInfo.setJobName(jobKey.getName());
                jobInfo.setCron(trigger.getCronExpression());
                jobInfo.setStatus(state.name());
                jobInfo.setOrder(jobDetail.getJobDataMap().getInt(ORDER_KEY));
                jobInfo.setJavaData(jobDetail.getJobDataMap().getString(DATA_KEY));
                jobInfos.add(jobInfo);
            }

        }
        jobInfos.sort(Comparator.comparing(JobInfo::getOrder));
        return jobInfos;
    }


}
