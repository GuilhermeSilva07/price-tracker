CREATE TABLE products (
    id UUID PRIMARY KEY,
    url VARCHAR(2048) NOT NULL,
    external_id VARCHAR(255) NOT NULL,
    target_price NUMERIC(12, 2) NOT NULL,
    current_price NUMERIC(12, 2),
    user_id UUID NOT NULL REFERENCES users (id)
);

CREATE INDEX idx_products_user_id ON products (user_id);
