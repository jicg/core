package com.jicg.service.core.manager;

import cn.hutool.core.convert.ConverterRegistry;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import com.jicg.service.core.manager.bean.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author jicg on 2021/3/31
 */
@Slf4j
@Component
public class ManagerApplicationRunner implements ApplicationRunner {
    final static Map<String, TableInfo> tableMap = new HashMap<>();
    public static Dict tableSqls = Dict.create();

    public void registerXls() {
        ConverterRegistry.getInstance().putCustom(ColumnType.class, (value, defaultValue) ->
        {
            if (value == null || StrUtil.isEmpty("" + value)) return ColumnType.string;
            try {
                return ColumnType.valueOf(StrUtil.toString(value));
            } catch (Exception e) {
                log.error(e.getLocalizedMessage(), e);
            }

            return ColumnType.string;
        });
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        registerXls();
        reload();
    }

    public static void reload() {
        tableMap.clear();
        List<TableInfo> tableInfos = new ArrayList<>();

        List<File> files = FileUtil.loopFiles("system", pathname -> pathname.isFile()
                && pathname.getName().startsWith("table_")
                && StrUtil.endWithAnyIgnoreCase(pathname.getName(), ".xls", ".xlsx"));
        for (File file : files) {
            List<TableInfo> tableInfos2 =
                    XlsUtils.readAll(file, "tables", TableInfo.class);
            List<SelectOps> selOpts =
                    XlsUtils.readAll(file, "tables_select_ops", SelectOps.class);
            List<ButtonInfo> buttonInfos =
                    XlsUtils.readAll(file, "tables_btns", ButtonInfo.class);
            for (TableInfo tableInfo : tableInfos2) {
                if (!StrUtil.isEmpty(tableInfo.getUrl())) continue;
                List<ColumnInfo> columnInfos =
                        XlsUtils.readAll(file, tableInfo.getName(), ColumnInfo.class);
                tableInfo.setColumns(columnInfos);
                for (ColumnInfo columnInfo : columnInfos) {
                    if (columnInfo.getView_type() == ColumnType.select) {
                        columnInfo.setSelectOps(selOpts.stream()
                                .filter(selectOps -> StrUtil.equalsIgnoreCase(
                                        selectOps.getGroupname(),
                                        columnInfo.getSelGroup())
                                ).collect(Collectors.toList())
                        );
                    }
                    tableInfo.setButtons(buttonInfos.stream().filter(
                            it -> StrUtil.equalsIgnoreCase(tableInfo.getName(), it.getTable())).collect(Collectors.toList()));
                }
            }
            tableInfos.addAll(tableInfos2);
        }
        tableInfos.forEach(c -> {
            tableMap.put(c.getName(), c);
        });
        tableInfos.forEach(c -> {
            int i = 0;
            for (ColumnInfo columnInfo : c.getColumns()) {
                columnInfo.setList_size(StrUtil.length(columnInfo.getRemark()));
                if (StrUtil.isEmpty(columnInfo.getApiName()))
                    columnInfo.setApiName(columnInfo.getName());
                if (StrUtil.contains(columnInfo.getApiName(), ";")) {
//                    columnInfo.setApiName(StrUtil.replace(columnInfo.getApiName(), ";", "$"));
                    columnInfo.setApiName("col_" + i);
                }
                if (StrUtil.isNotEmpty(columnInfo.getLinkTable()) && StrUtil.isEmpty(columnInfo.getLinkTableName())) {
                    if (tableMap.containsKey(columnInfo.getLinkTable()))
                        columnInfo.setLinkTableName(tableMap.get(columnInfo.getLinkTable()).getRemark());
                }
                i++;
            }
            String upTableStr = c.getUpTable();
            if (StrUtil.isNotEmpty(upTableStr)) {
                TableInfo upTable = tableMap.get(upTableStr);
                upTable.getItemTables().add(c);
            }
        });
    }

}
