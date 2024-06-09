package com.example.database;

import com.example.domain.MyException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDatabase {
    private static Connection connection = null;
    private static String url = "jdbc:mysql://localhost:3306/mydatabase";
    private static String username = "root";
    private static String password = "5747";

    public static Connection getConnect(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new MyException(e);
        } catch (ClassNotFoundException e) {
            throw new MyException(e);
        }
        return connection;
    }
}
