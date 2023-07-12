package Processors;

import TelegramBot.BotSendMessage;
import Utils.Parser;
import bot.keyboard.Keyboard;
import bot.message.BotMessage;
import bot.message.Finance;
import bot.state.State;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;


public class ExpensesProcessor {

    public BotMessage getExpenses(BotMessage botMessage) {

        List<SendMessage> messages = new ArrayList<SendMessage>();
        BotSendMessage sendMessage = new BotSendMessage();
        FinancialCalculationProcessor finance = new FinancialCalculationProcessor();

        switch (botMessage.getSession()) {
            case ExpensesMenu -> {
                if (Keyboard.replyKeyboar.EXPENSES.contains(botMessage.getUserMessageText())) {
                    botMessage.setFinanceSubCategory(botMessage.getUserMessageText());

                    if (botMessage.getFinanceSubCategory().equals("Прочее")) {
                        botMessage.updateBotState(State.WaitingComment);
                        messages.add(sendMessage.sendMessage(Finance.SAVE_OTHER));
                    } else {
                        botMessage.updateBotState(State.WaitingSum);
                        messages.add(sendMessage.sendMessage(Finance.NUMBER.concat(botMessage.getUserMessageText())));
                    }
                } else {
                    messages.add(sendMessage.sendMessageAndKeyboard(Finance.CATEGORY_ERROR, Keyboard.replyKeyboar.EXPENSES));
                }
            }
            case WaitingSum -> {
                if (botMessage.getUserMessageText().matches(Parser.regNumberValid) || botMessage.getUserMessageText().matches(Parser.regNumberNoValid)) {
                    botMessage = finance.getFinance(botMessage);
                    botMessage.updateBotState(State.WaitCallbackSaveOrDelete);

                    messages = botMessage.getMessages();
                } else {
                    messages.add(sendMessage.sendMessage(Finance.NUMBER_ERROR));
                }
            }
            case WaitingComment -> {
                botMessage.updateBotState(State.WaitingSum);
                botMessage.setComment(botMessage.getUserMessageText());
                messages.add(sendMessage.sendMessage(Finance.NUMBER.concat(botMessage.getFinanceSubCategory())));
            }
            default -> {
                botMessage.updateBotState(State.ExpensesMenu);
                messages.add(sendMessage.sendMessageAndKeyboard(Finance.EXPENSES, Keyboard.replyKeyboar.EXPENSES));
            }
        }
        botMessage.setMessages(messages);

        return botMessage;
    } 
}