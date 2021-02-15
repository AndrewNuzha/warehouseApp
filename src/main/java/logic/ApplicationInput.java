package logic;

import data.enums.SystemMessages;
import utils.StringParser;

import java.sql.SQLException;
import java.util.Scanner;

public class ApplicationInput {

    private boolean isExitNeeded = false;
    private static final char[] ILLEGAL_CHARACTERS = {'/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*',
            '\\', '<', '>', '|', '\"', ':', '-', '+'};
    private final int MAX_FILE_NAME_LENGTH = 15;

    public void run() {
        Scanner scanner = new Scanner(System.in);
        DatabaseConnector databaseConnector = new DatabaseConnector();
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(databaseConnector);
        ApplicationService applicationService = new ApplicationService(databaseConnector);
        try {
            databaseInitializer.initializeTables();
            System.out.println(SystemMessages.startMessage.getMessage());
            while (!isExitNeeded) {
                int selectedOption = enterOption(scanner);
                if (!isExitNeeded) {
                    if (selectedOption == 1) {
                        String[] params = enterLocationAndLoad(scanner);
                        applicationService.insertLocationAndLoad(params);
                    } else if (selectedOption == 2) {
                        applicationService.printLocationAndLoadInfo();
                    } else {
                        String fileName = enterFileName(scanner);
                        applicationService.generateXmlRecordFile(fileName);
                    }
                }
            }
        } catch (Exception ex) {
            System.err.println(SystemMessages.unknownErrorMessage.getMessage() + ex.getMessage());
        } finally {
            scanner.close();
        }
    }

    public int enterOption(Scanner scanner) {
        boolean isInputCorrect = false;
        int enteredOption = 0;
        while (!isInputCorrect) {
            System.out.print(SystemMessages.selectOptionMessage.getMessage());
            String enteredString = scanner.nextLine();
            try {
                enteredOption = Integer.parseInt(enteredString);
                if (enteredOption == 4) {
                    isExitNeeded = true;
                    break;
                } else if (enteredOption >= 1 && enteredOption <= 4) {
                    isInputCorrect = true;
                } else {
                    throw new IllegalArgumentException();
                }
            } catch (Exception ex) {
                System.out.println(SystemMessages.wrongInputMessage.getMessage());
            }
        }
        return enteredOption;
    }

    public String[] enterLocationAndLoad(Scanner scanner) {
        boolean isInputCorrect = false;
        String[] params = new String[2];
        while (!isInputCorrect) {
            System.out.print(SystemMessages.enterLocationAndLoadMessage.getMessage());
            String enteredString = scanner.nextLine();
            try {
                isInputCorrect = checkLocationAndLoadInput(enteredString);
                if (isInputCorrect) {
                    params = enteredString.split(" ");
                } else {
                    throw new IllegalArgumentException();
                }
            } catch (Exception ex) {
                System.out.println(SystemMessages.wrongInputMessage.getMessage());
            }
        }
        return params;
    }

    public String enterFileName(Scanner scanner) {
        boolean isInputCorrect = false;
        String fileName = "";
        while (!isInputCorrect) {
            System.out.print(SystemMessages.enterFileNameMessage.getMessage());
            String enteredString = scanner.nextLine();
            try {
                isInputCorrect = checkFileName(enteredString);
                if (isInputCorrect) {
                    fileName = enteredString;
                } else {
                    throw new IllegalArgumentException();
                }
            } catch (Exception ex) {
                System.out.println(SystemMessages.wrongInputMessage.getMessage());
            }
        }
        return fileName;
    }

    private boolean checkLocationAndLoadInput(String enteredString) {
        String[] params = enteredString.split(" ");
        boolean checkResult = false;
        if (params.length != 2) {
            return false;
        } else {
            checkResult = StringParser.tryParseInt(params[0]);
            if (StringParser.parseInt(params[0]) <= 0) {
                return false;
            }
            return checkResult;
        }
    }

    private boolean checkFileName(String fileName) {
        boolean result = false;
        if (fileName != null) {
            if (fileName.length() <= MAX_FILE_NAME_LENGTH && fileName.length() > 0) {
                for (char c : ILLEGAL_CHARACTERS) {
                    if (fileName.contains(Character.toString(c))) {
                        return false;
                    }
                }
                result = true;
            }
        }
        return result;
    }

}
