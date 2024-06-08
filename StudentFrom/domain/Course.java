package domain;

import java.io.Serializable;

public class Course implements Serializable{
    private String name;
    private String courseNumber;

    public Course(String courseNumber, String name) {
        this.courseNumber = courseNumber;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    @Override
    public String toString() {
        return String.format("|        %-16s", this.courseNumber) + "|"
                + String.format("     %-40s", this.name) + "|";
    }

}
