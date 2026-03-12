package com.keystone.ai.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 聊天响应 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {

    /** AI 回复内容 */
    private String content;

    /** 使用的模型 */
    private String model;

    /** Token 消耗信息 */
    private Usage usage;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Usage {
        private Long promptTokens;
        private Long completionTokens;
        private Long totalTokens;
    }
}
