package LibBaseDto.DtoBaseBot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.math.BigDecimal;
import java.util.List;


public final class BotMessage {

    public final String greeting = """
        Здравствуй %s,
        я твой персональный финансовый ассистент.
        Надеюсь, я смогу помочь тебе тратить меньше денег.
        Не забывай, что ты моя сладкая булочка!
        """;
    public final String mainMenuQuestion = "Что тебя интересует?";
    public final String expensesCategoryQuestion = "Выбери категорию расходов";
    public final String incomeCategoryQuestion = "Выбери категорию доходов";
    public final String categoryError = "Не могу понять в какую категорию записать.\nВыбери категорию";
    public final String finance = "Введи сумму, я запишу ее в категорию ";
    public final String negativeNumber = "Итоговая сумма отрицательная.\nВведи сумму заново";
    public final String zeroNumber = "Итоговая сумма равно 0.\nВведи сумму заново";
    public final String saveQuestion = "Мне записать %s?";
    public final String save = "Записал %s, что-то еще?";
    public final String saveOther = "Для категории Прочее необходимо указать комментарий";
    public final String delete = "Удалил ";
    public final String error = "Я еще только учусь, в будущем смогу помогать тебе лучше!";
    public final String develop = "Извини, данный функционал еще в разработке, я не могу им пользоваться.";

    public final String reportCategoryQuestion = "По какой категории построить отчет?";
    public final String reportPeriodQuestion = "Выбери, за какой период построить отчет:";
    public final String reportResultMessage = "Твои %s за период с %s по %s составили %s";
    public final String reportResultMessageDetail = "Из них:\n";
    public final String reportResultMessageCategory = "%s : %s\n";

    List<SendMessage> messages;
    Message message;
    Message previousMessage;
    CallbackQuery previousMessageCallback;
    BigDecimal financeSum;
    String financeCategory;
    String previousFinanceCategory;
    String financeSubCategory;
    String previousFinanceSubCategory;
    Integer previousBotMessageId;
    String comment;

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

    public String getFinanceSubCategory() {
        return financeSubCategory;
    }

    public void setFinanceSubCategory(String financeSubCategory) {
        this.financeSubCategory = financeSubCategory;
    }

    public String getPreviousFinanceSubCategory() {
        return previousFinanceSubCategory;
    }

    public void setPreviousFinanceSubCategory(String previousFinanceSubCategory) {
        this.previousFinanceSubCategory = previousFinanceSubCategory;
    }

    public Integer getPreviousBotMessageId() {
        return previousBotMessageId;
    }

    public void setPreviousBotMessageId(Integer previousBotMessageId) {
        this.previousBotMessageId = previousBotMessageId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}