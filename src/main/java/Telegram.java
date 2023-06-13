import route.RouteCallback;
import route.RouteMessage;
import Utils.BotSendMessage;
import LibBaseDto.DtoBaseBot.Bot;
import LibBaseDto.DtoBaseBot.BotMessage;
import LibBaseDto.DtoBaseUser.UserCommand;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class Telegram extends AbilityBot {

    Bot bot = new Bot();
    BotSendMessage sendMessage = new BotSendMessage();
    BotMessage botMessage = new BotMessage();
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
            botMessage.setPreviousBotMessageId(execute(message).getMessageId());
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

            if ((botMessage.getFinanceSum() != null && botMessage.getFinanceCategory() != null && botMessage.getFinanceSubCategory() != null) 
            || (botMessage.getFinanceCategory() != null && botMessage.getFinanceCategory().equals(UserCommand.report))) {
                sendMessage(sendMessage.updateMessage(update.getMessage().getChatId(), botMessage.getPreviousBotMessageId()));
            }

            botMessage.setMessage(update.getMessage());
            botMessage = routeMessage.routeMessageProcessor(botMessage);
            for (SendMessage message : botMessage.getMessages()) {
                sendMessage(message);
            }

        }

        if (update.hasCallbackQuery()) {

            botMessage.setPreviousMessageCallback(update.getCallbackQuery());
            answerCallback(botMessage.getPreviousMessageCallback().getId());

            botMessage = routeCallback.routeCallbacProcessor(botMessage);
            for (SendMessage message : botMessage.getMessages()) {
                sendMessage(message);
            }

            sendMessage(sendMessage.updateMessage(update.getCallbackQuery().getMessage()));

        }
        
    }

}