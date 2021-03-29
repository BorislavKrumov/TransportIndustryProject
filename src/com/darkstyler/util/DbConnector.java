package com.darkstyler.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {
    private static final String CONNECTION_STRING ="jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "transport_industry";
    private static final String DB_USER = "user1";
    private static final String DB_PASSWORD = "nocomment123";
    private static DbConnector instance;
    private Connection connection;

    private DbConnector(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(CONNECTION_STRING+DATABASE_NAME,DB_USER,DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static DbConnector getInstance() {
        if(instance == null){
            instance = new DbConnector();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
