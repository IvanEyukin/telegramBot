package LibBaseDto.DtoBaseUser;

import java.util.Map;

public class UserCommand {

    public static final String start = "/start";
    public static final String expenses = "Расходы";
    public static final String income = "Доходы";
    public static final String report = "Отчеты";
    public static final String help = "Помощь";
    public static final Map<String, String> UserComand = Map.of(
        "/expenses", expenses, 
        "/income", income, 
        "/report", report,
        "/help", help
    );

}