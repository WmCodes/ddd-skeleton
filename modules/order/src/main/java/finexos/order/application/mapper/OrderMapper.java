package finexos.order.application.mapper;

import finexos.order.application.dto.OrderResponse;
import finexos.order.core.Order;

import java.util.List;

public class OrderMapper {

    public OrderResponse toResponse(Order order) {
        List<OrderResponse.ItemDto> items = order.items().stream()
                .map(item -> new OrderResponse.ItemDto(
                        item.productId(), item.quantity(), item.unitPrice()))
                .toList();

        return new OrderResponse(
                order.id().value(),
                order.customerId(),
                items,
                order.status(),
                order.totalAmount(),
                order.createdAt()
        );
    }
}
