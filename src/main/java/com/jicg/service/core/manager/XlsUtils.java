package  com.jicg.service.core.manager;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import  com.jicg.service.core.annos.XlsAlias;
import lombok.extern.slf4j.Slf4j;
import sun.nio.ch.IOUtil;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author jicg on 2021/3/31
 */
@Slf4j
public class XlsUtils {

    public static <T> List<T> readAll(File file, String sheetName, Class<T> tClass) {
        ExcelReader excelReader = null;
        try {
            excelReader = ExcelUtil.getReader(file, sheetName);
            for (Field field : ReflectUtil.getFields(tClass)) {
                XlsAlias xlsAlias = field.getAnnotation(XlsAlias.class);
                if (xlsAlias == null || StrUtil.isEmpty(xlsAlias.value())) continue;
                excelReader.getHeaderAlias().put(xlsAlias.value(), field.getName());
            }
            return excelReader.readAll(tClass);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            throw e;
        } finally {
            IoUtil.close(excelReader);
        }
    }
}
