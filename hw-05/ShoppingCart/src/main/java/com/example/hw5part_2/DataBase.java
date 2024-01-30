package com.example.hw5part_2;

import java.sql.*;
import java.util.ArrayList;

public class DataBase {
    private Connection conn;

    public DataBase() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/products", "root", "tagvi_400");
    }

    public Connection getConnection(){
        return this.conn;
    }

    public void close() throws SQLException {
        this.conn.close();
    }
}