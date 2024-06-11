package cn.bravedawn.service;

import cn.bravedawn.entity.Item;
import cn.bravedawn.model.bo.ItemBO;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/5/13 17:09
 */
public interface ItemService {

    /**
     * 扣减库存
     * @param itemId
     * @param amount
     * @return
     */
    boolean decreaseStock(Integer itemId, Integer amount);

    /**
     * 查询商品信息
     * @param itemId
     * @return
     */
    ItemBO getItemById(long itemId);

    /**
     * 增加销量
     * @param itemId
     * @param amount
     */
    void increaseSales(Integer itemId, Integer amount);

    /**
     * 查询商品
     * @param id 商品id
     * @return
     */
    ItemBO getItem(String id);
}
