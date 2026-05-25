package demo.order.domain;

import demo.frameworks.domain.core.AggregateNotFoundException;
import demo.frameworks.domain.core.IdentityService;
import demo.order.core.Order;
import demo.order.core.OrderId;
import demo.order.core.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository repository;

    @Mock
    private IdentityService identityService;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderService(repository, identityService);
    }

    @Test
    @DisplayName("createOrder creates and saves order")
    void createOrder() {
        when(identityService.nextIdentity()).thenReturn("order-id-1");

        Order order = orderService.createOrder("customer-1");

        assertThat(order.customerId()).isEqualTo("customer-1");
        assertThat(order.status()).isEqualTo(OrderStatus.CREATED);
        verify(repository).save(any(Order.class));
    }

    @Test
    @DisplayName("getOrder returns order by id")
    void getOrder() {
        OrderId id = new OrderId("test-1");
        Order expected = Order.create(id, "customer-1");
        when(repository.findById(id)).thenReturn(expected);

        Order result = orderService.getOrder(id);

        assertThat(result.customerId()).isEqualTo("customer-1");
    }

    @Test
    @DisplayName("getOrder throws when not found")
    void getOrderWhenNotFound() {
        OrderId id = new OrderId("missing");
        when(repository.findById(id)).thenThrow(new AggregateNotFoundException(id.value(), Order.class, "orders"));

        assertThatThrownBy(() -> orderService.getOrder(id))
                .isInstanceOf(AggregateNotFoundException.class);
    }

    @Test
    @DisplayName("listOrders returns all orders")
    void listOrders() {
        Order order1 = Order.create("customer-1");
        Order order2 = Order.create("customer-2");
        when(repository.findAll()).thenReturn(List.of(order1, order2));

        List<Order> result = orderService.listOrders();

        assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("cancelOrder cancels and saves order")
    void cancelOrder() {
        OrderId id = new OrderId("test-1");
        Order order = Order.create(id, "customer-1");
        when(repository.findById(id)).thenReturn(order);

        Order result = orderService.cancelOrder(id);

        assertThat(result.status()).isEqualTo(OrderStatus.CANCELLED);
        verify(repository).save(order);
    }

    @Test
    @DisplayName("processOrder processes and saves order")
    void processOrder() {
        OrderId id = new OrderId("test-1");
        Order order = Order.create(id, "customer-1");
        when(repository.findById(id)).thenReturn(order);

        Order result = orderService.processOrder(id);

        assertThat(result.status()).isEqualTo(OrderStatus.PROCESSING);
        verify(repository).save(order);
    }

    @Test
    @DisplayName("completeOrder completes and saves order")
    void completeOrder() {
        OrderId id = new OrderId("test-1");
        Order order = Order.create(id, "customer-1");
        order.process();
        when(repository.findById(id)).thenReturn(order);

        Order result = orderService.completeOrder(id);

        assertThat(result.status()).isEqualTo(OrderStatus.COMPLETED);
        verify(repository).save(order);
    }
}
