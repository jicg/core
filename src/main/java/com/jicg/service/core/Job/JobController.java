package   com.jicg.service.core.Job;

import  com.jicg.service.core.Job.bean.JobInfo;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<JobInfo> list() throws SchedulerException {
        return jobService.getAll();
    }
    @ResponseBody
    @GetMapping(path = "/sys/api/job/pause")
    public String pause(@RequestParam String jobName, @RequestParam String jobGroup) throws SchedulerException {
        jobService.pauseJob(jobName,jobGroup);
        return "ok";
    }
    @ResponseBody
    @GetMapping(path = "/sys/api/job/resume")
    public String resumeJob(String jobName, String jobGroup) throws SchedulerException {
        jobService.resumeJob(jobName,jobGroup);
        return "ok";
    }
    @ResponseBody
    @GetMapping(path = "/sys/api/job/trigger")
    public String triggerJob(String jobName, String jobGroup) throws SchedulerException {
        jobService.triggerJob(jobName,jobGroup);
        return "ok";
    }

    @ResponseBody
    @GetMapping(path = "/sys/api/job/cron")
    public String cronJob(String jobName, String jobGroup, String cron) throws SchedulerException {
        jobService.cronJob(jobName,jobGroup,cron);
        return "ok";
    }

    @ResponseBody
    @GetMapping(path = "/sys/api/job/delete")
    public String deleteJob(String jobName, String jobGroup) throws SchedulerException {
        jobService.deleteJob(jobName,jobGroup);
        return "ok";
    }

}
