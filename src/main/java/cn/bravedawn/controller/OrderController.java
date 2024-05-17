package cn.bravedawn.controller;

import cn.bravedawn.model.Vo;
import cn.bravedawn.model.dto.OrderReqDTO;
import cn.bravedawn.entity.OrderInfo;
import cn.bravedawn.entity.UserInfo;
import cn.bravedawn.exception.BusinessException;
import cn.bravedawn.exception.ExceptionEnum;
import cn.bravedawn.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/5/13 15:51
 */

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Vo createOrder(@RequestBody @Valid OrderReqDTO reqDTO, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws BusinessException {

        if(bindingResult.hasErrors()) {
            log.error("请求参数错误");
            throw new BusinessException(ExceptionEnum.PARAMETER_VALIDATION_ERROR);
        }

        // todo 暂时先写死，获取用户的登陆信息
        UserInfo userModel = new UserInfo();
        userModel.setId(1);
        userModel.setAge(18);
        userModel.setName("小明");
        userModel.setGender(0);

        // 创建订单
        OrderInfo orderModel = orderService.createOrder(userModel, reqDTO);

        return Vo.success(orderModel);
    }
}
