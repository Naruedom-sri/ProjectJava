package file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

public class FileStudentRepo implements StudentRepository {

    private String fileName = "D:\\Student.txt";
    private static long nextNumber;
    private Map<String, Student> repo; // main
    private Map<String, Student> studentRm; // temp
    private Map<String, List<Student>> studentInCourse;

    public FileStudentRepo() {
        File file = new File(fileName);
        if (file.exists()) { // ดึงข้อมูลตอนเปิดแอพ
            try (FileInputStream fi = new FileInputStream(file);
                    BufferedInputStream bi = new BufferedInputStream(fi);
                    ObjectInputStream oi = new ObjectInputStream(bi);) {
                nextNumber = oi.readLong();
                repo = (Map<String, Student>) oi.readObject();
                studentRm = (Map<String, Student>) oi.readObject();
                studentInCourse = (Map<String, List<Student>>) oi.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            nextNumber = 0;
            repo = new HashMap<>();
            studentRm = new HashMap<>();
            studentInCourse = new HashMap<>();       
        }
    }

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
            try (FileOutputStream fo = new FileOutputStream(fileName);
                    BufferedOutputStream bo = new BufferedOutputStream(fo);
                    ObjectOutputStream oo = new ObjectOutputStream(bo);) {
                oo.writeLong(nextNumber);
                oo.writeObject(repo);
                oo.writeObject(studentRm);
                oo.writeObject(studentInCourse);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        try (FileOutputStream fo = new FileOutputStream(fileName);
                BufferedOutputStream bo = new BufferedOutputStream(fo);
                ObjectOutputStream oo = new ObjectOutputStream(bo);) {
            oo.writeLong(nextNumber);
            oo.writeObject(repo);
            oo.writeObject(studentRm);
            oo.writeObject(studentInCourse);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            try (FileOutputStream fo = new FileOutputStream(fileName);
                    BufferedOutputStream bo = new BufferedOutputStream(fo);
                    ObjectOutputStream oo = new ObjectOutputStream(bo);) {
                oo.writeLong(nextNumber);
                oo.writeObject(repo);
                oo.writeObject(studentRm);
                oo.writeObject(studentInCourse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public boolean registerStudentIntoCourse(String courseNumber, String studentNumber) {
        if (studentInCourse.get(courseNumber) == null) {
            studentInCourse.put(courseNumber, new ArrayList<>());
            studentInCourse.get(courseNumber).add(checkStudent(studentNumber));
            try (FileOutputStream fo = new FileOutputStream(fileName);
                    BufferedOutputStream bo = new BufferedOutputStream(fo);
                    ObjectOutputStream oo = new ObjectOutputStream(bo);) {
                oo.writeLong(nextNumber);
                oo.writeObject(repo);
                oo.writeObject(studentRm);
                oo.writeObject(studentInCourse);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        if (getStudentInCourse(courseNumber).noneMatch(c -> c.getStudentNumber().equals(studentNumber))) {
            boolean t = studentInCourse.get(courseNumber).add(checkStudent(studentNumber));
            try (FileOutputStream fo = new FileOutputStream(fileName);
                    BufferedOutputStream bo = new BufferedOutputStream(fo);
                    ObjectOutputStream oo = new ObjectOutputStream(bo);) {
                oo.writeLong(nextNumber);
                oo.writeObject(repo);
                oo.writeObject(studentRm);
                oo.writeObject(studentInCourse);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return t;
        }
        return false;

    }

    @Override
    public boolean withdrawStudentIntoCourse(String courseNumber, String studentNumber) {
        if (getStudentInCourse(courseNumber).anyMatch(c -> c.getStudentNumber().equals(studentNumber))) {
            boolean t = studentInCourse.get(courseNumber).remove(checkStudent(studentNumber));
            try (FileOutputStream fo = new FileOutputStream(fileName);
                    BufferedOutputStream bo = new BufferedOutputStream(fo);
                    ObjectOutputStream oo = new ObjectOutputStream(bo);) {
                oo.writeLong(nextNumber);
                oo.writeObject(repo);
                oo.writeObject(studentRm);
                oo.writeObject(studentInCourse);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return t;
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

    @Override
    public Student checkStudentRemove(String studentNumber) {
        return studentRm.get(studentNumber);
    }

}
