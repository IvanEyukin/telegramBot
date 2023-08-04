package bot.handler.message;

import bot.entitie.Bot;
import bot.message.Global;
import bot.message.send.MessageBuilder;
import bot.state.State;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;


public class HandlerStart {

    Bot bot;
    List<SendMessage> messages = new ArrayList<>();
    MessageBuilder message = new MessageBuilder();

    public HandlerStart(Bot bot) {
        this.bot = bot;
    }

    public Bot getStart() {
        bot.setSum(null);
        bot.setCategory(null);
        bot.setSubCategory(null);
        bot.updateBotState(State.Start);
        messages.add(message.sendMessage(String.format(Global.GREETING, bot.getUser().getUser())));
        messages.add(message.sendMessage(Global.MENU));
        bot.setMessages(messages);

        return bot;
    }
}