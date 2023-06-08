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
import java.util.List;


public class Telegram extends AbilityBot {

    Bot bot = new Bot();
    BotMessage botMessage = new BotMessage();
    KeyboardMessage keyboardMessage = new KeyboardMessage();

    public Telegram(Bot bot) {
        super(bot.getToken(), bot.getName());
    }

    @Override
    public long creatorId() {
        return bot.getCreatorId();
    }

    @Override
    public void onUpdateReceived(Update update) {

        List<String> categoryList = keyboardMessage.classicButton;
        BigDecimal number;

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Message message = update.getMessage();
            
            if (messageText.equals("Привет") || messageText.equals("/start")) {

                sendMessage(message, String.format(botMessage.greeting, update.getMessage().getChat().getFirstName()));
                sendMessageAndKeyboard(message, botMessage.category, keyboardMessage.keyboardType.classic);

            } else if (categoryList.contains(messageText)) {
                sendMessage(message, botMessage.finance.concat(messageText));
            } else if (messageText.matches("((-|\\+)?[0-9]+(\\,[0-9]+)?)+")
                    || messageText.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {

                number = Parser.parseIntToString(messageText);
                switch (number.compareTo(new BigDecimal("0"))) {
                    case (-1):
                        sendMessage(message, botMessage.negativeNumber);
                        break;
                    case (0):
                        sendMessage(message, botMessage.zeroNumber);
                        break;
                    case (1):
                        message.setText(number.toEngineeringString());
                        bot.setLastMessage(message);
                        sendMessageAndKeyboard(message, String.format(botMessage.save, number), keyboardMessage.keyboardType.inLine);
                        break;
                }

            } else {
                sendMessageAndKeyboard(message, botMessage.error, keyboardMessage.keyboardType.classic);
            }
        }

        if (update.hasCallbackQuery()) {

            answerCallback(update.getCallbackQuery().getId());
            String callBack = update.getCallbackQuery().getData();
            Message message = update.getCallbackQuery().getMessage();
            
            if (callBack.equals(keyboardMessage.deleteButton.callBack)) {
                sendMessage(message, botMessage.delete.concat(bot.getLastMessage().getText()));
            } else if (callBack.equals(keyboardMessage.addButton.callBack)) {
                sendMessage(message, botMessage.add);
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
            e.printStackTrace();
        }

    }

    private void sendMessageAndKeyboard(Message message, String textToSend, String keyboardType) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(textToSend);

        if (keyboardType.equals(keyboardMessage.keyboardType.classic)) {
            sendMessage.setReplyMarkup(Keyboard.getKeyboardMarkup());
        } else if (keyboardType.equals(keyboardMessage.keyboardType.inLine)) {
            sendMessage.setReplyMarkup(Keyboard.getInlineMessageButtons());
        } 

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    private void answerCallback (String callbackId) {

        AnswerCallbackQuery answerCallback = new AnswerCallbackQuery();
        answerCallback.setCallbackQueryId(callbackId);

        try {
            execute(answerCallback);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

}
