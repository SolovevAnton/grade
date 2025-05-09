-- Description: Adds all constraints and sequences to the tables.

-- === Constraints for 'students' table ===

-- Sequence and Default for student_id (to mimic SERIAL)
CREATE SEQUENCE students_student_id_seq;
ALTER TABLE students
    ALTER COLUMN id SET DEFAULT nextval('students_student_id_seq');
ALTER SEQUENCE students_student_id_seq OWNED BY students.id;

-- Primary Key
ALTER TABLE students
    ADD CONSTRAINT pk_students PRIMARY KEY (id);

-- NOT NULL constraints
ALTER TABLE students
    ALTER COLUMN first_name SET NOT NULL,
    ALTER COLUMN last_name SET NOT NULL,
    ALTER COLUMN date_of_birth SET NOT NULL,
    ALTER COLUMN email SET NOT NULL;

-- UNIQUE constraints
ALTER TABLE students
    ADD CONSTRAINT uq_students_phone_number UNIQUE (phone_number),
    ADD CONSTRAINT uq_students_email UNIQUE (email);

-- DEFAULT constraints for timestamps and enrollment_date
ALTER TABLE students
    ALTER COLUMN enrollment_date SET DEFAULT CURRENT_DATE,
    ALTER COLUMN created_at SET DEFAULT NOW(),
    ALTER COLUMN updated_at SET DEFAULT NOW();

-- === Constraints for 'subjects' table ===

-- Sequence and Default for subject_id (to mimic SERIAL)
CREATE SEQUENCE subjects_subject_id_seq;
ALTER TABLE subjects
    ALTER COLUMN id SET DEFAULT nextval('subjects_subject_id_seq');
ALTER SEQUENCE subjects_subject_id_seq OWNED BY subjects.id;

-- Primary Key
ALTER TABLE subjects
    ADD CONSTRAINT pk_subjects PRIMARY KEY (id);

-- NOT NULL constraints
ALTER TABLE subjects
    ALTER COLUMN subject_name SET NOT NULL;

-- UNIQUE constraints
ALTER TABLE subjects
    ADD CONSTRAINT uq_subjects_subject_name UNIQUE (subject_name);

-- CHECK constraints
ALTER TABLE subjects
    ADD CONSTRAINT chk_subjects_credits CHECK (credits > 0);

-- DEFAULT constraints for timestamps
ALTER TABLE subjects
    ALTER COLUMN created_at SET DEFAULT NOW(),
    ALTER COLUMN updated_at SET DEFAULT NOW();


-- === Constraints for 'exam_results' table ===

-- Sequence and Default for exam_id (to mimic SERIAL)
CREATE SEQUENCE exam_results_exam_id_seq;
ALTER TABLE exam_results
    ALTER COLUMN id SET DEFAULT nextval('exam_results_exam_id_seq');
ALTER SEQUENCE exam_results_exam_id_seq OWNED BY exam_results.id;

-- Primary Key
ALTER TABLE exam_results
    ADD CONSTRAINT pk_exam_results PRIMARY KEY (id);

-- NOT NULL constraints
ALTER TABLE exam_results
    ALTER COLUMN student_id SET NOT NULL,
    ALTER COLUMN subject_id SET NOT NULL,
    ALTER COLUMN exam_date SET NOT NULL;

-- Foreign Key constraints
ALTER TABLE exam_results
    ADD CONSTRAINT fk_exam_results_student
        FOREIGN KEY (student_id) REFERENCES students (id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_exam_results_subject
        FOREIGN KEY (subject_id) REFERENCES subjects (id) ON DELETE RESTRICT;

-- CHECK constraints
ALTER TABLE exam_results
    ADD CONSTRAINT chk_exam_results_grade CHECK (grade > 0 AND grade <= 5);

-- UNIQUE constraint (composite)
ALTER TABLE exam_results
    ADD CONSTRAINT uq_exam_results_student_subject_date UNIQUE (student_id, subject_id, exam_date);

-- DEFAULT constraints for timestamps
ALTER TABLE exam_results
    ALTER COLUMN created_at SET DEFAULT NOW(),
    ALTER COLUMN updated_at SET DEFAULT NOW();
