package com.jicg.service.core.manager.bean;

import cn.hutool.core.convert.Converter;
import cn.hutool.core.convert.ConverterRegistry;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;

/**
 * @author jicg on 2021/3/31
 */
@Slf4j
public enum ColumnType {
    select, datenumber, date, check, string, textarea, object;
}
