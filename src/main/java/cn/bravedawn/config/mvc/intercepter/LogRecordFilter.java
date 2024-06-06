package cn.bravedawn.config.mvc.intercepter;

import cn.bravedawn.util.IpUtil;
import cn.bravedawn.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/5/29 13:58
 *
 * 日志记录
 */
@Slf4j
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Component
@WebFilter(filterName = "ContentCachingFilter", urlPatterns = "/**")
public class LogRecordFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        long startTime = System.currentTimeMillis();
        ContentCachingRequestWrapper req = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper resp = new ContentCachingResponseWrapper(response);
        String requestBodyStr = "";
        if (req.getMethod().equals(HttpMethod.GET.name())) {
            ObjectMapper mapper = new ObjectMapper();
            requestBodyStr = mapper.writeValueAsString(req.getParameterMap());
        } else {
            byte[] requestBody = StreamUtils.copyToByteArray(req.getInputStream());
            requestBodyStr = StrUtil.plainText(new String(requestBody, StandardCharsets.UTF_8));
        }
        log.info("访问IP:{}, 开始计时:{}, URI:{}, 请求方式:{}, 请求参数:{}.",
                IpUtil.getIpAddr(request),
                new SimpleDateFormat("hh:mm:ss.SSS").format(new Date()),
                request.getRequestURI(),
                req.getMethod(),
                requestBodyStr);

        filterChain.doFilter(req, resp);

        // 请求后的日志打印
        byte[] responseBody = resp.getContentAsByteArray();
        log.info("响应参数:{}.", new String(responseBody, StandardCharsets.UTF_8));
        long costTime = System.currentTimeMillis() - startTime;
        log.info("请求:{}, cost end time is [{}]ms.", req.getRequestURI(), costTime);
        resp.copyBodyToResponse();
        MDC.clear();

    }
}
