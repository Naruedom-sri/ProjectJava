package com.example.servicesz;

import java.util.Collection;
import java.util.stream.Stream;

import com.example.domain.Student;

public interface StudentRepository {

    public String showAllStudent();

    public String showAllStudentRemove();

    public Student checkStudentRemove(String studentNumber);

    public Student returnStudent(String studentNumber);

    public Student checkStudent(String studentNumber);

    public Student addStudent(String studentName, String year);

    public Collection<Student> getStudents();

    public Student removeStudent(String studentNumber);

    public boolean registerStudentIntoCourse(String courseNumber, String studentNumber);

    public boolean withdrawStudentIntoCourse(String courseNumber, String studentNumber);

    public Stream<Student> getStudentInCourse(String courseNumber);

}
