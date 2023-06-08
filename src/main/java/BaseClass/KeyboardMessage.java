package BaseClass;

import java.util.List;

public class KeyboardMessage {

    List<String> classicButton = List.of("Еда", "Алкоголь", "Транспорт", "Жилье", "Депозит", "Прочее");
    BotinLineButton addButton = BotinLineButton.setInLineButton("Добавить", "Add");
    BotinLineButton deleteButton = BotinLineButton.setInLineButton("Удалить", "Delete");
    KeyboardType keyboardType = new KeyboardType();

    public class KeyboardType {
        final String classic = "KeyboardMarkup";
        final String inLine = "InlineKeyboard";
    }

}
