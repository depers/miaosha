package cn.bravedawn.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/4/28 16:35
 */
public class RequestUtils {

    /**
     * 获取httpStatus
     * @return
     */
    public static HttpStatus getHttpStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.OK;
        } else {
            return HttpStatus.valueOf(statusCode);
        }
    }
}
