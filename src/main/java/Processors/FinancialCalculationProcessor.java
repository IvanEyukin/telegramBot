package Processors;

import TelegramBot.BotSendMessage;
import bot.entitie.Bot;
import bot.keyboard.Keyboard;
import bot.message.Finance;
import bot.state.State;
import bot.utils.Parser;

import org.apache.commons.lang3.math.NumberUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class FinancialCalculationProcessor {

    public Bot getFinance(Bot bot) {
        BotSendMessage sendMessage = new BotSendMessage();
        List<SendMessage> messages = new ArrayList<>();
        BigDecimal number;

        if (bot.getSum() == null || bot.getPreviousState() != State.WaitCallbackSaveOrDelete) {
            number = Parser.calculationNumberFromString(bot.getUserMessageText());
        } else {
            String numberSecond;

            if (NumberUtils.isCreatable(bot.getUserMessageText())) {
                numberSecond = "+".concat(bot.getUserMessageText());
            } else {
                numberSecond = bot.getUserMessageText();
            }
            number = Parser.calculationNumberFromString(bot.getSum().toEngineeringString().concat(numberSecond));
        }

        switch (number.compareTo(new BigDecimal("0"))) {
            case (-1) -> {
                messages.add(sendMessage.sendMessage(Finance.NUMBER_NEGATIVE));
                bot.setSum(null);
            }
            case (0) -> {
                messages.add(sendMessage.sendMessage(Finance.NUMBER_ZERO));
                bot.setSum(null);
            }
            case (1) -> {
                bot.setSum(number);
                messages.add(sendMessage.sendMessageAndInline(String.format(Finance.SAVE_QUESTION, bot.getSum()), Keyboard.finance));
                bot.setMessageHasInLineKeyboaard(true);
            }
        }
        bot.setMessages(messages);

        return bot;
    }
}