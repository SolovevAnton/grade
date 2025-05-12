--test
UPDATE students
SET profile_keywords = profile_keywords || ARRAY ['added']
WHERE first_name LIKE 'Eve%';


--extras for analyse
EXPLAIN ANALYZE
SELECT *
FROM students s
WHERE s.last_name = 'Moore';

EXPLAIN ANALYZE
SELECT *
FROM students s
WHERE s.last_name ILIKE 'M%';


EXPLAIN ANALYSE
SELECT *
FROM students
WHERE address_details @> '{"city": "City4"}';