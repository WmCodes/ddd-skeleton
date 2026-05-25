package finexos.order.jpa;

import finexos.order.core.Order;
import finexos.order.core.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("checkstyle:MagicNumber")
class ExternalOrderAdapterTest {

    private final ExternalOrderAdapter adapter = new ExternalOrderAdapter();

    @Test
    @DisplayName("toDomain creates Order from external items")
    void toDomain() {
        List<ExternalOrderAdapter.ExternalItem> items = List.of(
                new ExternalOrderAdapter.ExternalItem("product-1", 2, 10.0),
                new ExternalOrderAdapter.ExternalItem("product-2", 1, 25.0)
        );

        Order order = adapter.toDomain("ext-1", "customer-1", items);

        assertThat(order.id().value()).isEqualTo("ext-1");
        assertThat(order.customerId()).isEqualTo("customer-1");
        assertThat(order.status()).isEqualTo(OrderStatus.CREATED);
        assertThat(order.items()).hasSize(2);
        assertThat(order.totalAmount()).isEqualTo(45.0);
    }

    @Test
    @DisplayName("toDomain creates Order with empty items")
    void toDomainEmptyItems() {
        Order order = adapter.toDomain("ext-2", "customer-2", List.of());

        assertThat(order.items()).isEmpty();
        assertThat(order.totalAmount()).isZero();
    }
}
