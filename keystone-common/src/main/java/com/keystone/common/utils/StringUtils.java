package com.keystone.common.utils;

/**
 * 字符串工具类
 */
public final class StringUtils {

    private StringUtils() {
    }

    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * 判断字符串是否非空
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 判断字符串是否为空白
     */
    public static boolean isBlank(String str) {
        return str == null || str.isBlank();
    }

    /**
     * 判断字符串是否非空白
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 去除前后空格
     */
    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    /**
     * 格式化字符串
     * 使用 {} 作为占位符
     */
    public static String format(String template, Object... params) {
        if (isEmpty(template) || params == null || params.length == 0) {
            return template;
        }
        StringBuilder sb = new StringBuilder(template.length() + 50);
        int paramIndex = 0;
        for (int i = 0; i < template.length(); i++) {
            if (template.charAt(i) == '{' && i + 1 < template.length() && template.charAt(i + 1) == '}') {
                if (paramIndex < params.length) {
                    sb.append(params[paramIndex++]);
                } else {
                    sb.append("{}");
                }
                i++;
            } else {
                sb.append(template.charAt(i));
            }
        }
        return sb.toString();
    }
}
