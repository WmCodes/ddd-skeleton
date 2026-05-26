# CLAUDE.md — :order.api

可运行的 Spring Boot 应用入口 + REST API 层。

## 模块信息

| 属性 | 值 |
|------|-----|
| Gradle 路径 | `:order.api` |
| 物理路径 | `application/order.api/` |
| 内部依赖 | `:order` (api), `:order.jpa` (api), `:domain.core` (api) |
| 外部依赖 | `spring-boot-starter-web`, `spring-boot-starter-data-jpa`, `postgresql` |

## 启动方式

```bash
# 1. 启动数据库 (需要 Docker)
scripts/setup-dev

# 2. 启动应用
scripts/run-server
# 或直接:
gradle :order.api:bootRun
```

应用默认运行在 **http://localhost:8080**

## API 端点

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/orders` | 创建订单 (201) |
| GET | `/api/orders/{id}` | 查询订单 |
| GET | `/api/orders` | 订单列表 |
| POST | `/api/orders/{id}/cancel` | 取消订单 |
| POST | `/api/orders/{id}/process` | 处理订单 |
| POST | `/api/orders/{id}/complete` | 完成订单 |

## 配置

| 配置项 | 值 |
|--------|-----|
| 数据源 | `jdbc:postgresql://localhost:5432/demo_skeleton` |
| JPA ddl-auto | `validate` (由 Flyway 管理) |
| Flyway | 启用，迁移文件在 `classpath:db/migration/` |
| 端口 | 8080 |

## 数据库迁移

| 文件 | 说明 |
|------|------|
| `V001__create_orders_table.sql` | 创建 orders 表 |
| `V002__create_order_items_table.sql` | 创建 order_items 表（外键关联 orders） |

## 常见命令

```bash
gradle :order.api:test
gradle :order.api:check
gradle :order.api:bootRun
```

## 依赖关系

```
:order.api (顶层可运行应用)
  ├── :order
  │   ├── :domain.core
  │   └── :application.core
  └── :order.jpa
      └── :order
```