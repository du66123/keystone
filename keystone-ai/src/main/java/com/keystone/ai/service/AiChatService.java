package com.keystone.ai.service;

import com.keystone.ai.config.AiProperties;
import com.keystone.ai.domain.ChatRequest;
import com.keystone.ai.domain.ChatResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Map;

/**
 * AI 聊天 Service
 * 统一封装 Spring AI ChatClient，支持同步/流式调用
 */
@Service
public class AiChatService {

    private static final Logger log = LoggerFactory.getLogger(AiChatService.class);

    private final ChatClient chatClient;
    private final AiProperties aiProperties;

    public AiChatService(@Qualifier("openAiChatModel") ChatModel openAiChatModel, AiProperties aiProperties) {
        this.chatClient = ChatClient.builder(openAiChatModel).build();
        this.aiProperties = aiProperties;
    }

    /**
     * 同步聊天
     */
    public ChatResponse chat(ChatRequest request) {
        String systemPrompt = request.getSystemPrompt() != null
                ? request.getSystemPrompt()
                : aiProperties.getSystemPrompt();

        String result = chatClient.prompt()
                .system(systemPrompt)
                .user(request.getMessage())
                .options(buildOptions(request))
                .call()
                .content();

        ChatResponse response = new ChatResponse();
        response.setContent(result);
        response.setModel(getModel(request));
        return response;
    }

    /**
     * 流式聊天 (SSE)
     */
    public Flux<String> chatStream(ChatRequest request) {
        String systemPrompt = request.getSystemPrompt() != null
                ? request.getSystemPrompt()
                : aiProperties.getSystemPrompt();

        return chatClient.prompt()
                .system(systemPrompt)
                .user(request.getMessage())
                .options(buildOptions(request))
                .stream()
                .content();
    }

    /**
     * 构建模型选项
     */
    private OpenAiChatOptions buildOptions(ChatRequest request) {
        return OpenAiChatOptions.builder()
                .model(getModel(request))
                .temperature(request.getTemperature() != null
                        ? request.getTemperature()
                        : aiProperties.getTemperature())
                .maxTokens(request.getMaxTokens() != null
                        ? request.getMaxTokens()
                        : aiProperties.getMaxTokens())
                .build();
    }

    private String getModel(ChatRequest request) {
        return request.getModel() != null ? request.getModel() : aiProperties.getDefaultModel();
    }
}
