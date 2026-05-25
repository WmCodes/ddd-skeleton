package finexos.order.core;

import java.util.Objects;

public class OrderItem {
    private final String productId;
    private final int quantity;
    private final double unitPrice;

    public OrderItem(String productId, int quantity, double unitPrice) {
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String productId() {
        return productId;
    }

    public int quantity() {
        return quantity;
    }

    public double unitPrice() {
        return unitPrice;
    }

    public double totalPrice() {
        return quantity * unitPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem orderItem)) return false;
        return quantity == orderItem.quantity
                && Double.compare(unitPrice, orderItem.unitPrice) == 0
                && productId.equals(orderItem.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, quantity, unitPrice);
    }
}
