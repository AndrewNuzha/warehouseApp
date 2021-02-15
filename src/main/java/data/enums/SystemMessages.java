package data.enums;

public enum SystemMessages {

    startMessage("Программа склада запущена."),
    selectOptionMessage("Пожалуйста, выберите одну из следующих опций, введя ее номер: " +
            "\n 1 - Создать грузы (и ячейку)\n 2 - Вывод информации о грузах в ячейках\n " +
            "3 - Экспорт данных в xml-файл\n " +
            "4 - Завершить работу \n"),
    enterLocationAndLoadMessage("Введите количество грузов (целое положительное число) и имя ячейки, " +
            "разделенные пробелом (напр: 3 AAA):\n"),
    enterFileNameMessage("Введите название файла (макс. длина 15 символов): "),
    noLoadsAndLocationsMessage(ConsoleColors.YELLOW.color() +
            "Пока что вы не добавили ни одной ячейки с грузами\n" + ConsoleColors.RESET.color()),
    locationSavedMessage(ConsoleColors.GREEN.color() + "Записана ячейка с названием: " +
            ConsoleColors.RESET.color()),
    loadsSavedMessage(ConsoleColors.GREEN.color() + "Грузы сохранены\n" + ConsoleColors.RESET.color()),
    fileSavedMessage(ConsoleColors.GREEN.color() + "Файл сохранен и находится в корне каталога\n"
            + ConsoleColors.RESET.color()),
    wrongInputMessage(ConsoleColors.YELLOW.color() + "Некорректный ввод" + ConsoleColors.RESET.color()),
    dbInitializationErrorMessage("При инициализации таблиц БД произошла ошибка\n"),
    marshallerErrorMessage(ConsoleColors.RED.color() + "Во время работы маршаллера произошла ошибка" +
            ConsoleColors.RESET.color()),
    fileNotFoundErrorMessage(ConsoleColors.RED.color() + "Не удалось получить доступ к файлу " +
            ConsoleColors.RESET.color()),
    fileNotRecordedMessage(ConsoleColors.RED.color() + "Не удалось записать файл " + ConsoleColors.RESET.color()),
    unknownErrorMessage("Во время работы программы произошла непредвиденная ошибка: ");


    private String message;

    SystemMessages(String value) {
        this.message = value;
    }

    public String getMessage() {
        return message;
    }

}
