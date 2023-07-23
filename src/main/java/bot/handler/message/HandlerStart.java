package bot.handler.message;

import TelegramBot.BotSendMessage;
import bot.entitie.Bot;
import bot.message.Global;
import bot.state.State;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;


public class HandlerStart {

    Bot bot;
    List<SendMessage> messages = new ArrayList<>();
    BotSendMessage sendMessage = new BotSendMessage();

    public HandlerStart(Bot bot) {
        this.bot = bot;
    }

    public Bot getStart() {
        bot.setSum(null);
        bot.setCategory(null);
        bot.setSubCategory(null);
        bot.updateBotState(State.Start);
        messages.add(sendMessage.sendMessage(String.format(Global.GREETING, bot.getUser().getUser())));
        messages.add(sendMessage.sendMessage(Global.MENU));
        bot.setMessages(messages);

        return bot;
    }
}