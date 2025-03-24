CREATE TABLE
    pass_hashes
(
    id          SERIAL PRIMARY KEY,
    salt        INT,
    hash_result BIGINT
);

CREATE TABLE
    users
(
    id           SERIAL PRIMARY KEY,
    login        VARCHAR(50) NOT NULL UNIQUE,
    pass_hash_id INT,
    is_blocked   BOOLEAN,
    role         VARCHAR(10),
    email        VARCHAR(50) UNIQUE,
    tel          VARCHAR(15),
    CONSTRAINT fk_passhash FOREIGN KEY (pass_hash_id) REFERENCES pass_hashes (id)
);

CREATE TABLE
    orders
(
    id            SERIAL PRIMARY KEY,
    user_id       INT,
    creation_time TIMESTAMP,
    status        VARCHAR(10),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE
    brands
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR UNIQUE
);

CREATE TABLE
    products
(
    id       SERIAL PRIMARY KEY,
    model    VARCHAR(25),
    price    DECIMAL(6, 2),
    size     SMALLINT,
    brand_id INT,
    year     SMALLINT,
    CONSTRAINT fk_brand FOREIGN KEY (brand_id) REFERENCES brands (id),
    UNIQUE (model, size, brand_id, year)
);

CREATE TABLE
    orderpositions
(
    id         SERIAL PRIMARY KEY,
    order_id   INT,
    product_id INT,
    quantity   SMALLINT,
    CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES orders (id),
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES products (id)
);