CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    firstname VARCHAR(128) NOT NULL,
    lastname  VARCHAR(128) NOT NULL,
    email VARCHAR(128) UNIQUE NOT NULL,
    password VARCHAR(128) NOT NULL,
    phone_number VARCHAR(64) NOT NULL,
    role VARCHAR(32)
);

CREATE TABLE restaurants (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(128) NOT NULL,
    address VARCHAR(256) NOT NULL,
    phone_number VARCHAR(64) NOT NULL
);

CREATE TABLE spots (
    id SERIAL PRIMARY KEY,
    table_number INT NOT NULL,
    seats INTEGER NOT NULL,
    restaurant_id BIGINT REFERENCES restaurants(id) ON DELETE CASCADE
);

CREATE TABLE reservations (
    id BIGSERIAL PRIMARY KEY,
    reservation_time TIMESTAMP NOT NULL,
    guests INTEGER NOT NULL,
    reservation_status VARCHAR(32),
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    spot_id INT REFERENCES spots(id) ON DELETE CASCADE
);

CREATE TABLE payments (
    id BIGSERIAL PRIMARY KEY,
    reservation_id BIGINT REFERENCES reservations(id) ON DELETE CASCADE,
    amount DECIMAL(10, 2),
    payment_date TIMESTAMP NOT NULL,
    payment_status VARCHAR(32)
);

CREATE TABLE menu_items (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    description TEXT,
    restaurant_id BIGINT REFERENCES restaurants(id) ON DELETE CASCADE
);

CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    reservation_id BIGINT REFERENCES reservations(id) ON DELETE CASCADE,
    order_time TIMESTAMP NOT NULL,
    order_status VARCHAR(32),
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    restaurant_id BIGINT REFERENCES restaurants(id) ON DELETE CASCADE
);

CREATE TABLE order_items (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT REFERENCES orders(id) ON DELETE CASCADE,
    menu_item_id INT REFERENCES menu_items(id),
    quantity INT NOT NULL
);

CREATE TABLE reviews (
    id SERIAL PRIMARY KEY,
    rating INTEGER CHECK (rating BETWEEN 1 AND 5),
    description TEXT,
    review_date TIMESTAMP NOT NULL,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    restaurant_id BIGINT REFERENCES restaurants(id) ON DELETE CASCADE
);

CREATE TABLE events (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    event_date TIMESTAMP NOT NULL,
    restaurant_id BIGINT REFERENCES restaurants(id) ON DELETE CASCADE
);

CREATE TABLE blacklists (
    id BIGSERIAL PRIMARY KEY,
    reason TEXT,
    addition_date TIMESTAMP NOT NULL,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    restaurant_id BIGINT REFERENCES restaurants(id) ON DELETE CASCADE
);