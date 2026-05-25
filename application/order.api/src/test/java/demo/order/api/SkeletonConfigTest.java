package demo.order.api;

import demo.frameworks.domain.core.IdentityService;
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
