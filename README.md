# Demo Skeleton

基于 DDD 最佳实践的可复用项目骨架。

## 概述

本项目是一个基于 Spring Boot + DDD 的实践示例，展示了领域驱动设计在 Java 项目中的完整应用。

## 特性

- **Spring Boot 3.2+** — 现代 Java 微框架
- **Java 21** — 最新 LTS 版本
- **Gradle 8.x** — 构建系统
- **JUnit 5 + Testcontainers** — 测试基础设施
- **JaCoCo** — 100% 覆盖率要求
- **Checkstyle** — Google 风格代码规范
- **DDD 实践** — 领域驱动设计完整示例

## 快速开始

### 环境要求

- Java 21+
- Gradle 8.x
- Docker (开发环境)

### 安装

```bash
# 启动开发环境
./scripts/setup-dev

# 启动应用
./scripts/run-server

# 运行测试和检查
./scripts/test-all
```

### 构建 Docker 镜像

```bash
./scripts/build-all
```

## 项目结构

```
demo-skeleton/
├── frameworks/                    # 框架模块
│   ├── domain.core/              # 领域基础（异常、ID 生成、时钟）
│   ├── application.core/         # 应用基础（UseCase 注解）
│   └── context.core/             # 请求上下文
├── modules/                       # 业务领域
│   └── order/                    # 订单领域示例
│       ├── core/                 # 核心实体
│       ├── domain/               # 领域层
│       └── application/          # 应用层
├── gateways/                      # 网关实现
│   └── order.jpa/               # JPA 持久化
├── application/                   # 应用接口
│   └── order.api/               # REST API
├── gradle/config/               # Gradle 配置
│   ├── scripts/                 # Gradle 脚本
│   └── checkstyle/              # Checkstyle 规则
└── scripts/                     # 开发脚本
```

## DDD 实践

- **领域驱动设计** — 清晰的领域分层
- **防腐层 (ACL)** — 隔离外部系统概念
- **仓储模式** — 领域仓储接口与实现分离
- **测试驱动开发 (TDD)** — 测试先行

## 开发工作流

1. **规划** — 使用 planner agent 创建实现计划
2. **TDD** — 先写测试（RED），再实现（GREEN），最后重构（IMPROVE）
3. **代码审查** — 使用 code-reviewer agent
4. **提交** — 约定式提交格式
5. **构建** — checkstyle → 测试 → 覆盖率验证

## 构建命令

| 命令 | 说明 |
|------|------|
| `./gradlew check` | 运行 checkstyle + 测试 + 覆盖率验证 |
| `./gradlew test` | 仅运行测试 |
| `./gradlew bootRun` | 启动应用 |
| `./gradlew buildDockerImage` | 构建 Docker 镜像 |
| `./gradlew pushDockerImage` | 推送 Docker 镜像 |
