package finexos.order.core;

import finexos.order.domain.exception.OrderDomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings("checkstyle:MagicNumber")
class OrderTest {

    @Test
    @DisplayName("create order with valid customer id")
    void create() {
        Order order = Order.create("customer-1");
        assertThat(order.customerId()).isEqualTo("customer-1");
        assertThat(order.status()).isEqualTo(OrderStatus.CREATED);
        assertThat(order.items()).isEmpty();
        assertThat(order.totalAmount()).isZero();
    }

    @Test
    @DisplayName("create order throws when customer id is null")
    void createWithNullCustomerId() {
        assertThatThrownBy(() -> Order.create((String) null))
                .isInstanceOf(OrderDomainException.class)
                .hasMessageContaining("\u5ba2\u6237");
    }

    @Test
    @DisplayName("create order throws when customer id is blank")
    void createWithBlankCustomerId() {
        assertThatThrownBy(() -> Order.create("  "))
                .isInstanceOf(OrderDomainException.class)
                .hasMessageContaining("\u5ba2\u6237");
    }

    @Test
    @DisplayName("addItem adds item to created order")
    void addItem() {
        Order order = Order.create("customer-1");
        order.addItem("product-1", 2, 10.0);
        assertThat(order.items()).hasSize(1);
        assertThat(order.totalAmount()).isEqualTo(20.0);
    }

    @Test
    @DisplayName("addItem throws when order is not CREATED")
    void addItemWhenNotCreated() {
        Order order = Order.create("customer-1");
        order.process();
        assertThatThrownBy(() -> order.addItem("product-1", 1, 10.0))
                .isInstanceOf(OrderDomainException.class);
    }

    @Test
    @DisplayName("addItem throws when quantity is zero")
    void addItemWithZeroQuantity() {
        Order order = Order.create("customer-1");
        assertThatThrownBy(() -> order.addItem("product-1", 0, 10.0))
                .isInstanceOf(OrderDomainException.class);
    }

    @Test
    @DisplayName("addItem throws when unit price is zero")
    void addItemWithZeroPrice() {
        Order order = Order.create("customer-1");
        assertThatThrownBy(() -> order.addItem("product-1", 1, 0))
                .isInstanceOf(OrderDomainException.class);
    }

    @Test
    @DisplayName("process transitions from CREATED to PROCESSING")
    void process() {
        Order order = Order.create("customer-1");
        order.process();
        assertThat(order.status()).isEqualTo(OrderStatus.PROCESSING);
    }

    @Test
    @DisplayName("process throws when not CREATED")
    void processWhenWrongStatus() {
        Order order = Order.create("customer-1");
        order.process();
        assertThatThrownBy(order::process)
                .isInstanceOf(OrderDomainException.class);
    }

    @Test
    @DisplayName("cancel transitions from CREATED to CANCELLED")
    void cancelFromCreated() {
        Order order = Order.create("customer-1");
        order.cancel();
        assertThat(order.status()).isEqualTo(OrderStatus.CANCELLED);
    }

    @Test
    @DisplayName("complete transitions from PROCESSING to COMPLETED")
    void complete() {
        Order order = Order.create("customer-1");
        order.process();
        order.complete();
        assertThat(order.status()).isEqualTo(OrderStatus.COMPLETED);
    }

    @Test
    @DisplayName("complete throws when not PROCESSING")
    void completeWhenWrongStatus() {
        Order order = Order.create("customer-1");
        assertThatThrownBy(order::complete)
                .isInstanceOf(OrderDomainException.class);
    }

    @Test
    @DisplayName("cancel throws when already COMPLETED")
    void cancelWhenCompleted() {
        Order order = Order.create("customer-1");
        order.process();
        order.complete();
        assertThatThrownBy(order::cancel)
                .isInstanceOf(OrderDomainException.class);
    }

    @Test
    @DisplayName("totalAmount sums all items")
    void totalAmount() {
        Order order = Order.create("customer-1");
        order.addItem("product-1", 2, 10.0);
        order.addItem("product-2", 3, 5.0);
        assertThat(order.totalAmount()).isEqualTo(35.0);
    }

    @Test
    @DisplayName("items returns defensive copy")
    void itemsReturnsDefensiveCopy() {
        Order order = Order.create("customer-1");
        order.addItem("product-1", 1, 10.0);
        assertThatThrownBy(() -> order.items().add(new OrderItem("p2", 1, 1)))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
