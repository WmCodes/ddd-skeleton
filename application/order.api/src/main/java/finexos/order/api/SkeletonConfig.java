package finexos.order.api;

import finexos.frameworks.domain.core.IdentityService;
import finexos.frameworks.domain.core.UuidBasedIdentityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SkeletonConfig {

    @Bean
    public IdentityService identityService() {
        return new UuidBasedIdentityService();
    }
}
