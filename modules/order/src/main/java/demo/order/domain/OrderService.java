package demo.order.domain;

import demo.frameworks.application.core.UseCase;
import demo.frameworks.domain.core.IdentityService;
import demo.order.core.Order;
import demo.order.core.OrderId;

import java.util.List;

@UseCase
public class OrderService {

    private final OrderRepository repository;
    private final IdentityService identityService;

    public OrderService(OrderRepository repository, IdentityService identityService) {
        this.repository = repository;
        this.identityService = identityService;
    }

    public Order createOrder(String customerId) {
        OrderId id = new OrderId(identityService.nextIdentity());
        Order order = Order.create(id, customerId);
        repository.save(order);
        return order;
    }

    public Order getOrder(OrderId id) {
        return repository.findById(id);
    }

    public List<Order> listOrders() {
        return repository.findAll();
    }

    public Order cancelOrder(OrderId id) {
        Order order = repository.findById(id);
        order.cancel();
        repository.save(order);
        return order;
    }

    public Order processOrder(OrderId id) {
        Order order = repository.findById(id);
        order.process();
        repository.save(order);
        return order;
    }

    public Order completeOrder(OrderId id) {
        Order order = repository.findById(id);
        order.complete();
        repository.save(order);
        return order;
    }
}
