package com.keystone.framework.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT 配置属性
 */
@Component
@ConfigurationProperties(prefix = "keystone.token")
public class TokenProperties {

    /** 令牌密钥 */
    private String secret = "keystoneDefaultSecretKeyForJwtTokenSigning2026";

    /** 令牌有效期（分钟），默认 720 分钟 = 12 小时 */
    private int expireMinutes = 720;

    /** 令牌请求头标识 */
    private String header = "Authorization";

    /** 令牌前缀 */
    private String prefix = "Bearer ";

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public int getExpireMinutes() {
        return expireMinutes;
    }

    public void setExpireMinutes(int expireMinutes) {
        this.expireMinutes = expireMinutes;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
