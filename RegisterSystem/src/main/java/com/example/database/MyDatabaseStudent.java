package com.example.database;

import com.example.domain.Course;
import com.example.domain.MyException;
import com.example.domain.Student;
import com.example.servicesz.StudentRepository;

import java.sql.*;
import java.util.*;
import java.util.stream.Stream;

public class MyDatabaseStudent implements StudentRepository {

    private long nextNumber = 0;
    private Map<String, Student> repo;
    private Map<String, Student> studentRm;
    private Map<String, List<Student>> studentInCourse = new HashMap<>();


    public MyDatabaseStudent() {

        try (Connection connection = ConnectDatabase.getConnect();
             PreparedStatement preparedStatementStudent = connection.prepareStatement("SELECT * FROM students");
             PreparedStatement preparedStatementStudentRemove = connection.prepareStatement("SELECT * FROM studentremoves");
             PreparedStatement preparedStatementStudentInCourse = connection.prepareStatement("SELECT * FROM studentincourses");
             ResultSet resultSetStudent = preparedStatementStudent.executeQuery();
             ResultSet resultSetStudentRemove = preparedStatementStudentRemove.executeQuery();
             ResultSet resultSetStudentInCourse = preparedStatementStudentInCourse.executeQuery();) {
            repo = new HashMap<>();
            while (resultSetStudent.next()) {
                String studentNumber = resultSetStudent.getString(1);
                String studentName = resultSetStudent.getString(2);
                String studentYear = resultSetStudent.getString(3);
                repo.put(studentNumber, new Student(studentNumber, studentName, studentYear));

            }
            studentRm = new HashMap<>();
            while (resultSetStudentRemove.next()) {
                String studentNumber = resultSetStudentRemove.getString(1);
                String studentName = resultSetStudentRemove.getString(2);
                String studentYear = resultSetStudentRemove.getString(3);
                studentRm.put(studentNumber, new Student(studentNumber, studentName, studentYear));
            }
            studentInCourse = new HashMap<>();
            while (resultSetStudentInCourse.next()) {
                String courseNumber = resultSetStudentInCourse.getString(1);
                String studentNumber = resultSetStudentInCourse.getString(2);
                String studentName = resultSetStudentInCourse.getString(3);
                String studentYear = resultSetStudentInCourse.getString(4);
                List<Student> list = new ArrayList<>();
                list.add(new Student(studentNumber, studentName, studentYear));
                studentInCourse.put(courseNumber,list);
            }
        } catch (SQLException e) {
            throw new MyException(e);
        }
    }

    @Override
    public String showAllStudent() {
        StringJoiner sj = new StringJoiner("\n", "", "");
        String studentNumber = "";
        String studentName = "";
        String studentYear = "";
        try (Connection connection = ConnectDatabase.getConnect();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM students");) {

            while (rs.next()) {
                studentNumber = rs.getString(1);
                studentName = rs.getString(2);
                studentYear = rs.getString(3);
                String s = String.format("|          %-16s", studentNumber) + "|" + String.format("               %-24s", studentName) + "|"
                        + String.format("         %-9s", studentYear) + "|";
                sj.add(s);
            }
        } catch (SQLException e) {
            throw new MyException(e);
        }
        return sj.toString();
    }

    @Override
    public String showAllStudentRemove() {
        StringJoiner sj = new StringJoiner("\n", "", "");
        String studentNumber = "";
        String studentName = "";
        String studentYear = "";
        try (Connection connection = ConnectDatabase.getConnect();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM studentremoves");) {
            while (rs.next()) {
                studentNumber = rs.getString(1);
                studentName = rs.getString(2);
                studentYear = rs.getString(3);
                String s = String.format("|          %-16s", studentNumber) + "|" + String.format("               %-24s", studentName) + "|"
                        + String.format("         %-9s", studentYear) + "|";
                sj.add(s);
            }
        } catch (SQLException e) {
            throw new MyException(e);
        }
        return sj.toString();
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
        try (Connection connection = ConnectDatabase.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO students(student_number,student_name,student_year)" + "VALUES(?,?,?)");) {
            preparedStatement.setString(1, studentNumber);
            preparedStatement.setString(2, studentName);
            preparedStatement.setString(3, year);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new MyException(e);
        }
        Student s = new Student(studentNumber, studentName, year);
        if (repo.putIfAbsent(studentNumber, s) == null) {
            return s;
        }
        return null;
    }

    @Override
    public Student removeStudent(String studentNumber) {
        Student s = repo.remove(studentNumber);
        studentRm.putIfAbsent(studentNumber, s);
        try (Connection connection = ConnectDatabase.getConnect();
             PreparedStatement preparedStatement1 = connection.prepareStatement("DELETE FROM students WHERE student_number=?");
             PreparedStatement preparedStatement2 = connection.prepareStatement("INSERT INTO studentremoves(student_number,student_name,student_year)" + "VALUES(?,?,?)");) {

            preparedStatement1.setString(1, studentNumber);
            preparedStatement1.executeUpdate();
            preparedStatement2.setString(1, s.getStudentNumber());
            preparedStatement2.setString(2, s.getName());
            preparedStatement2.setString(3, s.getYear());
            preparedStatement2.executeUpdate();

        } catch (SQLException e) {
            throw new MyException(e);
        }
        return s;
    }

    @Override
    public Collection<Student> getStudents() {
        return repo.values();
    }

