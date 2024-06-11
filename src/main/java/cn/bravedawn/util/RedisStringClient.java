package cn.bravedawn.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/6/5 19:09
 */
@Component
@Slf4j
public class RedisStringClient<T> {

    @Autowired
    private RedisTemplate<String, T> redisTemplate;


    /**
     * 获取指定key的值,如果key不存在返回null
     * 返回值：返回 key 的值，如果 key 不存在时，返回 nil
     *
     * @param key
     * @return
     */
    public T get(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("get命令操作失败，请求参数：{}", key, e);
        }
        return null;
    }


    /**
     * 设置key的值为value
     * 返回值：操作成功完成时返回 OK
     *
     * @param key
     * @return
     */
    public String set(String key, T value) {
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            log.error("set命令操作失败，参数key：{}，参数value：{}", key, value, e);
        }
        return null;
    }


    /**
     * 删除指定的key，返回值：被删除 key 的数量
     * 返回值：被删除 key 的数量
     *
     * @param key
     * @return
     */
    public Boolean del(String key) {
        try {
            return redisTemplate.delete(key);
        } catch (Exception e) {
            log.error("del命令操作失败，参数key：{}", key, e);
        }
        return false;
    }


    /**
     * 通过key向指定的value值追加值
     * 返回值：追加指定值之后， key中字符串的长度
     *
     * @param key
     * @return
     */
    public Integer append(String key, String value) {
        try {
            return redisTemplate.opsForValue().append(key, value);
        } catch (Exception e) {
            log.error("append命令操作失败，参数key：{}，参数value：{}", key, value, e);
        }
        return 0;
    }

    /**
     * 判断key是否存在
     * 返回值：true/false
     *
     * @param key
     * @return
     */
    public Boolean exists(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("exists命令操作失败，参数key：{}", key, e);
        }
        return false;
    }


    /**
     * 设置key的超时时间为seconds
     * 返回值：若 key 存在返回 1 ，否则返回 0
     *
     * @param key
     * @return
     */
    public Boolean expire(String key, long seconds) {
        try {
            return redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("expire命令操作失败，参数key：{}，参数seconds：{}", key, seconds, e);
        }
        return false;
    }

    /**
     * 返回 key 的剩余过期时间（单位秒）
     * 返回值：当 key 不存在时，返回 -2 。 当 key 存在但没有设置剩余生存时间时，返回 -1 。 否则，以秒为单位，返回 key 的剩余生存时间
     *
     * @param key
     * @return
     */
    public Long ttl(String key) {
        try {
            return redisTemplate.getExpire(key, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("ttl命令操作失败，参数key：{}", key, e);
        }
        return 0L;
    }


    /**
     * 设置指定key的值为value,当key不存在时才设置
     * 返回值：设置成功返回 1，设置失败返回 0
     *
     * @param key
     * @return
     */
    public Boolean setnx(String key, T value) {
        try {
            return redisTemplate.opsForValue().setIfAbsent(key, value);
        } catch (Exception e) {
            log.error("setnx命令操作失败，参数key：{}，参数value：{}", key, value, e);
        }
        return false;
    }

    /**
     * 设置指定key的值为value,并设置过期时间
     * 返回值：设置成功时返回 OK
     *
     * @param key
     * @return
     */
    public void setex(String key, T value, long seconds) {
        try {
            redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("setex命令操作失败，参数key：{}，参数value：{}", key, value, e);
        }
    }

    /**
     * 通过key 和offset 从指定的位置开始将原先value替换
     * 返回值：被修改后的字符串长度
     *
     * @param key
     * @return
     */
    public void setrange(String key, int offset, T value) {
        try {
            redisTemplate.opsForValue().set(key, value, offset);
        } catch (Exception e) {
            log.error("setrange命令操作失败，参数key：{}，参数value：{}，参数offset：{}", key, value, offset, e);
        }
    }


    /**
     * 通过批量的key获取批量的value
     * 返回值：一个包含所有给定 key 的值的列表。
     *
     * @param keys
     * @return
     */
    public List<T> mget(List<String> keys) {
        try {
            return redisTemplate.opsForValue().multiGet(keys);
        } catch (Exception e) {
            log.error("mget命令操作失败，参数key：{}", keys.toString(), e);
        }
        return null;
    }

    /**
     * 批量的设置key:value,也可以一个
     * 返回值：总是返回 OK
     *
     * @param map
     * @return
     */
    public void mset(Map<String, T> map) {
        try {
            redisTemplate.opsForValue().multiSet(map);
        } catch (Exception e) {
            log.error("mset命令操作失败，参数key：{}", map.toString(), e);
        }
    }


    /**
     * 设置key的值,并返回一个旧值
     * 返回值：返回给定 key 的旧值，当 key 没有旧值时，即 key 不存在时，返回 nil
     *
     * @param key
     * @return
     */
    public T getSet(String key, T value) {
        try {
            return redisTemplate.opsForValue().getAndSet(key, value);
        } catch (Exception e) {
            log.error("getSet命令操作失败，参数key：{}，参数value：{}", key, value, e);
        }
        return null;
    }

    /**
     * 通过下标和 key 获取指定下标位置的 value
     * 返回值：截取得到的子字符串
     *
     * @param key
     * @return
     */
    public String getrange(String key, int startOffset, int endOffset) {
        try {
            return redisTemplate.opsForValue().get(key, startOffset, endOffset);
        } catch (Exception e) {
            log.error("getrange命令操作失败，参数key：{}，参数startOffset：{}，参数offset：{}", key, startOffset, endOffset, e);
        }
        return null;
    }


    /**
     * 通过key 对value进行加值+1操作,当value不是int类型时会返回错误,当key不存在是则value为1
     * 返回值：执行INCR命令之后 key 的值
     *
     * @param key
     * @return
     */
    public Long incr(String key) {
        try {
            return redisTemplate.opsForValue().increment(key);
        } catch (Exception e) {
            log.error("incr命令操作失败，参数key：{}", key, e);
        }
        return 0L;
    }


    /**
     * 通过key给指定的value加值
     * 返回值：执行INCR命令之后 key 的值
     *
     * @param key
     * @return
     */
    public Long incrBy(String key, long increment) {
        try {
            return redisTemplate.opsForValue().increment(key, increment);
        } catch (Exception e) {
            log.error("incrBy命令操作失败，参数key：{}，参数increment：{}", key, increment, e);
        }
        return 0L;
    }

    /**
     * 对key的值做减减操作
     * 返回值：执行INCR命令之后 key 的值
     *
     * @param key
     * @return
     */
    public Long decr(String key) {
        try {
            return redisTemplate.opsForValue().decrement(key);
        } catch (Exception e) {
            log.error("decr命令操作失败，参数key：{}", key, e);
        }
        return 0L;
    }

    /**
     * 对key的值做减减操作,减去指定的值
     * 返回值：执行INCR命令之后 key 的值
     *
     * @param key
     * @return
     */
    public Long decrBy(String key, long decrement) {
        try {
            return redisTemplate.opsForValue().decrement(key, decrement);
        } catch (Exception e) {
            log.error("decrBy命令操作失败，参数key：{}，参数decrement：{}", key, decrement, e);
        }
        return 0L;
    }


    /**
     * 通过key获取value值的长度
     * 返回值：value值的长度
     *
     * @param key
     * @return
     */
    public Long strlen(String key) {
        try {
            return redisTemplate.opsForValue().size(key);
        } catch (Exception e) {
            log.error("strlen命令操作失败，参数key：{}", key, e);
        }
        return 0L;
    }


}
