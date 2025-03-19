/*DROP TABLE IF EXISTS cards;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;*/


INSERT INTO users (login, name, password, registration_date)
VALUES ('1', 'first',
        '$100801$MQ==$seWMrv4cuFwYE+SVDl3ru/fbr9U0Ct8R5ZwW6O+MCYFWgTfrkQgUjrnk1tfR3Dj8AshTYkBJzAMFJWcNiq8Ryg==', NOW()),
       ('2', 'second',
        '$100801$Mg==$AeTcOMuY5rkyvW2VY5+lIDAzk7ecc81nEIAg4P7JY9Vjdi4iQL9Xep6dLPmgzP58h0n9VMHMcBrDqElFTwyD8A==', NOW()),
       ('3', 'third',
        '$100801$Mw==$siZr7COdxYj2w3XCvvHkBswpim+qQVNew/Jrubyi/omn89Dn6r1+UndnYnCLJxli3+3cQyYMlLH46j3FTXo5tA==', NOW());

INSERT INTO categories (name, user_id)
VALUES ('firstCat', 1),
       ('secondCat', 1),
       ('thirdCat', 2);

INSERT INTO cards (question, answer, category_id, creation_date)
VALUES ('Q1', 'A1', 1, NOW()), -- Assuming 'Q1' and 'A1' are linked to the category with ID 1
       ('Q2', 'A2', 1, NOW()), -- Assuming 'Q2' and 'A2' are also linked to the category with ID 1
       ('Q3', 'A3', 2, NOW()),
       ('Q4', 'A4', 3, NOW());


