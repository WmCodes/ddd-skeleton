package finexos.order.jpa;

import finexos.order.core.OrderStatus;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class OrderStatusConverterTest {

    private final OrderStatusConverter converter = new OrderStatusConverter();

    @Test
    void convertToDatabaseColumn() {
        assertThat(converter.convertToDatabaseColumn(OrderStatus.CREATED)).isEqualTo("CREATED");
    }

    @Test
    void convertToDatabaseColumnNull() {
        assertThat(converter.convertToDatabaseColumn(null)).isNull();
    }

    @Test
    void convertToEntityAttribute() {
        assertThat(converter.convertToEntityAttribute("CREATED")).isEqualTo(OrderStatus.CREATED);
    }

    @Test
    void convertToEntityAttributeNull() {
        assertThat(converter.convertToEntityAttribute(null)).isNull();
    }
}
