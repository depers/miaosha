package cn.bravedawn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/4/19 17:17
 */

/**
 * 启动类
 */
@SpringBootApplication
public class MiaoShaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiaoShaApplication.class, args);
    }
}
