package demo.order.jpa;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("checkstyle:MagicNumber")
class OrderItemEntityTest {

    @Test
    void protectedConstructor() throws Exception {
        var constructor = OrderItemEntity.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    void accessors() {
        OrderItemEntity entity = new OrderItemEntity("p1", 2, 10.0);
        assertThat(entity.getId()).isNull();
        assertThat(entity.getProductId()).isEqualTo("p1");
        assertThat(entity.getQuantity()).isEqualTo(2);
        assertThat(entity.getUnitPrice()).isEqualTo(10.0);
    }

    @Test
    void equality() {
        OrderItemEntity e1 = new OrderItemEntity("p1", 2, 10.0);
        OrderItemEntity e2 = new OrderItemEntity("p1", 2, 10.0);
        OrderItemEntity e3 = new OrderItemEntity("p2", 1, 5.0);
        assertThat(e1).isEqualTo(e2);
        assertThat(e1).isNotEqualTo(e3);
        assertThat(e1).isNotEqualTo(null);
        assertThat(e1).isNotEqualTo("not-an-entity");
    }

    @Test
    void hashCodeIsConsistent() {
        OrderItemEntity entity = new OrderItemEntity("p1", 2, 10.0);
        assertThat(entity.hashCode()).isEqualTo(new OrderItemEntity("p1", 2, 10.0).hashCode());
    }
}
