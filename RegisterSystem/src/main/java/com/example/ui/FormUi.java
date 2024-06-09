package com.example.ui;

import java.io.Console;
import java.util.Scanner;

import com.example.database.MyDatabaseCourse;
import com.example.database.MyDatabaseStudent;
import com.example.file.FileCourseRepo;
import com.example.file.FileStudentRepo;
import com.example.repository.MemCourseRepo;
import com.example.repository.MemStudentRepo;
import com.example.servicesz.FormService;

public class FormUi {

    private String clearSpace = "\n\n\n\n\n\n\n\n\n\n\n\n";
    private final Scanner sc1 = new Scanner(System.in);
    private final Console console = System.console();
    private FormService service;

    public FormUi(String from) {
        if (from.equals("file")) {
            var s = new FileStudentRepo();
            var c = new FileCourseRepo();
            service = new FormService(s, c);
        } else if (from.equals("memmory")) {
            var s = new MemStudentRepo();
            var c = new MemCourseRepo();
            service = new FormService(s, c);
        } else if (from.equals("database")) {
            var s = new MyDatabaseStudent();
            var c = new MyDatabaseCourse();
            service = new FormService(s, c);
        }
    }

    public void start() {
        login();
    }

    private void login() {
        String login = clearSpace + """
                ===========================================================
                |                From Register of Hogwarts                |
                ===========================================================
                | Login for                                               |
                -----------------------------------------------------------
                | 1. Professor                                            |
                | 2. Close program                                        |
                -----------------------------------------------------------
                | Choose : """;

        String accoutAdmin = """

                ===========================================================
                |                       For Profressor                    |
                ===========================================================
                ------------------- Please enter username -----------------
                | Press [c] for cancel
                | Enter :  """;

        while (true) {
            System.out.print(login);
            String choose = sc1.nextLine();
            if (choose.isBlank()) {
                continue;
            }
            var checkAns = new Scanner(choose);
            if (checkAns.hasNext("1")) {
                switch (choose) {
                    case "1" -> {
                        String cancel = "";
                        while (true) {
                            System.out.print(accoutAdmin);
                            String userNameAdmin = sc1.nextLine();
                            var checkUserAdmin = new Scanner(userNameAdmin);
                            if (checkUserAdmin.hasNext("profressor")) {
                                break;
                            } else if (checkUserAdmin.hasNext("c")) {
                                cancel = "c";
                                break;
                            } else if (userNameAdmin.isBlank()) {
                                continue;
                            }
                            System.out.println("------------ Username incorrect!, enter agian -------------");
                        }
                        if (cancel.equals("c")) {
                            break;
                        }
                        while (true) {
                            char[] password = console.readPassword("""

                                    ------------------- Please enter password ------------------
                                    | Press [c] for cancel  
                                    | Enter : """);
                            var checkPass = new Scanner(String.valueOf(password));
                            if (checkPass.hasNext("12345")) {
                                System.out.println(
                                        """
                                                ============================================================
                                                |                     Log-in Successful                    |
                                                ============================================================""");
                                break;
                            } else if (String.valueOf(password).isBlank()) {
                                continue;
                            } else if (String.valueOf(password).equals("c")) {
                                cancel = "c";
                                break;
                            }
                            System.out.println("------------- Password incorrect!, enter agian ------------- ");
                        }
                        if (cancel.equals("c")) {
                            break;
                        }
                        menuAdmin();
                    }
                }
            } else if (checkAns.hasNext("2")) {
                break;
            }
        }
        System.out.println(
                """
                        ===========================================================
                        |                       End Program                       |
                        =========================================================== """);
    }

