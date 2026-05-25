package demo.frameworks.application.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

class UseCaseTest {

    @Test
    @DisplayName("UseCase 注解包含 Component")
    void annotationIncludesComponent() {
        assertThat(UseCase.class.isAnnotationPresent(Component.class)).isTrue();
    }

    @Test
    @DisplayName("UseCase 注解包含 Transactional")
    void annotationIncludesTransactional() {
        assertThat(UseCase.class.isAnnotationPresent(Transactional.class)).isTrue();
    }
}
