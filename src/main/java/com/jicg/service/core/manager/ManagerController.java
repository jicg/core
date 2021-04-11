package  com.jicg.service.core.manager;

import cn.hutool.cache.file.LFUFileCache;
import cn.hutool.core.date.DateBetween;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.DbUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.Page;
import cn.hutool.db.PageResult;
import cn.hutool.db.sql.*;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.level.Level;
import  com.jicg.service.core.Utils;
import  com.jicg.service.core.database.DB4BosFn;
import  com.jicg.service.core.database.DBCore;
import  com.jicg.service.core.manager.bean.ColumnInfo;
import  com.jicg.service.core.manager.bean.ColumnType;
import  com.jicg.service.core.manager.bean.TableInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.*;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

/**
 * @author jicg on 2021/3/31
 */
@Controller
@Slf4j
public class ManagerController {

    LFUFileCache cache = new LFUFileCache(1000, 1024 * 10, 2000);

    @GetMapping(path = "/manager/")
    public String index()  {
        return "redirect:/manager/index.html";
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
                    .put("url", tableInfo.getUrl())
                    .build();
        }).collect(Collectors.toList());
    }

    @PostMapping("/sys/manager/api/{tableName}/list")
    @ResponseBody
    public Dict listObject(@PathVariable("tableName") String tableName,
                           @RequestBody QueryParam param) throws SQLException {
        TableInfo tf = ManagerApplicationRunner.tableMap.get(tableName);
        String sql = (String) ManagerApplicationRunner.tableSqls.get(tableName);
        if (sql == null) {
            sql = new TableBuildSql(tf).toSql();
            ManagerApplicationRunner.tableSqls.get(tableName, sql);
        }
        Entity where = param.where;
        tf.getColumns().stream().filter(columnInfo -> columnInfo.getView_type() == ColumnType.date).forEach(columnInfo -> {
            if (where.containsKey(columnInfo.getName())) {
                List<String> dates = (List<String>) where.get(columnInfo.getName());
                Date datebeg = DateUtil.parse(dates.get(0), "yyyyMMdd HH:mm:ss");
                Date dateend = DateUtil.parse(dates.get(1), "yyyyMMdd HH:mm:ss");
                Condition condition = new Condition(columnInfo.getName(), "BETWEEN", datebeg);
                condition.setValue(datebeg);
                condition.setSecondValue(dateend);
                where.set(columnInfo.getName(), condition);
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


    @GetMapping("/sys/manager/api/{tableName}/get")
    @ResponseBody
    public Entity getObject(@PathVariable("tableName") String tableName,
                            @RequestParam("id") long id) throws SQLException {
        TableInfo tf = ManagerApplicationRunner.tableMap.get(tableName);
        String sql = (String) ManagerApplicationRunner.tableSqls.get(tableName);
        if (sql == null) {
            sql = new TableBuildSql(tf).toSql();
            ManagerApplicationRunner.tableSqls.get(tableName, sql);
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

    @GetMapping("/sys/manager/{tableName}.js")
    @ResponseBody
    public String getJS(@PathVariable String tableName) {
        try {
            byte[] bytes = cache.getFileBytes(FileUtil.file("system/" + tableName + ".js"));
            return StrUtil.str(bytes, "UTF8");
        } catch (Exception e) {
            return "";
        }
    }


    @GetMapping("/sys/manager/api/table/reload")
    @ResponseBody
    public String reload() {
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
