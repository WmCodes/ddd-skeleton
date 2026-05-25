CREATE TABLE orders (
    id          VARCHAR(64)     NOT NULL PRIMARY KEY,
    customer_id VARCHAR(128)    NOT NULL,
    status      VARCHAR(32)     NOT NULL,
    created_at  TIMESTAMP       NOT NULL
);