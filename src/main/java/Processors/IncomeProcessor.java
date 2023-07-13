package Processors;

import TelegramBot.BotSendMessage;
import Utils.Parser;
import bot.dto.Bot;
import bot.keyboard.Keyboard;
import bot.message.Finance;
import bot.state.State;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;


public class IncomeProcessor {

    public Bot getIncome(Bot bot) {

        List<SendMessage> messages = new ArrayList<>();
        BotSendMessage sendMessage = new BotSendMessage();
        FinancialCalculationProcessor finance = new FinancialCalculationProcessor();

        switch (bot.getState()) {
            case IncomeMenu -> {
                if (Keyboard.replyKeyboar.INCOME.contains(bot.getUserMessageText())) {
                    bot.setSubCategory(bot.getUserMessageText());

                    if (bot.getSubCategory().equals("Прочее")) {
                        bot.updateBotState(State.WaitingComment);
                        messages.add(sendMessage.sendMessage(Finance.SAVE_OTHER));
                    } else {
                        bot.updateBotState(State.WaitingSum);
                        messages.add(sendMessage.sendMessage(Finance.NUMBER.concat(bot.getUserMessageText())));
                    }
                } else {
                    messages.add(sendMessage.sendMessageAndKeyboard(Finance.CATEGORY_ERROR, Keyboard.replyKeyboar.INCOME));
                }
            }
            case WaitingSum -> {
                if (bot.getUserMessageText().matches(Parser.regNumberValid) || bot.getUserMessageText().matches(Parser.regNumberNoValid)) {
                    bot = finance.getFinance(bot);
                    bot.updateBotState(State.WaitCallbackSaveOrDelete);
                    
                    messages = bot.getMessages();
                } else {
                    messages.add(sendMessage.sendMessage(Finance.NUMBER_ERROR));
                }
            }
            case WaitingComment -> {
                bot.updateBotState(State.WaitingSum);
                bot.setComment(bot.getUserMessageText());
                messages.add(sendMessage.sendMessage(Finance.NUMBER.concat(bot.getSubCategory()))); 
            }
            default -> {
                bot.updateBotState(State.IncomeMenu);
                messages.add(sendMessage.sendMessageAndKeyboard(Finance.INCOME, Keyboard.replyKeyboar.INCOME));
            }
        }
        bot.setMessages(messages);

        return bot;
    }
}