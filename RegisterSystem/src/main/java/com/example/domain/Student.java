package com.example.domain;

import java.io.Serializable;

public class Student implements Serializable, Comparable<Student> {
    private String studentNumber;
    private final String name;
    private final String year;

    public Student(String studentNumber, String name, String year) {
        this.studentNumber = studentNumber;
        this.name = name;
        this.year = year;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public String getName() {
        return name;
    }

    public String getYear() {
        return year;
    }

    public String toString() {
        return String.format("|          %-16s", this.studentNumber) + "|" + String.format("               %-24s", this.name) + "|"
                + String.format("         %-9s", this.year) + "|";

    }

    @Override
    public int compareTo(Student o) {
        return this.studentNumber.compareTo(o.studentNumber);
    }

}
