package finexos.frameworks.domain.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DomainExceptionTest {

    @Test
    @DisplayName("构造函数带消息")
    void constructorWithMessage() {
        DomainException ex = new DomainException("test message");
        assertThat(ex.getMessage()).isEqualTo("test message");
    }

    @Test
    @DisplayName("构造函数带消息和cause")
    void constructorWithMessageAndCause() {
        Throwable cause = new RuntimeException("cause");
        DomainException ex = new DomainException("test message", cause);
        assertThat(ex.getMessage()).isEqualTo("test message");
        assertThat(ex.getCause()).isEqualTo(cause);
    }
}
