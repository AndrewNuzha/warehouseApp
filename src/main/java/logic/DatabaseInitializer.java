package logic;

import data.enums.SystemMessages;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    private DatabaseConnector databaseConnector;
    private final String CREATE_LOCATION_QUERY = "CREATE TABLE IF NOT EXISTS location (\n" +
            "  id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,\n" +
            "  name varchar(32) NOT NULL);";
    private final String CREATE_LOAD_QUERY = "CREATE TABLE IF NOT EXISTS load (\n" +
            "  id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,\n" +
            "  name varchar(32) NOT NULL,\n" +
            "  Loc_id INTEGER,\n" +
            "  FOREIGN KEY(Loc_id) REFERENCES location(id));";

    public DatabaseInitializer(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    public void initializeTables() {
        initializeTable(CREATE_LOCATION_QUERY);
        initializeTable(CREATE_LOAD_QUERY);

    }

    private void initializeTable(String query) {
        try (Connection connection = databaseConnector.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException exception) {
            System.out.println(SystemMessages.dbInitializationErrorMessage.getMessage());
        }
    }
}
