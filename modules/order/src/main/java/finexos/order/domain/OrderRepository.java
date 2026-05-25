package finexos.order.domain;

import finexos.order.core.Order;
import finexos.order.core.OrderId;
import finexos.frameworks.domain.core.AggregateNotFoundException;

import java.util.List;

public interface OrderRepository {
    void save(Order order);
    Order findById(OrderId id) throws AggregateNotFoundException;
    List<Order> findAll();
    void deleteById(OrderId id);
}
