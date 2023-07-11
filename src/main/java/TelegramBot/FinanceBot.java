package TelegramBot;

import LibBaseDto.DtoBaseUser.UserInfo;
import LibBaseDto.DtoBaseBot.BotMessage;
import Route.RouteCallback;
import Route.RouteMessage;
import Scheduler.BotReminderTask;
import Scheduler.ScheduledTask;
import Scheduler.SchedulerMessage;
import bot.log.LogMessage;
import bot.session.Session;
import bot.setting.Setting;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.Map;


public class FinanceBot extends AbilityBot implements BotReminderTask.Callback{

    private final ResponceMessage responceMessage;
    private final ScheduledTask scheduledTask;
    
    private FinanceBot(String botToken, String botUsername) {
        super(botToken, botUsername);
        responceMessage = new ResponceMessage(sender);
        scheduledTask = new ScheduledTask(new BotReminderTask(this));
        scheduledTask.startExecutionAt(Setting.schedulerTimeStart.hour, Setting.schedulerTimeStart.minut, Setting.schedulerTimeStart.second);
    }

    public FinanceBot() {
        this(Setting.token, Setting.name) ;
    }

    @Override
    public long creatorId() {
        return Setting.creatorId;
    }

    @Override
    public void onUpdateReceived(Update update) {

        Session Sessions = new Session();
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

            botMessage = Sessions.getSession(botMessage);

            if (botMessage.getMessageHasInLineKeyboaard() == true) {
                responceMessage.sendMessage(botMessage, sendMessage.updateMessage(botMessage.getUserInfo().getId(), botMessage.getPreviousBotMessageId()));
                botMessage.setMessageHasInLineKeyboaard(false);
            }

            botMessage = routeMessage.routeMessageProcessor(botMessage);
            for (SendMessage message : botMessage.getMessages()) {
                responceMessage.sendMessage(botMessage, message);
            }

            if (botMessage.getAdminNotificationMessages() != null && !botMessage.getAdminNotificationMessages().isEmpty()) {
                for (Map.Entry<Long, String> adminMessage : botMessage.getAdminNotificationMessages().entrySet()) {
                    responceMessage.sendMessage(adminMessage.getKey(), adminMessage.getValue());
                    LogMessage.outLogMessage(LogMessage.Service.NOTIFICATION, LogMessage.Message.DISTRIBUTION.concat(Long.toString(adminMessage.getKey())));
                }
                responceMessage.sendMessage((long) Setting.creatorId, botMessage.adminNotificationStop);
            }

        }

        if (update.hasCallbackQuery()) {

            responceMessage.answerCallback(update.getCallbackQuery().getId());

            user.setId(update.getCallbackQuery().getFrom().getId());
            botMessage.setUserInfo(user);
            botMessage.setCallbackData(update.getCallbackQuery().getData());

            botMessage = Sessions.getSession(botMessage);
            botMessage = routeCallback.routeCallbacProcessor(botMessage);
            for (SendMessage message : botMessage.getMessages()) {
                responceMessage.sendMessage(botMessage, message);
            }

            responceMessage.sendMessage(botMessage, sendMessage.updateMessage(update.getCallbackQuery().getMessage()));
            
        }

    }

    @Override
    public void onTimeForBotReminderTask() {

        SchedulerMessage message = new SchedulerMessage();
        Map<Long, String> userMessage = message.botReminder();

        for (Map.Entry<Long, String> user : userMessage.entrySet()) {
            responceMessage.sendMessage(user.getKey(), user.getValue());
            LogMessage.outLogMessage(LogMessage.Service.SCHEDULER, LogMessage.Message.DISTRIBUTION.concat(Long.toString(user.getKey())));
        }

    }

}