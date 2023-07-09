package Processors;

import LibBaseDto.DtoBaseBot.BotMessage;
import LibBaseDto.DtoBaseKeyboard.KeyboardMessage;
import TelegramBot.BotSendMessage;
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

        switch (botMessage.getBotState()) {
            case AdminMenu -> {
                messages.add(sendMessage.sendMessage(botMessage.adminNotification));
                botMessage.updateBotState(State.WaitingMessageMailings);
            }
            case WaitingMessageMailings -> {
                messages.add(sendMessage.sendMessage(botMessage.adminNotificationStart));
                botMessage.setAdminNotificationMessages(notification.startNotification(botMessage.getUserMessageText()));
                botMessage.updateBotState(State.Start);
            }
            default -> {
                messages.add(sendMessage.sendMessageAndKeyboard(botMessage.adminQuestion, keyboardMessage.getAdminMenuButton()));
                botMessage.updateBotState(State.AdminMenu);
            }
        }
        botMessage.setMessages(messages);

        return botMessage;
    }
}