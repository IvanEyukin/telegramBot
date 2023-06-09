import route.RouteCallback;
import route.RouteMessage;
import LibBaseDto.DtoBaseBot.Bot;
import LibBaseDto.DtoBaseBot.BotMessage;
import LibBaseDto.DtoBaseKeyboard.KeyboardMessage;
import LibBaseDto.DtoBaseUser.UserCommand;
import LibBaseDto.DtoBaseUser.UserMassage;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class Telegram extends AbilityBot {

    Bot bot = new Bot();
    BotMessage botMessage = new BotMessage();
    KeyboardMessage keyboardMessage = new KeyboardMessage();
    UserCommand userCommand = new UserCommand();
    UserMassage userMassage = new UserMassage();

    RouteMessage routeMessage = new RouteMessage();
    RouteCallback routeCallback = new RouteCallback();

    public Telegram(Bot bot) {
        super(bot.getToken(), bot.getName());
    }

    @Override
    public long creatorId() {
        return bot.getCreatorId();
    }

    private void sendMessage(SendMessage message) {

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    private void sendMessage(EditMessageReplyMarkup message) {

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    private void answerCallback (String callbackId) {

        AnswerCallbackQuery answerCallback = new AnswerCallbackQuery();
        answerCallback.setCallbackQueryId(callbackId);

        try {
            execute(answerCallback);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            botMessage = routeMessage.routeMessageProcessor(update.getMessage());
            for (SendMessage message : botMessage.getMessages()) {
                sendMessage(message);
            }

        }

        if (update.hasCallbackQuery()) {

            botMessage.setLastMessageCallback(update.getCallbackQuery());
            answerCallback(botMessage.getLastMessageCallback().getId());

            botMessage = routeCallback.routeCallbacProcessor(botMessage);
            for (SendMessage message : botMessage.getMessages()) {
                sendMessage(message);
            }

            sendMessage(routeMessage.updateMessage(update.getCallbackQuery().getMessage()));

        }
        
    }

}