CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       firstname VARCHAR(128) NOT NULL,
                       lastname  VARCHAR(128) NOT NULL,
                       email VARCHAR(128) UNIQUE NOT NULL,
                       password VARCHAR(128) NOT NULL,
                       phone_number VARCHAR(64) NOT NULL,
                       role VARCHAR(32) NOT NULL
);

CREATE TABLE restaurant (
                            id BIGSERIAL PRIMARY KEY,
                            name VARCHAR(128) NOT NULL,
                            address VARCHAR(256) NOT NULL,
                            image VARCHAR(128),
                            phone_number VARCHAR(64) NOT NULL
);

CREATE TABLE spot (
                      id SERIAL PRIMARY KEY,
                      table_number INT NOT NULL,
                      seats INTEGER NOT NULL,
                      restaurant_id BIGINT NOT NULL REFERENCES restaurant(id) ON DELETE CASCADE
);

CREATE TABLE reservation (
                             id BIGSERIAL PRIMARY KEY,
                             time TIMESTAMP NOT NULL,
                             guests INTEGER NOT NULL,
                             status VARCHAR(32) NOT NULL ,
                             user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                             spot_id INT NOT NULL REFERENCES spot(id) ON DELETE CASCADE
);

CREATE TABLE payment (
                         id BIGSERIAL PRIMARY KEY,
                         reservation_id BIGINT NOT NULL UNIQUE REFERENCES reservation(id) ON DELETE CASCADE,
                         amount DECIMAL(10, 2),
                         date TIMESTAMP NOT NULL,
                         status VARCHAR(32) NOT NULL
);

CREATE TABLE menu_item (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(100) NOT NULL,
                           price DECIMAL(10, 2) NOT NULL,
                           image VARCHAR(128),
                           description TEXT,
                           restaurant_id BIGINT NOT NULL REFERENCES restaurant(id) ON DELETE CASCADE
);

CREATE TABLE orders (
                        id BIGSERIAL PRIMARY KEY,
                        reservation_id BIGINT NOT NULL UNIQUE REFERENCES reservation(id) ON DELETE CASCADE,
                        time TIMESTAMP NOT NULL,
                        status VARCHAR(32) NOT NULL,
                        user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                        restaurant_id BIGINT NOT NULL REFERENCES restaurant(id) ON DELETE CASCADE
);

CREATE TABLE order_menu_item (
                                 id BIGSERIAL PRIMARY KEY,
                                 order_id BIGINT NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
                                 menu_item_id INT NOT NULL REFERENCES menu_item(id),
                                 quantity INT NOT NULL
);

CREATE TABLE review (
                        id SERIAL PRIMARY KEY,
                        rating INTEGER CHECK (rating BETWEEN 1 AND 5),
                        description TEXT,
                        date TIMESTAMP NOT NULL,
                        image VARCHAR(128),
                        user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                        restaurant_id BIGINT NOT NULL REFERENCES restaurant(id) ON DELETE CASCADE
);

CREATE TABLE event (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       description TEXT,
                       date TIMESTAMP NOT NULL,
                       restaurant_id BIGINT NOT NULL REFERENCES restaurant(id) ON DELETE CASCADE
);

CREATE TABLE blacklist (
                           id BIGSERIAL PRIMARY KEY,
                           reason TEXT,
                           date TIMESTAMP NOT NULL,
                           user_id BIGINT NOT NULL REFERENCES  users(id) ON DELETE CASCADE,
                           restaurant_id BIGINT NOT NULL REFERENCES restaurant(id) ON DELETE CASCADE
);