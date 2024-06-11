package cn.bravedawn.model.dto.login;

import lombok.Data;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/5/29 12:10
 */

@Data
public class UserLoginReqDTO {

    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
}
