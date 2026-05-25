package finexos.frameworks.domain.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UuidBasedIdentityServiceTest {

    private final UuidBasedIdentityService service = new UuidBasedIdentityService();

    @Test
    @DisplayName("nextIdentity 生成唯一标识")
    void nextIdentityGeneratesUniqueIdentity() {
        String id1 = service.nextIdentity();
        String id2 = service.nextIdentity();
        assertThat(id1).isNotEqualTo(id2);
        assertThat(id1).doesNotContain("-");
        assertThat(id1).isEqualTo(id1.toLowerCase());
    }

    @Test
    @DisplayName("nextReadableIdentity 生成可读标识")
    void nextReadableIdentityGeneratesReadableIdentity() {
        String id = service.nextReadableIdentity();
        assertThat(id).matches("\\d{21}");
    }
}
