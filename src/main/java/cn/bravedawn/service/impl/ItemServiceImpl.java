package cn.bravedawn.service.impl;

import cn.bravedawn.dao.ItemMapper;
import cn.bravedawn.dao.PromoMapper;
import cn.bravedawn.entity.Item;
import cn.bravedawn.entity.Promo;
import cn.bravedawn.model.bo.ItemBO;
import cn.bravedawn.model.bo.PromoBO;
import cn.bravedawn.service.ItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/5/13 17:10
 */

@Service
public class ItemServiceImpl implements ItemService {


    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private PromoMapper promoMapper;

    @Override
    public boolean decreaseStock(Integer itemId, Integer amount) {
        int affectedRow =  itemMapper.decreaseStock(itemId,amount);
        if(affectedRow > 0){
            // 更新库存成功
            return true;
        }else{
            // 更新库存失败
            return false;
        }
    }

    @Override
    public ItemBO getItemById(Integer itemId) {
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
        itemBO.setPromo(promoBO);
        return itemBO;
    }

    @Override
    public void increaseSales(Integer itemId, Integer amount) {
        itemMapper.increaseSale(itemId, amount);
    }
}
