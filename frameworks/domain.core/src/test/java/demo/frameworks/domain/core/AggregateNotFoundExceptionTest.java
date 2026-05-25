package demo.frameworks.domain.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AggregateNotFoundExceptionTest {

    @Test
    @DisplayName("带id的构造函数")
    void constructorWithId() {
        AggregateNotFoundException ex = new AggregateNotFoundException("123", Order.class, "orders");
        assertThat(ex.getMessage()).contains("123").contains("Order").contains("orders");
    }

    @Test
    @DisplayName("不带id的构造函数")
    void constructorWithoutId() {
        AggregateNotFoundException ex = new AggregateNotFoundException(Order.class, "orders");
        assertThat(ex.getMessage()).contains("Order").contains("orders");
    }

    // Helper class for testing
    static class Order {
    }
}
