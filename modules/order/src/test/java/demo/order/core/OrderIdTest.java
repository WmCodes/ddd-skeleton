package demo.order.core;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class OrderIdTest {

    @Test
    void equality() {
        OrderId id1 = new OrderId("123");
        OrderId id2 = new OrderId("123");
        OrderId id3 = new OrderId("456");
        assertThat(id1).isEqualTo(id2);
        assertThat(id1).isNotEqualTo(id3);
        assertThat(id1).isNotEqualTo(null);
        assertThat(id1).isNotEqualTo("123");
    }

    @Test
    void hashCodeIsConsistent() {
        OrderId id = new OrderId("123");
        assertThat(id.hashCode()).isEqualTo(new OrderId("123").hashCode());
    }

    @Test
    void value() {
        OrderId id = new OrderId("test-id");
        assertThat(id.value()).isEqualTo("test-id");
    }
}
