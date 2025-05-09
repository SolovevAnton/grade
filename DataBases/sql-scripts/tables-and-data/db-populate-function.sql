-- Function 1: Populate Students
--------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION populate_students_data()
    RETURNS VOID AS
$$
DECLARE
    -- Student data arrays
    first_names_arr     TEXT[] := ARRAY ['Alice', 'Bob', 'Charlie', 'David', 'Eve', 'Fiona', 'George', 'Hannah', 'Ian', 'Julia', 'Kyle', 'Liam', 'Mia', 'Noah', 'Olivia'];
    last_names_arr      TEXT[] := ARRAY ['Smith', 'Jones', 'Williams', 'Brown', 'Davis', 'Miller', 'Wilson', 'Moore', 'Taylor', 'Anderson', 'Thomas', 'Jackson'];
    specializations_arr TEXT[] := ARRAY ['Computer Science', 'Electrical Engineering', 'Mechanical Engineering', 'Civil Engineering', 'Biochemistry', 'Physics', 'Mathematics', 'Literature', 'History', 'Economics'];
    keywords_arr        TEXT[] := ARRAY ['programming', 'ai', 'robotics', 'data_analysis', 'web_dev', 'circuits', 'thermodynamics', 'structures', 'genetics', 'quantum', 'algebra', 'poetry', 'epochs', 'markets', 'research', 'innovation'];

    -- Loop counters and temp variables
    i                   INTEGER;
    phone_prefix_val    TEXT; -- Changed phone_first to phone_prefix_val for clarity
    rand_fn_idx         INTEGER;
    rand_ln_idx         INTEGER;
    rand_spec_idx       INTEGER;
    rand_keyword_count  INTEGER;
    temp_keywords       TEXT[];
BEGIN
    RAISE NOTICE 'Generating 100,000 students...';
    FOR i IN 1..100000
        LOOP
            rand_fn_idx := FLOOR(RANDOM() * ARRAY_LENGTH(first_names_arr, 1) + 1);
            rand_ln_idx := FLOOR(RANDOM() * ARRAY_LENGTH(last_names_arr, 1) + 1);
            rand_spec_idx := FLOOR(RANDOM() * ARRAY_LENGTH(specializations_arr, 1) + 1);

            -- Using your corrected phone number logic idea
            phone_prefix_val := 'P' || LPAD((FLOOR(i / 10000) + 100)::TEXT, 3, '0');

            -- Generate random profile keywords
            temp_keywords := ARRAY []::TEXT[];
            rand_keyword_count := FLOOR(RANDOM() * 4 + 1); -- 1 to 4 keywords
            FOR k_idx IN 1..rand_keyword_count
                LOOP
                    temp_keywords := ARRAY_APPEND(temp_keywords,
                                                  keywords_arr[FLOOR(RANDOM() * ARRAY_LENGTH(keywords_arr, 1) + 1)]);
                END LOOP;
            temp_keywords := ARRAY(SELECT DISTINCT e FROM UNNEST(temp_keywords) e); -- Ensure unique keywords

            INSERT INTO students (first_name, last_name, date_of_birth, phone_number, email, specialization,
                                  enrollment_date, profile_keywords, address_details, personal_statement)
            VALUES (first_names_arr[rand_fn_idx] || ' ' || i,
                    last_names_arr[rand_ln_idx],
                    (CURRENT_DATE - (FLOOR(RANDOM() * (25 - 18 + 1) + 18) * INTERVAL '1 year') -
                     (FLOOR(RANDOM() * 365) * INTERVAL '1 day')), -- Age 18-25
                    phone_prefix_val || '-' || LPAD(FLOOR(RANDOM() * 1000)::TEXT, 3, '0') || '-' ||
                    LPAD((i % 10000)::TEXT, 4, '0'), -- Unique phone logic
                    'student' || i || '@example.com', -- Unique email
                    specializations_arr[rand_spec_idx],
                    (CURRENT_DATE - (FLOOR(RANDOM() * 4) * INTERVAL '1 year') -
                     (FLOOR(RANDOM() * 365) * INTERVAL '1 day')), -- Enrolled in last 4 years
                    temp_keywords,
                    ('{"city": "City' || FLOOR(RANDOM() * 100) || '", "zip": "' || FLOOR(RANDOM() * 90000 + 10000) ||
                     '", "street": "Street ' || FLOOR(RANDOM() * 1000) || '"}')::jsonb,
                    'Personal statement for student ' || i ||
                    '. Lorem ipsum dolor sit amet, consectetur adipiscing elit.');

            IF i % 10000 = 0 THEN
                RAISE NOTICE '  Generated % students...', i;
            END IF;
        END LOOP;
    RAISE NOTICE 'Student generation complete.';
