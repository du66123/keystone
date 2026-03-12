package com.keystone.framework.config;

import com.mybatisflex.core.audit.AuditManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis-Flex 配置
 */
@Configuration
public class MyBatisFlexConfig {

    private static final Logger log = LoggerFactory.getLogger(MyBatisFlexConfig.class);

    public MyBatisFlexConfig() {
        // 开启 SQL 审计（开发环境，可打印执行的 SQL）
        AuditManager.setAuditEnable(true);
        AuditManager.setMessageCollector(auditMessage ->
                log.debug("SQL 审计 [{}ms]: {}", auditMessage.getElapsedTime(), auditMessage.getFullSql())
        );
    }
}
