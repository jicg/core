package  com.jicg.service.core.Job.bean;

import cn.hutool.json.JSONUtil;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author jicg on 2021/3/20
 */
@Data

public class JobInfo {


    public JobInfo() {
    }

    public JobInfo(String name, String remark, String cron, String jobName, String groupName, String javaClass,
                   String javaData, String proName, String status, int order) {
        this.name = name;
        this.remark = remark;
        this.cron = cron;
        this.jobName = jobName;
        this.groupName = groupName;
        this.javaClass = javaClass;
        this.javaData = javaData;
        this.proName = proName;
        this.status = status;
        this.order = order;
    }

    private String name;
    private int order = 100;
    private String remark;

    private String cron;
    private String jobName;
    private String groupName;


    private String javaClass;
    private String javaData = "s";
    private String proName;


    private String status;


    public static JobInfo.JobInfoBuilder builder() {
        return new JobInfo.JobInfoBuilder();
    }


    public static class JobInfoBuilder {
        private String name;
        private String remark;
        private String cron;
        private String jobName;
        private String groupName;
        private String javaClass;
        private String javaData;
        private String proName;
        private String status;
        private int order;

        JobInfoBuilder() {
        }

        public JobInfo.JobInfoBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public JobInfo.JobInfoBuilder remark(final String remark) {
            this.remark = remark;
            return this;
        }

        public JobInfo.JobInfoBuilder cron(final String cron) {
            this.cron = cron;
            return this;
        }

        public JobInfo.JobInfoBuilder jobName(final String jobName) {
            this.jobName = jobName;
            return this;
        }

        public JobInfo.JobInfoBuilder groupName(final String groupName) {
            this.groupName = groupName;
            return this;
        }

        public JobInfo.JobInfoBuilder javaClass(final String javaClass) {
            this.javaClass = javaClass;
            return this;
        }

        public JobInfo.JobInfoBuilder javaData(final String javaData) {
            this.javaData = javaData;
            return this;
        }

        public JobInfo.JobInfoBuilder proName(final String proName) {
            this.proName = proName;
            return this;
        }

        public JobInfo.JobInfoBuilder status(final String status) {
            this.status = status;
            return this;
        }

        public JobInfo.JobInfoBuilder order(final int order) {
            this.order = order;
            return this;
        }


        public JobInfo build() {
            return new JobInfo(this.name, this.remark, this.cron, this.jobName, this.groupName, this.javaClass, this.javaData, this.proName, this.status, this.order);
        }

        public String toString() {
            return "JobInfo.JobInfoBuilder(name=" + this.name + ", remark=" + this.remark + ", cron=" + this.cron + ", jobName=" + this.jobName + ", groupName=" + this.groupName + ", javaClass=" + this.javaClass + ", javaData=" + this.javaData + ", proName=" + this.proName + ", status=" + this.status + ")";
        }
    }


}
