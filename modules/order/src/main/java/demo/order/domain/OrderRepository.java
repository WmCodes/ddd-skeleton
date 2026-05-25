package demo.order.domain;

import demo.order.core.Order;
import demo.order.core.OrderId;
import demo.frameworks.domain.core.AggregateNotFoundException;

import java.util.List;

public interface OrderRepository {
    void save(Order order);
    Order findById(OrderId id) throws AggregateNotFoundException;
    List<Order> findAll();
    void deleteById(OrderId id);
}
