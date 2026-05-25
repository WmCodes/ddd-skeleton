package demo.order.domain.exception;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class OrderDomainExceptionTest {

    @Test
    void messageOnly() {
        OrderDomainException ex = new OrderDomainException("test error");
        assertThat(ex.getMessage()).isEqualTo("test error");
    }

    @Test
    void messageWithCause() {
        RuntimeException cause = new RuntimeException("cause");
        OrderDomainException ex = new OrderDomainException("test error", cause);
        assertThat(ex.getMessage()).isEqualTo("test error");
        assertThat(ex.getCause()).isSameAs(cause);
    }
}
