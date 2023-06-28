package LibBaseDto.DtoBaseBot;

import java.math.BigDecimal;
import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import BotFSM.BotState;
import LibBaseDto.DtoBaseUser.UserInfo;


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
    public final String financeError = "Мне кажется, что ты ввел не сумму. Пожалуйста, введи Сумму";
    public final String negativeNumber = "Итоговая сумма отрицательная.\nВведи сумму заново";
    public final String zeroNumber = "Итоговая сумма равно 0.\nВведи сумму заново";
    public final String saveQuestion = "Мне записать %s?";
    public final String save = "Записал %s, что-то еще?";
    public final String saveOther = "Для категории Прочее необходимо указать комментарий";
    public final String delete = "Удалил %s. Введи новую сумму";
    public final String error = "Я еще только учусь, в будущем смогу помогать тебе лучше!";
    public final String develop = "Извини, данный функционал еще в разработке, я не могу им пользоваться.";

    public final String reportCategoryQuestion = "По какой категории построить отчет?";
    public final String reportCategoryError = "Извини, но я не понял, по какой категории построить отчет?";
    public final String reportPeriodQuestion = "Выбери, за какой период построить отчет:";
    public final String reportResultMessage = "Твои %s за период с %s по %s составили %s";
    public final String reportResultMessageDetail = "Из них:\n";
    public final String reportResultMessageCategory = "%s : %s\n";
    public final String reportResultMessageError = "Извини, но я не нашел твоих данных в базе";

    public final static String schedulerBotReminder = "Привет %s! Ты ведь не забыл записать расходы и доходы за сегодня?";

    public final String helpStart = "%s, давай я немного расскажу о себе, выбери, что тебе интересно:";
    public final String helpInfoQuestion = "Это очень сложный вопрос, уточни, что конкретно тебя интересует?";
    public final String helpError = "Извини, но я не понял, что именно ты хочешь узнать?";
    public final String helpBotInfo = """
            Я - телеграм бот, разработанный на Java.
            В своей основе я использую библиотеку org.telegram.telegrambots v 6.5.0.
            Я использую две базы данных для себя: 

            Redis - для хранения состояний пользователей.

            SQLite - для хранения информации пользователей.
            """;
    public final String helpInfoSave = """
            Да, я храню информацию о тебе.
            Не переживай, все что я знаю о тебе, это только общедоступная информация Телеграм.
            Как я храню информацию:

            1. Я храню информацию в своей памяти, чтобы понимать, какими моими возможностями ты пользуешься. 
            Кстати, эта информация удаляется спустя 10 минут, если ты со мной не общаешься. 

            2. Я храню всю информацию, что ты попросил меня записать.
            Эта информация обезличина (привязывается к твоему Телеграм ID).
            Доступ к ней имеет только мой разработчик (Не переживай, ему не интересно сколько денег ты тратишь или зарабатываешь).
            """;
    public final String helpSaveQuestion = """
            Я сохраняю данные, когда ты нажимаешь кнопку "Сохранить".
            Если ты ввел информацию, но не нажал конпку "Сохранить", то я ничего не запишу.
            """;
    public final String helpDeleteInfo = """
            К сожалению, пока я не умею удалять информацию, которую ты попросил меня записать.
            Возможно, в будущем появится такая возможность, но пока она не доступна.
            """;
    public final String helpWitingQuestion = """
            Рекомендую вводить только сумму одним числом, так меньше шансов моей ошибки,
            но я могу складывать и вычитать числа, к примеру, если ты напишешь в сообщении: 

            100-50+70-25+19,75
            То я предложу тебе сохранить 114,75.

            Так же я могу складывать числа из нескольких твоих сообщений, если ты не нажмешь на кнопки "Сохранить" или "Удалить", например: 

            Ты: 250
            Я: Мне записать 250?
            Ты: 100
            Я: Мне записать 350?

            Таким образом я умею только складывать, даже если ты напишешь -100.
            """;

    UserInfo userInfo;
    BotState botState;
    BotState previousBotState;
    String userMessageText;
    String callbackData;
    BigDecimal financeSum;
    String financeCategory;
    String financeSubCategory;
    String comment;
    Integer previousBotMessageId;
    Boolean messageHasInLineKeyboaard = false;
    List<SendMessage> messages;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public BotState getBotState() {
        return botState;
    }

    public void setBotState(BotState botState) {
        this.botState = botState;
    }

    public BotState getPreviousBotState() {
        return previousBotState;
    }

    public void setPreviousBotState(BotState previousBotState) {
        this.previousBotState = previousBotState;
    }

    public String getUserMessageText() {
        return userMessageText;
    }

    public void setUserMessageText(String userMessageText) {
        this.userMessageText = userMessageText;
    }

    public String getCallbackData() {
        return callbackData;
    }

    public void setCallbackData(String callbackData) {
        this.callbackData = callbackData;
    }

    public List<SendMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<SendMessage> messages) {
        this.messages = messages;
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

    public String getFinanceSubCategory() {
        return financeSubCategory;
    }

    public void setFinanceSubCategory(String financeSubCategory) {
        this.financeSubCategory = financeSubCategory;
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

    public Boolean getMessageHasInLineKeyboaard() {
        return messageHasInLineKeyboaard;
    }

    public void setMessageHasInLineKeyboaard(Boolean messageHasInLineKeyboaard) {
        this.messageHasInLineKeyboaard = messageHasInLineKeyboaard;
    }

    public void updateBotState(BotState botState) {
        this.previousBotState = getBotState();
        this.botState = botState;
    }

}