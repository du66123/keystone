package com.keystone.ai.domain;

import lombok.Data;

import java.util.List;

/**
 * 聊天请求 DTO
 */
@Data
public class ChatRequest {

    /** 用户消息 */
    private String message;

    /** 模型名称 (可选, 覆盖默认) */
    private String model;

    /** 温度 (可选) */
    private Double temperature;

    /** 最大 Token 数 (可选) */
    private Integer maxTokens;

    /** 系统提示词 (可选, 覆盖默认) */
    private String systemPrompt;

    /** 历史消息 (可选, 多轮对话) */
    private List<MessageItem> history;

    @Data
    public static class MessageItem {
        /** 角色: user / assistant / system */
        private String role;
        /** 内容 */
        private String content;
    }
}
