package finexos.order.jpa;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("checkstyle:MagicNumber")
class OrderEntityTest {

    @Test
    void protectedConstructor() throws Exception {
        var constructor = OrderEntity.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    void accessors() {
        LocalDateTime now = LocalDateTime.now();
        List<OrderItemEntity> items = List.of(new OrderItemEntity("p1", 2, 10.0));
        OrderEntity entity = new OrderEntity("order-1", "customer-1", "CREATED", now, items);
        assertThat(entity.getId()).isEqualTo("order-1");
        assertThat(entity.getCustomerId()).isEqualTo("customer-1");
        assertThat(entity.getStatus()).isEqualTo("CREATED");
        assertThat(entity.getCreatedAt()).isEqualTo(now);
        assertThat(entity.getItems()).hasSize(1);
    }

    @Test
    void setStatus() {
        OrderEntity entity = new OrderEntity("order-1", "customer-1", "CREATED", LocalDateTime.now(), List.of());
        entity.setStatus("PROCESSING");
        assertThat(entity.getStatus()).isEqualTo("PROCESSING");
    }
}
