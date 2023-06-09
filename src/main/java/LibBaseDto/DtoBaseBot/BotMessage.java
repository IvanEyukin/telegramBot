package LibBaseDto.DtoBaseBot;

import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

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

    List<SendMessage> messages;
    Message lastMessage;
    CallbackQuery lastMessageCallback;

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

    public List<SendMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<SendMessage> messages) {
        this.messages = messages;
    }

    public CallbackQuery getLastMessageCallback() {
        return lastMessageCallback;
    }

    public void setLastMessageCallback(CallbackQuery lastMessageCallback) {
        this.lastMessageCallback = lastMessageCallback;
    }

}
