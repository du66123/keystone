package com.keystone.ai.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Prompt 模板管理 Service
 * 管理和复用预定义的提示词模板
 */
@Service
public class PromptTemplateService {

    private static final Logger log = LoggerFactory.getLogger(PromptTemplateService.class);

    /** 内存级模板存储 (后续可迁移到数据库或 Redis) */
    private final Map<String, String> templates = new ConcurrentHashMap<>();

    public PromptTemplateService() {
        // 注册内置模板
        registerBuiltinTemplates();
    }

    /**
     * 注册内置模板
     */
    private void registerBuiltinTemplates() {
        templates.put("translate", """
                你是一个专业的翻译助手。请将以下内容翻译成{targetLanguage}:
                
                {content}
                """);

        templates.put("summarize", """
                请对以下内容进行摘要总结，保留关键信息，控制在200字以内:
                
                {content}
                """);

        templates.put("code_review", """
                你是一个资深的代码审查专家。请对以下代码进行审查，指出潜在的问题和改进建议:
                
                ```{language}
                {code}
                ```
                """);

        templates.put("sql_generate", """
                你是一个数据库专家。根据以下需求描述，生成对应的 MySQL SQL 语句:
                
                需求: {requirement}
                相关表结构: {schema}
                """);

        templates.put("text_polish", """
                你是一个文字编辑专家。请优化以下文本的表达，使其更加专业流畅:
                
                {content}
                """);
    }

    /**
     * 获取模板
     */
    public String getTemplate(String key) {
        return templates.get(key);
    }

    /**
     * 渲染模板 (简单的占位符替换)
     */
    public String render(String key, Map<String, String> variables) {
        String template = templates.get(key);
        if (template == null) {
            throw new IllegalArgumentException("模板不存在: " + key);
        }
        String result = template;
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            result = result.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return result;
    }

    /**
     * 注册自定义模板
     */
    public void register(String key, String template) {
        templates.put(key, template);
        log.info("注册 Prompt 模板: {}", key);
    }

    /**
     * 列出所有模板名称
     */
    public Map<String, String> listTemplates() {
        return Map.copyOf(templates);
    }
}