    @Override
    public Student checkStudentRemove(String studentNumber) {
        Student s = null;
        String studentName = "";
        String studentNumberCheck = "";
        String studentYear = "";
        if (studentRm.get(studentNumber) == null){
            return  s;
        }
        try (Connection connection = ConnectDatabase.getConnect();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM studentremoves WHERE student_number=?");
             ) {
            statement.setString(1, studentNumber);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                studentNumberCheck = rs.getString(1);
                studentName = rs.getString(2);
                studentYear = rs.getString(3);
                s = new Student(studentNumberCheck, studentName, studentYear);
            }
        } catch (SQLException e) {
            throw new MyException(e);
        }
        return s;
    }

    @Override
    public Student returnStudent(String studentNumber) {
        if (checkStudentRemove(studentNumber) != null) {
            repo.putIfAbsent(studentNumber, studentRm.get(studentNumber));
            Student s = studentRm.remove(studentNumber);
            try (Connection connection = ConnectDatabase.getConnect();
                 PreparedStatement preparedStatement1 = connection.prepareStatement("DELETE FROM studentremoves WHERE student_number=?");
                 PreparedStatement preparedStatement2 = connection.prepareStatement("INSERT INTO students(student_number,student_name,student_year)" + "VALUES(?,?,?)");) {

                preparedStatement1.setString(1, studentNumber);
                preparedStatement1.executeUpdate();
                preparedStatement2.setString(1, s.getStudentNumber());
                preparedStatement2.setString(2, s.getName());
                preparedStatement2.setString(3, s.getYear());
                preparedStatement2.executeUpdate();

            } catch (SQLException e) {
                throw new MyException(e);
            }
        }
        return null;
    }

    @Override
    public Student checkStudent(String studentNumber) {
        Student s = null;
        String studentName = "";
        String studentYear = "";
        if (repo.get(studentNumber) == null){
            return s;
        }
        try (Connection connection = ConnectDatabase.getConnect();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM students WHERE student_number=?");) {
            statement.setString(1, studentNumber);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                studentName = rs.getString(2);
                studentYear = rs.getString(3);
                s = new Student(studentNumber, studentName, studentYear);
            }
        } catch (SQLException e) {
            throw new MyException(e);
        }
        return s;
    }

    @Override
    public boolean registerStudentIntoCourse(String courseNumber, String studentNumber) {
        if (studentInCourse.get(courseNumber) == null) {
            studentInCourse.put(courseNumber, new ArrayList<>());
            studentInCourse.get(courseNumber).add(checkStudent(studentNumber));
            try (Connection connection = ConnectDatabase.getConnect();
                 PreparedStatement statement = connection.prepareStatement("INSERT INTO studentincourses(course_number,student_number,student_name,student_year)" + "VALUES(?,?,?,?)");
                 PreparedStatement statement2 = connection.prepareStatement("SELECT * FROM students WHERE student_number=?") ) {
                ;
                statement2.setString(1,studentNumber);
                ResultSet resultSet = statement2.executeQuery();
                while (resultSet.next()){
                    statement.setString(1, courseNumber);
                    statement.setString(2, resultSet.getString(1));
                    statement.setString(3,resultSet.getString(2));
                    statement.setString(4,resultSet.getString(3));
                    statement.executeUpdate();
                }
            } catch (SQLException e) {
                throw new MyException(e);
            }
            return true;
        }
        if (getStudentInCourse(courseNumber).noneMatch(c -> c.getStudentNumber().equals(studentNumber))) {
            try (Connection connection = ConnectDatabase.getConnect();
                 PreparedStatement statement = connection.prepareStatement("INSERT INTO studentincourses(course_number,student_number,student_name,student_year)" + "VALUES(?,?,?,?)");
                 PreparedStatement statement2 = connection.prepareStatement("SELECT * FROM students WHERE student_number=?") ) {
                ;
                statement2.setString(1,studentNumber);
                ResultSet resultSet = statement2.executeQuery();
                while (resultSet.next()){
                    statement.setString(1, courseNumber);
                    statement.setString(2, resultSet.getString(1));
                    statement.setString(3,resultSet.getString(2));
                    statement.setString(4,resultSet.getString(3));
                    statement.executeUpdate();
                }
            } catch (SQLException e) {
                throw new MyException(e);
            }
            return studentInCourse.get(courseNumber).add(checkStudent(studentNumber));
        }
        return false;
    }

    @Override
    public boolean withdrawStudentIntoCourse(String courseNumber, String studentNumber) {
        if (getStudentInCourse(courseNumber).anyMatch(c -> c.getStudentNumber().equals(studentNumber))) {
            try (Connection connection = ConnectDatabase.getConnect();
                 PreparedStatement statement = connection.prepareStatement("DELETE FROM studentincourses WHERE student_number=?");
                 PreparedStatement statement2 = connection.prepareStatement("SELECT * FROM students WHERE student_number=?") ) {
                ;
                statement2.setString(1,studentNumber);
                ResultSet resultSet = statement2.executeQuery();
                while (resultSet.next()){
                    statement.setString(1, resultSet.getString(1));
                    statement.executeUpdate();
                }
            } catch (SQLException e) {
                throw new MyException(e);
            }
            Student removeStudent = studentInCourse.get(courseNumber).stream().filter(c -> c.getStudentNumber().equals(studentNumber)).findFirst().orElse(null);
            return studentInCourse.get(courseNumber).remove(removeStudent);
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
