package cn.bravedawn.service;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/6/11 21:23
 *
 * 封装本地缓存
 */
public interface CacheService {

    void setCache(String key, Object value);

    Object getCache(String key);
}
