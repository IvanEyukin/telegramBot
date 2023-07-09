package TelegramBot;

import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import LibBaseDto.DtoBaseBot.BotMessage;
import bot.session.Session;


public class ResponceMessage {

    private final MessageSender sender;
    Session Sessions = new Session();

    public ResponceMessage(MessageSender sender) {
        this.sender = sender;
    }

    public void sendMessage(Long chatId, String message) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);

        try {
            sender.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public void sendMessage(BotMessage botMessage, SendMessage message) {

        message.setChatId(botMessage.getUserInfo().getId());

        try {
            botMessage.setPreviousBotMessageId(sender.execute(message).getMessageId());
            Sessions.setSession(botMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public void sendMessage(BotMessage botMessage, EditMessageReplyMarkup message) {

        message.setChatId(botMessage.getUserInfo().getId());

        try {
            sender.execute(message);
            Sessions.setSession(botMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public void answerCallback(String callbackId) {

        AnswerCallbackQuery answerCallback = new AnswerCallbackQuery();
        answerCallback.setCallbackQueryId(callbackId);

        try {
            sender.execute(answerCallback);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

}