package com.jicg.service.core.manager;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.db.*;
import cn.hutool.db.sql.*;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.jicg.service.core.config.AppProperties;
import com.jicg.service.core.config.auth.CoreStpUtil;
import com.jicg.service.core.database.DB4BosFn;
import com.jicg.service.core.manager.bean.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jicg on 2021/3/31
 */
@Controller
@Slf4j
@SaCheckLogin(type = CoreStpUtil.type)
public class ManagerController {

    final AppProperties appProperties;

    public ManagerController(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @GetMapping("/sys/manager/api/title")
    @ResponseBody
    public String getTitle() {
        return appProperties.getTitle();
    }

    @GetMapping("/sys/manager/api/mod/list")
    @ResponseBody
    public List<String> getMods() {
        return ManagerApplicationRunner.mods;
    }

    @GetMapping("/sys/manager/api/menu/list")
    @ResponseBody
    public List<Map<String, Object>> getMenus() {
        return ManagerApplicationRunner.tableMap.keySet().stream().filter(key -> StrUtil.equalsIgnoreCase(
                ManagerApplicationRunner.tableMap.get(key).getIsmenu(), "Y"
        )).map(key -> {
            TableInfo tableInfo = ManagerApplicationRunner.tableMap.get(key);
            return MapUtil.builder(
                            new HashMap<String, Object>())
                    .put("name", tableInfo.getName())
                    .put("remark", tableInfo.getRemark())
                    .put("group", tableInfo.getGroup())
                    .put("url", tableInfo.getUrl())
                    .put("orderno", tableInfo.getOrderno())
                    .build();
        }).sorted((map1, map2) -> ObjectUtil.compare(MapUtil.getInt(map1, "orderno"), MapUtil.getInt(map2, "orderno"), true)).collect(Collectors.toList());
    }


    @GetMapping("/sys/manager/api/menu/list2")
    @ResponseBody
    public List<Map<String, Object>> getMenus2(@RequestParam(name = "mod", defaultValue = "") String mod) {
        List<String> strs = ManagerApplicationRunner.tableMap.keySet().stream().filter(key ->
                StrUtil.equalsIgnoreCase(ManagerApplicationRunner.tableMap.get(key).getIsmenu(), "Y") &&
                        StrUtil.equalsIgnoreCase(ManagerApplicationRunner.tableMap.get(key).getMod(), mod)).collect(Collectors.toList());
        List<Dict> dicts = new ArrayList<>();
        List<String> groups = new ArrayList<>();
        List<TableInfo> menuList = strs.stream().map(ManagerApplicationRunner.tableMap::get).collect(Collectors.toList());
        strs.forEach(key -> {
            TableInfo tableInfo = ManagerApplicationRunner.tableMap.get(key);
            Dict dict = Dict.create();
            if (StrUtil.isEmpty(tableInfo.getGroup())) {
                dict.set("name", tableInfo.getName())
                        .set("remark", tableInfo.getRemark())
                        .set("group", tableInfo.getGroup())
                        .set("url", tableInfo.getUrl())
                        .set("orderno", tableInfo.getOrderno());
            } else {
                if (groups.contains(tableInfo.getGroup())) {
                    return;
                }
                groups.add(tableInfo.getGroup());
                dict.set("group", tableInfo.getGroup())
                        .set("menus", menuList.stream().filter(d -> StrUtil.equals(d.getGroup(), tableInfo.getGroup()))
                                .sorted(Comparator.comparingLong(TableInfo::getOrderno))
                                .collect(Collectors.toList()))
                        .set("orderno", tableInfo.getOrderno());
            }
            dicts.add(dict);
        });
        return dicts.stream().sorted((map1, map2) -> ObjectUtil.compare(MapUtil.getInt(map1, "orderno"), MapUtil.getInt(map2, "orderno"), true)).collect(Collectors.toList());
    }

    @PostMapping("/sys/manager/api/{tableName}/run/{btn}")
    @ResponseBody
    public String execBtn(@PathVariable("tableName") String tableName, @PathVariable("btn") String btnName,
                          @RequestBody List<String> data) throws Exception {
        TableInfo tf = ManagerApplicationRunner.tableMap.get(tableName);
        if (tf == null) throw new RuntimeException("表不存在：" + tableName);
        ButtonInfo btn = tf.getButtons().stream().filter(it -> StrUtil.equalsIgnoreCase(it.getName(), btnName)).findFirst().orElseThrow(() -> new RuntimeException("按钮不存在【" + tableName + "】"));
        if (data == null) data = ListUtil.toList("");
        Connection conn = null;
        CallableStatement call = null;
        String msg = "ok";
        try {
            conn = Db.use().getConnection();
            conn.setAutoCommit(false);
            data.add("jicg");
            Object[] prams = data.toArray();
            call = StatementUtil.prepareCall(conn,
                    "call " + btn.getProcName() + "(" + data.stream().map(it -> "?").collect(Collectors.joining(",")) + ",?,?" + ")",
                    prams
            );
            int len = prams.length;
            call.registerOutParameter(len + 1, java.sql.Types.INTEGER);
            call.registerOutParameter(len + 2, java.sql.Types.VARCHAR);
            call.execute();
            int code = call.getInt(len + 1);
            msg = call.getString(len + 2);
            if (code != 0) {
                throw new RuntimeException(msg);
            }
            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            IoUtil.close(call);
            IoUtil.close(conn);
        }
        return msg;
    }

    private static final String OPERATOR_IN = "IN";
    private static final List<String> OPERATORS = Arrays.asList("<>", "<=", "<", ">=", ">", "=", "!=", OPERATOR_IN);

    @PostMapping("/sys/manager/api/{tableName}/list")
    @ResponseBody
    public Dict listObject(@PathVariable("tableName") String tableName,
                           @RequestBody QueryParam param) throws SQLException {
        TableInfo tf = ManagerApplicationRunner.tableMap.get(tableName);
        String sql = (String) ManagerApplicationRunner.tableSqls.get(tableName + "@list");

        if (sql == null) {
            sql = new TableBuildSql(tf).toListSql();
            ManagerApplicationRunner.tableSqls.get(tableName + "@list", sql);
        }
        Entity where = param.where;
        tf.getColumns().stream().filter(c -> !StrUtil.equalsIgnoreCase(c.getIsListCol(), "N")).forEach(columnInfo -> {
            if (columnInfo.getView_type() == ColumnType.date && where.containsKey(columnInfo.getApiName())) {
                List<String> dates = (List<String>) where.get(columnInfo.getApiName());
                Date datebeg = DateUtil.parse(dates.get(0), "yyyyMMdd HH:mm:ss");
                Date dateend = DateUtil.parse(dates.get(1), "yyyyMMdd HH:mm:ss");
                Condition condition = new Condition(columnInfo.getApiName(), "BETWEEN", datebeg);
                condition.setValue(datebeg);
                condition.setSecondValue(dateend);
                where.set(columnInfo.getName(), condition);
            } else if (columnInfo.getView_type() == ColumnType.object && where.containsKey(columnInfo.getApiName())) {
                String valueData = "" + where.get(columnInfo.getApiName());
                if (JSONUtil.isJson(valueData)) {
                    JSON json = JSONUtil.parse(valueData);
                    Condition condition = new Condition(false);
                    condition.setField(columnInfo.getApiName());
                    condition.setOperator("");
                    condition.setValue(json.getByPath("sql"));
                    where.set(columnInfo.getName(), condition);
                }
            } else if (where.containsKey(columnInfo.getApiName())) {

                String valueStr = where.getStr(columnInfo.getApiName());
                final List<String> strs = StrUtil.split(valueStr, StrUtil.C_SPACE, 2);
                if (strs.size() < 2) {

                    if (StrUtil.startWith(valueStr, "=")) {
                        where.set(columnInfo.getApiName(), "= " + StrUtil.subSuf(valueStr, 1));
                    } else {
                        where.set(columnInfo.getApiName(), "like %" + valueStr + "%");
                    }
                }
//                // 处理常用符号和IN
//                final String firstPart = strs.get(0).trim().toUpperCase();
//                if (OPERATORS.contains(firstPart)) {
//
//                }
            }
        });

        Order[] orders = param.orders.stream().map(queryOrder -> new Order(queryOrder.field, StrUtil.equalsIgnoreCase(queryOrder.order, "asc") ?
                Direction.ASC : Direction.DESC)).toArray(Order[]::new);
        Page page = new Page(param.page - 1, param.pageSize);
        page.setOrder(orders);
        PageResult<Entity> pageResult = DB4BosFn.page(SqlBuilder.of(sql)
                .where(Query.of(where).getWhere()), page);
        return Dict.create().set("total", pageResult.getTotal()).set("page", pageResult.getPage() + 1)
                .set("pageSize", pageResult.getPageSize()).set("totalPage", pageResult.getTotalPage())
                .set("datas", pageResult);
    }


    @PostMapping("/sys/manager/api/{tableName}/sql")
    @ResponseBody
    public Object getObjectSql(@PathVariable("tableName") String tableName,
                               @RequestParam(value = "type", defaultValue = "json") String type,
                               @RequestBody QueryParam param) throws SQLException {
        TableInfo tf = ManagerApplicationRunner.tableMap.get(tableName);
        TableBuildSql tableBuildSql = new TableBuildSql(tf);
        String sql = tableBuildSql.toInSql();
        if (param == null || param.where.size() == 0) return StrUtil.equalsIgnoreCase(type, "xml") ?
                XmlUtil.mapToXml(Dict.create().set("sql", sql).set("desc", "全部"), "filter") : Dict.create().set("sql", sql).set("desc", "全部");
        Entity where = Entity.create();
        List<String> descs = new ArrayList<>();
        param.where.keySet().forEach(key -> {
            Optional<ColumnInfo> column = tf.getColumns().stream().filter(columnInfo -> columnInfo.getApiName().equalsIgnoreCase(key)).findFirst();
            String columnName = key;
            if (column.isPresent()) {
                columnName = column.get().getName();
                if (column.get().getView_type() == ColumnType.date) {
                    List<String> dates = (List<String>) param.where.get(key);
                    Condition condition = new Condition(false);
                    condition.setField("");
                    condition.setOperator("");
                    condition.setValue(tableBuildSql.getAlias(columnName) + "." + TableBuildSql.commaLast(columnName)
                            + " BETWEEN " + "to_date('" + dates.get(0) + "','yyyyMMdd HH24:mi:ss')"
                            + " AND to_date('" + dates.get(1) + "','yyyyMMdd HH24:mi:ss')");
                    where.set("", condition);
                    descs.add(column.get().getRemark() + ": " + dates.get(0) + "～" + dates.get(1));
                } else if (column.get().getView_type() == ColumnType.object) {
                    String valueData = "" + param.where.get(key);
                    if (JSONUtil.isJson(valueData)) {
                        JSON json = JSONUtil.parse(valueData);
                        Condition condition = new Condition(false);
                        condition.setField("");
                        condition.setOperator("");
                        condition.setValue(tableBuildSql.getAlias(columnName) + "." + TableBuildSql.commaLast(columnName) + " " + json.getByPath("sql"));
                        where.set(column.get().getApiName(), condition);
                        descs.add(column.get().getRemark() + ": " + json.getByPath("desc"));
                        return;
                    }
                } else {
                    String valueStr = param.where.getStr(key);
                    final List<String> strs = StrUtil.split(valueStr, StrUtil.C_SPACE, 2);
                    if (strs.size() < 2) {
                        String ckey = tableBuildSql.getAlias(columnName) + "." + TableBuildSql.commaLast(columnName);
                        if (StrUtil.startWith(valueStr, "=")) {
                            where.set(ckey, "= " + StrUtil.subSuf(valueStr, 1));
                        } else {
                            where.set(ckey, "like %" + valueStr + "%");
                        }
                        descs.add(column.get().getRemark() + ": " + param.where.get(key));
                        return;
                    }

                }
                descs.add(column.get().getRemark() + ": " + param.where.get(key));
            } else {
                descs.add("特殊条件[" + key + "]:" + param.where.get(key));
                where.set(key, param.where.get(key));
//                descs.add("特殊条件[" + key + "]:" + param.where.get(key));
//                String valueStr = where.getStr(key);
//                final List<String> strs = StrUtil.split(valueStr, StrUtil.C_SPACE, 2);
//                if (strs.size() < 2) {
//                    if (StrUtil.startWith(valueStr, "=")) {
//                        where.set(key, "= " + StrUtil.subSuf(valueStr,1));
//                    } else {
//                        where.set(key, "like %" + valueStr + "%");
//                    }
//                }
            }
            where.set(tableBuildSql.getAlias(columnName) + "." + TableBuildSql.commaLast(columnName), param.where.get(key));


        });

        SqlBuilder sqlBuilder = SqlBuilder.of(sql)
                .where(Query.of(where).getWhere());
        String strSql = sqlBuilder.toString();
        for (int i = 0; i < sqlBuilder.getParamValues().size(); i++) {
            Object val = sqlBuilder.getParamValues().get(i);
            if (val instanceof String) {
                strSql = strSql.replaceFirst("\\?", "'" + val + "'");
            } else if (val instanceof Number) {
                strSql = strSql.replaceFirst("\\?", "" + val);
            } else {
                strSql = strSql.replaceFirst("\\?", "'" + val + "'");
            }
        }
        Dict dict = Dict.create().set("sql", strSql).set("desc", descs.stream().map(s -> tf.getRemark() + "( " + s + " )").collect(Collectors.joining(" 并且 ")));
        return StrUtil.equalsIgnoreCase(type, "xml") ?
                XmlUtil.mapToXml(dict, "filter").getChildNodes() : dict;
    }


    @GetMapping("/sys/manager/api/{tableName}/get")
    @ResponseBody
    public Entity getObject(@PathVariable("tableName") String tableName,
                            @RequestParam("id") long id) throws SQLException {
        TableInfo tf = ManagerApplicationRunner.tableMap.get(tableName);
        String sql = (String) ManagerApplicationRunner.tableSqls.get(tableName + "@get");
        if (sql == null) {
            sql = new TableBuildSql(tf).toSql();
            ManagerApplicationRunner.tableSqls.get(tableName + "@get", sql);
        }
        Entity where = Entity.create().set("id", id);
        SqlBuilder builder = SqlBuilder.of(sql).where(Query.of(where).getWhere());

        List<Entity> entities = DbUtil.use().query(builder.build(), builder.getParamValueArray());
        if (entities == null || entities.size() <= 0) throw new RuntimeException("id 不存在");
        return entities.get(0);
    }


    @GetMapping("/sys/manager/api/table")
    @ResponseBody
    public TableInfo getTable(String name) {
        return ManagerApplicationRunner.tableMap.get(name);
    }

    @GetMapping("/sys/manager/api/table/list")
    @ResponseBody
    public List<TableInfo> getTables() {
        return ManagerApplicationRunner.tableMap.keySet().stream().map(ManagerApplicationRunner.tableMap::get).collect(Collectors.toList());
    }


    @GetMapping("/sys/manager/api/table/reload")
    @ResponseBody
    public String reload() {
        StaticController.cache.clear();
        ManagerApplicationRunner.reload();
        return "ok";
    }

    @Data
    public static class QueryParam {
        private List<QueryOrder> orders = new ArrayList<>();
        private Entity where;
        private int page = 1;
        private int pageSize = 30;
    }

    @Data
    public static class QueryOrder {
        String field = "";
        String order = "";
    }
}
