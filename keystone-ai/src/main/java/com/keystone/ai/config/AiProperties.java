package com.keystone.ai.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * AI 模块配置属性
 */
@Component
@ConfigurationProperties(prefix = "keystone.ai")
public class AiProperties {

    /** 默认使用的模型提供商: openai / ollama */
    private String provider = "openai";

    /** 默认模型名称 */
    private String defaultModel = "gpt-4o";

    /** 默认温度 */
    private double temperature = 0.7;

    /** 最大 Token 数 */
    private int maxTokens = 4096;

    /** 系统提示词 */
    private String systemPrompt = "你是 Keystone 智能助手，回答问题简洁准确。";

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getDefaultModel() {
        return defaultModel;
    }

    public void setDefaultModel(String defaultModel) {
        this.defaultModel = defaultModel;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getMaxTokens() {
        return maxTokens;
    }

    public void setMaxTokens(int maxTokens) {
        this.maxTokens = maxTokens;
    }

    public String getSystemPrompt() {
        return systemPrompt;
    }

    public void setSystemPrompt(String systemPrompt) {
        this.systemPrompt = systemPrompt;
    }
}
