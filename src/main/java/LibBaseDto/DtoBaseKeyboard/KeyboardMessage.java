package LibBaseDto.DtoBaseKeyboard;

import java.util.List;

import LibBaseDto.DtoBaseUser.UserMassage;

public class KeyboardMessage {

    UserMassage userMassage = new UserMassage();

    List<String> mainMenuButton = List.of(userMassage.expenses, userMassage.income, userMassage.reports);
    List<String> expensesMenuButton = List.of("Еда", "Алкоголь", "Транспорт", "Жилье", "Депозит", "Прочее");
    List<String> incomeMenuButton = List.of("Зарплата", "Подарки", "Снятие с депозита", "Прочее");
    KeyboardInLineButton deleteButton = KeyboardInLineButton.setInLineButton("Удалить", "Delete");
    KeyboardInLineButton saveButton = KeyboardInLineButton.setInLineButton("Сохранить", "Save");
    KeyboardType keyboardType = new KeyboardType();

    public class KeyboardType {
        public final String classic = "KeyboardMarkup";
        public final String inLine = "InlineKeyboard";
    }

    public List<String> getMainMenuButton() {
        return mainMenuButton;
    }

    public List<String> getExpensesMenuButton() {
        return expensesMenuButton;
    }

    public List<String> getIncomeMenuButton() {
        return incomeMenuButton;
    }

    public KeyboardInLineButton getDeleteButton() {
        return deleteButton;
    }

    public KeyboardInLineButton getSaveButton() {
        return saveButton;
    }

    public KeyboardType getKeyboardType() {
        return keyboardType;
    }

}