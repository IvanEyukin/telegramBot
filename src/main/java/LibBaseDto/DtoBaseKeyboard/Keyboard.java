package LibBaseDto.DtoBaseKeyboard;

import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;


public class Keyboard {

    private static List<KeyboardRow> keyboardCategory(List<String> buttons) {

        List<KeyboardRow> keyboard = new ArrayList<>();

        if (buttons.size() > 3) {
        
            List<String> firstButton = buttons.subList(0, (buttons.size() + 1) / 2);
            KeyboardRow keyboardFirstRow = new KeyboardRow();
            for (String first : firstButton) {
                keyboardFirstRow.add(first);
            }

            List<String> secondButton = buttons.subList((buttons.size() + 1) / 2, buttons.size());
            KeyboardRow keyboardSecondRow = new KeyboardRow();
            for (String second : secondButton) {
                keyboardSecondRow.add(second);
            }

            keyboard.add(keyboardFirstRow);
            keyboard.add(keyboardSecondRow);

        } else {

            KeyboardRow keyboardRow = new KeyboardRow();
            for (String button : buttons) {
                keyboardRow.add(button);
            }
            
            keyboard.add(keyboardRow);

        }

        return keyboard;
        
    }

    public static ReplyKeyboardMarkup getKeyboardMarkup(List<String> buttons) {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboardCategory(buttons));
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

    public static InlineKeyboardMarkup getInlineMessageButtons(List<KeyboardInLineButton> buttons) {

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        for (KeyboardInLineButton button : buttons) {
            rowList.add(getButton(button.name, button.callBack));
        }

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;

    }
    
}
