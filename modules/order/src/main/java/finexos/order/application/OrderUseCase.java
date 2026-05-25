package finexos.order.application;

import finexos.frameworks.application.core.UseCase;
import finexos.order.application.dto.OrderRequest;
import finexos.order.application.dto.OrderResponse;
import finexos.order.application.mapper.OrderMapper;
import finexos.order.core.Order;
import finexos.order.core.OrderId;
import finexos.order.domain.OrderService;

import java.util.List;

@UseCase
public class OrderUseCase {

    private final OrderService orderService;
    private final OrderMapper mapper;

    public OrderUseCase(OrderService orderService, OrderMapper mapper) {
        this.orderService = orderService;
        this.mapper = mapper;
    }

    public OrderResponse createOrder(OrderRequest request) {
        Order order = orderService.createOrder(request.customerId());
        return mapper.toResponse(order);
    }

    public OrderResponse getOrder(OrderId id) {
        Order order = orderService.getOrder(id);
        return mapper.toResponse(order);
    }

    public List<OrderResponse> listOrders() {
        return orderService.listOrders().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public OrderResponse cancelOrder(OrderId id) {
        Order order = orderService.cancelOrder(id);
        return mapper.toResponse(order);
    }

    public OrderResponse processOrder(OrderId id) {
        Order order = orderService.processOrder(id);
        return mapper.toResponse(order);
    }

    public OrderResponse completeOrder(OrderId id) {
        Order order = orderService.completeOrder(id);
        return mapper.toResponse(order);
    }
}
