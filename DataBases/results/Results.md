## Schema creation

ERD:

![ERD.png](ERD.png)

Tested data created with [db-populate-function.sql](../sql-scripts/tables-and-data/db-populate-function.sql)

## Indexes analysis

### B-tree

**No index**

```sql
EXPLAIN ANALYZE
SELECT *
FROM students s
WHERE s.last_name = 'Moore';

--output
Seq Scan ON students s  (COST=0.00..5595.00 ROWS=8350 width=309) (actual TIME=0.048..21.860 ROWS=8382 loops=1)
  FILTER: ((last_name)::TEXT = 'Moore'::TEXT)
  ROWS Removed BY FILTER: 91618
Planning TIME: 0.060 MS --PostgreSQL took to plan the query — quite fast
Execution TIME: 22.094 MS --start to finish, including scanning, filtering, and returning rows
```

**With index**

```sql
CREATE INDEX idx_students_last_name ON students (last_name);

EXPLAIN ANALYZE
SELECT *
FROM students s
WHERE s.last_name = 'Moore';

--out
Bitmap Heap Scan ON students s  (COST=97.01..4587.46 ROWS=8350 width=309) (actual TIME=1.207..4.137 ROWS=8382 loops=1)
  RECHECK Cond: ((last_name)::TEXT = 'Moore'::TEXT)
  Heap Blocks: exact=3783
  ->  Bitmap INDEX Scan ON idx_students_last_name  (COST=0.00..94.92 ROWS=8350 width=0) (actual TIME=0.934..0.935 ROWS=8382 loops=1)
        INDEX Cond: ((last_name)::TEXT = 'Moore'::TEXT)
Planning TIME: 0.324 ms
Execution TIME: 4.394 ms
```

x5 time improvement!

### Hash index

**Without index equals**

```sql
EXPLAIN ANALYZE
SELECT *
FROM students s
WHERE s.last_name = 'Moore';

--out
Seq Scan ON students s  (COST=0.00..5595.00 ROWS=8350 width=309) (actual TIME=0.071..17.256 ROWS=8382 loops=1)
  FILTER: ((last_name)::TEXT = 'Moore'::TEXT)
  ROWS Removed BY FILTER: 91618
Planning TIME: 0.353 MS
Execution TIME: 17.490 MS
```

**Without index like**

```sql
EXPLAIN ANALYZE
SELECT *
FROM students s
WHERE s.last_name ILIKE 'M%';

--out
Seq Scan ON students s  (COST=0.00..5595.00 ROWS=16670 width=309) (actual TIME=0.017..40.386 ROWS=16605 loops=1)
  FILTER: ((last_name)::TEXT ~~* 'M%'::TEXT)
  ROWS Removed BY FILTER: 83395
Planning TIME: 0.257 MS
Execution TIME: 40.765 MS
```

**With hash**

**Equals**

```sql
EXPLAIN ANALYZE
SELECT *
FROM students s
WHERE s.last_name = 'Moore';

--out
Bitmap Heap Scan ON students s  (COST=316.71..4807.17 ROWS=8350 width=309) (actual TIME=0.974..5.334 ROWS=8382 loops=1)
  RECHECK Cond: ((last_name)::TEXT = 'Moore'::TEXT)
  Heap Blocks: exact=3783
  ->  Bitmap INDEX Scan ON idx_students_last_name  (COST=0.00..314.62 ROWS=8350 width=0) (actual TIME=0.702..0.703 ROWS=8382 loops=1)
        INDEX Cond: ((last_name)::TEXT = 'Moore'::TEXT)
Planning TIME: 0.113 ms
Execution TIME: 5.654 ms
```

**Like**
*Index is not used, even its avaliable*

```sql
EXPLAIN ANALYZE
SELECT *
FROM students s
WHERE s.last_name ILIKE 'M%';

--out
Seq Scan ON students s  (COST=0.00..5595.00 ROWS=16670 width=309) (actual TIME=0.016..41.654 ROWS=16605 loops=1)
  FILTER: ((last_name)::TEXT ~~* 'M%'::TEXT)
  ROWS Removed BY FILTER: 83395
Planning TIME: 0.305 MS
Execution TIME: 42.032 MS
```

### GIN

**no index**

```sql
EXPLAIN ANALYSE
SELECT *
FROM students
WHERE address_details @> '{"city": "City4"}';

--out
Seq Scan ON students  (COST=0.00..5595.00 ROWS=1010 width=309) (actual TIME=0.047..24.354 ROWS=999 loops=1)
"  Filter: (address_details @> '{""city"": ""City4""}'::jsonb)"
  ROWS Removed BY FILTER: 99001
Planning TIME: 0.125 MS
Execution TIME: 24.400 MS
```

**with gin**

```sql
EXPLAIN ANALYSE
SELECT *
FROM students
WHERE address_details @> '{"city": "City4"}';

--out
Bitmap Heap Scan ON students  (COST=35.83..2429.37 ROWS=1010 width=309) (actual TIME=0.517..1.471 ROWS=999 loops=1)
"  Recheck Cond: (address_details @> '{""city"": ""City4""}'::jsonb)"
  Heap Blocks: exact=900
  ->  Bitmap INDEX Scan ON idx_students_address_details  (COST=0.00..35.58 ROWS=1010 width=0) (actual TIME=0.461..0.461 ROWS=999 loops=1)
"        Index Cond: (address_details @> '{""city"": ""City4""}'::jsonb)"
Planning TIME: 0.148 ms
Execution TIME: 1.510 MS
```

### GIST

