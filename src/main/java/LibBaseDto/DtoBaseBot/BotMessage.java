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
    Message previousMessage;
    CallbackQuery previousMessageCallback;
    BigDecimal financeSum;
    String financeCategory;
    String previousFinanceCategory;
    Integer previousBotMessageId;

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

    public Message getPreviousMessage() {
        return previousMessage;
    }

    public void setPreviousMessage(Message previousMessage) {
        this.previousMessage = previousMessage;
    }

    public CallbackQuery getPreviousMessageCallback() {
        return previousMessageCallback;
    }

    public void setPreviousMessageCallback(CallbackQuery previousMessageCallback) {
        this.previousMessageCallback = previousMessageCallback;
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

    public String getPreviousFinanceCategory() {
        return previousFinanceCategory;
    }

    public void setPreviousFinanceCategory(String previousFinanceCategory) {
        this.previousFinanceCategory = previousFinanceCategory;
    }

    public Integer getPreviousBotMessageId() {
        return previousBotMessageId;
    }

    public void setPreviousBotMessageId(Integer previousBotMessageId) {
        this.previousBotMessageId = previousBotMessageId;
    }

}
