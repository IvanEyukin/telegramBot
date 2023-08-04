package bot.keyboard.inline;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import java.util.List;


public class SettingKeyboard {
    private static InlineKeyboardButton notificationAll = InLineKeyboard.setButton("Напоминать всегда ", "NotificationAll");
    private static InlineKeyboardButton notificationActive = InLineKeyboard.setButton("Если сегодня не было общения с ботом", "NotificationActive");
    private static InlineKeyboardButton notificationFalse = InLineKeyboard.setButton("Не напоминать", "NotificationDisabled");

    public final static List<InlineKeyboardButton> NOTIFICATION = List.of(notificationAll, notificationActive, notificationFalse);
}