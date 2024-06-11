package cn.bravedawn.controller;

import cn.bravedawn.exception.BusinessException;
import cn.bravedawn.exception.ExceptionEnum;
import cn.bravedawn.model.dto.login.UserLoginReqDTO;
import cn.bravedawn.model.dto.UserRegisterReqDTO;
import cn.bravedawn.model.dto.login.UserLoginRespDTO;
import cn.bravedawn.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/5/29 11:00
 *
 * 注册登录
 */

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping(value = "/register")
    public void register(@RequestBody @Valid UserRegisterReqDTO user,
                         BindingResult bindingResult,
                         HttpServletRequest request) {
        // 参数校验
        if (bindingResult.hasErrors()) {
            log.error("请求参数错误, error={}", bindingResult.getAllErrors());
            throw new BusinessException(ExceptionEnum.PARAMETER_VALIDATION_ERROR);
        }

        // 调用注册service
        userService.register(user, request);
    }



    @PostMapping(value = "/login")
    public UserLoginRespDTO login(@RequestBody @Valid UserLoginReqDTO reqDTO,
                                  BindingResult bindingResult,
                                  HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            log.error("请求参数错误");
            throw new BusinessException(ExceptionEnum.PARAMETER_VALIDATION_ERROR);
        }

        // 调用登录service
        return userService.login(reqDTO, request);
    }

}
