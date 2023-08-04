package bot.keyboard.inline;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;


public class InLineKeyboard {

    private static InlineKeyboardMarkup setKeyboardInLine(List<InlineKeyboardButton> buttons) {
        List<List<InlineKeyboardButton>> buttonsKeyboard = new ArrayList<List<InlineKeyboardButton>>();
        buttonsKeyboard.add(buttons);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(buttonsKeyboard);

        return inlineKeyboardMarkup;
    }

    private static InlineKeyboardMarkup setKeyboardInColumn(List<InlineKeyboardButton> buttons) {
        List<List<InlineKeyboardButton>> buttonsKeyboard = new ArrayList<List<InlineKeyboardButton>>();
        for (InlineKeyboardButton button : buttons) {
            List<InlineKeyboardButton> row = new ArrayList<InlineKeyboardButton>();
            row.add(button);
            buttonsKeyboard.add(row);
        }
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(buttonsKeyboard);

        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardButton setButton(String name, String callBack) {
        InlineKeyboardButton inLineButton = new InlineKeyboardButton();
        inLineButton.setText(name);
        inLineButton.setCallbackData(callBack);

        return inLineButton;
    }

    public static InlineKeyboardMarkup setKeyboard(List<InlineKeyboardButton> buttons) {
        if (buttons.size() > 2){
            return setKeyboardInColumn(buttons);
        } else {
            return setKeyboardInLine(buttons);
        }
    }
}