END;
$$ LANGUAGE plpgsql;

--------------------------------------------------------------------------------
-- Function 2: Populate Subjects
--------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION populate_subjects_data()
    RETURNS VOID AS
$$
DECLARE
    -- Subject data arrays
    keywords_arr         TEXT[] := ARRAY ['programming', 'ai', 'robotics', 'data_analysis', 'web_dev', 'circuits', 'thermodynamics', 'structures', 'genetics', 'quantum', 'algebra', 'poetry', 'epochs', 'markets', 'research', 'innovation']; -- Re-declared for this scope
    subject_prefixes     TEXT[] := ARRAY ['Introduction to', 'Advanced', 'Principles of', 'Applied', 'Theoretical', 'Experimental'];
    subject_topics       TEXT[] := ARRAY ['Computing', 'Electronics', 'Mechanics', 'Construction', 'Biology', 'Astrophysics', 'Statistics', 'Creative Writing', 'World History', 'Microeconomics', 'Algorithms', 'Databases'];
    lecturer_first_names TEXT[] := ARRAY ['Dr. Eleanor', 'Prof. Samuel', 'Dr. Beatrice', 'Prof. Arthur', 'Dr. Clara', 'Prof. Victor'];
    lecturer_last_names  TEXT[] := ARRAY ['Vance', 'Sterling', 'Hayes', 'Reed', 'Weiss', 'Monroe'];
    departments_arr      TEXT[] := ARRAY ['Science & Engineering', 'Humanities', 'Social Sciences', 'Arts'];

    -- Loop counters and temp variables
    i                    INTEGER;
    rand_fn_idx          INTEGER;
    rand_ln_idx          INTEGER;
    rand_dept_idx        INTEGER;
    rand_keyword_count   INTEGER;
    temp_outcomes        TEXT[];
BEGIN
    RAISE NOTICE 'Generating 1,000 subjects...';
    FOR i IN 1..1000
        LOOP
            rand_fn_idx := FLOOR(RANDOM() * ARRAY_LENGTH(lecturer_first_names, 1) + 1);
            rand_ln_idx := FLOOR(RANDOM() * ARRAY_LENGTH(lecturer_last_names, 1) + 1);
            rand_dept_idx := FLOOR(RANDOM() * ARRAY_LENGTH(departments_arr, 1) + 1);

            -- Generate random learning outcomes
            temp_outcomes := ARRAY []::TEXT[];
            rand_keyword_count := FLOOR(RANDOM() * 3 + 2); -- 2 to 4 outcomes
            FOR k_idx IN 1..rand_keyword_count
                LOOP
                    temp_outcomes := ARRAY_APPEND(temp_outcomes, 'Outcome ' ||
                                                                 keywords_arr[FLOOR(RANDOM() * ARRAY_LENGTH(keywords_arr, 1) + 1)] ||
                                                                 ' ' || k_idx);
                END LOOP;

            INSERT INTO subjects (subject_name, lecturer_first_name, lecturer_last_name, department,
                                  credits, description, learning_outcomes)
            VALUES (subject_prefixes[FLOOR(RANDOM() * ARRAY_LENGTH(subject_prefixes, 1) + 1)] || ' ' ||
                    subject_topics[FLOOR(RANDOM() * ARRAY_LENGTH(subject_topics, 1) + 1)] || ' ' ||
                    i, -- Semi-unique name
                    lecturer_first_names[rand_fn_idx],
                    lecturer_last_names[rand_ln_idx],
                    departments_arr[rand_dept_idx],
                    FLOOR(RANDOM() * 4 + 3)::INTEGER, -- Credits 3-6
                    'Description for subject ' || i ||
                    '. Covers fundamental concepts and advanced topics. Sed ut perspiciatis unde omnis iste natus error sit voluptatem.',
                    temp_outcomes);
            IF i % 100 = 0 THEN
                RAISE NOTICE '  Generated % subjects...', i;
            END IF;
        END LOOP;
    RAISE NOTICE 'Subject generation complete.';
