import LibBaseDto.DtoBaseBot.BotSetting;
import LibBaseDto.DtoBaseBot.BotMessage;
import LibBaseDto.DtoBaseUser.UserCommand;
import route.RouteCallback;
import route.RouteMessage;
import Utils.BotSendMessage;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class Telegram extends AbilityBot {

    BotSetting botSetting;
    BotSendMessage sendMessage = new BotSendMessage();
    BotMessage botMessage = new BotMessage();
    RouteMessage routeMessage = new RouteMessage();
    RouteCallback routeCallback = new RouteCallback();

    public Telegram(BotSetting botSetting) {
        super(botSetting.getToken(), botSetting.getName());
        this.botSetting = botSetting;
    }

    @Override
    public long creatorId() {
        return botSetting.getCreatorId();
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
                || (botMessage.getFinanceCategory() != null && botMessage.getFinanceCategory().equals(UserCommand.report) && botMessage.getFinanceSubCategory() != null )) {
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

            botMessage = routeCallback.routeCallbacProcessor(botMessage, botSetting);
            for (SendMessage message : botMessage.getMessages()) {
                sendMessage(message);
            }

            sendMessage(sendMessage.updateMessage(update.getCallbackQuery().getMessage()));

        }
        
    }

}