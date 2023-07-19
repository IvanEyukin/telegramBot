package Processors;

import TelegramBot.BotSendMessage;
import bot.entitie.Bot;
import bot.keyboard.Keyboard;
import bot.message.Admin;
import bot.state.State;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;


public class AdminProcessor {

    public Bot adminMenu(Bot bot) {

        NotificationProcessor notification = new NotificationProcessor();
        List<SendMessage> messages = new ArrayList<SendMessage>();
        BotSendMessage sendMessage = new BotSendMessage();

        switch (bot.getState()) {
            case AdminMenu -> {
                messages.add(sendMessage.sendMessage(Admin.NOTIFICATION));
                bot.updateBotState(State.WaitingMessageMailings);
            }
            case WaitingMessageMailings -> {
                messages.add(sendMessage.sendMessage(Admin.NOTIFICATION_START));
                bot.setAdminNotificationMessages(notification.startNotification(bot.getUserMessageText()));
                bot.updateBotState(State.Start);
            }
            default -> {
                messages.add(sendMessage.sendMessageAndKeyboard(Admin.MENU, Keyboard.replyKeyboar.ADMIN));
                bot.updateBotState(State.AdminMenu);
            }
        }
        bot.setMessages(messages);

        return bot;
    }
}