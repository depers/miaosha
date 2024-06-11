package cn.bravedawn.config.mvc.intercepter;

import cn.bravedawn.common.RedisKeyEnum;
import cn.bravedawn.config.AppProperties;
import cn.bravedawn.entity.UserInfo;
import cn.bravedawn.model.Vo;
import cn.bravedawn.util.RedisStringClient;
import cn.bravedawn.util.SpringContextUtil;
import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/5/29 13:59
 *
 * 鉴权拦截器
 */
@Slf4j
public class AuthenticationHandlerInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("对请求进行鉴权，url={}", request.getRequestURI());
        String token = request.getHeader("Authentication");
        AppProperties appProperties = SpringContextUtil.getBean(AppProperties.class);

        // 进行白名单校验
        List<String> whiteList = new ArrayList(Arrays.asList(appProperties.getWhiteList().split(",")));
        for (String whiteUrl : whiteList) {
            RequestMatcher requestMatcher = new AntPathRequestMatcher(whiteUrl);
            if (requestMatcher.matches(request)) {
                log.info("白名单无需鉴权，url={}，whiteUrl={}", request.getRequestURI(), whiteUrl);
                return true;
            }
        }

        // 进行token鉴权
        RedisStringClient<UserInfo> redisStringClient = SpringContextUtil.getBean(RedisStringClient.class);
        String key = String.format(RedisKeyEnum.LOGIN_USER_INFO.getKey(), token);
        UserInfo userInfo = redisStringClient.get(key);
        if (userInfo == null) {
            log.error("Redis中用户信息不存在，鉴权失败");
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            PrintWriter writer = response.getWriter();
            Vo vo = Vo.unauthorized();
            writer.println(JSONUtil.toJsonStr(vo));
            writer.flush();
            return false;
        } else {
            log.info("鉴权通过，url={}", request.getRequestURI());
        }

        return true;
    }
}
