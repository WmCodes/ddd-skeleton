# CLAUDE.md — :application.core

共享应用层基础设施。提供用例标记注解和异常翻译接口。

## 模块信息

| 属性 | 值 |
|------|-----|
| Gradle 路径 | `:application.core` |
| 物理路径 | `frameworks/application.core/` |
| 包前缀 | `demo.frameworks.application.core` |
| 内部依赖 | `:domain.core` (api) |
| 外部依赖 | `spring-boot`, `spring-tx` |

## 提供的能力

| 类/接口 | 说明 |
|---------|------|
| `@UseCase` | 组合注解 = `@Component` + `@Transactional` + 安全检查支持 (authorities/authenticated) |
| `ExceptionTranslator` | 应用边界异常翻译接口 |

## `@UseCase` 注解说明

```java
@UseCase  // 默认：普通 @Component + @Transactional
@UseCase(authorities = "ORDER_WRITE")  // 需要 ORDER_WRITE 权限
@UseCase(authenticated = true)  // 仅需认证
```

注意：安全检查逻辑（authorities/authenticated）需要结合实际的 SecurityContext 实现才生效，当前为声明式设计。

## 常用命令

```bash
gradle :application.core:test
gradle :application.core:check
```

## 依赖关系

```
:application.core    (被以下模块依赖)
  ← :order  (api)
```