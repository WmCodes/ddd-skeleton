package demo.order.jpa;

import demo.frameworks.domain.core.AggregateNotFoundException;
import demo.order.core.Order;
import demo.order.core.OrderId;
import demo.order.core.OrderItem;
import demo.order.core.OrderStatus;
import demo.order.domain.OrderRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class OrderJpaRepository implements OrderRepository {

    private final EntityManager entityManager;

    public OrderJpaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Order order) {
        OrderEntity entity = entityManager.find(OrderEntity.class, order.id().value());
        if (entity != null) {
            entity.setStatus(order.status().name());
            entity.getItems().clear();
            for (OrderItem item : order.items()) {
                entity.getItems().add(toItemEntity(item));
            }
            entityManager.flush();
        } else {
            OrderEntity newEntity = new OrderEntity(
                    order.id().value(),
                    order.customerId(),
                    order.status().name(),
                    order.createdAt(),
                    order.items().stream()
                            .map(this::toItemEntity)
                            .toList());
            entityManager.persist(newEntity);
        }
    }

    @Override
    public Order findById(OrderId id) {
        OrderEntity entity = entityManager.find(OrderEntity.class, id.value());
        if (entity == null) {
            throw new AggregateNotFoundException(id.value(), Order.class, "orders");
        }
        return toDomain(entity);
    }

    @Override
    public List<Order> findAll() {
        return entityManager
                .createQuery("SELECT o FROM OrderEntity o", OrderEntity.class)
                .getResultList()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void deleteById(OrderId id) {
        OrderEntity entity = entityManager.find(OrderEntity.class, id.value());
        if (entity != null) {
            entityManager.remove(entity);
        }
    }

    private Order toDomain(OrderEntity entity) {
        Order order = Order.create(new OrderId(entity.getId()), entity.getCustomerId());
        for (OrderItemEntity itemEntity : entity.getItems()) {
            order.addItem(itemEntity.getProductId(), itemEntity.getQuantity(), itemEntity.getUnitPrice());
        }
        OrderStatus persisted = OrderStatus.valueOf(entity.getStatus());
        return advanceToStatus(order, persisted);
    }

    private Order advanceToStatus(Order order, OrderStatus target) {
        if (target == OrderStatus.CREATED) {
            return order;
        }
        if (target == OrderStatus.CANCELLED) {
            order.cancel();
            return order;
        }
        order.process();
        if (target == OrderStatus.PROCESSING) {
            return order;
        }
        order.complete();
        return order;
    }

    private OrderItemEntity toItemEntity(OrderItem item) {
        return new OrderItemEntity(item.productId(), item.quantity(), item.unitPrice());
    }
}
