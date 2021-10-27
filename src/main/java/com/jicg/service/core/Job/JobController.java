package com.jicg.service.core.Job;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.jicg.service.core.Job.bean.BaseJobAction;
import com.jicg.service.core.Job.bean.JobInfo;
import com.jicg.service.core.config.auth.CoreStpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jicg on 2021/3/21
 */
@Slf4j
@Controller
@SaCheckLogin(type = CoreStpUtil.type)
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
    public List<Dict> list2(@RequestParam(name = "mod", defaultValue = "") String mod) throws Exception {
        List<Dict> dicts = new ArrayList<>();
        List<JobInfo> allJobs = jobService.getAll();
        if (!StrUtil.isEmpty(mod)) {
            allJobs = allJobs.stream().filter(jobInfo -> {
                if (JSONUtil.isJson(jobInfo.getJavaData())) {
                    BaseJobAction jobAction = JSONUtil.toBean(jobInfo.getJavaData(), BaseJobAction.class);
                    return StrUtil.containsIgnoreCase(jobAction.getJavaClass(), mod);
                }
                return false;
            }).collect(Collectors.toList());
        }
        List<JobInfo> finalAllJobs = allJobs;
        List<String> group = finalAllJobs.stream().map(JobInfo::getGroupName).distinct().collect(Collectors.toList());

        group.forEach(key -> {
            List<JobInfo> jobs = finalAllJobs.stream().filter(jobInfo ->
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
