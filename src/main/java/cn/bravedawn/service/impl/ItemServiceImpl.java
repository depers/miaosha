package cn.bravedawn.service.impl;

import cn.bravedawn.common.RedisKeyEnum;
import cn.bravedawn.dao.ItemMapper;
import cn.bravedawn.dao.PromoMapper;
import cn.bravedawn.entity.Item;
import cn.bravedawn.entity.Promo;
import cn.bravedawn.model.bo.ItemBO;
import cn.bravedawn.model.bo.PromoBO;
import cn.bravedawn.service.CacheService;
import cn.bravedawn.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/5/13 17:10
 */

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {


    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private PromoMapper promoMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private CacheService cacheService;

    @Override
    public boolean decreaseStock(Integer itemId, Integer amount) {
        int affectedRow = itemMapper.decreaseStock(itemId, amount);
        if (affectedRow > 0) {
            // 更新库存成功
            return true;
        } else {
            // 更新库存失败
            return false;
        }
    }

    @Override
    public ItemBO getItemById(long itemId) {
        Item item = itemMapper.selectByPrimaryKey(itemId);
        if (item == null) {
            return null;
        }

        Promo promo = promoMapper.selectByItemId(itemId);
        if (promo == null) {
            return null;
        }
        ItemBO itemBO = new ItemBO();
        PromoBO promoBO = new PromoBO();
        BeanUtils.copyProperties(item, itemBO);

        BeanUtils.copyProperties(promo, promoBO);
        promoBO.setStartDate(new DateTime(promo.getStartDate()));
        promoBO.setEndDate(new DateTime(promo.getEndDate()));
        itemBO.setPromo(promoBO);
        return itemBO;
    }

    @Override
    public void increaseSales(Integer itemId, Integer amount) {
        itemMapper.increaseSale(itemId, amount);
    }

    @Override
    public ItemBO getItem(String id) {
        String itemKey = String.format(RedisKeyEnum.ITEM_INFO.getKey(), id);
        ItemBO item = null;
        // 先从本地缓存中获取
        item = (ItemBO) cacheService.getCache(itemKey);

        // 从Redis中获取
        if (item == null) {
            log.info("本地缓存中没有找到，key={}", itemKey);
            item = (ItemBO) redisTemplate.opsForValue().get(itemKey);
            if (item == null) {
                log.info("redis缓存中没有找到，key={}", itemKey);
                // 从数据库中获取
                item = getItemById(Long.parseLong(id));
                redisTemplate.opsForValue().set(itemKey, item);
                redisTemplate.expire(itemKey, RedisKeyEnum.ITEM_INFO.getExpireTime(), TimeUnit.SECONDS);
            }
            cacheService.setCache(itemKey, item);
        }

        return item;
    }
}
