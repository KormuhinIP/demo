DROP table students;
create table students (id BIGINT identity primary key, photo varchar(255), last_name VARCHAR(255), first_name VARCHAR(255),
patronymic VARCHAR(255), phone VARCHAR(255), birthDay timestamp, license VARCHAR(255));

