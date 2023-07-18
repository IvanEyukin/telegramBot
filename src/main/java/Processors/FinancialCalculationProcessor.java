package Processors;

import TelegramBot.BotSendMessage;
import bot.dto.Bot;
import bot.keyboard.Keyboard;
import bot.message.Finance;
import bot.state.State;
import bot.utils.Parser;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class FinancialCalculationProcessor {

    public Bot getFinance(Bot bot) {

        BotSendMessage sendMessage = new BotSendMessage();
        List<SendMessage> messages = new ArrayList<>();
        BigDecimal number = Parser.calculationNumberFromString(bot.getUserMessageText());

        switch (number.compareTo(new BigDecimal("0"))) {
            case (-1) -> {
                messages.add(sendMessage.sendMessage(Finance.NUMBER_NEGATIVE));
            }
            case (0) -> {
                messages.add(sendMessage.sendMessage(Finance.NUMBER_ZERO));
            }
            case (1) -> {
                if (bot.getSum() == null || bot.getPreviousState() != State.WaitCallbackSaveOrDelete) {
                    bot.setSum(number);
                } else {
                    bot.setSum(number.add(bot.getSum()));
                }

                messages.add(sendMessage.sendMessageAndInline(String.format(Finance.SAVE_QUESTION, bot.getSum()), Keyboard.finance));
                bot.setMessageHasInLineKeyboaard(true);
            }
        }
        bot.setMessages(messages);

        return bot;
    }
}