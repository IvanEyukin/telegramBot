package BaseClass;

import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;


public class Keyboard {

    private static List<KeyboardRow> keyboardCategory() {

        KeyboardMessage message = new KeyboardMessage();
        List<KeyboardRow> keyboard = new ArrayList<>();
        List<String> button = message.classicButton;
        
        List<String> firstButton = button.subList(0, (button.size() + 1) / 2);
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        for (String first : firstButton) {
            keyboardFirstRow.add(first);
        }

        List<String> secondButton = button.subList((button.size() + 1) / 2, button.size());
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        for (String second : secondButton) {
            keyboardSecondRow.add(second);
        }

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
        KeyboardMessage message = new KeyboardMessage();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        rowList.add(getButton(message.deleteButton.name, message.deleteButton.callBack));
        rowList.add(getButton(message.addButton.name, message.addButton.callBack));
        
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }
    
}
