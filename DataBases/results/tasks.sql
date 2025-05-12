--Tasks

--task 1: student by last name exact match
SELECT *
FROM students s
WHERE last_name = 'Wilson';

--task 2: student by first name part match
SELECT *
FROM students
WHERE first_name ILIKE 'eve%';

--task 3: Найти пользователя по телефонному номеру (частичное совпадение).
SELECT*
FROM students
WHERE phone_number LIKE '%777%';

--task 4: Найти пользователя с его оценками по фамилии (частичное совпадение)
SELECT st.first_name, sb.subject_name, er.grade
FROM students st
         JOIN exam_results er
              ON st.id = er.student_id
         JOIN subjects sb
              ON er.subject_id = sb.id
WHERE st.last_name ILIKE '%m%';

--task 5: Добавьте триггер, который будет обновлять колонку updated_datetime на текущее значение даты в случае обновления информации о студенте.
CREATE OR REPLACE FUNCTION update_updated_at_column()
    RETURNS TRIGGER AS
$$
BEGIN
    new.updated_at = NOW();
    RETURN new;
END;
$$;

CREATE TRIGGER products_updated_at_trigger
    BEFORE INSERT OR UPDATE
    ON students
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

-- task 6: Добавьте валидацию на уровне БД, которая будет проверять имя студента на наличие конкретных символов (не сохраняйте студентов, у которых в имени встречаются символы '@', '#', '$').
CREATE OR REPLACE FUNCTION students_validation()
    RETURNS TRIGGER AS
$$
BEGIN
    IF new.first_name ~ '[@#$]' THEN
        RAISE EXCEPTION 'Student first name cannot contain "@", "#", or "$". Invalid name provided: %', new.first_name;
    END IF;
    RETURN new;
END;
$$;

CREATE TRIGGER students_name_validation_trigger
    BEFORE INSERT OR UPDATE
    ON students
    FOR EACH ROW
EXECUTE FUNCTION students_validation();


--task 7: Создайте таблицу snapshot, которая будет содержать следующие данные: student name, student surname, subject name, mark (snapshot в данном случае значит, что в случае изменения каких-то данных в исходной таблице, данные в снепшоте не изменятся).
CREATE TABLE exam_results_snapshot
(
    snapshot_id        SERIAL PRIMARY KEY,
    student_first_name VARCHAR(100),
    student_last_name  VARCHAR(100),
    subject_name       VARCHAR(200),
    exam_grade         SMALLINT,
    snapshot_taken_at  timestamptz DEFAULT NOW()
);

INSERT INTO exam_results_snapshot (student_first_name,
                                   student_last_name,
                                   subject_name,
                                   exam_grade)
SELECT st.first_name, st.last_name, sb.subject_name, er.grade
FROM students st
         JOIN exam_results er ON st.id = er.student_id
         JOIN subjects sb ON sb.id = er.subject_id;

--task 8: Напишите функцию, которая будет вычислять среднюю оценку для конкретного студента.
CREATE OR REPLACE FUNCTION calculate_student_average_grade(
    this_student_id INTEGER
)
    RETURNS NUMERIC(3, 2)
AS
$$
DECLARE
    average_grade NUMERIC(3, 2);
BEGIN
    SELECT AVG(er.grade)
    INTO average_grade
    FROM exam_results er
    WHERE er.student_id = this_student_id;
    RETURN average_grade;
END;
$$ LANGUAGE plpgsql;;

SELECT calculate_student_average_grade(110040);

--task 9: Напишите функцию, которая будет вычислять среднюю оценку среди всех студентов по конкретному предмету.
CREATE OR REPLACE FUNCTION calculate_subject_average_grade(
    this_subject_id INTEGER
)
    RETURNS NUMERIC(3, 2)
AS
$$
DECLARE
    average_grade NUMERIC(3, 2);
BEGIN
    SELECT AVG(er.grade)
    INTO average_grade
    FROM exam_results er
    WHERE er.subject_id = this_subject_id;
    RETURN average_grade;
END;
$$ LANGUAGE plpgsql;

SELECT calculate_subject_average_grade(1001);

--task 10: Напишите функцию, которая будет возвращать студентов в «красной зоне» («красная зона» значит, что у студента как минимум 2 оценки меньше 3).
CREATE OR REPLACE FUNCTION find_students_in_red_zone()
    RETURNS TABLE
            (
                student_id INTEGER
            )
AS
$$
BEGIN
    RETURN QUERY
        SELECT st.id
        FROM students st
                 JOIN exam_results er ON st.id = er.student_id
        WHERE er.grade < 3
        GROUP BY st.id
        HAVING COUNT(*) > 2;
END;
$$ LANGUAGE plpgsql;

SELECT find_students_in_red_zone();