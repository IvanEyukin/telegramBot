package TelegramBot;

import LibBaseDto.DtoBaseBot.BotSetting;
import LibBaseDto.DtoBaseUser.UserInfo;
import LibBaseDto.DtoBaseBot.BotMessage;
import Route.RouteCallback;
import Route.RouteMessage;
import BotFSM.BotStateCash;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class FinanceBot extends AbilityBot {

    BotStateCash botStateCash = new BotStateCash();

    private FinanceBot(String botToken, String botUsername) {
        super(botToken, botUsername);
    }

    public FinanceBot() {
        this(BotSetting.token, BotSetting.name);
    }

    @Override
    public long creatorId() {
        return BotSetting.creatorId;
    }

    private void sendMessage(BotMessage botMessage, SendMessage message) {

        message.setChatId(botMessage.getUserInfo().getId());

        try {
            botMessage.setPreviousBotMessageId(execute(message).getMessageId());
            botStateCash.setBotState(botMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    private void sendMessage(BotMessage botMessage, EditMessageReplyMarkup message) {

        message.setChatId(botMessage.getUserInfo().getId());

        try {
            execute(message);
            botStateCash.setBotState(botMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    private void answerCallback(String callbackId) {

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

        BotSendMessage sendMessage = new BotSendMessage();
        RouteMessage routeMessage = new RouteMessage();
        RouteCallback routeCallback = new RouteCallback();
        BotMessage botMessage = new BotMessage();
        UserInfo user = new UserInfo();

        if (update.hasMessage() && update.getMessage().hasText()) {

            user.setId(update.getMessage().getChat().getId());
            user.setName(update.getMessage().getChat().getUserName());
            user.setFirstName(update.getMessage().getChat().getFirstName());
            user.setLastName(update.getMessage().getChat().getLastName());
            user.setDateMessage(update.getMessage().getDate());

            botMessage.setUserInfo(user);
            botMessage.setUserMessageText(update.getMessage().getText());

            botMessage = botStateCash.getBotState(botMessage);

            if (botMessage.getMessageHasInLineKeyboaard() == true) {
                sendMessage(botMessage, sendMessage.updateMessage(botMessage.getUserInfo().getId(), botMessage.getPreviousBotMessageId()));
                botMessage.setMessageHasInLineKeyboaard(false);
            }

            botMessage = routeMessage.routeMessageProcessor(botMessage);
            for (SendMessage message : botMessage.getMessages()) {
                sendMessage(botMessage, message);
            }

        }

        if (update.hasCallbackQuery()) {

            answerCallback(update.getCallbackQuery().getId());

            user.setId(update.getCallbackQuery().getFrom().getId());
            botMessage.setUserInfo(user);
            botMessage.setCallbackData(update.getCallbackQuery().getData());

            botMessage = botStateCash.getBotState(botMessage);
            
            botMessage = routeCallback.routeCallbacProcessor(botMessage);
            for (SendMessage message : botMessage.getMessages()) {
                sendMessage(botMessage, message);
            }

            botMessage.setMessageHasInLineKeyboaard(false);
            sendMessage(botMessage, sendMessage.updateMessage(update.getCallbackQuery().getMessage()));
            
        }

    }

}