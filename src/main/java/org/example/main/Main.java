package org.example.main;

import org.example.util.DatabaseConnection;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        // Connection myConn = null;
        // Statement myStamt = null;
        // ResultSet myRes = null;

        // String url = "jdbc:mysql://localhost:3306/project";
        // String user = "root";
        // String password = "1234";

        try (
                // Connection myConn = DriverManager.getConnection(url, user, password);
                Connection myConn = DatabaseConnection.getInstance();
                Statement myStamt = myConn.createStatement();
                ResultSet myRes = myStamt.executeQuery("SELECT * FROM employees");
                ) {
            // myConn = DriverManager.getConnection(url, user, password);
            // myStamt = myConn.createStatement();
            // myRes = myStamt.executeQuery("SELECT * FROM employees");

            while(myRes.next()) {
                System.out.println(myRes.getString("first_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Algo salio mal!");
        } /* finally {
            if (myRes != null) {
                myRes.close();
            }
            if (myStamt != null) {
                myRes.close();
            }
            if (myConn != null) {
                myRes.close();
            }
        } */
    }
}
