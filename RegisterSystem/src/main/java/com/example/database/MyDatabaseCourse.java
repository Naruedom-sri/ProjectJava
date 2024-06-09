package com.example.database;

import com.example.domain.Course;
import com.example.domain.MyException;
import com.example.servicesz.CourseRepository;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class MyDatabaseCourse implements CourseRepository {

    private static long nextNumber = 0;
    private Map<String, Course> repo;

    public MyDatabaseCourse() {
        try (Connection connection = ConnectDatabase.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM courses");
             ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            repo = new HashMap<>();
            while (resultSet.next()) {
                String courseNumber = resultSet.getString(1);
                String courseName = resultSet.getString(2);
                repo.put(courseNumber, new Course(courseNumber, courseName));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        @Override
        public String showAllCourse () {
            StringJoiner sj = new StringJoiner("\n", "", "");
            String courseNumber = "";
            String courseName = "";

            try (Connection connection = ConnectDatabase.getConnect();
                 Statement statement = connection.createStatement();
                 ResultSet rs = statement.executeQuery("SELECT * FROM courses");) {

                while (rs.next()) {
                    courseNumber = rs.getString(1);
                    courseName = rs.getString(2);
                    String s = String.format("|        %-16s", courseNumber) + "|"
                            + String.format("     %-40s", courseName) + "|";
                    sj.add(s);
                }
            } catch (SQLException e) {
                throw new MyException(e);
            }
            return sj.toString();
        }

        @Override
        public Course addCourse (String courseName){
            String courseNumber = "L_10" + ++nextNumber;
            try (Connection connection = ConnectDatabase.getConnect();
                 PreparedStatement preparedStatement = connection.prepareStatement("INSERT courses(course_number,course_name)" + "VALUES(?,?)");) {
                preparedStatement.setString(1, courseNumber);
                preparedStatement.setString(2, courseName);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new MyException(e);
            }
            Course c = new Course(courseNumber, courseName);
            if (repo.putIfAbsent(courseNumber, c) == null) {
                return c;
            }
            return null;
        }

        @Override
        public Course removeCourse (String courseNumber){
            Course c = repo.remove(courseNumber);
            String courseName = "";
            try (Connection connection = ConnectDatabase.getConnect();
                 PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM courses WHERE course_number=?");
            ) {
                preparedStatement.setString(1, courseNumber);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new MyException(e);
            }
            return c;
        }

        @Override
        public String getCourseNumber (String courseNumber){
            String c = "";
            try (Connection connection = ConnectDatabase.getConnect();
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM courses WHERE course_number=?");
            ) {
                preparedStatement.setString(1, courseNumber);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    c = resultSet.getString(1);
                }

            } catch (SQLException e) {
                throw new MyException(e);
            }
            return c;
        }

        @Override
        public String getCourseName (String courseNumber){
            String c = "";
            try (Connection connection = ConnectDatabase.getConnect();
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM courses WHERE course_number=?");
            ) {
                preparedStatement.setString(1, courseNumber);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    c = resultSet.getString(2);
                }
            } catch (SQLException e) {
                throw new MyException(e);
            }
            return c;
        }

        @Override
        public Course findCourse (String courseNumber){
            Course c = null;
            String courseName = "";
            if (repo.get(courseNumber) == null) {
                return c;
            }
            try (Connection connection = ConnectDatabase.getConnect();
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM courses WHERE course_number=?");
            ) {
                preparedStatement.setString(1, courseNumber);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    courseName = resultSet.getString(2);
                }
                c = new Course(courseNumber, courseName);

            } catch (SQLException e) {
                throw new MyException(e);
            }
            return c;
        }
    }
