EXPLAIN ANALYSE
SELECT *
FROM exam_results
ORDER BY exam_results.exam_location <-> POINT(100, 100)
LIMIT 1000;