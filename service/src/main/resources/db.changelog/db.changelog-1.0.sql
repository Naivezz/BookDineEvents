--liquibase formatted sql

--changeset naivez:1
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    firstname VARCHAR(128) NOT NULL,
    lastname  VARCHAR(128) NOT NULL,
    email VARCHAR(128) UNIQUE NOT NULL,
    password VARCHAR(128) NOT NULL,
    phone_number VARCHAR(64) NOT NULL,
    role VARCHAR(32) NOT NULL,
    is_blacklisted BOOLEAN DEFAULT FALSE,
    blacklist_reason TEXT
);
--rollback DROP TABLE users;


--changeset naivez:2
CREATE TABLE restaurant (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(128) NOT NULL,
    address VARCHAR(256) NOT NULL,
    image VARCHAR(128),
    phone_number VARCHAR(64) NOT NULL
);
--rollback DROP TABLE restaurant;


--changeset naivez:3
CREATE TABLE reservation (
    id BIGSERIAL PRIMARY KEY,
    time TIMESTAMP NOT NULL,
    guests INTEGER NOT NULL,
    status VARCHAR(32) NOT NULL,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    restaurant_id BIGINT NOT NULL REFERENCES restaurant(id) ON DELETE CASCADE,
    amount DECIMAL(10, 2),
    payment_time TIMESTAMP,
    payment_status VARCHAR(32)
);
--rollback DROP TABLE reservation;


--changeset naivez:4
CREATE TABLE menu_item (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    image VARCHAR(128),
    description TEXT,
    restaurant_id BIGINT NOT NULL REFERENCES restaurant(id) ON DELETE CASCADE
);
--rollback DROP TABLE menu_item;


--changeset naivez:5
CREATE TABLE reservation_menu_item (
    id BIGSERIAL PRIMARY KEY,
    reservation_id BIGINT NOT NULL REFERENCES reservation(id) ON DELETE CASCADE,
    menu_item_id INT NOT NULL REFERENCES menu_item(id),
    quantity INT NOT NULL
);
--rollback DROP TABLE reservation_menu_item;


--changeset naivez:6
CREATE TABLE review (
    id SERIAL PRIMARY KEY,
    rating INTEGER CHECK (rating BETWEEN 1 AND 5),
    description TEXT,
    time TIMESTAMP NOT NULL,
    image VARCHAR(128),
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    restaurant_id BIGINT NOT NULL REFERENCES restaurant(id) ON DELETE CASCADE
);
--rollback DROP TABLE review;


--changeset naivez:7
CREATE TABLE event (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    time TIMESTAMP NOT NULL,
    restaurant_id BIGINT NOT NULL REFERENCES restaurant(id) ON DELETE CASCADE
);
--rollback DROP TABLE event;
