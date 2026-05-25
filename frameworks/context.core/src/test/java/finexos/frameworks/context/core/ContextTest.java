package finexos.frameworks.context.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ContextTest {

    @Test
    void privateConstructor() throws Exception {
        var constructor = Context.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    @DisplayName("operator 返回当前操作者")
    void operatorReturnsCurrentOperator() {
        Operator operator = new Operator("123", "test", Set.of("READ"));
        Context.resetOperator(operator);
        assertThat(Context.operator()).isEqualTo(operator);
    }
}
