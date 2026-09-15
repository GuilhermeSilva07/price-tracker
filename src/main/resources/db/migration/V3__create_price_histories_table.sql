CREATE TABLE price_histories (
    id UUID PRIMARY KEY,
    product_id UUID NOT NULL REFERENCES products (id),
    price NUMERIC(12, 2) NOT NULL,
    checked_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_price_histories_product_id ON price_histories (product_id);
