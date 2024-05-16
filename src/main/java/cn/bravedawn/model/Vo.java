package cn.bravedawn.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/4/28 16:42
 */

@Data
@Builder
public class Vo {

    private static final String SUCCESS_CODE = "0000";
    private static final String SUCCESS_MSG = "请求成功";
    private static final String FAIL_CODE = "9999";
    private static final String FAIL_MSG = "请求失败";
    private String code;
    private String msg;
    private Object data;


    public static Vo success(Object data) {
        return Vo.builder().code(SUCCESS_CODE).msg(SUCCESS_MSG).data(data).build();
    }

    public static Vo success() {
        return Vo.builder().code(SUCCESS_CODE).msg(SUCCESS_MSG).build();
    }

    public static Vo fail() {
        return Vo.builder().code(FAIL_CODE).msg(FAIL_MSG).build();
    }




}
