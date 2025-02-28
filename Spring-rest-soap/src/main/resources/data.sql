INSERT INTO users (email, name, surname)
VALUES ('alice@example.com', 'Alice', 'Johnson'),
       ('bob@example.com', 'Bob', 'Smith'),
       ('charlie@example.com', 'Charlie', 'Brown');

-- Insert tasks
INSERT INTO tasks (name, description, created_date, deadline)
VALUES ('Task 1', 'Description for Task 1', NOW(), DATEADD('DAY', 7, NOW())),
       ('Task 2', 'Description for Task 2', NOW(), DATEADD('DAY', 14, NOW())),
       ('Task 3', 'Description for Task 3', NOW(), DATEADD('DAY', 21, NOW()));

-- Assign tasks to users
-- Alice has 2 tasks
INSERT INTO users_tasks (tasks_id, user_id)
VALUES ((SELECT id FROM tasks WHERE name = 'Task 1'), (SELECT id FROM users WHERE email = 'alice@example.com')),
       ((SELECT id FROM tasks WHERE name = 'Task 2'), (SELECT id FROM users WHERE email = 'alice@example.com')),

-- Bob has 1 task
       ((SELECT id FROM tasks WHERE name = 'Task 3'), (SELECT id FROM users WHERE email = 'bob@example.com'));

-- Charlie has 0 tasks (no entry in users_tasks)