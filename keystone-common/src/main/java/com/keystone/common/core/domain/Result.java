package com.keystone.common.core.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 统一 API 响应封装
 *
 * @param <T> 数据类型
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 状态码 */
    private int code;

    /** 消息 */
    private String msg;

    /** 数据 */
    private T data;

    /** 时间戳 */
    private long timestamp;

    public Result() {
        this.timestamp = System.currentTimeMillis();
    }

    private Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    // ==================== 成功 ====================

    public static <T> Result<T> ok() {
        return new Result<>(HttpCode.SUCCESS, "操作成功", null);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(HttpCode.SUCCESS, "操作成功", data);
    }

    public static <T> Result<T> ok(String msg, T data) {
        return new Result<>(HttpCode.SUCCESS, msg, data);
    }

    // ==================== 失败 ====================

    public static <T> Result<T> fail() {
        return new Result<>(HttpCode.ERROR, "操作失败", null);
    }

    public static <T> Result<T> fail(String msg) {
        return new Result<>(HttpCode.ERROR, msg, null);
    }

    public static <T> Result<T> fail(int code, String msg) {
        return new Result<>(code, msg, null);
    }

    // ==================== 辅助判断 ====================

    public boolean isSuccess() {
        return this.code == HttpCode.SUCCESS;
    }

    /**
     * HTTP / 业务状态码常量
     */
    public static final class HttpCode {
        /** 成功 */
        public static final int SUCCESS = 200;
        /** 未授权 */
        public static final int UNAUTHORIZED = 401;
        /** 禁止访问 */
        public static final int FORBIDDEN = 403;
        /** 资源未找到 */
        public static final int NOT_FOUND = 404;
        /** 服务器内部错误 */
        public static final int ERROR = 500;

        private HttpCode() {
        }
    }
}
