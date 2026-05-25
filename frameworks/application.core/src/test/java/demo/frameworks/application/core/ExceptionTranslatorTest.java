package demo.frameworks.application.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExceptionTranslatorTest {

    private final ExceptionTranslator translator = new ExceptionTranslator() {
        @Override
        public RuntimeException translate(Throwable throwable) {
            return new RuntimeException(throwable);
        }

        @Override
        public String translate(String message) {
            return message;
        }
    };

    @Test
    @DisplayName("translate 转换Throwable")
    void translateThrowable() {
        RuntimeException result = translator.translate(new RuntimeException("test"));
        assertThat(result.getCause()).isNotNull();
        assertThat(result.getCause().getMessage()).isEqualTo("test");
    }

    @Test
    @DisplayName("translate 转换String")
    void translateString() {
        String result = translator.translate("test message");
        assertThat(result).isEqualTo("test message");
    }
}
