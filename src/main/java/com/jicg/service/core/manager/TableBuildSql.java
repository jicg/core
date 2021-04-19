package com.jicg.service.core.manager;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.sql.SqlBuilder;
import cn.hutool.json.JSONUtil;
import com.jicg.service.core.manager.bean.ColumnInfo;
import com.jicg.service.core.manager.bean.ColumnType;
import com.jicg.service.core.manager.bean.TableInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.jicg.service.core.manager.ManagerApplicationRunner.tableMap;

/**
 * @author jicg on 2021/4/4
 */

public class TableBuildSql {
    private final TableInfo mainTable;
    private final Map<String, LinkInfo> aliasMap = new HashMap<>();
    List<String> linkColumnStr = new ArrayList<>();

    public TableBuildSql(TableInfo mainTable) {
        this.mainTable = mainTable;
        init();
    }

    private void init() {
        linkColumnStr = new ArrayList<>();
        for (ColumnInfo columnInfo : mainTable.getColumns()) {
            String[] linkStrs = commaSplit(columnInfo.getName());
            if (linkStrs.length <= 1) continue;
            for (int i = 0; i < linkStrs.length - 1; i++) {
                if (!linkColumnStr.contains(linkStrs[i])) {
                    linkColumnStr.add(linkStrs[i]);
                }
            }
        }


        int index = 0;
        for (ColumnInfo columnInfo : mainTable.getColumns()) {
            if (columnInfo.getView_type() != ColumnType.object) continue;
            if (aliasMap.containsKey(columnInfo.getName())) continue;
            String alias = "t" + index++;
            TableInfo linkTable = tableMap.get(columnInfo.getLinkTable());
            if (linkTable == null)
                throw new RuntimeException("字段：" + mainTable.getName() + "." + columnInfo.getName() + " 关联表" + columnInfo.getLinkTable() + "不存在");
            aliasMap.put(columnInfo.getName(), new LinkInfo(columnInfo, linkTable, alias));
            if (!linkColumnStr.contains(columnInfo.getName()))
                linkColumnStr.add(columnInfo.getName());
        }
        linkColumnStr.sort(String::compareTo);
        //c_orig_id;code
        //c_orig_id;c_area_id;code
        for (String str : linkColumnStr) {
            if (aliasMap.containsKey(str)) continue;
            LinkInfo upLink = aliasMap.get(commaRemoveLast(str));
            if (upLink == null) continue;
            String linkColumnName = commaLast(str);
            TableInfo upLinkTable = upLink.getLinkTable();
            ColumnInfo linkColumn = upLinkTable.getColumns().stream().filter(col -> StrUtil.equalsIgnoreCase(linkColumnName, col.getName())).findFirst()
                    .orElseThrow(() -> new RuntimeException("关联：" + upLinkTable.getName() + "." + linkColumnName + "不存在"));
            TableInfo linkTable = tableMap.get(linkColumn.getLinkTable());
            String alias = "t" + index++;
            aliasMap.put(str, new LinkInfo(linkColumn, linkTable, alias));

        }
    }

    public String getRealName(TableInfo tableInfo) {
        String realName = tableInfo.getRealTable();
        return StrUtil.nullToDefault(realName, tableInfo.getName());
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LinkInfo {
        ColumnInfo mainColumn;
        TableInfo linkTable;
        String alias;
    }

    public String toSql() {
        String realName = mainTable.getRealTable();
        String table = StrUtil.nullToDefault(realName, mainTable.getName());
        String selSql = mainTable.getColumns().stream().map(c -> {
            String[] linkStrs = commaSplit(c.getName());
            if (linkStrs.length > 1) {
                return aliasMap.get(commaRemoveLast(c.getName())).getAlias() + "." + commaLast(c.getName()) + " as " + c.getApiName();
            } else if (c.getView_type() == ColumnType.object) {
                LinkInfo linkInfo = aliasMap.get(c.getName());

                return mainTable.getName() + "." + c.getName() + " as " + c.getApiName() + " , " +
                        linkInfo.getAlias() + "." + linkInfo.getLinkTable().getShowColumn() + " as " + c.getApiName() + "_desc";
            }
            return mainTable.getName() + "." + c.getName() + " as " + c.getApiName();

        }).collect(Collectors.joining(","));
        StringBuffer fromSql = new StringBuffer();
        fromSql.append("select ").append(selSql);
        fromSql.append(" from ").append(table).append(" ").append(mainTable.getName());
        for (String key : linkColumnStr) {
            String upKey = commaRemoveLast(key);
            String mainAlias = mainTable.getName();
            if (StrUtil.isNotEmpty(upKey)) {
                mainAlias = aliasMap.get(upKey).alias;
            }
            LinkInfo upLink = aliasMap.get(key);
            LinkInfo linkInfo = aliasMap.get(key);
            fromSql.append(" left join ").append(getRealName(linkInfo.getLinkTable())).append(" ")
                    .append(linkInfo.getAlias()).append(" on (").append(mainAlias).append(".")
                    .append(upLink.getMainColumn().getName()).append(" = ").append(linkInfo.getAlias()).append(".id  )");
        }
        return fromSql.insert(0, "select * from (").append(")").toString();
    }

    public String getAlias(String columnName) {
        String upKey = commaRemoveLast(columnName);
        String mainAlias = mainTable.getName();
        if (StrUtil.isNotEmpty(upKey)) {
            mainAlias = aliasMap.get(upKey).alias;
        }
        return mainAlias;
    }

    public String toInSql() {
        String realName = mainTable.getRealTable();
        String table = StrUtil.nullToDefault(realName, mainTable.getName());

        StringBuffer fromSql = new StringBuffer();
        fromSql.append("select ").append(mainTable.getName() + ".id");
        fromSql.append(" from ").append(table).append(" ").append(mainTable.getName());
        for (String key : linkColumnStr) {
            String upKey = commaRemoveLast(key);
            String mainAlias = mainTable.getName();
            if (StrUtil.isNotEmpty(upKey)) {
                mainAlias = aliasMap.get(upKey).alias;
            }
            LinkInfo upLink = aliasMap.get(key);
            LinkInfo linkInfo = aliasMap.get(key);
            fromSql.append(" left join ").append(getRealName(linkInfo.getLinkTable())).append(" ")
                    .append(linkInfo.getAlias()).append(" on (").append(mainAlias).append(".")
                    .append(upLink.getMainColumn().getName()).append(" = ").append(linkInfo.getAlias()).append(".id  )");
        }
        return fromSql.toString();
//        return fromSql.insert(0, "select * from (").append(")").toString();
    }


    public static String[] commaSplit(String s) {
        StringBuilder str = new StringBuilder();
        String[] cols = s.split(";");
        String[] strs = new String[cols.length];
        for (int i = 0; i < cols.length; i++) {
            str.append(cols[i]);
            strs[i] = str.toString();
            str.append(";");
        }
        return strs;
    }

    public static String commaLast(String key) {
        return key.contains(";") ? key.substring(key.lastIndexOf(";") + 1) : key;
    }

    public static String commaRemoveLast(String key) {
        int index = key.lastIndexOf(";");
        if (index <= 0) return "";
        return key.substring(0, index);
    }
}
