package cn.bravedawn.service.impl;

import cn.bravedawn.dao.OrderInfoMapper;
import cn.bravedawn.dao.UserInfoMapper;
import cn.bravedawn.entity.OrderInfo;
import cn.bravedawn.entity.UserInfo;
import cn.bravedawn.exception.BusinessException;
import cn.bravedawn.exception.ExceptionEnum;
import cn.bravedawn.model.bo.ItemBO;
import cn.bravedawn.model.dto.OrderReqDTO;
import cn.bravedawn.service.ItemService;
import cn.bravedawn.service.OrderService;
import cn.bravedawn.util.SnowflakeSequenceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/5/13 16:30
 */

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private SnowflakeSequenceUtil snowflakeSequenceUtil;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderInfo createOrder(UserInfo userInfo, OrderReqDTO reqDTO) {
        //1.校验下单状态,下单的商品是否存在，用户是否合法，购买数量是否正确
        ItemBO itemModel = itemService.getItemById(Integer.parseInt(reqDTO.getItemId()));
        if(itemModel == null){
            throw new BusinessException(ExceptionEnum.PARAMETER_VALIDATION_ERROR);
        }

        // 校验用户是否存在
        int userInfoCount = userInfoMapper.selectCountById(userInfo.getId());
        if(userInfoCount == 0){
            throw new BusinessException(ExceptionEnum.PARAMETER_VALIDATION_ERROR);
        }

        // 校验购买数量
        int amount = Integer.parseInt(reqDTO.getAmount());
        if(amount <= 0 || amount > 99){
            throw new BusinessException(ExceptionEnum.PARAMETER_VALIDATION_ERROR);
        }

        Integer promoId = Integer.valueOf(reqDTO.getPromoId());
        //校验活动信息
        //（1）校验对应活动是否存在这个适用商品
        if(promoId.intValue() != itemModel.getPromo().getId()){
            throw new BusinessException(ExceptionEnum.PARAMETER_VALIDATION_ERROR);
            //（2）校验活动是否正在进行中
        }else if(itemModel.getPromo().getStatus() != 2) {
            throw new BusinessException(ExceptionEnum.PARAMETER_VALIDATION_ERROR);
        }

        //2.落单减库存
        boolean result = itemService.decreaseStock(itemModel.getId(), amount);
        if(!result){
            throw new BusinessException(ExceptionEnum.STOCK_NOT_ENOUGH);
        }

        //3.订单入库
        OrderInfo orderModel = new OrderInfo();
        orderModel.setUserId(userInfo.getId());
        orderModel.setItemId(itemModel.getId());
        orderModel.setAmount(amount);
        orderModel.setItemPrice(itemModel.getPromo().getPromoItemPrice());
        orderModel.setPromoId(promoId);
        orderModel.setOrderPrice(new BigDecimal(orderModel.getItemPrice()).multiply(new BigDecimal(amount)).doubleValue());

        // //生成交易流水号存在重复的情况，需要在方法上加同步操作
        long id = snowflakeSequenceUtil.getNextNum();
        orderModel.setId(id);
        orderInfoMapper.insertSelective(orderModel);

        // //加上商品的销量
        itemService.increaseSales(itemModel.getId(), amount);
        //4.返回前端
        return orderModel;
    }


}
