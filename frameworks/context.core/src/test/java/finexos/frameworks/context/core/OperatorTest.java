package finexos.frameworks.context.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OperatorTest {

    @Test
    @DisplayName("构造函数初始化")
    void constructorInitializesFields() {
        Operator operator = new Operator("123", "test", Set.of("READ", "WRITE"));
        assertThat(operator.id()).isEqualTo("123");
        assertThat(operator.name()).isEqualTo("test");
        assertThat(operator.authorities()).containsExactlyInAnyOrder("READ", "WRITE");
    }

    @Test
    @DisplayName("authorities 返回不可变集合")
    void authoritiesReturnsUnmodifiableSet() {
        Operator operator = new Operator("123", "test", Set.of("READ"));
        assertThatThrownBy(() -> operator.authorities().add("WRITE"))
            .isInstanceOf(UnsupportedOperationException.class);
    }
}
