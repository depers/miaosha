package cn.bravedawn.service;

import cn.bravedawn.model.dto.OrderReqDTO;
import cn.bravedawn.entity.OrderInfo;
import cn.bravedawn.entity.UserInfo;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/5/13 16:29
 */
public interface OrderService {

    OrderInfo createOrder(UserInfo userInfo, OrderReqDTO reqDTO);

}
