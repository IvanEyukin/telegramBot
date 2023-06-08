package BaseClass;

import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;


public class keyboard {

    private static List<KeyboardRow> keyboardCategory() {

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add("Еда");
        keyboardFirstRow.add("Алкоголь");
        keyboardFirstRow.add("Транспорт");

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add("Жилье");
        keyboardSecondRow.add("Депозит");
        keyboardSecondRow.add("Прочее");

        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);

        return keyboard;
        
    }

    public static ReplyKeyboardMarkup getKeyboardMarkup() {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboardCategory());
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        return replyKeyboardMarkup;
    }

    private static List<InlineKeyboardButton> getButton(String buttonName, String buttonCallBackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(buttonName);
        button.setCallbackData(buttonCallBackData);

        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(button);
        return keyboardButtonsRow;
    }

    public static InlineKeyboardMarkup getInlineMessageButtons() {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        rowList.add(getButton("Удалить", "Delite"));
        rowList.add(getButton("Добавить", "Add"));

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }
    
}
