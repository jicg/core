package  com.jicg.service.core.annos;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author jicg on 2021/3/21
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface JobJava {
    String cron();

    String name();

    int order() default 100;

    String jobName();

    String groupName();

    String remark() default "";

}
