package cn.bravedawn.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/6/4 19:41
 */

@Component
@ConfigurationProperties(prefix = "miaosha")
@Data
public class AppProperties {

    /**
     * 鉴权白名单
     */
    private String whiteList;
}
