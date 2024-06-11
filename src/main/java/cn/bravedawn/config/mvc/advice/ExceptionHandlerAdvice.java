package cn.bravedawn.config.mvc.advice;

import cn.bravedawn.model.Vo;
import cn.bravedawn.exception.BusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/4/28 16:48
 */


@ControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {

    private ObjectMapper objectMapper = new ObjectMapper();
    private static final String DEFAULT_ERROR_CODE = "9999";

    @ExceptionHandler(Throwable.class)
    public void handle(Throwable e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.error("系统出现异常", e);

        if (!response.isCommitted()) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try(OutputStream out = response.getOutputStream()) {
                Vo vo = Vo.builder().build();
                if (e instanceof BusinessException) {
                    BusinessException businessException = (BusinessException) e;
                    vo.setCode(String.valueOf(businessException.getErrCode()));
                    vo.setMsg(businessException.getErrMsg());
                } else {
                    vo.setCode(DEFAULT_ERROR_CODE);
                    vo.setMsg("系统出现未知异常");
                }
                out.write(objectMapper.writeValueAsBytes(vo));
                wrapResponseHeader(response);
            } catch (IOException exception) {
                log.error("异常处理错误", exception);
            }
        }



    }

    private void wrapResponseHeader(HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
    }
}
