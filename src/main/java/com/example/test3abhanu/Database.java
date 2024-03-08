package com.example.test3abhanu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    static String jdbcurl="jdbc:mysql://localhost:3306/Test3A";
    static String jdbcusername="root";
    static String jdbcpassword="";

    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(jdbcurl,jdbcusername,jdbcpassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
