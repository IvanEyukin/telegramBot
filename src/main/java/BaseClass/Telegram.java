package BaseClass;

import Utils.Parser;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class Telegram extends AbilityBot {

    private static final String GREETING = """
            Здравствуй %s,
            я твой персональный финансовый ассистент.
            Надеюсь, я смогу помочь тебе тратить меньше денег
            Не забывай, что ты моя сладкая булочка
            """;
    private static final String ERROR_MESSAGE = "Я еще только учусь, в будущем смогу помогать тебе лучше!";
    private static final String CATEGORY = "Пожалуйста, выбери категорию расходов";
    private static final String FINANCE_MESSAGE = "Пожалуйста, введи сумму, я запишу ее в категорию ";
    private static final String NEGATIVE_NUMBER_MESSAGE = "Итоговая сумма отрицательная.\nВведите сумму заново";
    private static final String ZERO_NUMBER_MESSAGE = "Итоговая сумма равно 0.\nВведите сумму заново";
    private static final String SAVE_MESSAGE = "Записал %s, что-то еще?";
    private static final String KEYBOARD_KeyboardMarkup = "KeyboardMarkup";
    private static final String KEYBOARD_InlineKeyboard = "InlineKeyboard";
    private static final String DELETE_MESSAGE = "Удалил ";
    private static final String ADD_MESSAGE = "Хорошо, введи сумму, которую нужно добавить";

    Bot bot = new Bot();

    public Telegram(Bot bot) {
        super(bot.getToken(), bot.getName());
    }

    @Override
    public long creatorId() {
        return bot.getCreatorId();
    }

    @Override
    public void onUpdateReceived(Update update) {

        List<String> categoryList = new ArrayList<String>();
        categoryList.add("Еда");
        categoryList.add("Алкоголь");
        categoryList.add("Транспорт");
        categoryList.add("Жилье");
        categoryList.add("Депозит");
        categoryList.add("Прочее");

        BigDecimal number;

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Message message = update.getMessage();
            
            if (messageText.equals("Привет") || messageText.equals("/start")) {

                sendMessage(message, String.format(GREETING, update.getMessage().getChat().getFirstName()));
                sendMessageAndKeyboard(message, CATEGORY, KEYBOARD_KeyboardMarkup);

            } else if (categoryList.contains(messageText)) {
                sendMessage(message, FINANCE_MESSAGE.concat(messageText));
            } else if (messageText.matches("((-|\\+)?[0-9]+(\\,[0-9]+)?)+")
                    || messageText.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {

                number = Parser.parseIntToString(messageText);
                switch (number.compareTo(new BigDecimal("0"))) {
                    case (-1):
                        sendMessage(message, NEGATIVE_NUMBER_MESSAGE);
                        break;
                    case (0):
                        sendMessage(message, ZERO_NUMBER_MESSAGE);
                        break;
                    case (1):
                        message.setText(number.toEngineeringString());
                        bot.setLastMessage(message);
                        sendMessageAndKeyboard(message, String.format(SAVE_MESSAGE, number), KEYBOARD_InlineKeyboard);
                        break;
                }

            } else {
                sendMessageAndKeyboard(message, ERROR_MESSAGE, KEYBOARD_KeyboardMarkup);
            }
        }

        if (update.hasCallbackQuery()) {

            answerCallback(update.getCallbackQuery().getId());
            String callBack = update.getCallbackQuery().getData();
            Message message = update.getCallbackQuery().getMessage();
            
            if (callBack.equals("Delite")) {
                sendMessage(message, DELETE_MESSAGE.concat(bot.getLastMessage().getText()));
            } else if (callBack.equals("Add")) {
                sendMessage(message, ADD_MESSAGE);
            }

            updateMessage(message);

        }
    }

    private void sendMessage(Message message, String textToSend) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(textToSend);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
        }

    }

    private void updateMessage(Message message) {

        EditMessageReplyMarkup updateMesasge = new EditMessageReplyMarkup();
        updateMesasge.setChatId(message.getChatId());
        updateMesasge.setMessageId(message.getMessageId());
        updateMesasge.setReplyMarkup(null);

        try {
            execute(updateMesasge);
        } catch (TelegramApiException e) {
            System.out.println("\n" + "Ошибка обновления сообщения" + "\n");
            e.printStackTrace();
        }

    }

    private void sendMessageAndKeyboard(Message message, String textToSend, String keyboardType) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(textToSend);

        if (keyboardType.equals(KEYBOARD_KeyboardMarkup)) {
            sendMessage.setReplyMarkup(keyboard.getKeyboardMarkup());
        } else if (keyboardType.equals(KEYBOARD_InlineKeyboard)) {
            sendMessage.setReplyMarkup(keyboard.getInlineMessageButtons());
        } 

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println("\n" + "Ошибка отправки сообщения" + "\n");
            e.printStackTrace();
        }

    }

    private void answerCallback (String callbackId) {

        AnswerCallbackQuery answerCallback = new AnswerCallbackQuery();
        answerCallback.setCallbackQueryId(callbackId);

        try {
            execute(answerCallback);
        } catch (TelegramApiException e) {
            System.out.println("\n" + "Ошибка при отправки ответа на Вызов" + "\n");
            e.printStackTrace();
        }

    }

}
