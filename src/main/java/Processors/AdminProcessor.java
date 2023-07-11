package Processors;

import LibBaseDto.DtoBaseKeyboard.KeyboardMessage;
import TelegramBot.BotSendMessage;
import bot.message.Admin;
import bot.message.BotMessage;
import bot.state.State;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;


public class AdminProcessor {

    public BotMessage adminMenu(BotMessage botMessage) {

        NotificationProcessor notification = new NotificationProcessor();
        List<SendMessage> messages = new ArrayList<SendMessage>();
        KeyboardMessage keyboardMessage = new KeyboardMessage();
        BotSendMessage sendMessage = new BotSendMessage();

        switch (botMessage.getSession()) {
            case AdminMenu -> {
                messages.add(sendMessage.sendMessage(Admin.NOTIFICATION));
                botMessage.updateBotState(State.WaitingMessageMailings);
            }
            case WaitingMessageMailings -> {
                messages.add(sendMessage.sendMessage(Admin.NOTIFICATION_START));
                botMessage.setAdminNotificationMessages(notification.startNotification(botMessage.getUserMessageText()));
                botMessage.updateBotState(State.Start);
            }
            default -> {
                messages.add(sendMessage.sendMessageAndKeyboard(Admin.MENU, keyboardMessage.getAdminMenuButton()));
                botMessage.updateBotState(State.AdminMenu);
            }
        }
        botMessage.setMessages(messages);

        return botMessage;
    }
}