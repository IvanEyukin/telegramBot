package LibBaseDto.DtoBaseBot;

public final class BotMessage {

    public final String greeting = """
        Здравствуй %s,
        я твой персональный финансовый ассистент.
        Надеюсь, я смогу помочь тебе тратить меньше денег
        Не забывай, что ты моя сладкая булочка
        """;
    public final String category = "Выбери категорию расходов";
    public final String finance = "Введи сумму, я запишу ее в категорию ";
    public final String negativeNumber = "Итоговая сумма отрицательная.\nВведи сумму заново";
    public final String zeroNumber = "Итоговая сумма равно 0.\nВведи сумму заново";
    public final String save = "Записал %s, что-то еще?";
    public final String delete = "Удалил ";
    public final String add = "Хорошо, введи сумму, которую нужно добавить";
    public final String error = "Я еще только учусь, в будущем смогу помогать тебе лучше!";

}
