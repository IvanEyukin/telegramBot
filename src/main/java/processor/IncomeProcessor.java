package processor;

import LibBaseDto.DtoBaseBot.BotMessage;
import LibBaseDto.DtoBaseKeyboard.KeyboardMessage;
import Utils.BotSendMessage;
import Utils.Parser;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;


public class IncomeProcessor {

    public BotMessage getIncome(BotMessage botMessage) {

        List<SendMessage> messages = new ArrayList<>();
        KeyboardMessage keyboardMessage = new KeyboardMessage();
        BotSendMessage sendMessage = new BotSendMessage();
        FinancialCalculationProcessor finance = new FinancialCalculationProcessor();
        String messageText = botMessage.getMessage().getText();

        if (keyboardMessage.getIncomeMenuButton().contains(messageText)) {

            botMessage.setFinanceSubCategory(messageText);

            if (botMessage.getFinanceSubCategory().equals("Прочее")) {
                messages.add(sendMessage.sendMessage(botMessage.getMessage(), botMessage.saveOther));
            } else {
                messages.add(sendMessage.sendMessage(botMessage.getMessage(), botMessage.finance.concat(messageText)));
            }

        } else if (botMessage.getFinanceSubCategory() != null && botMessage.getFinanceSum() == null && botMessage.getComment() == null && botMessage.getFinanceSubCategory().equals("Прочее")) {

            botMessage.setComment(messageText);
            messages.add(sendMessage.sendMessage(botMessage.getMessage(), botMessage.finance.concat(botMessage.getFinanceSubCategory()))); 

        } else if (messageText.matches(Parser.regNumberValid) || messageText.matches(Parser.regNumberNoValid)) {

            if (botMessage.getFinanceSubCategory() != null) {  

                botMessage = finance.getFinance(botMessage);
                messages = botMessage.getMessages();

            } else {
                messages.add(sendMessage.sendMessageAndKeyboard(botMessage.getMessage(), botMessage.categoryError, keyboardMessage.getKeyboardType().classic, keyboardMessage.getIncomeMenuButton()));
            }
        
        } else if (botMessage.getFinanceSubCategory() == null) {
            messages.add(sendMessage.sendMessageAndKeyboard(botMessage.getMessage(), botMessage.incomeCategoryQuestion, keyboardMessage.getKeyboardType().classic, keyboardMessage.getIncomeMenuButton()));
        }

        botMessage.setMessages(messages);

        return botMessage;

    }
    
}