package Processors;

import LibBaseDto.DtoBaseBot.BotMessage;
import LibBaseDto.DtoBaseKeyboard.KeyboardMessage;
import TelegramBot.BotSendMessage;
import Utils.Parser;
import BotFSM.BotState;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;


public class ExpensesProcessor {

    public BotMessage getExpenses(BotMessage botMessage) {

        List<SendMessage> messages = new ArrayList<SendMessage>();
        KeyboardMessage keyboardMessage = new KeyboardMessage();
        BotSendMessage sendMessage = new BotSendMessage();
        FinancialCalculationProcessor finance = new FinancialCalculationProcessor();

        switch (botMessage.getBotState()) {
            case ExpensesMenu -> {
                if (keyboardMessage.getExpensesMenuButton().contains(botMessage.getUserMessageText())) {
                    botMessage.setFinanceSubCategory(botMessage.getUserMessageText());

                    if (botMessage.getFinanceSubCategory().equals("Прочее")) {
                        botMessage.updateBotState(BotState.EnterComment);
                        messages.add(sendMessage.sendMessage(botMessage.saveOther));
                    } else {
                        botMessage.updateBotState(BotState.EnterSum);
                        messages.add(sendMessage.sendMessage(botMessage.finance.concat(botMessage.getUserMessageText())));
                    }

                } else {
                    messages.add(sendMessage.sendMessageAndKeyboard(botMessage.categoryError, keyboardMessage.getExpensesMenuButton()));
                }
            }
            case EnterSum -> {
                if (botMessage.getUserMessageText().matches(Parser.regNumberValid) || botMessage.getUserMessageText().matches(Parser.regNumberNoValid)) {
                    botMessage = finance.getFinance(botMessage);
                    botMessage.updateBotState(BotState.WaitCallbackFinance);

                    messages = botMessage.getMessages();
                } else {
                    messages.add(sendMessage.sendMessage(botMessage.financeError));
                }
            }
            case EnterComment -> {
                botMessage.updateBotState(BotState.EnterSum);
                botMessage.setComment(botMessage.getUserMessageText());
                messages.add(sendMessage.sendMessage(botMessage.finance.concat(botMessage.getFinanceSubCategory())));
            }
            default -> {
                botMessage.updateBotState(BotState.ExpensesMenu);
                messages.add(sendMessage.sendMessageAndKeyboard(botMessage.expensesCategoryQuestion, keyboardMessage.getExpensesMenuButton()));
            }
        }

        botMessage.setMessages(messages);

        return botMessage;

    }
    
}