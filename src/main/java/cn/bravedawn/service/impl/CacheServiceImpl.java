package cn.bravedawn.service.impl;

import cn.bravedawn.service.CacheService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/6/11 21:24
 */

@Service
public class CacheServiceImpl implements CacheService {

    private Cache<String,Object> commonCache = null;

    @PostConstruct
    public void init(){
        commonCache = CacheBuilder.newBuilder()
                //设置缓存容器的初始容量为10
                .initialCapacity(10)
                //设置缓存中最大可以存储100个KEY,超过100个之后会按照LRU的策略移除缓存项
                .maximumSize(100)
                //设置写缓存后多少秒过期
                .expireAfterWrite(60, TimeUnit.SECONDS).build();
    }


    @Override
    public void setCache(String key, Object value) {
        commonCache.put(key, value);
    }

    @Override
    public Object getCache(String key) {
        // 如果没有找到则返回null
        return commonCache.getIfPresent(key);
    }
}
