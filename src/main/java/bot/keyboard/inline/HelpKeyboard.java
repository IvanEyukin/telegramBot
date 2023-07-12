package bot.keyboard.inline;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import java.util.List;


public class HelpKeyboard {
    private static InlineKeyboardButton privatInfo = InLineKeyboard.setButton("Какую информацию ты сохраняешь?", "HelpPrivat");
    private static InlineKeyboardButton saveInfo = InLineKeyboard.setButton("Когда ты сохраняешь данные?", "HelpSave");
    private static InlineKeyboardButton deleteInfo = InLineKeyboard.setButton("Можно ли удалить сохраненные данные?", "HelpDelete");
    private static InlineKeyboardButton writInfo = InLineKeyboard.setButton("Как записывать расходы/доходы?", "HelpWrit");

    public final static List<InlineKeyboardButton> HELP = List.of(privatInfo, saveInfo, deleteInfo, writInfo);
}