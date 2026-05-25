package finexos.order.api;

import finexos.frameworks.domain.core.IdentityService;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class SkeletonConfigTest {

    @Test
    void identityServiceBean() {
        SkeletonConfig config = new SkeletonConfig();
        IdentityService service = config.identityService();
        assertThat(service).isNotNull();
        assertThat(service.nextIdentity()).isNotNull();
    }
}
