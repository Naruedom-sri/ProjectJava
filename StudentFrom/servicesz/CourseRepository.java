package servicesz;

import domain.Course;

public interface CourseRepository {

    public String showAllCourse();

    public Course addCourse(String courseName);

    public Course removeCourse(String courseNumber);

    public String getCourseNumber(String couresNumber);

    public String getCourseName(String couresNumber);

    public Course findCourse(String courseNumber);




}
