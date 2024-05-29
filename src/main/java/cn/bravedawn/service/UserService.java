package cn.bravedawn.service;

import cn.bravedawn.model.dto.login.UserLoginReqDTO;
import cn.bravedawn.model.dto.UserRegisterReqDTO;
import cn.bravedawn.model.dto.login.UserLoginRespDTO;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/5/29 11:16
 */
public interface UserService {

    /**
     * 注册
     * @param reqDTO 请求参数
     * @param request Http请求对象
     */
    void register(UserRegisterReqDTO reqDTO, HttpServletRequest request);

    /**
     * 登录
     * @param reqDTO 请求参数
     * @param response Http响应对象
     */
    UserLoginRespDTO login(UserLoginReqDTO reqDTO, HttpServletRequest request);
}
