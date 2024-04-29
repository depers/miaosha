package cn.bravedawn.config.mvc.bean;

import lombok.Builder;
import lombok.Data;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/4/28 16:42
 */

@Data
@Builder
public class Vo<T> {

    private String code;
    private String msg;
    private T data;
}
