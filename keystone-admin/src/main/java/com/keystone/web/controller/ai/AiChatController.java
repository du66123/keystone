package com.keystone.web.controller.ai;

import com.keystone.ai.domain.ChatRequest;
import com.keystone.ai.domain.ChatResponse;
import com.keystone.ai.service.AiChatService;
import com.keystone.ai.service.PromptTemplateService;
import com.keystone.common.core.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Map;

/**
 * AI 聊天控制器
 */
@Tag(name = "AI 对话")
@RestController
@RequestMapping("/api/ai")
public class AiChatController {

    private final AiChatService chatService;
    private final PromptTemplateService promptTemplateService;

    public AiChatController(AiChatService chatService,
                            PromptTemplateService promptTemplateService) {
        this.chatService = chatService;
        this.promptTemplateService = promptTemplateService;
    }

    @Operation(summary = "同步聊天")
    @PostMapping("/chat")
    public Result<ChatResponse> chat(@RequestBody ChatRequest request) {
        ChatResponse response = chatService.chat(request);
        return Result.ok(response);
    }

    @Operation(summary = "流式聊天 (SSE)")
    @PostMapping(value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatStream(@RequestBody ChatRequest request) {
        return chatService.chatStream(request);
    }

    @Operation(summary = "使用 Prompt 模板聊天")
    @PostMapping("/chat/template/{templateKey}")
    public Result<ChatResponse> chatWithTemplate(
            @PathVariable String templateKey,
            @RequestBody Map<String, String> variables) {
        String renderedPrompt = promptTemplateService.render(templateKey, variables);
        ChatRequest request = new ChatRequest();
        request.setMessage(renderedPrompt);
        return Result.ok(chatService.chat(request));
    }

    @Operation(summary = "获取所有 Prompt 模板")
    @GetMapping("/templates")
    public Result<Map<String, String>> listTemplates() {
        return Result.ok(promptTemplateService.listTemplates());
    }

    @Operation(summary = "注册自定义 Prompt 模板")
    @PostMapping("/templates")
    public Result<Void> registerTemplate(@RequestParam String key, @RequestBody String template) {
        promptTemplateService.register(key, template);
        return Result.ok();
    }
}
