package finexos.order.application;

import finexos.order.application.dto.OrderRequest;
import finexos.order.application.dto.OrderResponse;
import finexos.order.application.mapper.OrderMapper;
import finexos.order.core.Order;
import finexos.order.core.OrderId;
import finexos.order.core.OrderStatus;
import finexos.order.domain.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("checkstyle:MagicNumber")
class OrderUseCaseTest {

    @Mock
    private OrderService orderService;

    private OrderMapper mapper;
    private OrderUseCase orderUseCase;

    @BeforeEach
    void setUp() {
        mapper = new OrderMapper();
        orderUseCase = new OrderUseCase(orderService, mapper);
    }

    @Test
    @DisplayName("createOrder creates order and returns response")
    void createOrder() {
        Order order = Order.create("customer-1");
        when(orderService.createOrder("customer-1")).thenReturn(order);

        OrderResponse response = orderUseCase.createOrder(new OrderRequest("customer-1"));

        assertThat(response.customerId()).isEqualTo("customer-1");
        assertThat(response.status()).isEqualTo(OrderStatus.CREATED);
    }

    @Test
    @DisplayName("getOrder returns order response by id")
    void getOrder() {
        OrderId id = new OrderId("test-1");
        Order order = Order.create(id, "customer-1");
        when(orderService.getOrder(id)).thenReturn(order);

        OrderResponse response = orderUseCase.getOrder(id);

        assertThat(response.id()).isEqualTo("test-1");
        assertThat(response.customerId()).isEqualTo("customer-1");
    }

    @Test
    @DisplayName("listOrders returns all order responses")
    void listOrders() {
        Order order1 = Order.create("customer-1");
        Order order2 = Order.create("customer-2");
        when(orderService.listOrders()).thenReturn(List.of(order1, order2));

        List<OrderResponse> responses = orderUseCase.listOrders();

        assertThat(responses).hasSize(2);
    }

    @Test
    @DisplayName("cancelOrder cancels and returns response")
    void cancelOrder() {
        OrderId id = new OrderId("test-1");
        Order order = Order.create(id, "customer-1");
        when(orderService.cancelOrder(id)).thenAnswer(invocation -> {
            order.cancel();
            return order;
        });

        OrderResponse response = orderUseCase.cancelOrder(id);

        assertThat(response.status()).isEqualTo(OrderStatus.CANCELLED);
    }

    @Test
    @DisplayName("processOrder processes and returns response")
    void processOrder() {
        OrderId id = new OrderId("test-1");
        Order order = Order.create(id, "customer-1");
        when(orderService.processOrder(id)).thenAnswer(invocation -> {
            order.process();
            return order;
        });

        OrderResponse response = orderUseCase.processOrder(id);

        assertThat(response.status()).isEqualTo(OrderStatus.PROCESSING);
    }

    @Test
    @DisplayName("completeOrder completes and returns response")
    void completeOrder() {
        OrderId id = new OrderId("test-1");
        Order order = Order.create(id, "customer-1");
        order.process();
        when(orderService.completeOrder(id)).thenAnswer(invocation -> {
            order.complete();
            return order;
        });

        OrderResponse response = orderUseCase.completeOrder(id);

        assertThat(response.status()).isEqualTo(OrderStatus.COMPLETED);
    }

    @Test
    @DisplayName("createOrder with items maps correctly")
    void createOrderWithItems() {
        Order order = Order.create("customer-1");
        order.addItem("product-1", 2, 10.0);
        when(orderService.createOrder("customer-1")).thenReturn(order);

        OrderResponse response = orderUseCase.createOrder(new OrderRequest("customer-1"));

        assertThat(response.items()).hasSize(1);
        assertThat(response.items().get(0).productId()).isEqualTo("product-1");
        assertThat(response.items().get(0).quantity()).isEqualTo(2);
        assertThat(response.items().get(0).unitPrice()).isEqualTo(10.0);
        assertThat(response.totalAmount()).isEqualTo(20.0);
    }
}
