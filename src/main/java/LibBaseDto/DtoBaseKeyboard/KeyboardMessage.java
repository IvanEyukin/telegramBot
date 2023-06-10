package LibBaseDto.DtoBaseKeyboard;

import java.util.List;

public class KeyboardMessage {

    List<String> classicButton = List.of("Еда", "Алкоголь", "Транспорт", "Жилье", "Депозит", "Прочее");
    KeyboardInLineButton deleteButton = KeyboardInLineButton.setInLineButton("Удалить", "Delete");
    KeyboardInLineButton saveButton = KeyboardInLineButton.setInLineButton("Сохранить", "Save");
    KeyboardType keyboardType = new KeyboardType();

    public class KeyboardType {
        public final String classic = "KeyboardMarkup";
        public final String inLine = "InlineKeyboard";
    }

    public List<String> getClassicButton() {
        return classicButton;
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