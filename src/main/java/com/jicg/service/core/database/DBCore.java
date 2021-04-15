package  com.jicg.service.core.database;

import ch.qos.logback.core.db.dialect.DBUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import cn.hutool.db.DbUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.Session;
import cn.hutool.db.ds.pooled.DbConfig;
import cn.hutool.log.level.Level;
import cn.hutool.setting.dialect.PropsUtil;
import  com.jicg.service.core.Utils;
import org.springframework.beans.BeanUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author jicg on 2021/3/17
 */

public class DBCore {

    static {
        DbUtil.setShowSqlGlobal(true, true, true, Level.INFO);
    }

    public static int batchSize = 1000;

    public static <T> void insert(List<T> dataList) throws SQLException {
        insert(null, dataList);
    }


    public static <T> void insert(Db db, List<T> dataList) throws SQLException {
        if (dataList == null || dataList.size() <= 0) return;
        String table = Utils.getTableName(dataList.get(0).getClass());
        if (db == null)
            db = Db.use();
        int i = 1;
        int size = dataList.size();
        List<T> datas = new ArrayList<>();
        for (T element : dataList) {
            datas.add(element);
            if ((i % batchSize == 0) || i == size) {
                saveList(db, table, datas);
                datas = new ArrayList<>();
            }
            i++;
        }
//        System.out.println("---end---" + ((System.currentTimeMillis() - time) / 1000));
    }

    public static <T> void saveList(String table, List<T> datas) throws SQLException {
        saveList(Db.use(), table, datas);
    }

    public static <T> void saveList(Db db, String table, List<T> datas) throws SQLException {
        if (db == null) db = Db.use();
        String sql = datas.stream().map(s -> "select get_sequences('" + table + "') as id from dual ")
                .collect(Collectors.joining(" union all "));
        List<Entity> ids = db.query(sql);
        AtomicInteger index = new AtomicInteger();
        List<Entity> entities = datas.stream().map((s) -> {
            BeanUtil.setFieldValue(s, "id", ids.get(index.getAndIncrement()).getInt("id"));
            return Entity.parse(s)
                    .setTableName(table);
        }).collect(Collectors.toList());
        db.insert(entities);
    }


    public static <T> List<T> queryList(Class<T> tClass, Dict dict) throws SQLException {
        if (dict == null) dict = new Dict();
        Entity entity = Entity.create(Utils.getTableName(tClass));
        dictToEntity(dict, entity);
        return Db.use().findAll(entity, tClass);
    }

    public static <T> List<T> queryList(Class<T> tClass) throws SQLException {
        return Db.use().findAll(Entity.create(Utils.getTableName(tClass)), tClass);
    }

    public static <T> void save(T data) throws SQLException {
        String table = Utils.getTableName(data.getClass());
        String sql = "select get_sequences('" + table + "') as id from dual ";
        Number number = DbUtil.use().queryNumber(sql);
        if (number == null) {
            throw new RuntimeException("查询id失败 sql为：" + sql);
        }
        DbUtil.use().insert(Entity.create(table).parseBean(data).set("id", number.intValue()));
    }

    public static <T> void save(Entity data) throws SQLException {
        if (StrUtil.isEmpty(data.getTableName())) throw new RuntimeException("请设置 tableName ");
        String sql = "select get_sequences('" + data.getTableName() + "') as id from dual ";
        Number number = DbUtil.use().queryNumber(sql);
        if (number == null) {
            throw new RuntimeException("查询id失败 sql为：" + sql);
        }
        DbUtil.use().insert(data.set("id", number.intValue()));
    }

    public static Entity queryOne(String tableName, Dict dict) throws SQLException {
        if (dict == null) dict = new Dict();
        Entity entity = Entity.create(tableName);
        List<Entity> entities = queryListEntityWidthDict(dict, entity);
        return entities.get(0);
    }

    private static List<Entity> queryListEntityWidthDict(Dict dict, Entity entity) throws SQLException {
        dictToEntity(dict, entity);
        List<Entity> entities = Db.use().find(entity);
        if (entities == null || entities.size() <= 0) {
            throw new SQLException("没有数据");
        }
        return entities;
    }

    private static void dictToEntity(Dict dict, Entity entity) {
        for (String s : dict.keySet()) {
            entity.set(s, dict.get(s));
        }
    }

    public static <T> T queryOne(Class<T> tClass, Dict dict) throws SQLException {
        if (dict == null) dict = new Dict();
        Entity entity = Entity.create(Utils.getTableName(tClass));
        Entity entity1 = queryListEntityWidthDict(dict, entity).get(0);
        return entity1.toBean(tClass);
    }

    public static <T> void update(T t, Dict dict, String... upFields) throws SQLException {
        if (dict == null) dict = new Dict();
        String table = Utils.getTableName(t.getClass());
        Entity where = Entity.create();
        dictToEntity(dict, where);
        Entity props = Entity.parse(t);
        Entity data = Entity.create(table);
        if (ArrayUtil.isEmpty(upFields)) {
            data.putAll(props);
        } else {
            for (String field : upFields) {
                data.set(field, props.get(field));
            }
        }
        Db.use().update(data, where);
    }

    public static <T> void update(Entity record, Entity where) throws SQLException {
        Db.use().update(record, where);
    }
}
