package repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Stream;

import domain.MyException;
import domain.Student;
import servicesz.StudentRepository;

public class MemStudentRepo implements StudentRepository {

    private static long nextNumber = 0;
    private Map<String, Student> repo = new HashMap<>();
    private Map<String, Student> studentRm = new HashMap<>();
    private Map<String, List<Student>> studentInCourse = new HashMap<>();

    @Override
    public Student checkStudent(String studentNumber) {
        return repo.get(studentNumber);
    }

    @Override
    public Student addStudent(String studentName, String year) {
        String studentNumber = switch (year) {
            case "1" -> "G_0" + ++nextNumber;

            case "2" -> "H_0" + ++nextNumber;

            case "3" -> "R_0" + ++nextNumber;

            case "4" -> "S_0" + ++nextNumber;
            default -> throw new MyException("Error");
        };
        Student s = new Student(studentNumber, studentName, year);
        if (repo.putIfAbsent(studentNumber, s) == null) {
            return s;
        }
        return null;
    }

    @Override
    public Collection<Student> getStudents() {
        return repo.values();
    }

    @Override
    public Student removeStudent(String studentNumber) {
        Student s = repo.remove(studentNumber);
        studentRm.putIfAbsent(studentNumber, s);
        return s;
    }

    @Override
    public String showAllStudent() {
        StringJoiner sj = new StringJoiner("\n", "", "");
        for (var v : repo.values()) {
            sj.add(v.toString());
        }
        return sj.toString();
    }

    @Override
    public String showAllStudentRemove() {
        StringJoiner sj = new StringJoiner("\n", "", "");
        for (var v : studentRm.values()) {
            sj.add(v.toString());
        }
        return sj.toString();
    }

    @Override
    public Student returnStudent(String studentNumber) {
        if (checkStudentRemove(studentNumber) != null) {
            repo.putIfAbsent(studentNumber, studentRm.get(studentNumber));
            studentRm.remove(studentNumber);
        }
        return null;
    }

    @Override
    public Student checkStudentRemove(String studentNumber) {
        return studentRm.get(studentNumber);
    }

    @Override
    public boolean registerStudentIntoCourse(String courseNumber, String studentNumber) {
        if (studentInCourse.get(courseNumber) == null) {
            studentInCourse.put(courseNumber, new ArrayList<>());
            studentInCourse.get(courseNumber).add(checkStudent(studentNumber));
            return true;
        }
        if (getStudentInCourse(courseNumber).noneMatch(c -> c.getStudentNumber().equals(studentNumber))) {
            return studentInCourse.get(courseNumber).add(checkStudent(studentNumber));
        }
        return false;

    }

    @Override
    public boolean withdrawStudentIntoCourse(String courseNumber, String studentNumber) {
        if (getStudentInCourse(courseNumber).anyMatch(c -> c.getStudentNumber().equals(studentNumber))) {
            return studentInCourse.get(courseNumber).remove(checkStudent(studentNumber));
        }
        return false;
    }

    @Override
    public Stream<Student> getStudentInCourse(String courseNumber) {
        if (studentInCourse.get(courseNumber) == null) {
            return Stream.of();
        }
        return studentInCourse.get(courseNumber).stream();
    }

}
