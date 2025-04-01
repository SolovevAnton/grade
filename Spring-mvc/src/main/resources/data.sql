INSERT INTO products
    (model, price, size, production_year, brand, path_to_image)
VALUES ('BANDIT XV', 1000.0, 7, 2022, 'FONE', 'image/bandit-2022.png'),
       ('BANDIT XVI', 1000.1, 8, 2023, 'FONE', 'image/bandit-2023.png'),
       ('XR', 1500, 12, 2021, 'CORE', 'image/XR-2021.png');

INSERT INTO orders (user_id, status, created, updated)
VALUES (1, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO order_positions (order_id, product_id, quantity, created, updated)
VALUES (1, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (1, 2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (2, 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (3, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);