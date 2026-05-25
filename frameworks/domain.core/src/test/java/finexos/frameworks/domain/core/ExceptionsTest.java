package finexos.frameworks.domain.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ExceptionsTest {

    @Test
    void evaluateCatchesCheckedException() {
        assertThatThrownBy(() -> Exceptions.evaluate(() -> {
            throw new Exception("test");
        })).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("execute 执行Runnable并捕获异常")
    void executeExecutesRunnable() {
        Exceptions.execute(() -> {
            // no-op
        });
    }

    @Test
    @DisplayName("execute 捕获checked异常")
    void executeCatchesCheckedException() {
        assertThatThrownBy(() -> Exceptions.execute(() -> {
            throw new Exception("test");
        })).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("evaluate 执行Supplier并捕获异常")
    void evaluateExecutesSupplier() {
        String result = Exceptions.evaluate(() -> "test");
        assertThat(result).isEqualTo("test");
    }

    @Test
    @DisplayName("wrap 包装异常")
    void wrapWrapsException() {
        RuntimeException ex = Exceptions.wrap(new Exception("test"));
        assertThat(ex).isInstanceOf(RuntimeException.class);
        assertThat(ex.getCause()).isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("wrap 不包装RuntimeException")
    void wrapDoesNotWrapRuntimeException() {
        RuntimeException original = new RuntimeException("test");
        RuntimeException wrapped = Exceptions.wrap(original);
        assertThat(wrapped).isSameAs(original);
    }
}
