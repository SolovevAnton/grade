--students
CREATE INDEX idx_students_first_name ON students (first_name);
CREATE INDEX idx_students_last_name ON students USING hash (last_name);

--subjects
CREATE INDEX idx_subjects_department ON subjects USING hash (department);