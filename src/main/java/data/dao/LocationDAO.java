package data.dao;

import data.LocationDTO;
import data.entity.Load;
import data.entity.Location;
import data.enums.DatabaseErrorMessages;
import data.enums.SystemMessages;
import logic.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationDAO {

    private DatabaseConnector databaseConnector;
    private String idColumn;
    private String nameColumn;
    private String loadsCountColumn;
    private final String GET_LOCATION_ID_BY_NAME = "SELECT * FROM location WHERE name = ?";
    private final String INSERT_LOCATION_WITH_NAME = "INSERT INTO location(name) VALUES (?);";
    private final String GET_LOCATION_AND_LOADS_COUNT = "SELECT location.name, COUNT(*) AS loadsCount\n" +
            "FROM location LEFT JOIN load ON location.id = load.Loc_id \n" +
            "GROUP BY location.id ORDER BY location.name";
    private final String GET_ALL_LOCATIONS_AND_LOADS = "SELECT location.id AS locationId, " +
            "location.name AS locationName, load.id AS loadId, load.name AS loadName\n" +
            "FROM location \n" +
            "LEFT JOIN load ON location.id = load.Loc_id \n" +
            "ORDER BY location.name ASC, load.id ASC";

    public LocationDAO(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
        this.idColumn = "id";
        this.nameColumn = "name";
        this.loadsCountColumn = "loadsCount";
    }

    public int getLocationIdByName(String name) {
        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_LOCATION_ID_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Location location = new Location(resultSet.getInt(idColumn), resultSet.getString(nameColumn));
                return location.getId();
            }
        } catch (SQLException e) {
            System.out.println(DatabaseErrorMessages.getLocationErrorMessage.getMessage() + name);
        }
        return -1; //если объект с таким именем отсутствует
    }

    public List<LocationDTO> getLocationsDTOs() {
        List<LocationDTO> locationsDTOs = new ArrayList<>();
        try (Connection connection = databaseConnector.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_LOCATION_AND_LOADS_COUNT);
            while (resultSet.next()) {
                LocationDTO locationDTO = new LocationDTO(resultSet.getString(1),
                        resultSet.getInt(2));
                locationsDTOs.add(locationDTO);
            }
        } catch (SQLException exception) {
            System.out.println(DatabaseErrorMessages.getLocationDTOErrorMessage.getMessage());
        }
        return locationsDTOs;
    }

    public Map<Location, List<Load>> getAllLocationsAndLoads() {
        Map<Location, List<Load>> resultMap = new HashMap<>();
        List<Load> locationLoads;
        try (Connection connection = databaseConnector.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_ALL_LOCATIONS_AND_LOADS);
            while (resultSet.next()) {
                Location location = new Location(resultSet.getInt(1), resultSet.getString(2));
                Load load = new Load(resultSet.getInt(3), resultSet.getString(4));

                if (resultMap.containsKey(location)) {
                    resultMap.get(location).add(load);
                } else {
                    locationLoads = new ArrayList<>();
                    resultMap.put(location, locationLoads);
                    String name = load.getName();
                    resultMap.get(location).add(load);
                }
            }
        } catch (SQLException exception) {
            System.out.println(DatabaseErrorMessages.getLocationsAndLoadsErrorMessage.getMessage());
        }
        return resultMap;
    }

    public int insertLocationWithName(String name) {
        int id = 0;
        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_LOCATION_WITH_NAME,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, name);
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
                System.out.println(SystemMessages.locationSavedMessage.getMessage() + name);
            } else {
                throw new SQLException();
            }
        } catch (Exception e) {
            System.out.println(DatabaseErrorMessages.insertLocationErrorMessage.getMessage() + name);
        }
        return id;
    }

}
