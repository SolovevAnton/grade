-- Description: Defines the basic structure of tables without constraints.

-- 1. Students Table
CREATE TABLE students
(
    id                 INTEGER, -- Will be made SERIAL via sequence and default + PK in constraints
    first_name         VARCHAR(100),
    last_name          VARCHAR(100),
    date_of_birth      DATE,
    phone_number       VARCHAR(20),
    email              VARCHAR(255),
    specialization     VARCHAR(150),
    enrollment_date    DATE,
    profile_keywords   TEXT[],  -- For GIN index
    address_details    JSONB,   -- For GIN index
    personal_statement TEXT,    -- For B-tree or GIN index
    created_at         TIMESTAMPTZ,
    updated_at         TIMESTAMPTZ
);

-- 2. Subjects Table
CREATE TABLE subjects
(
    id                  INTEGER, -- Will be made SERIAL via sequence and default + PK in constraints
    subject_name        VARCHAR(200),
    lecturer_first_name VARCHAR(100),
    lecturer_last_name  VARCHAR(100),
    department          VARCHAR(100),
    credits             INTEGER,
    description         TEXT,    -- For GIN (FTS) index
    learning_outcomes   TEXT[],  -- For GIN index
    created_at          TIMESTAMPTZ,
    updated_at          TIMESTAMPTZ
);

-- 3. Exam Results Table
CREATE TABLE exam_results
(
    id            INTEGER,  -- Will be made SERIAL via sequence and default + PK in constraints
    student_id    INTEGER,
    subject_id    INTEGER,
    exam_date     DATE,
    grade SMALLINT,         -- e.g., 1,2..5
    exam_location POINT,    -- For GiST index
    notes         TEXT,     -- For general notes or FTS
    created_at    TIMESTAMPTZ,
    updated_at    TIMESTAMPTZ
);
