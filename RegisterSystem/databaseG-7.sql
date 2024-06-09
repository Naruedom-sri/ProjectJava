DROP DATABASE mydatabase;
CREATE DATABASE mydatabase;
USE mydatabase;

CREATE TABLE students(
	student_number VARCHAR(10),
    student_name VARCHAR(40),
    student_year VARCHAR(40)
);

CREATE TABLE courses(
	course_number VARCHAR(10),
    course_name VARCHAR(40)
);

CREATE TABLE studentremoves (
	student_number VARCHAR(10),
    student_name VARCHAR(40),
    student_year VARCHAR(40)
);

CREATE TABLE studentincourses (
	course_number VARCHAR(10),
	student_number VARCHAR(10),
    student_name VARCHAR(40),
    student_year VARCHAR(40)
);




DROP TABLE students;
DROP TABLE courses;
DROP TABLE studentremoves;
DROP TABLE studentincourses;



SELECT * FROM students;
SELECT * FROM courses;
SELECT * FROM studentremoves;
SELECT * FROM studentincourses;

