package finexos.order.application.mapper;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class OrderMapperConfigTest {

    @Test
    void orderMapperBean() {
        OrderMapperConfig config = new OrderMapperConfig();
        OrderMapper mapper = config.orderMapper();
        assertThat(mapper).isNotNull();
    }
}
