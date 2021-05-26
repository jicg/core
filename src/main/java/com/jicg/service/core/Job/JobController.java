package com.jicg.service.core.Job;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.jicg.service.core.Job.bean.JobInfo;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author jicg on 2021/3/21
 */
@Controller
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

//    @GetMapping(path = "/job/")
//    public String index() throws SchedulerException {
//        return "redirect:/job/index.html";
//    }

    @ResponseBody
    @GetMapping(path = "/sys/api/job/list")
    public List<JobInfo> list() throws Exception {
        return jobService.getAll();
    }

    @ResponseBody
    @GetMapping(path = "/sys/api/job/list2")
    public List<Dict> list2() throws Exception {
        List<Dict> dicts = new ArrayList<>();
        List<JobInfo> allJobs = jobService.getAll();
        List<String> group = jobService.getAll().stream().map(JobInfo::getGroupName).distinct().collect(Collectors.toList());
        group.forEach(key -> {
            List<JobInfo> jobs = allJobs.stream().filter(jobInfo ->
                    StrUtil.equals(key, jobInfo.getGroupName())).collect(Collectors.toList());
            dicts.add(Dict.create().set("key", key).set("jobs", jobs));
        });
        return dicts;
    }

    @ResponseBody
    @GetMapping(path = "/sys/api/job/pause")
    public String pause(@RequestParam String jobName, @RequestParam String jobGroup) throws Exception {
        jobService.pauseJob(jobName, jobGroup);
        return "ok";
    }

    @ResponseBody
    @GetMapping(path = "/sys/api/job/resume")
    public String resumeJob(String jobName, String jobGroup) throws Exception {
        jobService.resumeJob(jobName, jobGroup);
        return "ok";
    }

    @ResponseBody
    @GetMapping(path = "/sys/api/job/trigger")
    public String triggerJob(String jobName, String jobGroup) throws Exception {
        jobService.triggerJob(jobName, jobGroup);
        return "ok";
    }

    @ResponseBody
    @GetMapping(path = "/sys/api/job/cron")
    public String cronJob(String jobName, String jobGroup, String cron) throws Exception {
        jobService.cronJob(jobName, jobGroup, cron);
        return "ok";
    }

    @ResponseBody
    @GetMapping(path = "/sys/api/job/delete")
    public String deleteJob(String jobName, String jobGroup) throws Exception {
        jobService.deleteJob(jobName, jobGroup);
        return "ok";
    }

}
