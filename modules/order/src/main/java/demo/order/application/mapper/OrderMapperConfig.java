package demo.order.application.mapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderMapperConfig {

    @Bean
    public OrderMapper orderMapper() {
        return new OrderMapper();
    }
}
