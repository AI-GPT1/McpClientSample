package com.ai.mcpclient;

import io.modelcontextprotocol.client.McpSyncClient;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class McpClientApplication {

    private static final String SystemPrompt="你是一个可以查询天气的助手，可以调用工具回答用户关于天气相关问题。";

    public static void main(String[] args) {
        SpringApplication.run(McpClientApplication.class, args);
    }

    /**
     *
     * CommandLineRunner：Spring Boot 提供的接口，实现了 CommandLineRunner 接口的 Bean 会在 Spring Boot 应用启动完成后自动执行其 run 方法。
     * chatClientBuilder：ChatClient.Builder 用于构建聊天客户端的构建器
     * mcpSyncClients：SpringAI 根据 spring-ai-starter-mcp-client 依赖注入的 McpSyncClient 列表，用于调用外部工具或服务。
     *
     */
    @Bean
    public CommandLineRunner chatbot(ChatClient.Builder chatClientBuilder, List<McpSyncClient> mcpSyncClients) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                // 构建聊天客户端
                var chatClient = chatClientBuilder
                        //设置系统提示，引导 AI 的行为和角色
                        .defaultSystem(SystemPrompt)
                        //配置工具回调提供者，使 AI 能调用外部工具
                        .defaultTools(new SyncMcpToolCallbackProvider(mcpSyncClients))
                        //设置对话记忆，使用内存存储对话历史，保持上下文
                        .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                        .build();

                // 命令行开始聊天循环
                System.out.println("\n我是你的AI助手。");
                try (Scanner scanner = new Scanner(System.in)) {
                    while (true) {
                        System.out.print("\n用户: ");
                        System.out.println("\n助手: " +
                                chatClient.prompt(scanner.nextLine()) // chatClient.prompt(...)：将用户输入作为提示发送给 LLM
                                        .call()
                                        .content());
                        //.call().content()：调用 LLM 模型并获取响应内容
                    }
                }
            }
        };

    }

}
