create table users (
    user_id       VARCHAR(36) PRIMARY KEY,
    first_name    VARCHAR(191) NOT NULL COMMENT 'encrypted',
    last_name     VARCHAR(191) NOT NULL COMMENT 'encrypted',
    zip_code      VARCHAR(191) NOT NULL COMMENT 'encrypted',
    ship_address  VARCHAR(191) NOT NULL COMMENT 'encrypted',
    mail_address  VARCHAR(191) NOT NULL COMMENT 'encrypted',
    registered_at DATETIME     NOT NULL,
    deleted_at    DATETIME
);

create table products (
    product_id    VARCHAR(36) PRIMARY KEY,
    product_name  VARCHAR(36) NOT NULL,
    registered_at DATETIME    NOT NULL
);

create table product_stocks (
    product_id VARCHAR(36) PRIMARY KEY REFERENCES products (product_id),
    quantity   INT NOT NULL
);

create table product_prices (
    price_id      VARCHAR(36) PRIMARY KEY,
    product_id    VARCHAR(36) NOT NULL REFERENCES products (product_id),
    price         INT         NOT NULL,
    apply_start   DATETIME    NOT NULL,
    apply_end     DATETIME,
    registered_at DATETIME    NOT NULL
);

create table orders (
    order_id    VARCHAR(36) PRIMARY KEY,
    user_id     VARCHAR(36) NOT NULL REFERENCES users (user_id),
    product_id  VARCHAR(36) NOT NULL REFERENCES products (product_id),
    accepted_at DATETIME    NOT NULL
);

create table order_shipment_requests (
    shipment_id  VARCHAR(36) PRIMARY KEY,
    order_id     VARCHAR(36) NOT NULL REFERENCES orders (order_id),
    requested_at DATETIME    NOT NULL
);

create table order_shipment_results (
    shipment_id VARCHAR(36) PRIMARY KEY REFERENCES order_shipment_requests (shipment_id),
    shipped_at  DATETIME NOT NULL
);

create table order_arrival_results (
    shipment_id VARCHAR(36) PRIMARY KEY REFERENCES order_shipment_requests (shipment_id),
    arrived_at  DATETIME NOT NULL
);

create table order_takeback_results (
    shipment_id   VARCHAR(36) PRIMARY KEY REFERENCES order_shipment_requests (shipment_id),
    takebacked_at DATETIME NOT NULL
);