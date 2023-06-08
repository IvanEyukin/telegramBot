package LibBaseDto.DtoBaseKeyboard;

import java.util.List;

public class KeyboardMessage {

    List<String> classicButton = List.of("Еда", "Алкоголь", "Транспорт", "Жилье", "Депозит", "Прочее");
    KeyboardInLineButton addButton = KeyboardInLineButton.setInLineButton("Добавить", "Add");
    KeyboardInLineButton deleteButton = KeyboardInLineButton.setInLineButton("Удалить", "Delete");
    KeyboardType keyboardType = new KeyboardType();

    public class KeyboardType {
        public final String classic = "KeyboardMarkup";
        public final String inLine = "InlineKeyboard";
    }

    public List<String> getClassicButton() {
        return classicButton;
    }

    public KeyboardInLineButton getAddButton() {
        return addButton;
    }

    public KeyboardInLineButton getDeleteButton() {
        return deleteButton;
    }

    public KeyboardType getKeyboardType() {
        return keyboardType;
    }

}
