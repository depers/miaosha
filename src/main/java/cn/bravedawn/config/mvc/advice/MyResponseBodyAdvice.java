package cn.bravedawn.config.mvc.advice;


import cn.bravedawn.model.Vo;
import cn.bravedawn.util.RequestUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/4/28 15:58
 */

@ControllerAdvice
@Slf4j
public class MyResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private static final String SUCCESS_CODE = "0000";
    private static final String SUCCESS_MSG = "success";
    private ObjectMapper mapper = new ObjectMapper();


    @Override
    // 用于确定当前的advice 是否适用于当前的请求
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
        HttpStatus status = RequestUtils.getHttpStatus(servletRequest.getServletRequest());

        if (status.isError()) {
            return body;
        }

        if (AbstractJackson2HttpMessageConverter.class.isAssignableFrom(selectedConverterType)) {
            return Vo.builder().code(SUCCESS_CODE).msg(SUCCESS_MSG).data(body).build();
        }

        if (StringHttpMessageConverter.class.isAssignableFrom(selectedConverterType)) {
            Vo vo = Vo.builder().code(SUCCESS_CODE).msg(SUCCESS_MSG).build();

            try {
                vo.setData(mapper.writeValueAsString(body));
            } catch (JsonProcessingException e) {
                log.error("消息格式装换失败", e);
            }
            return vo;
        }

        return body;
    }
}
