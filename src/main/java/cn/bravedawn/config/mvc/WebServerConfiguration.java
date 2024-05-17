package cn.bravedawn.config.mvc;

import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/5/17 15:43
 */
@Configuration
public class WebServerConfiguration {

    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
        TomcatServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
        tomcatServletWebServerFactory.addConnectorCustomizers((connector) -> {
            Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
            // 定制化KeepAliveTimeout,设置30秒内没有请求则服务端自动断开keepalive链接
            protocol.setKeepAliveTimeout(30000);
            // 当客户端发送超过10000个请求则自动断开keepalive链接
            protocol.setMaxKeepAliveRequests(10000);
        });
        return tomcatServletWebServerFactory;
    }
}

