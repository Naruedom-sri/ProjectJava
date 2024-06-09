package com.example.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import com.example.domain.Course;
import com.example.domain.MyException;
import com.example.servicesz.CourseRepository;

public class FileCourseRepo implements CourseRepository {

    private String fileName = "D:\\Course.txt";
    private static long nextNumber;
    private Map<String, Course> repo;

    public FileCourseRepo() {
        File file = new File(fileName);
        if (file.exists()) { // ดึงข้อมูลตอนเปิดแอพ
            try (FileInputStream fi = new FileInputStream(file);
                    BufferedInputStream bi = new BufferedInputStream(fi);
                    ObjectInputStream oi = new ObjectInputStream(bi);) {
                nextNumber = oi.readLong();
                repo = (Map<String, Course>) oi.readObject();
            } catch (Exception e) {
                throw new MyException();
            }
        } else {
            nextNumber = 0;
            repo = new HashMap<>();
        }
    }

    @Override
    public String showAllCourse() {
        StringJoiner sj = new StringJoiner("\n", "", "");
        for (var v : repo.values()) {
            sj.add(v.toString());
        }
        return sj.toString();
    }

    @Override
    public Course addCourse(String courseName) {
        String courseNumber = "L_10" + ++nextNumber;
        Course c = new Course(courseNumber, courseName);
        if (repo.putIfAbsent(courseNumber, c) == null) {
            try (FileOutputStream fi = new FileOutputStream(fileName);
                    BufferedOutputStream bi = new BufferedOutputStream(fi);
                    ObjectOutputStream oi = new ObjectOutputStream(bi);) {
                oi.writeLong(nextNumber);
                oi.writeObject(repo);
            } catch (Exception e) {
                throw new MyException();
            }
            return c;
        }
        ;
        return null;
    }

    @Override
    public Course removeCourse(String courseNumber) {
        try (FileOutputStream fi = new FileOutputStream(fileName);
                BufferedOutputStream bi = new BufferedOutputStream(fi);
                ObjectOutputStream oi = new ObjectOutputStream(bi);) {
            oi.writeLong(nextNumber);
            oi.writeObject(repo);
        } catch (Exception e) {
            throw new MyException();
        }
        return repo.remove(courseNumber);
    }

    @Override
    public Course findCourse(String courseNumber) {
        return repo.get(courseNumber);
    }

    @Override
    public String getCourseNumber(String courseNumber) {
        return repo.get(courseNumber).getCourseNumber();
    }

    @Override
    public String getCourseName(String courseNumber) {
        return repo.get(courseNumber).getName();

    }

}
