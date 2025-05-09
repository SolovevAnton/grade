EXPLAIN ANALYZE
SELECT *
FROM students s
WHERE s.last_name = 'Moore';

EXPLAIN ANALYZE
SELECT *
FROM students s
WHERE s.last_name ILIKE 'M%';



