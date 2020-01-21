package database;

import java.sql.*;

public class DatabaseConnection {
    private Connection connection;
    private final static String address = "localhost";
    private final static String dbName = "chat";
    private final static String username = "root";
    private final static String password = "";
    private Statement statement;

    public DatabaseConnection() throws SQLException {
        loadDriver();
        connectToDatabase();
    }

    public boolean loadDriver() {
        System.out.println("checking driver:");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            return true;
        } catch (Exception e) {
            System.out.println("Error during driver loading!");
            return false;
        }

    }

    public void connectToDatabase() throws SQLException {
        String db = "jdbc:mysql://" + address + ":3306/" + dbName;
        connection = null;
        connection = DriverManager.getConnection(db, username, password);
    }

    public void closeConnection() throws SQLException {
        statement.close();
        connection.close();
    }

    public void createStatement() throws SQLException {
        statement = connection.createStatement();
    }

    public ResultSet executeQuery(String query) throws SQLException {
        return statement.executeQuery(query);
    }

    public boolean execute(String query) throws SQLException {
        return statement.execute(query);
    }
}
