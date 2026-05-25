package demo.order.api;

import demo.frameworks.domain.core.IdentityService;
import demo.frameworks.domain.core.UuidBasedIdentityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SkeletonConfig {

    @Bean
    public IdentityService identityService() {
        return new UuidBasedIdentityService();
    }
}