    private void menuAdmin() {

        String mainMenuAdmin = clearSpace + """
                ==============================================
                |                Menu Profressor             |
                ==============================================
                | 1. Show and register student               |
                | 2. Show and register course                |
                | 3. Check and register student in course    |
                | 4. Log-out                                 |
                ----------------------------------------------
                | Choose : """;
        String headerMenuShowAllStudent = clearSpace + """
                =======================================================================================
                |                                   Student of Hogwarts                               |
                =======================================================================================
                |      Student Number      |             Student Name              |       Year       |
                ======================================================================================= """;

        String footerMenuShowAllStudent = """
                ---------------------------------------------------------------------------------------
                | 1. Sort by studentnumber                                                            |
                | 2. Add a student                                                                    |
                | 3. Remove a student                                                                 |
                | 4. Return a student                                                                 |
                | Press [q] for back to menu                                                          |
                ---------------------------------------------------------------------------------------
                | Choose : """;
        String headerMenuShowAllStudentRemove = clearSpace + """
                =======================================================================================
                |                                   Student that Remove                               |
                =======================================================================================
                |      Student Number      |             Student Name              |       Year       |
                ======================================================================================= """;

        String footerMenuShowAllStudentRemove = """
                ----------------------------- Please enter student number -----------------------------
                | Press [c] for cancel                                                                |
                | Enter : """;
        String menuAddStudentName = """
                ----------------------------- Please enter student name -------------------------------
                | Press [c] for cancel                                                                |
                | Enter : """;
        String menuAddStudentYear = """
                ----------------------------- Please enter student year -------------------------------
                | Press [c] for cancel                                                                |
                | Enter [1|2|3|4] : """;
        String menuRemoveStudent = """
                ----------------------------- Please enter student number -----------------------------
                | Press [c] for cancel                                                                |
                | Enter : """;
        String menuCourseAdd = """
                ------------------------ Please enter course name ----------------------
                | Press [c] for cancel                                                 |
                | Enter : """;
        String menuCourseRemove = """
                ------------------------ Please enter course number --------------------
                | Press [c] for cancel                                                 |
                | Enter : """;
        String menuCourseCheck = """
                ---------------------- Please enter course number ----------------------
                | Press [q] for back to menu                                           |
                | Enter : """;

        String headerShowAllCourse = clearSpace + """
                ========================================================================
                |                                Course                                |
                ========================================================================
                |     Course Number      |                 Course Title                |
                ======================================================================== """;

        String footerShowAllCourse = """
                ------------------------------------------------------------------------
                | 1. Add a course                                                      |
                | 2. Remove a course                                                   |
                | Press [q] for back to menu                                           |
                ------------------------------------------------------------------------
                | Choose :  """;

        String headerShowStudentInCourse = """
                =======================================================================================
                |                                    Student in Course                                |
                =======================================================================================
                |      Student Number      |             Student Name              |       Year       |
                ======================================================================================= """;
        String footerShowStudentInCourse = """
                ---------------------------------------------------------------------------------------
                | 1. Add a student into course                                                        |
                | 2. Remove a student in course                                                       |
                | Press [q] for check course other                                                    |
                ---------------------------------------------------------------------------------------
                | Choose :  """;

        while (true) {
            System.out.print(mainMenuAdmin);
            String choose = sc1.nextLine();
            var checkSelect = new Scanner(choose);
            if (choose.isBlank()) {
                continue;
            }
            if (checkSelect.hasNext("[123]")) {
                switch (choose) {
                    case "1" -> {
                        String s = "";
                        while (true) {
                            System.out.println(headerMenuShowAllStudent);
                            if (s.equals("Sorted")) {
                                service.sortByStudentNumber();
                            } else {
                                System.out.println(service.showAllStudent());
                            }
                            System.out.print(footerMenuShowAllStudent);
                            String option = sc1.nextLine();
                            var checkAns = new Scanner(option);
                            if (checkAns.hasNext("[1234]")) {
                                while (true) {
                                    switch (option) {
                                        case "1" -> {
                                            s = "Sorted";
                                        }
                                        case "2" -> {
                                            while (true) {
                                                System.out.print(menuAddStudentName);
                                                String studentName = sc1.nextLine();
                                                if (studentName.isBlank()) {
                                                    System.out.println();
                                                    continue;
                                                } else if (studentName.equals("c")) {
                                                    break;
                                                } else  if (studentName.length() > 22){
                                                    System.out.println("------------------------------ Student name out of length! ----------------------------");
                                                    continue;
                                                }
                                                while (true) {
                                                    System.out.print(menuAddStudentYear);
                                                    String studentYear = sc1.nextLine();
                                                    var checkYear = new Scanner(studentYear);
                                                    if (checkYear.hasNext("[1234]")) {
                                                        service.addStudent(studentName, studentYear);
                                                        System.out.println(
                                                                "------------------------------ Add student successful ---------------------------------");
                                                        break;
                                                    } else if (studentYear.isBlank()) {
                                                        System.out.println();
                                                        continue;
                                                    } else if (studentYear.equals("c")) {
                                                        break;
                                                    }
                                                    System.out.println(
                                                            "------------------------------- Cann't add student! -----------------------------------");
                                                    System.out.println();
                                                }
                                                break;
                                            }
                                        }
                                        case "3" -> {
                                            while (true) {
                                                System.out.print(menuRemoveStudent);
                                                String studentNumber = sc1.nextLine();
                                                if (studentNumber.isBlank()) {
                                                    System.out.println();
                                                    continue;
                                                } else if (studentNumber.equals("c")) {
                                                    break;
                                                } else if (service.checkStudent(studentNumber) == null) {
                                                    System.out.println(
                                                            "---------------------------------- Cann't found student! ------------------------------");
                                                    System.out.println();
                                                    continue;
                                                }
                                                service.removeStudent(studentNumber);
                                                System.out.println(
                                                        "----------------------------- Remove student successful -------------------------------");
                                                break;
                                            }
                                        }
                                        case "4" -> {
                                            System.out.println(headerMenuShowAllStudentRemove);
                                            System.out.println(service.showAllStudentRemove());
                                            System.out.print(footerMenuShowAllStudentRemove);
                                            String studentNumber = sc1.nextLine();
                                            if (studentNumber.isBlank()) {
                                                continue;
                                            } else if (studentNumber.equals("c")) {
                                                break;
                                            } else if (service.checkStudentRemove(studentNumber) == null) {
                                                System.out.println(
                                                        "---------------------------------- Cann't found student! ------------------------------");
                                                System.out.println();
                                                continue;
                                            }
                                            service.returnStudent(studentNumber);
                                            System.out.println(
                                                    "----------------------------- Return student successful -------------------------------");
                                        }
                                    }
                                    break;
                                }
                            } else if (checkAns.hasNext("q")) {
                                break;
                            }
                        }
                    }
                    case "2" -> {
                        while (true) {
                            System.out.println(headerShowAllCourse);
                            System.out.println(service.showAllCourse());
                            System.out.print(footerShowAllCourse);
                            String option = sc1.nextLine();
                            if (option.equals("1") || option.equals("2")) {
                                switch (option) {
                                    case "1" -> {
                                        while (true) {
                                            System.out.println();
                                            System.out.print(menuCourseAdd);
                                            String courseName = sc1.nextLine();
                                            if (courseName.isBlank()) {
                                                continue;
                                            } else if (courseName.equals("c")) {
                                                break;
                                            }
                                            service.addCourse(courseName);
                                            System.out.println(
                                                    "------------------------ Add course successful ------------------------- ");
                                            break;
                                        }
                                    }
                                    case "2" -> {
                                        while (true) {
                                            System.out.println();
                                            System.out.print(menuCourseRemove);
                                            String courseNumber = sc1.nextLine();
                                            if (courseNumber.isBlank()) {
                                                continue;
                                            } else if (courseNumber.equals("c")) {
                                                break;
                                            } else if (service.findCourse(courseNumber) == null) {
                                                System.out.println(
                                                        "------------------------ Cann't found course! --------------------------");
                                                continue;
                                            }
                                            service.removeCourse(courseNumber);
                                            System.out.println(
                                                    "------------------------ Remove course successful ----------------------");
                                            break;
                                        }
                                    }
                                }
                            } else if (option.equals("q")) {
                                break;
                            }
                        }

                    }
                    case "3" -> {
                        while (true) {
                            System.out.println();
                            System.out.println(headerShowAllCourse);
                            System.out.println(service.showAllCourse());
                            System.out
                                    .println(
                                            "========================================================================");
                            System.out.print(menuCourseCheck);
                            String courseNumber = sc1.nextLine();
                            if (courseNumber.isBlank()) {
                                continue;
                            } else if (courseNumber.equals("q")) {
                                break;
                            } else if (service.findCourse(courseNumber) == null) {
                                System.out.println(
                                        "-------------------------- Cann't found course -------------------------");
                                continue;
                            }
                            while (true) {
                                System.out.println(clearSpace);
                                System.out.println(
                                        "=======================================================================================");
                                System.out.println(service.printHeaderCoureCheck(courseNumber));
                                System.out.println(headerShowStudentInCourse);
                                service.showAllStudentInCourse(courseNumber);
                                System.out.print(footerShowStudentInCourse);
                                String option = sc1.nextLine();
                                if (option.equals("1") || option.equals("2")) {
                                    switch (option) {
                                        case "1" -> {
                                            while (true) {
                                                System.out.println();
                                                System.out.println(headerMenuShowAllStudent);
                                                System.out.println(service.showAllStudent());
                                                System.out.println(
                                                        "=======================================================================================");
                                                System.out.print(menuRemoveStudent);
                                                String studentNumber = sc1.nextLine();
                                                if (studentNumber.isBlank()) {
                                                    continue;
                                                } else if (studentNumber.equals("c")) {
                                                    break;
                                                } else if (service.checkStudent(studentNumber) == null) {
                                                    System.out.println(
                                                            "--------------------------------- Cann't found student --------------------------------");
                                                    continue;
                                                }
                                                service.registerStudentIntoCourse(courseNumber, studentNumber);
                                                System.out
                                                        .println(
                                                                "------------------------------ Register student successful ----------------------------");
                                                break;
                                            }
                                        }
                                        case "2" -> {
                                            while (true) {
                                                System.out.println();
                                                System.out.print(menuRemoveStudent);
                                                String studentNumber = sc1.nextLine();
                                                if (studentNumber.isBlank()) {
                                                    continue;
                                                } else if (studentNumber.equals("c")) {
                                                    break;
                                                } else if (service.checkStudent(studentNumber) == null) {
                                                    System.out.println(
                                                            "--------------------------------- Cann't found student --------------------------------");
                                                    continue;
                                                }
                                                service.withdrawStudentIntoCourse(courseNumber, studentNumber);
                                                System.out.println(
                                                        "------------------------------ Withdraw student successful ---------------------------- ");
                                                break;
                                            }
                                        }
                                    }

                                } else if (option.equals("q")) {
                                    break;
                                }
                            }
                        }
                    }
                }

            } else if (checkSelect.hasNext("4")) {
                System.out.println("""
                        =============================================================================
                        |                           Log-out Successful                              |
                        ============================================================================= """);
                break;
            }
        }
    }

}