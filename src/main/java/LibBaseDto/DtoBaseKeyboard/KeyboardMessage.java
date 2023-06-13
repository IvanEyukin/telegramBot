package LibBaseDto.DtoBaseKeyboard;

import java.util.List;


public class KeyboardMessage {

    List<String> expensesMenuButton = List.of("Еда", "Алкоголь", "Транспорт", "Жилье", "Депозит", "Прочее");
    List<String> incomeMenuButton = List.of("Зарплата", "Подарки", "Снятие с депозита", "Прочее");
    List<String> reportMenuButton = List.of("Доходы", "Расходы");

    KeyboardInLineButton deleteButton = KeyboardInLineButton.setInLineButton("Удалить", "Delete");
    KeyboardInLineButton saveButton = KeyboardInLineButton.setInLineButton("Сохранить", "Save");
    KeyboardInLineButton lastDayButton = KeyboardInLineButton.setInLineButton("Вчера", "LastDay");
    KeyboardInLineButton lastWeekButton = KeyboardInLineButton.setInLineButton("Неделя", "LastWeek");
    KeyboardInLineButton lastTwoWeekButton = KeyboardInLineButton.setInLineButton("Две недели", "LastTwoWeek");
    KeyboardInLineButton lastMonthButton = KeyboardInLineButton.setInLineButton("Месяц", "LastMonth");

    List<KeyboardInLineButton> resulButtons = List.of(deleteButton, saveButton);
    List<KeyboardInLineButton> reportButtons = List.of(lastDayButton, lastWeekButton, lastTwoWeekButton, lastMonthButton);

    public List<String> getExpensesMenuButton() {
        return expensesMenuButton;
    }

    public List<String> getIncomeMenuButton() {
        return incomeMenuButton;
    }

    public List<String> getReportMenuButton() {
        return reportMenuButton;
    }
    
    public KeyboardInLineButton getDeleteButton() {
        return deleteButton;
    }

    public KeyboardInLineButton getSaveButton() {
        return saveButton;
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

    public List<KeyboardInLineButton> getResulButtons() {
        return resulButtons;
    }

    public List<KeyboardInLineButton> getReportButtons() {
        return reportButtons;
    }

}