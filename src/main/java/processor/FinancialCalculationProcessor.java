package processor;

import LibBaseDto.DtoBaseBot.BotMessage;
import LibBaseDto.DtoBaseKeyboard.KeyboardMessage;
import Utils.BotSendMessage;
import Utils.Parser;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class FinancialCalculationProcessor {

    BotSendMessage sendMessage = new BotSendMessage();
    KeyboardMessage keyboardMessage = new KeyboardMessage();

    public BotMessage getFinance(BotMessage botMessage) {

        List<SendMessage> messages = new ArrayList<>();
        BigDecimal number = Parser.parseIntToString(botMessage.getMessage().getText());

        switch (number.compareTo(new BigDecimal("0"))) {
            case (-1):
                messages.add(sendMessage.sendMessage(botMessage.getMessage(), botMessage.negativeNumber));
                break;
            case (0):
                messages.add(sendMessage.sendMessage(botMessage.getMessage(), botMessage.zeroNumber));
                break;
            case (1):

                if (botMessage.getFinanceSum() == null || !botMessage.getFinanceSubCategory().equals(botMessage.getPreviousFinanceSubCategory())) {
                
                    botMessage.setFinanceSum(number);
                    botMessage.setPreviousFinanceSubCategory(botMessage.getFinanceSubCategory());

                } else {
                    botMessage.setFinanceSum(number.add(botMessage.getFinanceSum()));
                }

                botMessage.setPreviousMessage(botMessage.getMessage());
                messages.add(sendMessage.sendMessageAndInline(botMessage.getMessage(), String.format(botMessage.saveQuestion, botMessage.getFinanceSum()), keyboardMessage.getResulButtons()));

                break;
        }

        botMessage.setMessages(messages);

        return botMessage;

    }

}
