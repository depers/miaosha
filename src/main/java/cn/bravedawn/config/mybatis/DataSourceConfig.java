package cn.bravedawn.config.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/5/15 17:06
 */

@Configuration
@MapperScan(basePackages = "cn.bravedawn.dao") // 配置Mybatis扫描包的路径
public class DataSourceConfig {

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:mappers/*.xml");
        // 设置mapper xml位置
        sqlSessionFactoryBean.setMapperLocations(resources);
        // 会话配置
        sqlSessionFactoryBean.setConfiguration(createConfiguration());

        return sqlSessionFactoryBean.getObject();
    }


    /**
     * 使用MyBatis Configuration代替mybatis.xml
     * @return
     */
    private org.apache.ibatis.session.Configuration createConfiguration() {
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        // 配置返回参数是否支持下划线到驼峰的转换
        configuration.setMapUnderscoreToCamelCase(false);
        // 配置拦截器
        configuration.addInterceptor(new SqlCostInterceptor());
        // 开启二级缓存
        configuration.setCacheEnabled(true);
        return configuration;
    }
}
