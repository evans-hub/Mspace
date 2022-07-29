/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mspace;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author lenovo
 */
public class DBConnection {
    public static void main(String[] args){
       DBConnection dbconnection=new DBConnection();
       Connection connection;
       connection=dbconnection.get_connection();
        System.out.println(connection);
    }

    public Connection get_connection() {
        Connection connection=null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
      connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/mspace","root","");
      
        }
        catch(ClassNotFoundException | SQLException e){
        System.out.println(e);
        }
        return connection;
    }
 
    }
