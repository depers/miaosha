package cn.bravedawn.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/5/29 11:04
 */

@Data
public class UserRegisterReqDTO {

    /**
     * 手机号
     */
    @NotBlank
    private String telPhone;
    /**
     * 验证码
     */
    @NotBlank
    private String otpCode;
    /**
     * 姓名
     */
    @NotBlank
    private String name;
    /**
     * 性别，1-男，0-女
     */
    @NotNull
    private Integer gender;
    /**
     * 年龄
     */
    @NotNull
    private Integer age;
    /**
     * 密码
     */
    @NotNull
    private String password;
}
