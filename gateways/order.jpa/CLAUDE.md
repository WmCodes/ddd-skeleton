# CLAUDE.md — :order.jpa

Order 领域的 JPA 基础设施实现。包含 Repository 实现、Entity 映射和防腐层 (ACL) 适配器。

## 模块信息

| 属性 | 值 |
|------|-----|
| Gradle 路径 | `:order.jpa` |
| 物理路径 | `gateways/order.jpa/` |
| 内部依赖 | `:order` (api), `:domain.core` (api) |
| 外部依赖 | `spring-boot-starter-data-jpa`, `postgresql`, `testcontainers` |

## 提供的能力

| 类 | 说明 |
|------|------|
| `OrderEntity` | JPA @Entity 映射 orders 表，@OneToMany 关联 OrderItemEntity |
| `OrderItemEntity` | JPA @Entity 映射 order_items 表，自增主键 |
| `OrderStatusConverter` | JPA @AttributeConverter: OrderStatus ↔ DB VARCHAR |
| `OrderJpaRepository` | 实现 OrderRepository 接口，EntityManager 实现 CRUD + 状态恢复 |
| `ExternalOrderAdapter` | 防腐层 (ACL) 适配器：外部订单记录 → 领域 Order 对象 |

## 关键实现细节

- `OrderJpaRepository.toDomain()`: 从 JPA Entity 重建领域 Order，通过 `advanceToStatus()` 沿状态机路径恢复状态
- `OrderJpaRepository.save()`: UPDATE 时清除 items 再重新添加，INSERT 时直接 persist
- `ExternalOrderAdapter`: 保持外部系统模型和领域模型之间的隔离

## 测试说明

使用 Testcontainers (PostgreSQL) 进行集成测试。

```bash
# 需要 Docker 环境
gradle :order.jpa:test
gradle :order.jpa:check
```

## 依赖关系

```
:order.jpa    (被以下模块依赖)
  ← :order.api  (api)
```