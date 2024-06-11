package cn.bravedawn.controller;

import cn.bravedawn.entity.Item;
import cn.bravedawn.exception.BusinessException;
import cn.bravedawn.exception.ExceptionEnum;
import cn.bravedawn.model.bo.ItemBO;
import cn.bravedawn.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/6/6 19:16
 */

@RestController
@RequestMapping("/item")
@Slf4j
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/get/{id}")
    public ItemBO getItem(@PathVariable(value = "id", required = false) String id) {

        if (StringUtils.isBlank(id)) {
            log.error("请求参数错误，id为空");
            throw new BusinessException(ExceptionEnum.PARAMETER_VALIDATION_ERROR);
        }

       return itemService.getItem(id);
    }

}
