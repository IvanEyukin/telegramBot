package bot.message.callback;

import bot.keyboard.Keyboard;

import java.util.Map;


public class CallbackSetting {
    public final static Map<String, String> notification = Map.of(
        "all", "напоминать регулярно",
        "active", "напоминать, если сегодня не было диалога с ботом",
        "disabled", "не напоминать"
    );
    public final static Map<String, String> notificationKey = Map.of(
        Keyboard.setting.get(0).getCallbackData(), "all",
        Keyboard.setting.get(1).getCallbackData(), "active",
        Keyboard.setting.get(2).getCallbackData(), "disabled"
    );
}