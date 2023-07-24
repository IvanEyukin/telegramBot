package bot.message.send;

import bot.entitie.Bot;
import bot.session.Session;

import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


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

    public void sendMessage(Bot bot, SendMessage message) {
        message.setChatId(bot.getUser().getId());

        try {
            bot.setBotMessageId(sender.execute(message).getMessageId());
            Sessions.setSession(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Bot bot, EditMessageReplyMarkup message) {
        message.setChatId(bot.getUser().getId());

        try {
            sender.execute(message);
            Sessions.setSession(bot);
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