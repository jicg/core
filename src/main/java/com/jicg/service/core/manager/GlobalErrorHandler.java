package  com.jicg.service.core.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@ControllerAdvice(basePackages = "com.jicg.service.core")
public class GlobalErrorHandler {
    @ExceptionHandler(value = Throwable.class)
    @ResponseBody
    public ResponseEntity handleException(Exception e, HttpServletResponse response) throws Exception {
        log.error(e.getLocalizedMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(findSqlMsg(e.getLocalizedMessage()));
    }


    public static String findSqlMsg(String message) {
        if (message != null && message.matches("[^*]+ORA-[0-9]*:[^*]*")) {
            String pattern = "ORA-[0-9]+:(.*)\n";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(message);
            if (m.find()) {
                return m.group().replaceAll("ORA-[0-9]+:", "").trim();
            }
        }

        if (message != null && message.matches("[^*]+Exception[^*]+ORA-[0-9]*:[^*]*")) {
            String pattern = "ORA-[0-9]+:(.*)\n";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(message);
            if (m.find()) {
                return m.group().replaceAll("ORA-[0-9]+:", "").trim();
            }
        }
        if (message != null && message.matches("[^*]+java.sql.SQLException:[^*]*")) {
            String pattern = "java.sql.SQLException:(.*)\n";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(message);
            if (m.find()) {
                return m.group().replaceAll("java.sql.SQLException:", "").trim();
            }
        }
        return message;
    }
}