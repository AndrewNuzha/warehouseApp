package logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    private final String driverName = "org.sqlite.JDBC";
    private final String connectionString = "jdbc:sqlite:test.db";

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            System.out.println("Отсутствует драйвер для подключения к БД");
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("Невозможно открыть соединение с БД");
            e.printStackTrace();
        }
        return connection;
    }

}
