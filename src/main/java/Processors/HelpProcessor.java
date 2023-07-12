package Processors;

import TelegramBot.BotSendMessage;
import bot.keyboard.Keyboard;
import bot.message.BotMessage;
import bot.message.Help;
import bot.state.State;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;


public class HelpProcessor {

    public BotMessage getHelp(BotMessage botMessage) {

        List<SendMessage> messages = new ArrayList<SendMessage>();
        BotSendMessage sendMessage = new BotSendMessage();

        switch (botMessage.getSession()) {
            case HelpMenu -> {
                if (Keyboard.replyKeyboar.HELP.contains(botMessage.getUserMessageText())) {
                    if (botMessage.getUserMessageText().contains("О боте")) {
                        messages.add(sendMessage.sendMessageAndKeyboard(Help.INFO_BOT, Keyboard.replyKeyboar.HELP));
                    } else if (botMessage.getUserMessageText().contains("Как бот работает с информацией")) {
                        messages.add(sendMessage.sendMessageAndInline(Help.INFO_QUESTION, Keyboard.help));
                        botMessage.setMessageHasInLineKeyboaard(true);
                        botMessage.updateBotState(State.InformationRetentionQuestionsSelection);
                    }
                } else {
                    messages.add(sendMessage.sendMessageAndKeyboard(Help.ERROR, Keyboard.replyKeyboar.HELP));
                }
            }
            default -> {
                messages.add(sendMessage.sendMessageAndKeyboard(String.format(Help.START, botMessage.getUserInfo().getUser()), Keyboard.replyKeyboar.HELP));
                botMessage.updateBotState(State.HelpMenu);
            }
        }
        botMessage.setMessages(messages);

        return botMessage;
    }
}