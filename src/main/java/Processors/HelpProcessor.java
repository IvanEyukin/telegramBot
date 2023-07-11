package Processors;

import LibBaseDto.DtoBaseKeyboard.KeyboardMessage;
import TelegramBot.BotSendMessage;
import bot.message.BotMessage;
import bot.message.Help;
import bot.state.State;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;


public class HelpProcessor {

    public BotMessage getHelp(BotMessage botMessage) {

        List<SendMessage> messages = new ArrayList<SendMessage>();
        KeyboardMessage keyboardMessage = new KeyboardMessage();
        BotSendMessage sendMessage = new BotSendMessage();

        switch (botMessage.getSession()) {
            case HelpMenu -> {
                if (keyboardMessage.getHelpMenuButton().contains(botMessage.getUserMessageText())) {
                    if (botMessage.getUserMessageText().contains("О боте")) {
                        messages.add(sendMessage.sendMessageAndKeyboard(Help.INFO_BOT, keyboardMessage.getHelpMenuButton()));
                    } else if (botMessage.getUserMessageText().contains("Как бот работает с информацией")) {
                        messages.add(sendMessage.sendMessageAndInline(Help.INFO_QUESTION, keyboardMessage.getHelpButtons()));
                        botMessage.setMessageHasInLineKeyboaard(true);
                        botMessage.updateBotState(State.InformationRetentionQuestionsSelection);
                    }
                } else {
                    messages.add(sendMessage.sendMessageAndKeyboard(Help.ERROR, keyboardMessage.getHelpMenuButton()));
                }
            }
            default -> {
                messages.add(sendMessage.sendMessageAndKeyboard(String.format(Help.START, botMessage.getUserInfo().getUser()), keyboardMessage.getHelpMenuButton()));
                botMessage.updateBotState(State.HelpMenu);
            }
        }
        botMessage.setMessages(messages);

        return botMessage;
    }
}