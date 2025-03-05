INSERT INTO users (email, name, surname)
VALUES ('dennis@paddyspub.com', 'Dennis', 'Reynolds'),
       ('diandra@paddyspub.com', 'D', 'Reynolds'),
       ('charlie@paddyspub.com', 'Charlie', 'Kelly');

-- Insert tasks
INSERT INTO tasks (name, description, created_date, deadline)
VALUES ('Task 1', 'Description for Task 1', NOW(), DATEADD('DAY', 7, NOW())),
       ('Task 2', 'Description for Task 2', NOW(), DATEADD('DAY', 14, NOW())),
       ('Task 3', 'Description for Task 3', NOW(), DATEADD('DAY', 21, NOW()));

-- Assign tasks to users

INSERT INTO users_tasks (tasks_id, user_id)
VALUES (1, 1),
       (2, 1),
       (3, 2);

-- Charlie has 0 tasks (no entry in users_tasks)