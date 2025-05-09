--students
CREATE INDEX idx_students_first_name ON students (first_name);
CREATE INDEX idx_students_last_name ON students USING hash (last_name);

CREATE INDEX idx_students_address_details ON students USING gin (address_details);
--subjects
CREATE INDEX idx_subjects_department ON subjects USING hash (department);

--exam-results
CREATE INDEX idx_exam_location_gist ON exam_results USING gist (exam_location);


