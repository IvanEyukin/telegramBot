package bot.handler.message.finance;

import bot.entitie.Bot;
import bot.keyboard.Keyboard;
import bot.message.Finance;
import bot.message.send.MessageBuilder;
import bot.state.State;
import bot.utils.Parser;

import org.apache.commons.lang3.math.NumberUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class HandlerCalculationSum {

    Bot bot;
    MessageBuilder message = new MessageBuilder();
    List<SendMessage> messages = new ArrayList<>();

    public HandlerCalculationSum(Bot bot) {
        this.bot = bot;
    }

    private BigDecimal parseNumber() {
        BigDecimal number;
        if (bot.getSum() == null || bot.getPreviousState() != State.WaitCallbackSaveOrDelete) {
            number = Parser.calculationNumberFromString(bot.getUserMessageText());
        } else {
            String numberSecond;
            if (NumberUtils.isCreatable(bot.getUserMessageText())) {
                numberSecond = "+".concat(bot.getUserMessageText());
            } else {
                numberSecond = Parser.checkAndConcatFirstChar(bot.getUserMessageText());
            }
            number = Parser.calculationNumberFromString(bot.getSum().toEngineeringString().concat(numberSecond));
        }
        return number;
    }

    public Bot getCalculation() {
        BigDecimal number = parseNumber();
        switch (number.compareTo(new BigDecimal("0"))) {
            case (-1) -> {
                bot.setSum(null);
                messages.add(message.sendMessage(Finance.NUMBER_NEGATIVE));
            }
            case (0) -> {
                bot.setSum(null);
                messages.add(message.sendMessage(Finance.NUMBER_ZERO));
            }
            case (1) -> {
                bot.setSum(number);
                bot.setMessageHasInLineKeyboaard(true);
                messages.add(message.sendMessageAndInline(String.format(Finance.SAVE_QUESTION, bot.getSum()), Keyboard.finance));
            }
        }
        bot.setMessages(messages);

        return bot;
    }
}