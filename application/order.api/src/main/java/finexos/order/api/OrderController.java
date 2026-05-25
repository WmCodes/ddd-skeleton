package finexos.order.api;

import finexos.order.application.OrderUseCase;
import finexos.order.application.dto.OrderRequest;
import finexos.order.application.dto.OrderResponse;
import finexos.order.core.OrderId;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderUseCase orderUseCase;

    public OrderController(OrderUseCase orderUseCase) {
        this.orderUseCase = orderUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@RequestBody OrderRequest request) {
        return orderUseCase.createOrder(request);
    }

    @GetMapping("/{id}")
    public OrderResponse getOrder(@PathVariable String id) {
        return orderUseCase.getOrder(new OrderId(id));
    }

    @GetMapping
    public List<OrderResponse> listOrders() {
        return orderUseCase.listOrders();
    }

    @PostMapping("/{id}/cancel")
    public OrderResponse cancelOrder(@PathVariable String id) {
        return orderUseCase.cancelOrder(new OrderId(id));
    }

    @PostMapping("/{id}/process")
    public OrderResponse processOrder(@PathVariable String id) {
        return orderUseCase.processOrder(new OrderId(id));
    }

    @PostMapping("/{id}/complete")
    public OrderResponse completeOrder(@PathVariable String id) {
        return orderUseCase.completeOrder(new OrderId(id));
    }
}
