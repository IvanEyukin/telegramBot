package Processors;

import LibBaseDto.DtoBaseBot.BotMessage;
import LibBaseDto.DtoBaseKeyboard.KeyboardMessage;
import TelegramBot.BotSendMessage;
import Utils.Parser;
import bot.state.State;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class FinancialCalculationProcessor {

    public BotMessage getFinance(BotMessage botMessage) {

        BotSendMessage sendMessage = new BotSendMessage();
        KeyboardMessage keyboardMessage = new KeyboardMessage();
        List<SendMessage> messages = new ArrayList<>();
        BigDecimal number = Parser.parseIntToString(botMessage.getUserMessageText());

        switch (number.compareTo(new BigDecimal("0"))) {
            case (-1) -> {
                messages.add(sendMessage.sendMessage(botMessage.negativeNumber));
            }
            case (0) -> {
                messages.add(sendMessage.sendMessage(botMessage.zeroNumber));
            }
            case (1) -> {
                if (botMessage.getFinanceSum() == null || botMessage.getPreviousBotState() != State.WaitCallbackSaveOrDelete) {
                    botMessage.setFinanceSum(number);
                } else {
                    botMessage.setFinanceSum(number.add(botMessage.getFinanceSum()));
                }

                messages.add(sendMessage.sendMessageAndInline(String.format(botMessage.saveQuestion, botMessage.getFinanceSum()),keyboardMessage.getResulButtons()));
                botMessage.setMessageHasInLineKeyboaard(true);
            }
        }

        botMessage.setMessages(messages);

        return botMessage;

    }

}