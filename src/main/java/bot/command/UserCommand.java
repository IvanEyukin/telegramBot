package bot.command;

import java.util.Map;


public class UserCommand {
    public static final String commandStart = "/start";
    public static final String start = "Привет";
    public static final String expenses = "Расходы";
    public static final String income = "Доходы";
    public static final String report = "Отчеты";
    public static final String help = "Помощь";
    public static final String setting = "Настройки";
    public static final Map<String, String> UserComand = Map.of(
        "/expenses", expenses, 
        "/income", income, 
        "/report", report,
        "/settings", setting,
        "/help", help
    );
}