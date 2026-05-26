# CLAUDE.md — :order

订单领域业务模块。包含领域实体、领域服务、应用用例和 DTO。

## 模块信息

| 属性 | 值 |
|------|-----|
| Gradle 路径 | `:order` |
| 物理路径 | `modules/order/` |
| 内部依赖 | `:domain.core` (api), `:application.core` (api) |
| 外部依赖 | `spring-boot`, `spring-tx` |

## 三层架构

### core/ — 领域实体与值对象

| 类 | 类型 | 说明 |
|------|------|------|
| `Order` | 聚合根 | 状态机: `CREATED → PROCESSING → COMPLETED`，任意 → `CANCELLED` |
| `OrderId` | 值对象 | 封装订单 ID 字符串，提供 `value()` |
| `OrderItem` | 值对象 | 商品明细: productId, quantity, unitPrice |
| `OrderStatus` | 枚举 | CREATED, PROCESSING, COMPLETED, CANCELLED |

`Order` 状态机约束：
- `addItem()` — 仅 CREATED 状态可添加
- `process()` — 仅 CREATED → PROCESSING
- `complete()` — 仅 PROCESSING → COMPLETED
- `cancel()` — CREATED 或 PROCESSING → CANCELLED

### domain/ — 领域服务与仓储接口

| 类/接口 | 说明 |
|---------|------|
| `OrderRepository` | 仓储接口: save, findById, findAll, deleteById |
| `OrderService` | 领域服务 (@UseCase)，编排订单 CRUD 流程 |
| `OrderDomainException` | 订单领域异常 |

### application/ — 应用层

| 类 | 说明 |
|------|------|
| `OrderUseCase` | 应用用例 (@UseCase)，协调领域服务 + DTO 映射 |
| `OrderRequest` | 创建订单请求 DTO (record) |
| `OrderResponse` | 订单响应 DTO (record)，含嵌套 ItemDto |
| `OrderMapper` | Order → OrderResponse 映射接口 (MapStruct) |
| `OrderMapperConfig` | Spring Configuration 注册 Mapper |

## 常用命令

```bash
gradle :order:test
gradle :order:check
```

## 依赖关系

```
:order    (被以下模块依赖)
  ← :order.jpa  (api)
  ← :order.api  (api)
```