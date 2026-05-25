package demo.order.core;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("checkstyle:MagicNumber")
class OrderItemTest {

    @Test
    void equality() {
        OrderItem item1 = new OrderItem("p1", 2, 10.0);
        OrderItem item2 = new OrderItem("p1", 2, 10.0);
        OrderItem item3 = new OrderItem("p2", 1, 5.0);
        assertThat(item1).isEqualTo(item2);
        assertThat(item1).isNotEqualTo(item3);
        assertThat(item1).isNotEqualTo(null);
        assertThat(item1).isNotEqualTo("not-an-item");
    }

    @Test
    void hashCodeIsConsistent() {
        OrderItem item = new OrderItem("p1", 2, 10.0);
        assertThat(item.hashCode()).isEqualTo(new OrderItem("p1", 2, 10.0).hashCode());
    }

    @Test
    void accessors() {
        OrderItem item = new OrderItem("p1", 3, 15.0);
        assertThat(item.productId()).isEqualTo("p1");
        assertThat(item.quantity()).isEqualTo(3);
        assertThat(item.unitPrice()).isEqualTo(15.0);
        assertThat(item.totalPrice()).isEqualTo(45.0);
    }
}
