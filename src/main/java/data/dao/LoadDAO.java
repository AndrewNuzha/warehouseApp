package data.dao;

import data.LocationDTO;
import data.entity.Load;
import data.enums.DatabaseErrorMessages;
import data.enums.SystemMessages;
import logic.DatabaseConnector;

import java.sql.*;
import java.util.List;

public class LoadDAO {

    private DatabaseConnector databaseConnector;
    private String idColumn;
    private String nameColumn;
    private String locationIdColumn;
    private final String INSERT_LOADS_FROM_LIST = "INSERT INTO load(name, Loc_id) VALUES (?,?);";
    private final String GET_LOADS_COUNT_WITH_NAME = "SELECT COUNT(*)\n" +
            "FROM load \n" +
            "WHERE name = ?";

    public LoadDAO(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
        this.idColumn = "id";
        this.nameColumn = "name";
        this.locationIdColumn = "Loc_id";
    }

    public void insertLoadsFromList(List<Load> loadsList) {
        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_LOADS_FROM_LIST)) {
            connection.setAutoCommit(false);
            for (Load load : loadsList) {
                preparedStatement.setString(1, load.getName());
                preparedStatement.setInt(2, load.getLocationId());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);
            System.out.println(SystemMessages.loadsSavedMessage.getMessage());
        } catch (SQLException ex) {
            System.out.println(DatabaseErrorMessages.loadsSaveErrorMessage.getMessage());
        }
    }

    public boolean doesLoadNameAlreadyExist(String loadName) {
        boolean result = false;
        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_LOADS_COUNT_WITH_NAME)) {
            preparedStatement.setString(1, loadName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int loadsWithSameNameCount = resultSet.getInt(1);
                if (loadsWithSameNameCount != 0) {
                    result = true;
                }
            }
        } catch (SQLException exception) {
            System.out.println(DatabaseErrorMessages.getSameLoadsErrorMessage.getMessage());
        }
        return result;
    }
}
