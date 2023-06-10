package LibBaseDto.DtoBaseBot;

import java.math.BigDecimal;
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
    public final String categoryError = "Не могу понять в какую категорию записать.\nВыбери категорию";
    public final String finance = "Введи сумму, я запишу ее в категорию ";
    public final String negativeNumber = "Итоговая сумма отрицательная.\nВведи сумму заново";
    public final String zeroNumber = "Итоговая сумма равно 0.\nВведи сумму заново";
    public final String saveQuestion = "Мне записать %s?";
    public final String save = "Записал %s, что-то еще?";
    public final String delete = "Удалил ";
    public final String error = "Я еще только учусь, в будущем смогу помогать тебе лучше!";

    List<SendMessage> messages;
    Message message;
    Message lastMessage;
    CallbackQuery lastMessageCallback;
    BigDecimal financeSum;
    String financeCategory;
    String lastFinanceCategory;
    Integer lastBotMessageId;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public List<SendMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<SendMessage> messages) {
        this.messages = messages;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

    public CallbackQuery getLastMessageCallback() {
        return lastMessageCallback;
    }

    public void setLastMessageCallback(CallbackQuery lastMessageCallback) {
        this.lastMessageCallback = lastMessageCallback;
    }

    public BigDecimal getFinanceSum() {
        return financeSum;
    }

    public void setFinanceSum(BigDecimal financeSum) {
        this.financeSum = financeSum;
    }

    public String getFinanceCategory() {
        return financeCategory;
    }

    public void setFinanceCategory(String financeCategory) {
        this.financeCategory = financeCategory;
    }

    public String getLastFinanceCategory() {
        return lastFinanceCategory;
    }

    public void setLastFinanceCategory(String lastFinanceCategory) {
        this.lastFinanceCategory = lastFinanceCategory;
    }

    public Integer getLastBotMessageId() {
        return lastBotMessageId;
    }

    public void setLastBotMessageId(Integer lastBotMessageId) {
        this.lastBotMessageId = lastBotMessageId;
    }

}
