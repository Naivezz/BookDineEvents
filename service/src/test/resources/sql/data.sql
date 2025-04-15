DELETE FROM reservation_menu_item;
DELETE FROM review;
DELETE FROM event;
DELETE FROM reservation;
DELETE FROM menu_item;
DELETE FROM restaurant;
DELETE FROM users;

INSERT INTO users (id, firstname, lastname, email, password, phone_number, role, is_blacklisted, blacklist_reason)
VALUES
    (1, 'John', 'Doe', 'john.doe@example.com', 'password123', '123-456-7890', 'USER', FALSE, NULL),
    (2, 'Jane', 'Smith', 'jane.smith@example.com', 'password123', '123-456-7891', 'ADMIN', FALSE, NULL),
    (3, 'Alice', 'Johnson', 'alice.johnson@example.com', 'password123', '123-456-7892', 'USER', TRUE, 'Spamming'),
    (4, 'Bob', 'Brown', 'bob.brown@example.com', 'password123', '123-456-7893', 'USER', FALSE, NULL);

INSERT INTO restaurant (id, name, address, image, phone_number)
VALUES
    (1, 'Pasta Place', '123 Pasta St, Food City', 'pasta_place.jpg', '555-123-4567'),
    (2, 'Burger King', '456 Burger Ave, Food City', 'burger_king.jpg', '555-234-5678');

INSERT INTO menu_item (id, name, price, image, description, restaurant_id)
VALUES
    (1, 'Spaghetti Bolognese', 12.99, 'spaghetti_bolognese.jpg', 'Classic spaghetti with meat sauce', 1),
    (2, 'Cheeseburger', 9.99, 'cheeseburger.jpg', 'Juicy cheeseburger with lettuce, tomato, and cheese', 2),
    (3, 'Caesar Salad', 7.99, 'caesar_salad.jpg', 'Fresh Caesar salad with dressing', 1);

INSERT INTO reservation (id, time, guests, status, user_id, restaurant_id, amount, payment_time, payment_status)
VALUES
    (1, '2025-04-20 19:00:00', 2, 'CONFIRMED', 1, 1, 50.00, '2025-04-20 19:05:00', 'REJECTED'),
    (2, '2025-04-21 20:00:00', 4, 'PENDING', 2, 2, NULL, NULL, 'PENDING'),
    (3, '2025-04-22 18:30:00', 3, 'CANCELLED', 3, 1, 75.00, '2025-04-22 18:35:00', 'COMPLETED');

INSERT INTO reservation_menu_item (reservation_id, menu_item_id, quantity)
VALUES
    (1, 1, 2),
    (1, 2, 1),
    (2, 3, 3);

INSERT INTO review (rating, description, time, image, user_id, restaurant_id)
VALUES
    (5, 'Great food and service!', '2025-04-15 18:00:00', 'review_1.jpg', 1, 1),
    (4, 'Good, but the pasta was a bit overcooked.', '2025-04-16 19:00:00', 'review_2.jpg', 2, 1),
    (2, 'Not great. The burger was cold.', '2025-04-17 20:00:00', 'review_3.jpg', 3, 2);

INSERT INTO event (name, description, time, restaurant_id)
VALUES
    ('Pasta Night', 'All-you-can-eat pasta night', '2025-04-25 18:00:00', 1),
    ('Burger Special', 'Special discount on all burgers', '2025-04-26 19:00:00', 2);


SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));
SELECT setval('restaurant_id_seq', (SELECT MAX(id) FROM restaurant));
SELECT setval('menu_item_id_seq', (SELECT MAX(id) FROM menu_item));
