package logic;

import data.DbInfo;
import data.LocationDTO;
import data.dao.LoadDAO;
import data.dao.LocationDAO;
import data.entity.Load;
import data.entity.Location;
import data.enums.SystemMessages;
import utils.RandomStringGenerator;
import utils.StringParser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApplicationService {

    private DatabaseConnector databaseConnector;
    private final LocationDAO locationDAO;
    private final LoadDAO loadDAO;

    public ApplicationService(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
        this.locationDAO = new LocationDAO(databaseConnector);
        this.loadDAO = new LoadDAO(databaseConnector);
    }

    public void insertLocationAndLoad(String[] params) {
        int loadsCount = StringParser.parseInt(params[0]);
        String locationName = params[1];
        int locationId = locationDAO.getLocationIdByName(locationName);
        if (locationId == -1) {
            locationId = locationDAO.insertLocationWithName(locationName);
        }
        List<Load> loads = generateLoads(loadsCount, locationId);
        loadDAO.insertLoadsFromList(loads);
    }

    public void printLocationAndLoadInfo() {
        List<LocationDTO> locationsDTOs = locationDAO.getLocationsDTOs();
        if (locationsDTOs.size() > 0) {
            printLocationDTOs(locationsDTOs);
        } else {
            System.out.println(SystemMessages.noLoadsAndLocationsMessage.getMessage());
        }
    }

    public void generateXmlRecordFile(String fileName) {
        Map<Location, List<Load>> allLocationsAndLoads = locationDAO.getAllLocationsAndLoads();
        DbInfo dbInfo = prepareDbInfo(allLocationsAndLoads);
        if (allLocationsAndLoads.size() > 0) {
            writeXmlFile(dbInfo, fileName);
        } else {
            System.out.println(SystemMessages.noLoadsAndLocationsMessage.getMessage());
        }
    }

    private DbInfo prepareDbInfo(Map<Location, List<Load>> allLocationsAndLoads) {
        List<Location> list = new ArrayList<>(allLocationsAndLoads.keySet());
        for (Location loc : list) {
            List<Load> loads = allLocationsAndLoads.get(loc);
            loc.setLoads(loads);
        }
        DbInfo dbInfo = new DbInfo();
        for (Location loc : list) {
            dbInfo.add(loc);
        }
        return dbInfo;
    }

    private void writeXmlFile(DbInfo dbInfo, String fileName) {
        JAXBContext contextObj;
        FileOutputStream fileOutputStream = null;
        try {
            try {
                contextObj = JAXBContext.newInstance(DbInfo.class);
                Marshaller marshallerObj = contextObj.createMarshaller();
                marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                fileOutputStream = new FileOutputStream(fileName + ".xml");
                marshallerObj.marshal(dbInfo, fileOutputStream);
                System.out.println(SystemMessages.fileSavedMessage.getMessage());
            } catch (JAXBException jaxbException) {
                System.out.println(SystemMessages.marshallerErrorMessage.getMessage());
            } catch (FileNotFoundException ex) {
                System.out.println(SystemMessages.fileNotFoundErrorMessage.getMessage() + fileName);
            } finally {
                fileOutputStream.close();
            }
        } catch (IOException | NullPointerException exception) {
            System.out.println(SystemMessages.fileNotRecordedMessage.getMessage() + fileName);
        }
    }

    private List<Load> generateLoads(int loadsCount, int locationId) {
        List<Load> loadsList = new ArrayList<>();
        for (int i = 0; i < loadsCount; i++) {
            Load load = new Load();
            load.setLocationId(locationId);
            load.setName(generateLoadName());
            loadsList.add(load);
        }
        return loadsList;
    }

    private void printLocationDTOs(List<LocationDTO> locationsDTOs) {
        System.out.printf("%7s %14s", "Имя ячейки", "Кол-во грузов");
        System.out.println();
        System.out.println("---------------------------------");
        for (LocationDTO locationDTO : locationsDTOs) {
            System.out.format("%7s %14s", locationDTO.getName(), locationDTO.getLoadsCount());
            System.out.println();
        }
        System.out.println("---------------------------------");
        System.out.println();
    }

    private String generateLoadName() {
        boolean doesNameAlreadyExist = true;
        String loadName = "";
        while (doesNameAlreadyExist) {
            loadName = RandomStringGenerator.generateString().toUpperCase();
            doesNameAlreadyExist = loadDAO.doesLoadNameAlreadyExist(loadName);
        }
        return loadName;
    }

}
