package com.jicg.service.core.config.auth;

import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.util.SaFoxUtil;
import cn.hutool.core.io.FileUtil;
import com.jicg.service.core.Utils;
import com.jicg.service.core.config.auth.storetool.ObjectRocksDbTemplate;
import com.jicg.service.core.config.auth.storetool.StringRocksDbTemplate;
import lombok.extern.slf4j.Slf4j;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jicg on 2021/10/8
 */
@Slf4j
@Component
@ConditionalOnProperty(value = "app.auth.enable", havingValue = "true")
@ConditionalOnClass(org.rocksdb.RocksDB.class)
public class SaTokenDaoRocksdb implements SaTokenDao {
    StringRocksDbTemplate stringRocksDbTemplate;
    ObjectRocksDbTemplate objectRocksDbTemplate;


    public SaTokenDaoRocksdb() {
        try {
            Options options = new Options();
            options.setCreateIfMissing(true);
            FileUtil.mkdir(Utils.ApplicationHomePath() + "/data/cache");
            stringRocksDbTemplate = new StringRocksDbTemplate(
                    RocksDB.open(options, Utils.ApplicationHomePath() + "/data/cache/core-sess.cache")
            );
//            stringRocksDbTemplate.forPrint();
            objectRocksDbTemplate = new ObjectRocksDbTemplate(
                    RocksDB.open(options, Utils.ApplicationHomePath() + "/data/cache/core-sess.obj.cache")
            );
        } catch (RocksDBException e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }


    /**
     * 获取Value，如无返空
     */
    @Override
    public String get(String key) {
        return stringRocksDbTemplate.getString(key);
    }

    /**
     * 写入Value，并设定存活时间 (单位: 秒)
     */
    @Override
    public void set(String key, String value, long timeout) {
        if (timeout == 0 || timeout <= SaTokenDao.NOT_VALUE_EXPIRE) {
            return;
        }
        // 判断是否为永不过期
        if (timeout == SaTokenDao.NEVER_EXPIRE) {
            stringRocksDbTemplate.putString(key, value, SaTokenDao.NEVER_EXPIRE);
        } else {
            stringRocksDbTemplate.putString(key, value, timeout * 1000);
        }
    }

    /**
     * 修修改指定key-value键值对 (过期时间不变)
     */
    @Override
    public void update(String key, String value) {
        long expire = getTimeout(key);
        // -2 = 无此键
        if (expire == SaTokenDao.NOT_VALUE_EXPIRE) {
            return;
        }
        this.set(key, value, expire);
    }

    /**
     * 删除Value
     */
    @Override
    public void delete(String key) {
        stringRocksDbTemplate.delete(key);
    }

    /**
     * 获取Value的剩余存活时间 (单位: 秒)
     */
    @Override
    public long getTimeout(String key) {
        return stringRocksDbTemplate.getExpire(key);
    }

    /**
     * 修改Value的剩余存活时间 (单位: 秒)
     */
    @Override
    public void updateTimeout(String key, long timeout) {
        // 判断是否想要设置为永久
        if (timeout == SaTokenDao.NEVER_EXPIRE) {
            long expire = getTimeout(key);
            if (expire == SaTokenDao.NEVER_EXPIRE) {
                // 如果其已经被设置为永久，则不作任何处理
            } else {
                // 如果尚未被设置为永久，那么再次set一次
                this.set(key, this.get(key), timeout);
            }
            return;
        }
        stringRocksDbTemplate.expire(key, timeout * 1000);
    }


    /**
     * 获取Object，如无返空
     */
    @Override
    public Object getObject(String key) {
//        System.out.println(key);
        return objectRocksDbTemplate.get(key);
    }

    /**
     * 写入Object，并设定存活时间 (单位: 秒)
     */
    @Override
    public void setObject(String key, Object object, long timeout) {
        if (timeout == 0 || timeout <= SaTokenDao.NOT_VALUE_EXPIRE) {
            return;
        }
        // 判断是否为永不过期
        if (timeout == SaTokenDao.NEVER_EXPIRE) {
            objectRocksDbTemplate.put(key, object, SaTokenDao.NEVER_EXPIRE);
        } else {
            objectRocksDbTemplate.put(key, object, timeout);
        }
    }

    /**
     * 更新Object (过期时间不变)
     */
    @Override
    public void updateObject(String key, Object object) {
        long expire = getObjectTimeout(key);
        // -2 = 无此键
        if (expire == SaTokenDao.NOT_VALUE_EXPIRE) {
            return;
        }
        this.setObject(key, object, expire);
    }

    /**
     * 删除Object
     */
    @Override
    public void deleteObject(String key) {
        objectRocksDbTemplate.delete(key);
    }

    /**
     * 获取Object的剩余存活时间 (单位: 秒)
     */
    @Override
    public long getObjectTimeout(String key) {
        return objectRocksDbTemplate.getExpire(key);
    }

    /**
     * 修改Object的剩余存活时间 (单位: 秒)
     */
    @Override
    public void updateObjectTimeout(String key, long timeout) {
        // 判断是否想要设置为永久
        if (timeout == SaTokenDao.NEVER_EXPIRE) {
            long expire = getObjectTimeout(key);
            if (expire == SaTokenDao.NEVER_EXPIRE) {
                // 如果其已经被设置为永久，则不作任何处理
            } else {
                // 如果尚未被设置为永久，那么再次set一次
                this.setObject(key, this.getObject(key), timeout);
            }
            return;
        }
        objectRocksDbTemplate.expire(key, timeout);
    }

    /**
     * 搜索数据
     */
    @Override
    public List<String> searchData(String prefix, String keyword, int start, int size) {
        List<String> keys = stringRocksDbTemplate.keys();
        return SaFoxUtil.searchList(keys, prefix, keyword, start, size);
    }

}
