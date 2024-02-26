package test_server_db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestDBServer {

    private Connection connection;
//
//
//    public void saveDataToDBServer() {
//
//        if(connection != null) {
//
//            // SQL Queries
//            String countSQL = "select count(*) as count from Reservations where id = ?";
//
//            // Statements
//            try {
//                PreparedStatement countStatement = connection.prepareStatement(countSQL);
//
//
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//
//            System.out.println("Saving data to a server...");
//        } else {
//            System.out.println("No connection to save data!");
//        }
//    }

    public void connect() {
        System.out.println("Connecting to a server...");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/nop2324";
            String user = "root";
            String pswd = "admin";
            connection = DriverManager.getConnection(url, user, pswd);
            System.out.println("Connected to: " + connection.toString());

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void disconnect() {
        try {
            connection.close();
            System.out.println("Disconnected from: " + connection.toString());
        } catch (SQLException e) {
            System.out.println("No connection to close!");
        }
    }
}
