package bot.keyboard.inline;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import java.util.List;


public class FinanceKeyboard {
    private static InlineKeyboardButton save = InLineKeyboard.setButton("Сохранить", "Save");
    private static InlineKeyboardButton delete = InLineKeyboard.setButton("Удалить", "Delete");

    public final static List<InlineKeyboardButton> FINANCE = List.of(save, delete);
}