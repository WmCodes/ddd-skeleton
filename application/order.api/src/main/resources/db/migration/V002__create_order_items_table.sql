CREATE TABLE order_items (
    id          BIGSERIAL       NOT NULL PRIMARY KEY,
    order_id    VARCHAR(64)     NOT NULL REFERENCES orders(id),
    product_id  VARCHAR(128)    NOT NULL,
    quantity    INTEGER         NOT NULL,
    unit_price  DOUBLE PRECISION NOT NULL
);

CREATE INDEX idx_order_items_order_id ON order_items(order_id);