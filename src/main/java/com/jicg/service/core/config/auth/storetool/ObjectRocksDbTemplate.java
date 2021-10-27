package com.jicg.service.core.config.auth.storetool;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.springframework.util.SerializationUtils;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 * @author jicg on 2021/10/8
 */

public class ObjectRocksDbTemplate {
    private final RocksDB rocksDB;

    public RocksDB getRocksDB() {
        return rocksDB;
    }

    public void delete(String key) {
        try {
            rocksDB.delete(key.getBytes(StandardCharsets.UTF_8));
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
    }

    public long getExpire(String key) {
        try {
            ValueBean valueBean = deserializeToValBean(rocksDB.get(key.getBytes(StandardCharsets.UTF_8)));
            if (valueBean == null) return 0;
            return valueBean.timeout;
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public void expire(String key, long timeout) {
        try {
            ValueBean valueBean = deserializeToValBean(rocksDB.get(key.getBytes(StandardCharsets.UTF_8)));
            valueBean.timeout = timeout;
            valueBean.cdate = System.currentTimeMillis();
            rocksDB.put(key.getBytes(StandardCharsets.UTF_8), serialize(valueBean));
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ValueBean implements Serializable {
        private Serializable value;
        private long timeout;
        private long cdate = System.currentTimeMillis();

        public ValueBean(Serializable value, long timeout) {
            this.value = value;
            this.timeout = timeout;
        }
    }

    public ObjectRocksDbTemplate(RocksDB rocksDB) {
        this.rocksDB = rocksDB;
    }

    public void put(String key, Object val, long timeout) {
        try {
            rocksDB.put(key.getBytes(StandardCharsets.UTF_8), serialize(new ValueBean((Serializable) val, timeout)));
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
    }

    private byte[] serialize(ValueBean valueBean) {
        if (valueBean == null) return null;
        return SerializationUtils.serialize(valueBean);
    }

    private Object deserialize(byte[] datas) {
        if (datas == null) return null;
        return SerializationUtils.deserialize(datas);
    }

    private ValueBean deserializeToValBean(byte[] datas) {
        return (ValueBean) deserialize(datas);
    }


    public Object get(String key) {
        try {
            ValueBean valueBean = deserializeToValBean(rocksDB.get(key.getBytes(StandardCharsets.UTF_8)));
            if (valueBean == null) return null;
            return valueBean.value;
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
        return null;
    }
}
