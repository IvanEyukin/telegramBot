package bot.message;

import java.util.Map;


public class Setting {
    public final static String MENU = "Какие настройки тебя интересуют?";
    public final static String NOTIFICATION = "%s, сейчас %s";
    public final static String SAVE = "Записал: ";
    public final static Map<String, String> notification = Map.of(
        "all", "включены регулярные напоминания",
        "active", "включены напоминания, если не было диалога с ботом",
        "disabled", "напоминания выключены"
    );
}