package com.example;

import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import com.example.ui.FormUi;

public class Main {

    public static void main(String[] args) {
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" + """
                    ====================================
                    | Where will the data be stored?   |
                    | 1. In file                       |
                    | 2. In memory                     |
                    | 3. In database                   |
                    | Enter : """);
            String from = sc.nextLine();
            if (from.equals("1")) {
                var f = new FormUi("file");
                f.start();
                break;
            } else if (from.equals("2")) {
                var f = new FormUi("memmory");
                f.start();
                break;
            } else if (from.equals("3")) {
                var f = new FormUi("database");
                f.start();
                break;
            }


        }
    }
}

