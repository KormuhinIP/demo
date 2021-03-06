DROP table students;
DROP table exams;
DROP table teachers;
DROP table payments;
DROP table lessons;



create table students (id BIGINT identity primary key, photo varchar(255), lastName VARCHAR(255), firstName VARCHAR(255),
patronymic VARCHAR(255), phone VARCHAR(255), birthDay timestamp, license VARCHAR(255), dateAdding timestamp);



create table exams (id BIGINT identity primary key, dateExam timestamp, student_id BIGINT, kindExam VARCHAR(255),
comment VARCHAR(255), evaluation VARCHAR(255));


create table teachers (id BIGINT identity primary key, lastName VARCHAR(255), firstName VARCHAR(255), patronymic VARCHAR(255),
numberLicense VARCHAR(255),birthDay timestamp, experience int, dateAdding timestamp);


create table payments (id BIGINT identity primary key, student_id BIGINT, paymentDate timestamp, sumPayment DOUBLE);

create table lessons (id BIGINT identity primary key, dateLesson timestamp, student_id BIGINT, teacher_id BIGINT);


