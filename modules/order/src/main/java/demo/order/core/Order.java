package demo.order.core;

import demo.order.domain.exception.OrderDomainException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Order {

    private final OrderId id;
    private final String customerId;
    private final List<OrderItem> items;
    private OrderStatus status;
    private final LocalDateTime createdAt;

    private Order(OrderId id, String customerId) {
        this.id = id;
        this.customerId = customerId;
        this.items = new ArrayList<>();
        this.status = OrderStatus.CREATED;
        this.createdAt = LocalDateTime.now();
    }

    public static Order create(OrderId id, String customerId) {
        validateCustomerId(customerId);
        return new Order(id, customerId);
    }

    public static Order create(String customerId) {
        return create(new OrderId(UUID.randomUUID().toString()), customerId);
    }

    public void addItem(String productId, int quantity, double unitPrice) {
        if (status != OrderStatus.CREATED) {
            throw new OrderDomainException("\u53ea\u80fd\u5bf9\u521b\u5efa\u72b6\u6001\u7684\u8ba2\u5355\u6dfb\u52a0\u5546\u54c1");
        }
        if (quantity <= 0 || unitPrice <= 0) {
            throw new OrderDomainException("\u6570\u91cf\u548c\u5355\u4ef7\u5fc5\u987b\u5927\u4e8e\u96f6");
        }
        items.add(new OrderItem(productId, quantity, unitPrice));
    }

    public void cancel() {
        if (status != OrderStatus.CREATED && status != OrderStatus.PROCESSING) {
            throw new OrderDomainException("\u53ea\u80fd\u53d6\u6d88\u521b\u5efa\u6216\u5904\u7406\u4e2d\u7684\u8ba2\u5355");
        }
        this.status = OrderStatus.CANCELLED;
    }

    public void complete() {
        if (status != OrderStatus.PROCESSING) {
            throw new OrderDomainException("\u53ea\u80fd\u5b8c\u6210\u5904\u7406\u4e2d\u7684\u8ba2\u5355");
        }
        this.status = OrderStatus.COMPLETED;
    }

    public void process() {
        if (status != OrderStatus.CREATED) {
            throw new OrderDomainException("\u53ea\u80fd\u5904\u7406\u521b\u5efa\u72b6\u6001\u7684\u8ba2\u5355");
        }
        this.status = OrderStatus.PROCESSING;
    }

    public OrderId id() {
        return id;
    }

    public String customerId() {
        return customerId;
    }

    public List<OrderItem> items() {
        return List.copyOf(items);
    }

    public OrderStatus status() {
        return status;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    public double totalAmount() {
        return items.stream().mapToDouble(OrderItem::totalPrice).sum();
    }

    private static void validateCustomerId(String customerId) {
        if (customerId == null || customerId.isBlank()) {
            throw new OrderDomainException("\u5ba2\u6237ID\u4e0d\u80fd\u4e3a\u7a7a");
        }
    }
}
