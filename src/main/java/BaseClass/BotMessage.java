package BaseClass;

public final class BotMessage {

    final String greeting = """
        Здравствуй %s,
        я твой персональный финансовый ассистент.
        Надеюсь, я смогу помочь тебе тратить меньше денег
        Не забывай, что ты моя сладкая булочка
        """;
    final String category = "Выбери категорию расходов";
    final String finance = "Введи сумму, я запишу ее в категорию ";
    final String negativeNumber = "Итоговая сумма отрицательная.\nВведи сумму заново";
    final String zeroNumber = "Итоговая сумма равно 0.\nВведи сумму заново";
    final String save = "Записал %s, что-то еще?";
    final String delete = "Удалил ";
    final String add = "Хорошо, введи сумму, которую нужно добавить";
    final String error = "Я еще только учусь, в будущем смогу помогать тебе лучше!";

}
