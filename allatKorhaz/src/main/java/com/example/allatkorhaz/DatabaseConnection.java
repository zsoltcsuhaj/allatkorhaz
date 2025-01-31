package com.example.allatkorhaz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection conn=null;
    private static final String URL="jdbc:mysql://localhost:3306/allatkorhaz";
    private static final String USER="root";
    private static final String PASSWORD="";

    public static Connection getConnection(){
        if(conn==null){
            try{
                conn=DriverManager.getConnection(URL,USER,PASSWORD);
            }catch (SQLException e){
                System.out.println("Adatbázis hiba: "+e.getMessage());
            }
        }
        return conn;
    }
}
