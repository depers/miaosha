package cn.bravedawn.config.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/5/15 16:34
 */

@Intercepts({
        // 更新，可更新，可插入，可删除，具体有MappedStatement的SQL语句决定
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        // 查询
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
})
@Slf4j
public class SqlCostInterceptor implements Interceptor {


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameterObject = null;
        if (invocation.getArgs() != null && invocation.getArgs().length > 1) {
            parameterObject = invocation.getArgs()[1];
        }
        String sqlId = mappedStatement.getId();
        BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
        Configuration configuration = mappedStatement.getConfiguration();

        long startTime = System.currentTimeMillis();
        Object result = invocation.proceed();
        long endTime = System.currentTimeMillis();
        long costTime = endTime - startTime;
        if (costTime > 1) {
            logSql(configuration, boundSql, sqlId, costTime);
        }
        return result;
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }


    private void logSql(Configuration configuration, BoundSql boundSql, String sqlId, long costTime) {
        String sql = showSql(configuration, boundSql);
        log.info("sql位置:{}", sqlId);
        log.info("sql语句:{}", sql);
        log.info("sql耗时:{}", costTime);
    }

    private String showSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        // 获取参数信息
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql();
        // 去除换行和空格
        sql = sql.replaceAll("[\\s]+", " ");

        // 是否有参数
        if (!parameterMappings.isEmpty() && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                String parameterValue = getParameterValue(parameterObject);
                sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(parameterValue));
            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    // 判断是否有get方法，然后找到\?将其进行替换
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(obj.toString()));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(obj.toString()));
                    }
                }
            }
        }

        return sql;
    }


    /**
     * 将String和Date类型进行加单引号的操作
     * @param obj
     * @return
     */
    private String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            value = "'" + obj + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }
        }
        return value;
    }
}
