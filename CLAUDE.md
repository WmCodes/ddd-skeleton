# CLAUDE.md — demo-skeleton

DDD 项目骨架，基于 Spring Boot 3.2.0 + Java 21 + Gradle 8.5。

## 环境要求

```bash
# Java 21 (zulu-21.jdk) — 系统默认是 Java 8，必须显式设置
export JAVA_HOME=/Library/Java/JavaVirtualMachines/zulu-21.jdk/Contents/Home

# Gradle 8.5 (手动安装路径)
export GRADLE_HOME=/Users/user/gradle-8.5
export PATH=$GRADLE_HOME/bin:$PATH

# 建议别名
alias gradle='JAVA_HOME=/Library/Java/JavaVirtualMachines/zulu-21.jdk/Contents/Home /Users/user/gradle-8.5/bin/gradle --no-daemon'
```

## 项目结构

```
demo-skeleton/
├── frameworks/          # 框架基础设施（纯技术基元，无业务逻辑）
│   ├── domain.core/     #   领域基元 — CLAUDE.md
│   ├── application.core/ #  应用基元 — CLAUDE.md
│   └── context.core/    #   请求上下文 — CLAUDE.md
├── modules/             # 业务领域模块（纯领域逻辑）
│   └── order/           #   订单模块 — CLAUDE.md
├── gateways/            # 基础设施实现（JPA/外部系统适配）
│   └── order.jpa/       #   Order JPA + ACL — CLAUDE.md
├── application/         # 可部署应用入口 + REST API
│   └── order.api/       #   Spring Boot 应用 — CLAUDE.md
├── gradle/config/       # Gradle 配置
│   ├── checkstyle/      #   Checkstyle 规则
│   └── scripts/         #   构建脚本
├── scripts/             # 开发辅助脚本
├── build.gradle         # 根构建文件
├── settings.gradle      # 模块注册（自动发现 frameworks/, modules/, gateways/, application/）
└── gradle.properties    # 依赖版本管理
```

## 模块导航

每个子模块都有独立的 CLAUDE.md，包含模块职责、类说明、命令和依赖关系：

| 层级 | 模块 | Gradle 路径 | 职责 | 快速链接 |
|------|------|------------|------|---------|
| **frameworks** | domain.core | `:domain.core` | 领域基元：DomainException、IdentityService、GlobalClock | [CLAUDE.md](frameworks/domain.core/CLAUDE.md) |
| | application.core | `:application.core` | 应用基元：@UseCase、ExceptionTranslator | [CLAUDE.md](frameworks/application.core/CLAUDE.md) |
| | context.core | `:context.core` | 请求上下文：ThreadLocal Operator | [CLAUDE.md](frameworks/context.core/CLAUDE.md) |
| **modules** | order | `:order` | 订单领域实体 + 领域服务 + 应用用例 | [CLAUDE.md](modules/order/CLAUDE.md) |
| **gateways** | order.jpa | `:order.jpa` | Order JPA 持久化 + 防腐层适配器 | [CLAUDE.md](gateways/order.jpa/CLAUDE.md) |
| **application** | order.api | `:order.api` | Spring Boot 入口 + REST API | [CLAUDE.md](application/order.api/CLAUDE.md) |

### 四层架构说明

```
application/  ─── REST 控制器、Spring Boot 启动类
     ↑
gateways/     ─── JPA Entity、Repository 实现、ACL 适配器
     ↑
modules/      ─── 领域实体、值对象、仓储接口、领域服务、应用用例、DTO
     ↑
frameworks/   ─── 跨领域共享基元：异常基类、ID 生成、@UseCase、上下文
```

### 依赖流向

```
:order.api
  ├── :order.jpa ──→ :domain.core
  │     └── :order ──→ :domain.core
  │           └── :application.core ──→ :domain.core
  └── :domain.core
```

## 常用命令

所有命令需使用 Java 21 + `--no-daemon`（避免守护进程缓存 Java 8 的 JVM）：

| 命令 | 说明 |
|------|------|
| `gradle build` | 完整构建（编译 + checkstyle + 测试 + JaCoCo 覆盖率检查） |
| `gradle check` | 检查生命周期（checkstyle + 测试 + JaCoCo） |
| `gradle test` | 运行所有测试 |
| `gradle :domain.core:test` | 运行指定模块测试 |
| `gradle :domain.core:checkstyleMain` | 运行指定模块 checkstyle（仅 main） |
| `gradle :domain.core:check` | 运行指定模块完整检查 |
| `gradle clean` | 清理构建产物 |

### 组合命令示例

```bash
# 所有模块 checkstyle（main + test）
gradle :domain.core:checkstyleMain :domain.core:checkstyleTest :application.core:checkstyleMain :application.core:checkstyleTest :context.core:checkstyleMain :context.core:checkstyleTest :order:checkstyleMain :order:checkstyleTest :order.jpa:checkstyleMain :order.jpa:checkstyleTest :order.api:checkstyleMain :order.api:checkstyleTest

# 所有模块测试
gradle :domain.core:test :application.core:test :context.core:test :order:test :order.jpa:test :order.api:test

# 所有模块完整检查
gradle :domain.core:check :application.core:check :context.core:check :order:check :order.jpa:check :order.api:check
```

## 技术栈

- **Java 21** (sourceCompatibility = 21)
- **Spring Boot 3.2.0** (Spring 6.1.1)
- **Gradle 8.5**
- **JUnit 5.10.1** + AssertJ 3.25.0 + Mockito 5.8.0
- **JaCoCo 0.8.11** (100% LINE 覆盖率要求)
- **Checkstyle 8.36.1** (Google 风格，仅检查 main 源码，test 默认跳过)
- **Lombok 1.18.30**
- **Flyway 9.22.3** (数据库迁移)
- **Testcontainers 1.19.3** (集成测试)
- **PostgreSQL 42.7.2**

## 测试要求

- 100% LINE 覆盖率（JaCoCo 强制执行）
- JUnit 5 + AssertJ 流式断言
- 测试方法名使用 camelCase（不允许下划线）

## 模块命名

settings.gradle 自动注册 `frameworks/`、`modules/`、`gateways/`、`application/` 下的目录。模块名扁平化：

| 物理路径 | Gradle 路径 | 说明 |
|---------|------------|------|
| `frameworks/domain.core` | `:domain.core` | 共享领域基础 |
| `frameworks/application.core` | `:application.core` | 共享应用基础 |
| `frameworks/context.core` | `:context.core` | 请求上下文 |
| `modules/order` | `:order` | 订单领域模块 |
| `gateways/order.jpa` | `:order.jpa` | Order JPA 持久化 |
| `application/order.api` | `:order.api` | 订单 API 应用 |
| `modules/ca.domain` | `:ca.domain` | (约定示例) |