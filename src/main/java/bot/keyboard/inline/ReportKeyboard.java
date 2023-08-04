package bot.keyboard.inline;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import java.util.List;


public class ReportKeyboard {
    private static InlineKeyboardButton toDay = InLineKeyboard.setButton("Сегодня", "ToDay");
    private static InlineKeyboardButton lastDay = InLineKeyboard.setButton("Вчера", "LastDay");
    private static InlineKeyboardButton lastWeek = InLineKeyboard.setButton("Неделя", "LastWeek");
    private static InlineKeyboardButton lastTwoWeek = InLineKeyboard.setButton("Две недели", "LastTwoWeek");
    private static InlineKeyboardButton lastMonth = InLineKeyboard.setButton("Месяц", "LastMonth");

    public final static List<InlineKeyboardButton> REPORT = List.of(toDay, lastDay, lastWeek, lastTwoWeek, lastMonth);
}