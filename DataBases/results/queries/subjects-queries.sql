EXPLAIN ANALYSE
SELECT *
FROM subjects sb
WHERE sb.department = 'Arts';

EXPLAIN ANALYSE
SELECT *
FROM subjects sb
WHERE sb.department ILIKE 'A%';