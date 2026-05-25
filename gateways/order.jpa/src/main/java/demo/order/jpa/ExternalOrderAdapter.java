package demo.order.jpa;

import demo.order.core.Order;
import demo.order.core.OrderId;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * Anti-Corruption Layer (ACL) adapter for transforming external order
 * representations into domain Order objects.
 */
@Component
public class ExternalOrderAdapter {

    /**
     * Converts an external order record into a domain Order aggregate.
     *
     * @param externalId   the external order identifier
     * @param customerId   the customer identifier
     * @param items        external order items to adapt
     * @return a new domain Order
     */
    public Order toDomain(String externalId, String customerId, List<ExternalItem> items) {
        Order order = Order.create(new OrderId(externalId), customerId);
        items.forEach(item ->
                order.addItem(item.productId(), item.quantity(), item.unitPrice()));
        return order;
    }

    /**
     * External order item record representing data from an external system.
     */
    public record ExternalItem(String productId, int quantity, double unitPrice) { }
}
