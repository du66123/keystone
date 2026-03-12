package com.keystone.common.constant;

/**
 * 通用常量
 */
public final class Constants {

    private Constants() {
    }

    /** UTF-8 编码 */
    public static final String UTF8 = "UTF-8";

    /** GBK 编码 */
    public static final String GBK = "GBK";

    /** http 请求前缀 */
    public static final String HTTP = "http://";

    /** https 请求前缀 */
    public static final String HTTPS = "https://";

    /** 成功标记 */
    public static final int SUCCESS = 200;

    /** 失败标记 */
    public static final int FAIL = 500;

    /** 登录成功 */
    public static final String LOGIN_SUCCESS = "Success";

    /** 登录失败 */
    public static final String LOGIN_FAIL = "Error";

    /** 验证码有效期（分钟）*/
    public static final int CAPTCHA_EXPIRATION = 5;

    /** 令牌前缀 */
    public static final String TOKEN_PREFIX = "Bearer ";

    /** 令牌头标识 */
    public static final String TOKEN_HEADER = "Authorization";

    /** 资源映射路径前缀 */
    public static final String RESOURCE_PREFIX = "/profile";
}
