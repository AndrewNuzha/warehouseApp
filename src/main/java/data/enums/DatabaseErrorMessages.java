package data.enums;

public enum DatabaseErrorMessages {

    loadsSaveErrorMessage(ConsoleColors.RED.color() + "Произошла ошибка во время сохранения списка грузов" +
            ConsoleColors.RESET.color()),
    getLocationDTOErrorMessage(ConsoleColors.RED.color() + "Произошла ошибка во время запроса " +
            "на получение списка ячеек и грузов" + ConsoleColors.RESET.color()),
    insertLocationErrorMessage(ConsoleColors.RED.color() + "Произошла ошибка во время записи " +
            "данных ячейки с названием: " + ConsoleColors.RESET.color()),
    getLocationsAndLoadsErrorMessage(ConsoleColors.RED.color() + "Произошла ошибка во время запроса " +
            "на получение списка ячеек и грузов" + ConsoleColors.RESET.color()),
    getSameLoadsErrorMessage(ConsoleColors.RED.color() + "Произошла ошибка при выполнении запроса на проверку " +
            "имени груза" + ConsoleColors.RESET.color()),
    getLocationErrorMessage(ConsoleColors.RED.color() + "Произошла ошибка во время запроса на получение " +
            "данных ячейки с названием: " + ConsoleColors.RESET.color());

    private String message;

    DatabaseErrorMessages(String value) {
        this.message = value;
    }

    public String getMessage() {
        return message;
    }

}
