# McpClientSample

## 项目介绍

McpClientSample是一个基于Spring Boot开发的示例项目，
集成了Spring AI框架和通义千问大模型，用于演示如何使用MCP(Model Context Protocol)客户端与AI大模型进行交互。
该项目提供了与通义千问大模型通信的基础框架，可以作为开发AI应用的起点。

该项目为mcp服务的客户端，所以需要依赖服务端启动暴露服务，服务端项目为[McpServerSample](https://github.com/AI-GPT1/McpServerSample)。

## 技术栈

- Java
- Spring Boot 3.4.4
- Spring AI 1.0.0-M7
- 通义千问SDK (spring-ai-alibaba-starter 1.0.0-M6.1)

## 依赖服务

依赖MCP服务端项目[McpServerSample](https://github.com/AI-GPT1/McpServerSample)

注意：需要先运行McpServerSample服务再运行McpClientSample服务，端口默认都用
application.properties中spring.ai.mcp.client.sse.connections.server1配置的地址需为McpServerSample部署的IP和端口号。

## 核心依赖包

项目主要集成了以下AI相关依赖：

- `spring-ai-starter-mcp-client`: Spring AI多通道客户端启动器
- `spring-ai-alibaba-starter`: 阿里巴巴通义千问集成启动器

## 项目结构

```
├── .idea/             # IDEA项目配置文件夹
├── src/               # 源代码目录
│   └── main/          # 主代码目录
│       ├── java/      # Java源代码
│       └── resources/ # 资源文件
├── target/            # 编译输出目录
├── pom.xml            # Maven项目配置文件
```


## 配置说明

### 重要配置 - 千问大模型API Key

在使用本项目前，**必须**配置千问大模型的API Key。请在`application.properties`文件中添加以下配置：

```properties
# 通义千问大模型API Key配置
spring.ai.dashscope.api-key=您的API密钥
```

### 其他可选配置

您还可以根据需要配置以下属性：

```properties
# 模型参数配置示例
spring.ai.model.anthropic.temperature=0.7
spring.ai.model.anthropic.max-tokens=1000
```

## 快速开始

1. **配置API Key**
   在`src/main/resources/application.properties`中设置您的通义千问API Key

2. **构建项目**
   ```bash
   mvn clean package
   ```

3. **运行项目**
   ```bash
   java -jar target/McpClientSample-0.0.1-SNAPSHOT.jar
   ```

## 使用说明

本项目提供了与通义千问大模型交互的基础框架。您可以通过Spring AI提供的`ChatClient`接口与大模型进行对话。
以下是一个简单的使用示例：

```java
@Autowired
private ChatClient chatClient;

public String generateResponse(String prompt) {
    return chatClient.call(prompt);
}
```

## 注意事项

1. 确保您的API Key有效且有足够的调用额度
2. 请遵守通义千问大模型的使用条款和规范
3. 对于生产环境，请考虑使用环境变量或密钥管理服务来存储API Key，避免硬编码

