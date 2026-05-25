package demo.order.application.dto;

import demo.order.core.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        String id,
        String customerId,
        List<ItemDto> items,
        OrderStatus status,
        double totalAmount,
        LocalDateTime createdAt
) {
    public record ItemDto(String productId, int quantity, double unitPrice) { }
}
