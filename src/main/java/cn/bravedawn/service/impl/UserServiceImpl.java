package cn.bravedawn.service.impl;

import cn.bravedawn.common.Constants;
import cn.bravedawn.common.RedisKeyEnum;
import cn.bravedawn.dao.UserInfoMapper;
import cn.bravedawn.entity.UserInfo;
import cn.bravedawn.exception.BusinessException;
import cn.bravedawn.exception.ExceptionEnum;
import cn.bravedawn.model.dto.login.UserLoginReqDTO;
import cn.bravedawn.model.dto.UserRegisterReqDTO;
import cn.bravedawn.model.dto.login.UserLoginRespDTO;
import cn.bravedawn.service.UserService;
import cn.bravedawn.util.RedisStringClient;
import cn.hutool.json.JSONUtil;
import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.druid.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/5/29 11:16
 */

@Service
@Slf4j
public class UserServiceImpl implements UserService {


    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RedisStringClient<UserInfo> redisStringClient;


    @Override
    public void register(UserRegisterReqDTO user, HttpServletRequest request) {
        //用户的注册流程
        try {
            UserInfo userModel = new UserInfo();
            userModel.setName(user.getName());
            userModel.setGender(user.getGender());
            userModel.setAge(user.getAge());
            userModel.setPhone(user.getTelPhone());
            String encodedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            userModel.setPassword(encodedPassword);
            userInfoMapper.insertSelective(userModel);
        } catch (DuplicateKeyException duplicateKeyException) {
            log.error("该用户手机号已注册，phone={}", user.getTelPhone());
            throw new BusinessException(ExceptionEnum.USER_IS_REGISTERED);
        }

    }

    @Override
    public UserLoginRespDTO login(UserLoginReqDTO reqDTO, HttpServletRequest request) {
        UserInfo userInfo = userInfoMapper.selectOnebyUsername(reqDTO.getUsername());
        if (userInfo == null) {
            log.error("用户不存在, username={}", reqDTO.getUsername());
            throw new BusinessException(ExceptionEnum.USER_NOT_EXIST);
        }

        if (!BCrypt.checkpw(reqDTO.getPassword(), userInfo.getPassword())) {
            log.error("用户密码错误, username={}", reqDTO.getUsername());
            throw new BusinessException(ExceptionEnum.USER_LOGIN_FAIL);
        }

        String token = UUID.randomUUID().toString().replace("-", "");
        UserLoginRespDTO respDTO = new UserLoginRespDTO();
        respDTO.setToken(token);

        // 将用户信息保存到redis中
        String key = String.format(RedisKeyEnum.LOGIN_USER_INFO.getKey(), token);
        redisStringClient.setex(key, userInfo, RedisKeyEnum.LOGIN_USER_INFO.getExpireTime());
        return respDTO;
    }
}
