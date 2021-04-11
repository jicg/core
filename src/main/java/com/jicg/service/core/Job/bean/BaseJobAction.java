package  com.jicg.service.core.Job.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jicg on 2021/3/21
 */

public class BaseJobAction {
    private String javaClass;
    private String javaMethod;
    private List<?> javaArgs = new ArrayList<>();

    public String getJavaClass() {
        return javaClass;
    }

    public void setJavaClass(String javaClass) {
        this.javaClass = javaClass;
    }

    public String getJavaMethod() {
        return javaMethod;
    }

    public void setJavaMethod(String javaMethod) {
        this.javaMethod = javaMethod;
    }

    public List<?> getJavaArgs() {
        return javaArgs;
    }

    public void setJavaArgs(List<?> javaArgs) {
        this.javaArgs = javaArgs;
    }
}
