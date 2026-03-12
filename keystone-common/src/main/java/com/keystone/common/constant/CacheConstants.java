package com.keystone.common.constant;

/**
 * 缓存相关常量
 */
public final class CacheConstants {

    private CacheConstants() {
    }

    /** 登录 Token 缓存前缀 */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /** 用户信息缓存前缀 */
    public static final String USER_DETAILS_KEY = "user_details:";

    /** 字典数据缓存前缀 */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /** 系统配置缓存前缀 */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /** 防重复提交缓存前缀 */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /** 限流缓存前缀 */
    public static final String RATE_LIMIT_KEY = "rate_limit:";

    /** Token 默认过期时间（分钟）*/
    public static final int TOKEN_EXPIRE_MINUTES = 720;
}
