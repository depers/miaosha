package cn.bravedawn.config.mvc.intercepter;

import cn.bravedawn.util.IpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/5/29 13:58
 */
@Slf4j
public class LogRecordHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long start = System.currentTimeMillis();
        request.setAttribute("startTime", start);

        BodyReaderHttpServletRequestWrapper req = new BodyReaderHttpServletRequestWrapper(request);
        String requestBodyStr = "";
        if (req.getMethod().equals(HttpMethod.GET.name())) {
            ObjectMapper mapper = new ObjectMapper();
            requestBodyStr = mapper.writeValueAsString(req.getParameterMap());
        } else {
            byte[] requestBody = StreamUtils.copyToByteArray(req.getInputStream());
            requestBodyStr = new String(requestBody, StandardCharsets.UTF_8).replaceAll("\r?\n", "");
        }
        log.info("访问IP:{}, 开始计时:{}, URI:{}, 请求方式:{}, 请求参数:{}.",
                IpUtil.getIpAddr(request),
                new SimpleDateFormat("hh:mm:ss.SSS").format(new Date()),
                request.getRequestURI(),
                req.getMethod(),
                requestBodyStr);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            log.info("ViewName: " + modelAndView.getViewName());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
