package  com.jicg.service.core.database;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.*;
import cn.hutool.db.handler.NumberHandler;
import cn.hutool.db.handler.PageResultHandler;
import cn.hutool.db.handler.RsHandler;
import cn.hutool.db.sql.*;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import javax.rmi.CORBA.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author jicg on 2021/3/28
 */
@Slf4j
public class DB4BosFn {

    public static List<Long> insert(Entity... entities) throws SQLException {
        Connection conn = null;
        try {
            conn = Db.use().getConnection();
            return getSql(conn, entities);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            DbUtil.close(conn);
        }
    }


    public static PreparedStatement prepareStatementForBatch(Connection conn, String sql, List<String> fields, Entity... entities) throws SQLException {
        Assert.notBlank(sql, "Sql String must be not blank!");
        sql = sql.trim();
        SqlLog.INSTANCE.logForBatch(sql);
        PreparedStatement ps = conn.prepareStatement(sql, new String[]{"id"});
        //null参数的类型缓存，避免循环中重复获取类型
        final Map<Integer, Integer> nullTypeMap = new HashMap<>();
        for (Entity entity : entities) {
            StatementUtil.fillParams(ps, CollUtil.valuesOfKeys(entity, fields), nullTypeMap);
            ps.addBatch();
        }
        return ps;
    }

    public static List<Long> getSql(Connection conn, Entity... entities) throws SQLException {
        List<Long> ids = new ArrayList<>();
        Entity entity = entities[0];
        List<String> fields = new ArrayList<>();
        StringBuffer sql = getSqlBuffer(entity, fields);

        PreparedStatement ps = conn.prepareStatement(sql.toString(), new String[]{"id"});
        for (int i = 0; i < entities.length; i++) {
            final Map<Integer, Integer> nullTypeMap = new HashMap<>();
            StatementUtil.fillParams(ps, CollUtil.valuesOfKeys(entities[i], fields), nullTypeMap);
            ps.addBatch();
            if (i % 1000 == 0) {
                ps.executeBatch();
                ids.addAll(getIds(ps));
            }
        }
        ps.executeBatch();
        ids.addAll(getIds(ps));
        return ids;
    }

    private static List<Long> getIds(PreparedStatement ps) throws SQLException {
        List<Long> ids = new ArrayList<>();
        ResultSet rs = ps.getGeneratedKeys();
        while (rs != null && rs.next()) {
            try {
                ids.add(rs.getBigDecimal(1).longValue());
            } catch (SQLException e) {
                // 可能会出现没有主键返回的情况
                e.printStackTrace();
            }
        }
        return ids;
    }

    private static StringBuffer getSqlBuffer(Entity entity,
                                             List<String> fields) {
        final StringBuilder fieldsPart = new StringBuilder();
        final StringBuilder placeHolder = new StringBuilder();
        boolean isFirst = true;
        StringBuffer sql = new StringBuffer();
        for (Map.Entry<String, Object> entry : entity.entrySet()) {
            String field = entry.getKey();
            Object value = entry.getValue();
            if (StrUtil.isNotBlank(field) && !(value instanceof Collection)) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    // 非第一个参数，追加逗号
                    fieldsPart.append(", ");
                    placeHolder.append(", ");
                }

                fieldsPart.append(field);
                if (field.equalsIgnoreCase("id")) {
                    placeHolder.append("get_sequences('" + entity.getTableName() + "')");
                } else {
                    fields.add(field);
                    placeHolder.append("?");
                }
            }
        }
        sql.append("INSERT INTO ")//
                .append(entity.getTableName()).append(" (").append(fieldsPart).append(") VALUES (")//
                .append(placeHolder.toString()).append(")");
        return sql;
    }

    public static List<Object> callProc(Connection conn, String pro, Object... args) throws SQLException {
        String str = "";
        DbUtil.use().query("");
        return new ArrayList<>();
    }


    public static PageResult<Entity> page(SqlBuilder sqlBuilder, Page page) throws SQLException {
        Connection conn = null;
        try {
            Db db = DbUtil.use();
            conn = db.getConnection();
            SqlConnRunner runner = db.getRunner();
            SqlBuilder sqlBuilderCnt = SqlBuilder.of(sqlBuilder.build()).insertPreFragment("SELECT count(1) from(").append(")");
            int count = query(conn, sqlBuilderCnt.build(), new NumberHandler(), sqlBuilder.getParamValueArray()).intValue();
            final PageResultHandler pageResultHandler = new PageResultHandler(new PageResult<>(page.getPageNumber(), page.getPageSize(), count), true);
            return runner.page(conn, sqlBuilder, page, pageResultHandler);
        } catch (Exception e) {
            log.error(sqlBuilder.build() + " :::: " + JSONUtil.toJsonStr(sqlBuilder.getParamValues()));
            throw e;
        } finally {
            DbUtil.close(conn);
        }
    }


    public static <T> T query(Connection conn, String sql, RsHandler<T> rsh, Object[] params) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = StatementUtil.prepareStatement(conn, sql, params);
            rs = ps.executeQuery();
            return rsh.handle(rs);
        } finally {
            DbUtil.close(ps, rs);
        }
    }


}
