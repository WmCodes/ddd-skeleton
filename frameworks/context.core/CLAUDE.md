# CLAUDE.md — :context.core

请求范围上下文模块。基于 ThreadLocal 存储当前操作者信息。

## 模块信息

| 属性 | 值 |
|------|-----|
| Gradle 路径 | `:context.core` |
| 物理路径 | `frameworks/context.core/` |
| 包前缀 | `demo.frameworks.context.core` |
| 依赖 | 无（纯 Java） |

## 提供的能力

| 类 | 说明 |
|------|------|
| `Context` | ThreadLocal 持有者 — `Context.operator()` 获取当前操作者，`Context.resetOperator(op)` 设置 |
| `Operator` | 不可变值对象 — `id()`, `name()`, `authorities()` (返回不可修改的 Set) |

## 使用方式

```java
// 设置当前操作者（通常在 Filter/Interceptor 中）
Context.resetOperator(new Operator("user-1", "张三", Set.of("ORDER_READ")));

// 获取当前操作者（在 Service/UseCase 中）
Operator op = Context.operator();
String currentUser = op.name();
```

## 注意事项

- ThreadLocal 存储，务必在请求结束后清理以免内存泄漏/线程串扰
- 当前模块不提供 Filter/Interceptor — 由消费方（如 `:order.api`）自行管理

## 常用命令

```bash
gradle :context.core:test
gradle :context.core:check
```