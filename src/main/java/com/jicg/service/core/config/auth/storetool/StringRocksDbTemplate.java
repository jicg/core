package com.jicg.service.core.config.auth.storetool;

import com.jicg.service.core.config.auth.storetool.ObjectRocksDbTemplate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksIterator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jicg on 2021/10/8
 */

public class StringRocksDbTemplate {
    private final ObjectRocksDbTemplate rocksDB;

    public ObjectRocksDbTemplate getRocksDB() {
        return rocksDB;
    }

    public void delete(String key) {
        rocksDB.delete(key);
    }

    public long getExpire(String key) {
        return rocksDB.getExpire(key);
    }

    public void expire(String key, long timeout) {
        rocksDB.expire(key, timeout);
    }

    public List<String> keys() {
        RocksIterator iter = rocksDB.getRocksDB().newIterator();
        List<String> obj = new ArrayList<>();
        for (iter.seekToFirst(); iter.isValid(); iter.next()) {
            obj.add(new String(iter.key()));
        }
        return obj;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ValueBean {
        private String value;
        private long timeout;
    }

    public StringRocksDbTemplate(RocksDB rocksDB) {
        this.rocksDB = new ObjectRocksDbTemplate(rocksDB);
    }


    public void putString(String key, String val, long timeout) {
        rocksDB.put(key, val, timeout);
    }

    public String getString(String key) {
        return (String) rocksDB.get(key);
    }
}
