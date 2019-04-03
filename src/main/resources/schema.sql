DROP table students;
DROP table exams;

create table students (id BIGINT identity primary key, photo varchar(255), last_name VARCHAR(255), first_name VARCHAR(255),
patronymic VARCHAR(255), phone VARCHAR(255), birthDay timestamp, license VARCHAR(255));



create table exams (id BIGINT identity primary key, date_exam timestamp, student_id BIGINT, kind_exam VARCHAR(255),
comment VARCHAR(255), evaluation VARCHAR(255));





