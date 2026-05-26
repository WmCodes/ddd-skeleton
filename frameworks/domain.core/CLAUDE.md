# CLAUDE.md — :domain.core

共享领域基础设施基元。纯 Java 模块，无 Spring 依赖。

## 模块信息

| 属性 | 值 |
|------|-----|
| Gradle 路径 | `:domain.core` |
| 物理路径 | `frameworks/domain.core/` |
| 包前缀 | `demo.frameworks.domain.core` |
| 依赖 | 仅 `com.fasterxml.uuid:java-uuid-generator:3.2.0` |

## 提供的能力

| 类/接口 | 说明 |
|---------|------|
| `DomainException` | 所有领域异常的基类 (extends RuntimeException) |
| `AggregateNotFoundException` | 聚合根未找到时抛出 |
| `Exceptions` | 工具类，将受检异常包装为运行时异常 |
| `IdentityService` | ID 生成接口 — `nextIdentity()` / `nextReadableIdentity()` |
| `UuidBasedIdentityService` | 基于 UUID 的 IdentityService 实现 |
| `GlobalClock` | 可替换的时钟封装（基于 `java.time.Clock`），便于测试 |

## 不变性说明

- `DomainException` 及其子类使用 `final` 字段
- `UuidBasedIdentityService` 是无状态的
- `GlobalClock` 是不可变封装

## 常用命令

```bash
gradle :domain.core:test
gradle :domain.core:check
gradle :domain.core:checkstyleMain
```

## 依赖关系

```
:domain.core    (被以下模块依赖)
  ← :application.core  (api)
  ← :order              (api)
  ← :order.jpa          (api)
  ← :order.api          (api)
```