END;
$$ LANGUAGE plpgsql;


--------------------------------------------------------------------------------
-- Function 3: Populate Exam Results
--------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION populate_exam_results_data()
    RETURNS VOID AS
$$
DECLARE
    i                  INTEGER;
    -- Removed max_student_id and max_subject_id as they are no longer used for random generation logic
    actual_student_ids INTEGER[];
    actual_subject_ids INTEGER[];
    student_id_count   INTEGER;
    subject_id_count   INTEGER;
    current_student_id INTEGER;
    current_subject_id INTEGER;
    random_date        DATE;
    random_grade       SMALLINT;
    random_point       point;
BEGIN
    RAISE NOTICE 'Fetching actual student and subject IDs for exam result generation...';

    SELECT ARRAY_AGG(id) INTO actual_student_ids FROM students;
    SELECT ARRAY_AGG(id) INTO actual_subject_ids FROM subjects;

    student_id_count := ARRAY_LENGTH(actual_student_ids, 1);
    subject_id_count := ARRAY_LENGTH(actual_subject_ids, 1);

    IF student_id_count IS NULL OR student_id_count = 0 THEN
        RAISE EXCEPTION 'No students found in the "students" table. Cannot generate exam results.';
    END IF;
    RAISE NOTICE 'Found % student IDs.', student_id_count;

    IF subject_id_count IS NULL OR subject_id_count = 0 THEN
        RAISE EXCEPTION 'No subjects found in the "subjects" table. Cannot generate exam results.';
    END IF;
    RAISE NOTICE 'Found % subject IDs.', subject_id_count;

    RAISE NOTICE 'Generating 1,000,000 exam results (this may take a while)...';
    FOR i IN 1..1000000
        LOOP
            -- Pick a random student_id and subject_id FROM THE FETCHED ARRAYS
            current_student_id := actual_student_ids[FLOOR(RANDOM() * student_id_count + 1)];
            current_subject_id := actual_subject_ids[FLOOR(RANDOM() * subject_id_count + 1)];

            -- Generate a random date within the last 2 years
            random_date := CURRENT_DATE - (FLOOR(RANDOM() * 730) * INTERVAL '1 day');

            -- Generate a random grade (1 to 5 as per updated schema for SMALLINT)
            random_grade := FLOOR(RANDOM() * 5 + 1)::SMALLINT;

            -- Generate a random point for exam_location
            random_point := POINT(RANDOM() * 180 - 90, RANDOM() * 360 - 180); -- Latitude (-90 to 90), Longitude (-180 to 180)

            INSERT INTO exam_results (student_id, subject_id, exam_date, grade, exam_location, notes)
            VALUES (current_student_id,
                    current_subject_id,
                    random_date,
                    random_grade,
                    random_point,
                    'Exam notes for result ' || i || '. Performance was satisfactory/needs improvement.')
            ON CONFLICT (student_id, subject_id, exam_date) DO NOTHING; -- Important for handling unique constraint

            IF i % 50000 = 0 THEN
                RAISE NOTICE '  Attempted to generate % exam results...', i;
            END IF;
        END LOOP;
    RAISE NOTICE 'Exam result generation attempt complete.';
END;
$$ LANGUAGE plpgsql;

SELECT populate_students_data();

SELECT populate_subjects_data();

SELECT populate_exam_results_data();