```sql
CREATE TABLE exam_results
(
    id            INTEGER,  -- Will be made SERIAL via sequence and default + PK in constraints  
    student_id    INTEGER,
    subject_id    INTEGER,
    exam_date     DATE,
    grade         SMALLINT, -- e.g., 1,2..5  
    exam_location point,    -- For GiST index  
    notes         TEXT,     -- For general notes or FTS  
    created_at    timestamptz,
    updated_at    timestamptz
);
```

```sql
EXPLAIN ANALYSE
SELECT *
FROM exam_results
ORDER BY exam_results.exam_location <-> POINT(100, 100)
LIMIT 1000;
--out
LIMIT
    (COST=49462.43..49579.11 ROWS =1000 width=135)
    (actual TIME=121.134..123.821 ROWS=1000 loops=1)
  ->  Gather MERGE  (COST=49462.43..146690.35 ROWS=833324 width=135) (actual TIME=121.131..123.774 ROWS=1000 loops=1)
        Workers Planned: 2
        Workers Launched: 2
        ->  Sort  (COST=48462.41..49504.06 ROWS=416662 width=135) (actual TIME=115.972..116.101 ROWS=517 loops=3)
"              Sort Key: ((exam_location <-> '(100,100)'::point))"
              Sort METHOD: top-N heapsort  Memory: 490kB
              Worker 0:  Sort METHOD: top-N heapsort  Memory: 493kB
              Worker 1:  Sort METHOD: top-N heapsort  Memory: 499kB
              ->  PARALLEL Seq Scan ON exam_results  (COST=0.00..25617.28 ROWS=416662 width=135) (actual TIME=0.033..71.588 ROWS=333330 loops=3)
Planning TIME: 0.102 ms
Execution TIME: 123.906 ms
```

**with index**

```sql
EXPLAIN ANALYSE
SELECT *
FROM exam_results
ORDER BY exam_results.exam_location <-> POINT(100, 100)
LIMIT 1000;

--out
LIMIT
    (COST=0.29..138.34 ROWS =1000 width=135)
    (actual TIME=0.155..18.196 ROWS=1000 loops=1)
  ->  INDEX Scan USING idx_exam_location_gist ON exam_results  (COST=0.29..138052.09 ROWS=999990 width=135) (actual TIME=0.154..18.063 ROWS=1000 loops=1)
"        Order By: (exam_location <-> '(100,100)'::point)"
Planning TIME: 0.131 ms
Execution TIME: 18.345 MS
```

### Indexes sizes

| Table        | Index Name                           | Total Size | Total Index Size for all indexes | Table Size | Index Size | Row Estimate | Index type |
|--------------|--------------------------------------|------------|----------------------------------|------------|------------|--------------|------------|
| exam_results | idx_exam_location_gist               | 291 MB     | 131 MB                           | 159 MB     | 71 MB      | 999990       | gist       |
| exam_results | pk_exam_results                      | 291 MB     | 131 MB                           | 159 MB     | 21 MB      | 999990       |            |
| exam_results | uq_exam_results_student_subject_date | 291 MB     | 131 MB                           | 159 MB     | 39 MB      | 999990       |            |
| students     | idx_students_first_name              | 78 MB      | 44 MB                            | 34 MB      | 3096 kB    | 100000       | B-Tree     |
| students     | idx_students_last_name               | 78 MB      | 44 MB                            | 34 MB      | 6032 kB    | 100000       | hash       |
| students     | uq_students_phone_number             | 78 MB      | 44 MB                            | 34 MB      | 9216 kB    | 100000       |            |
| students     | uq_students_email                    | 78 MB      | 44 MB                            | 34 MB      | 16 MB      | 100000       |            |
| students     | idx_students_address_details         | 78 MB      | 44 MB                            | 34 MB      | 5048 kB    | 100000       | gin        |
| students     | pk_students                          | 78 MB      | 44 MB                            | 34 MB      | 4392 kB    | 100000       |            |
| subjects     | idx_subjects_department              | 696 kB     | 304 kB                           | 360 kB     | 56 kB      | 1000         | hash       |
| subjects     | uq_subjects_subject_name             | 696 kB     | 304 kB                           | 360 kB     | 144 kB     | 1000         |            |
| subjects     | idx_subjects_lecturer_last_name      | 696 kB     | 304 kB                           | 360 kB     | 48 kB      | 1000         | hash       |
| subjects     | pk_subjects                          | 696 kB     | 304 kB                           | 360 kB     | 56 kB      | 1000         |            |

### Inserting operations with indexes

table exam_results - starts on empty table 1 ml rows with ` populate_exam_results_data()` function

**without index**

```log
Fetching actual student and subject IDs for exam result generation...
Found 100000 student IDs.
Found 1000 subject IDs.
Generating 1,000,000 exam results (this may take a while)...

[2025-05-09 20:35:02] 1 row retrieved starting from 1 in 26 s 934 ms (execution: 26 s 733 ms, fetching: 201 ms)
```

**with B-tree on notes column**

```log
[2025-05-09 20:42:04] 1 row retrieved starting from 1 in 33 s 210 ms (execution: 33 s 170 ms, fetching: 40 ms)
```

**with gist on exam_location**

```log
[2025-05-09 20:44:23] 1 row retrieved starting from 1 in 30 s 383 ms (execution: 30 s 290 ms, fetching: 93 ms)
```

**with gist and b-tree**

```log
[2025-05-09 20:45:43] 1 row retrieved starting from 1 in 42 s 682 ms (execution: 42 s 615 ms, fetching: 67 ms)
```

## Queries

tasks 1-4 can be found: [students-queries.sql](queries/students-queries.sql)
