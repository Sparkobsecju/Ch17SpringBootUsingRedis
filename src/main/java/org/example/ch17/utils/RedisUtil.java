package org.example.ch17.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public final class RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    //====================通用====================
    /**
     * 設定快取存活時間
     * @param key Cache 鍵
     * @param timeout 存活時間(秒)
     * @return
     */
    public boolean expire(String key, long timeout) {
        if (timeout > 0) {
            redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根據 key 獲取快取存活時間
     * @param key Cache 鍵，不能為 null
     * @return 存活時間(秒) 返回 0 時表示永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判斷 key 是否存在
     * @param key Cache 鍵
     * @return true 存在 ,false 不存在
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 刪除快取
     * @param key Cache 鍵
     */
    public boolean remove(String key) {
        if (hasKey(key)) {
            return redisTemplate.delete(key);
        } else {
            return false;
        }
    }

    /**
     * 批次刪除多個快取
     * @param keys Cache 鍵
     */
    public void remove(String... keys) {
        for (String key: keys) {
            remove(key);
        }
    }

    /**
     * 批次刪除模式匹配的 key Cache 鍵
     * @param pattern
     */
    public void removePattern(String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }


    //====================String====================
    /**
     * 寫入快取
     * @param key Cache 鍵
     * @param value 值
     */
    public void set(String key, Object value) {
        ValueOperations<Serializable, Object> operations =
                redisTemplate.opsForValue();
        operations.set(key, value);
    }

    /**
     * 寫入快取並設定存活時間
     * @param key Cache 鍵
     * @param value 值
     * @param timeout 存活時間(秒)
     */
    public void set(String key, Object value, long timeout) {
        ValueOperations<Serializable, Object> operations =
                redisTemplate.opsForValue();
        if (timeout > 0) {
            operations.set(key, value, timeout, TimeUnit.SECONDS);
        } else {
            operations.set(key, value);
        }
    }

    /**
     * 根據 key 獲取快取
     * @param key 鍵
     * @return
     */
    public Object get(final String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 遞增 +1
     * @param key Cache 鍵
     * @return 遞增後的值
     */
    public long incr(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    /**
     * 遞增指定的增量 +delta
     * @param key Cache 鍵
     * @param delta 遞增量
     * @return 遞增後的值
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("遞增的數必須大於 0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 遞減 -1
     * @param key Cache 鍵
     * @return 遞減後的值
     */
    public long decry(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }

    /**
     * 遞減指定的減量 -delta
     * @param key Cache 鍵
     * @param delta 遞減量
     * @return 遞減後的值
     */
    public long decry(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("遞減的數必須大於 0");
        }
        return redisTemplate.opsForValue().decrement(key, delta);
    }


    //====================Hash====================
    /**
     * 在 Hash 中存入一個快取資料
     * @param key Cache 鍵
     * @param hashKey Hash 鍵
     * @param value 值
     */
    public void hPut(String key, Object hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 在 Hash 中存入一個快取資料，並設定 Hash 存活時間
     * @param key Cache 鍵
     * @param hashKey Hash 鍵
     * @param value 值
     * @param timeout 存活時間(秒)
     */
    public void hPut(String key, Object hashKey, Object value, long timeout) {
        redisTemplate.opsForHash().put(key, hashKey, value);
        if (timeout > 0) {
            expire(key, timeout);
        }
    }

    /**
     * 在 Hash 中存入多個快取資料
     * @param key Cache 鍵
     * @param map Hash 集合
     */
    public void hPutAll(String key, Map<Object, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 在 Hash 中存入多個快取資料，並設定 Hash 存活時間
     * @param key Cache 鍵
     * @param map Hash 集合
     * @param timeout 存活時間(秒)
     */
    public void hPutAll(String key, Map<Object, Object> map, long timeout) {
        redisTemplate.opsForHash().putAll(key, map);
        if (timeout > 0) {
            expire(key, timeout);
        }
    }

    /**
     * 從 Hash 中獲取一個快取資料
     * @param key Cache 鍵
     * @param hashKey Hash 鍵
     * @return 值
     */
    public Object hGet(String key, Object hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 從 Hash 中獲取多個快取資料
     * @param key Cache 鍵
     * @return 整個 Hash 集合
     */
    public Map<Object, Object> hGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 刪除 Hash 中的快取資料
     * @param key Cache 鍵，不能為 null
     * @param hashKeys Hash key，可以有多個，不能為 null
     */
    public void hDel(String key, Object... hashKeys) {
        redisTemplate.opsForHash().delete(key, hashKeys);
    }

    /**
     * 判斷 Hash 中是否有該 Hash key
     * @param key Cache 鍵
     * @param hashKey Hash 鍵
     * @return true 存在, false 不存在
     */
    public boolean hHasKey(String key, Object hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }


    //====================List====================
    /**
     * 向 List 尾部增加一個快取資料
     * @param key Cache 鍵
     * @param value Element 元素
     */
    public void lAdd(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 向 List 尾部增加一個快取資料，並設定 List 存活時間
     * @param key Cache 鍵
     * @param value Element 元素
     * @param timeout 存活時間(秒)
     */
    public void lAdd(String key, Object value, long timeout) {
        redisTemplate.opsForList().rightPush(key, value);
        if (timeout > 0) {
            expire(key, timeout);
        }
    }

    /**
     * 將 Collection 中的所有元素依次添加到 List 尾部
     * @param key Cache 鍵
     * @param values Element 集合
     */
    public void lAddAll(String key, Collection<Object> values) {
        redisTemplate.opsForList().rightPushAll(key, values);
    }

    /**
     * 將 Collection 中的所有元素依次添加到 List 尾部，並設定 List 存活時間
     * @param key Cache 鍵
     * @param values Element 集合
     * @param timeout 存活時間(秒)
     */
    public void lAddAll(String key, Collection<Object> values, long timeout) {
        redisTemplate.opsForList().rightPushAll(key, values);
        if (timeout > 0) {
            expire(key, timeout);
        }
    }

    /**
     * 透過 index 獲取 List 中的 Element 元素
     * @param key Cache 鍵
     * @param index 索引。index >= 0 時，0 表示第一個元素，1 表示第二個元素，依次類推；
     *              index < 0 時，-1 表示倒數第一個元素，-2 表示倒數第二個元素，依次類推
     *              index 就是由左到右的索引
     * @return 值
     */
    public Object lGet(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 根據 range 獲取 List 中的 Element 集合
     * @param key Cache 鍵
     * @param start 起始索引
     * @param end 結束索引
     *        0 到 -1 表示所有值
     * @return Element 集合
     */
    public List<Object> lGetRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 移除值與 value 相同的 Element
     * @param key Cache 鍵
     * @param value Element 值
     * @return 元素移除的個數
     */
    public long lRemove(String key, Object value) {
        return redisTemplate.opsForList().remove(key, 0, value);
    }

    /**
     * 獲取 List 的長度
     * @param key Cache 鍵
     * @return List 的長度
     */
    public long lSize(String key) {
        return redisTemplate.opsForList().size(key);
    }


    //====================Set====================
    /**
     * 向 Set總表 中的其中一個 Set 增加一個到多個 Elements
     * @param key Cache 鍵
     * @param values Element 集合
     */
    public void sAdd(String key, Object... values) {
        redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 向 Set總表 中的其中一個 Set 增加一個到多個 Elements，並設定 Set 存活時間
     * @param key Cache 鍵
     * @param values Element 集合
     * @param timeout 存活時間(秒)
     */
    public void sAdd(String key, long timeout, Object... values) {
        redisTemplate.opsForSet().add(key, values);
        if (timeout > 0) {
            expire(key, timeout);
        }
    }

    /**
     * 根據 key 向 Set總表 獲取對應的 Set (獲取所有 Elements)
     * @param key Cache 鍵
     * @return Set 集合
     */
    public Set<Object> sGet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 從 Collection 中刪除一個或多個 Elements
     * @param key Cache 鍵
     * @param values Elements 集合
     * @return 刪除的元素個數
     */
    public long sRemove(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * 從 Set 中查詢是否有該 Element
     * @param key Cache 鍵
     * @param value Element 值
     * @return true 存在, false 不存在
     */
    public boolean sExist(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 獲取 Set 的長度
     * @param key Cache 鍵
     * @return Set 的長度
     */
    public long sSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }


    //====================ZSet====================
    /**
     * 向 ZSet (有序集合)中增加一個 Element
     * @param key Cache 鍵
     * @param value Element 值
     * @param score 分數
     */
    public void zAdd(String key, Object value, double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * 向 ZSet (有序集合)中獲取 分數range 內的 Element
     * @param key 鍵
     * @param min 最小分數
     * @param max 最大分數
     * @return Set(Element 集合)
     */
    public Set<Object> zRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    /**
     * 向 ZSet (有序集合)中刪除一個或多個 Elements，並返回元素刪除的個數
     * @param key 鍵
     * @param values Elements 集合，可以是一到多個
     * @return 刪除的 Element 個數，如果沒有刪除任何元素，返回 0
     */
    public long zRemove(String key, Object... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }

    /**
     * 獲取 ZSet (有序集合) 的長度
     * @param key 鍵
     * @return ZSet 的長度
     */
    public long zSize(String key) {
        return redisTemplate.opsForZSet().size(key);
    }
}
