package cn.bravedawn.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/5/16 16:43
 */

@Component
@ConfigurationProperties(prefix = "ms.snowflake")
@Data
public class SnowflakeSequenceConfig {

    private long centerId;
    private long workId;
}
