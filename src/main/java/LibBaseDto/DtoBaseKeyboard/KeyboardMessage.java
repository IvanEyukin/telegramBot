package LibBaseDto.DtoBaseKeyboard;

import java.util.List;


public class KeyboardMessage {

    List<String> expensesMenuButton = List.of("Еда", "Алкоголь", "Транспорт", "Жилье", "Депозит", "Прочее");
    List<String> incomeMenuButton = List.of("Зарплата", "Подарки", "Снятие с депозита", "Прочее");
    List<String> reportMenuButton = List.of("Доходы", "Расходы", "Бюджет");
    List<String> helpMenuButton = List.of("О боте", "Как бот работает с информацией");
    List<String> settingMenuButton = List.of("Настройка напоминаний");
    List<String> adminMenuButton = List.of("Сообщение пользователям");

    KeyboardInLineButton deleteButton = KeyboardInLineButton.setInLineButton("Удалить", "Delete");
    KeyboardInLineButton saveButton = KeyboardInLineButton.setInLineButton("Сохранить", "Save");

    KeyboardInLineButton toDayButton = KeyboardInLineButton.setInLineButton("Сегодня", "ToDay");
    KeyboardInLineButton lastDayButton = KeyboardInLineButton.setInLineButton("Вчера", "LastDay");
    KeyboardInLineButton lastWeekButton = KeyboardInLineButton.setInLineButton("Неделя", "LastWeek");
    KeyboardInLineButton lastTwoWeekButton = KeyboardInLineButton.setInLineButton("Две недели", "LastTwoWeek");
    KeyboardInLineButton lastMonthButton = KeyboardInLineButton.setInLineButton("Месяц", "LastMonth");

    KeyboardInLineButton privatInfoButton = KeyboardInLineButton.setInLineButton("Какую информацию ты сохраняешь?", "HelpPrivat");
    KeyboardInLineButton saveInfoButton = KeyboardInLineButton.setInLineButton("Когда ты сохраняешь данные?", "HelpSave");
    KeyboardInLineButton deleteInfoButton = KeyboardInLineButton.setInLineButton("Можно ли удалить сохраненные данные?", "HelpDelete");
    KeyboardInLineButton writInfoButton = KeyboardInLineButton.setInLineButton("Как записывать расходы/доходы?", "HelpWrit");

    KeyboardInLineButton notificationAllButton = KeyboardInLineButton.setInLineButton("Напоминать всегда ", "NotificationAll");
    KeyboardInLineButton notificationActiveButton = KeyboardInLineButton.setInLineButton("Если сегодня не было общения с ботом", "NotificationActive");
    KeyboardInLineButton notificationFalseButton = KeyboardInLineButton.setInLineButton("Не напоминать", "NotificationDisabled");

    List<KeyboardInLineButton> resulButtons = List.of(deleteButton, saveButton);
    List<KeyboardInLineButton> reportButtons = List.of(toDayButton, lastDayButton, lastWeekButton, lastTwoWeekButton, lastMonthButton);
    List<KeyboardInLineButton> helpButtons = List.of(privatInfoButton, saveInfoButton, deleteInfoButton, writInfoButton);
    List<KeyboardInLineButton> notificationButtons = List.of(notificationAllButton, notificationActiveButton, notificationFalseButton);

    public List<String> getExpensesMenuButton() {
        return expensesMenuButton;
    }

    public List<String> getIncomeMenuButton() {
        return incomeMenuButton;
    }

    public List<String> getReportMenuButton() {
        return reportMenuButton;
    }

    public List<String> getHelpMenuButton() {
        return helpMenuButton;
    }

    public List<String> getSettingMenuButton() {
        return settingMenuButton;
    }

    public List<String> getAdminMenuButton() {
        return adminMenuButton;
    }
    
    public KeyboardInLineButton getDeleteButton() {
        return deleteButton;
    }

    public KeyboardInLineButton getSaveButton() {
        return saveButton;
    }

    public KeyboardInLineButton getToDayButton() {
        return toDayButton;
    }

    public KeyboardInLineButton getLastDayButton() {
        return lastDayButton;
    }

    public KeyboardInLineButton getLastWeekButton() {
        return lastWeekButton;
    }

    public KeyboardInLineButton getLastTwoWeekButton() {
        return lastTwoWeekButton;
    }

    public KeyboardInLineButton getLastMonthButton() {
        return lastMonthButton;
    }

    public KeyboardInLineButton getPrivatInfoButton() {
        return privatInfoButton;
    }

    public KeyboardInLineButton getSaveInfoButton() {
        return saveInfoButton;
    }

    public KeyboardInLineButton getDeleteInfoButton() {
        return deleteInfoButton;
    }

    public KeyboardInLineButton getWritInfoButton() {
        return writInfoButton;
    }

    public KeyboardInLineButton getNotificationAllButton() {
        return notificationAllButton;
    }

    public KeyboardInLineButton getNotificationActiveButton() {
        return notificationActiveButton;
    }

    public KeyboardInLineButton getNotificationFalseButton() {
        return notificationFalseButton;
    }

    public List<KeyboardInLineButton> getResulButtons() {
        return resulButtons;
    }

    public List<KeyboardInLineButton> getReportButtons() {
        return reportButtons;
    }

    public List<KeyboardInLineButton> getHelpButtons() {
        return helpButtons;
    }

    public List<KeyboardInLineButton> getNotificationButtons() {
        return notificationButtons;
    }

}