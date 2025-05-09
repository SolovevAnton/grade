SELECT i.relname                                        "Table Name",
       indexrelname                                     "Index Name",
       PG_SIZE_PRETTY(PG_TOTAL_RELATION_SIZE(relid)) AS "Total Size",
       PG_SIZE_PRETTY(PG_INDEXES_SIZE(relid))        AS "Total Size of all Indexes",
       PG_SIZE_PRETTY(PG_RELATION_SIZE(relid))       AS "Table Size",
       PG_SIZE_PRETTY(PG_RELATION_SIZE(indexrelid))     "Index Size",
       reltuples::BIGINT                                "Estimated table row count"
FROM pg_stat_all_indexes i
         JOIN pg_class c ON i.relid = c.oid
WHERE i.relname IN ('students', 'subjects', 'exam_results')
ORDER BY "Table Name";