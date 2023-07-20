package TelegramBot;

import Scheduler.BotReminderTask;
import Scheduler.ScheduledTask;
import Scheduler.SchedulerMessage;
import bot.entitie.Bot;
import bot.entitie.User;
import bot.log.LogMessage;
import bot.log.Message;
import bot.log.Service;
import bot.message.Admin;
import bot.route.RouteCallback;
import bot.route.RouteMessage;
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
        Bot bot = new Bot();
        User user = new User();

        if (update.hasMessage() && update.getMessage().hasText()) {

            user.setId(update.getMessage().getChat().getId());
            user.setName(update.getMessage().getChat().getUserName());
            user.setFirstName(update.getMessage().getChat().getFirstName());
            user.setLastName(update.getMessage().getChat().getLastName());
            user.setDateMessage(update.getMessage().getDate());

            bot.setUser(user);
            bot.setUserMessageText(update.getMessage().getText());

            bot = Sessions.getSession(bot);

            if (bot.getMessageHasInLineKeyboaard() == true) {
                responceMessage.sendMessage(bot, sendMessage.updateMessage(bot.getUser().getId(), bot.getBotMessageId()));
                bot.setMessageHasInLineKeyboaard(false);
            }

            bot = routeMessage.routeMessageProcessor(bot);
            for (SendMessage message : bot.getMessages()) {
                responceMessage.sendMessage(bot, message);
            }

            if (bot.getAdminNotificationMessages() != null && !bot.getAdminNotificationMessages().isEmpty()) {
                for (Map.Entry<Long, String> adminMessage : bot.getAdminNotificationMessages().entrySet()) {
                    responceMessage.sendMessage(adminMessage.getKey(), adminMessage.getValue());
                    LogMessage.outLogMessage(Service.NOTIFICATION, Message.DISTRIBUTION.concat(Long.toString(adminMessage.getKey())));
                }
                responceMessage.sendMessage((long) Setting.creatorId, Admin.NOTIFICATION_STOP);
            }
        }

        if (update.hasCallbackQuery()) {
            responceMessage.answerCallback(update.getCallbackQuery().getId());

            user.setId(update.getCallbackQuery().getFrom().getId());
            bot.setUser(user);
            bot.setCallbackData(update.getCallbackQuery().getData());

            bot = Sessions.getSession(bot);
            bot = routeCallback.routeCallbacProcessor(bot);
            for (SendMessage message : bot.getMessages()) {
                responceMessage.sendMessage(bot, message);
            }
            responceMessage.sendMessage(bot, sendMessage.updateMessage(update.getCallbackQuery().getMessage()));
        }
    }

    @Override
    public void onTimeForBotReminderTask() {
        SchedulerMessage message = new SchedulerMessage();
        Map<Long, String> userMessage = message.botReminder();

        for (Map.Entry<Long, String> user : userMessage.entrySet()) {
            responceMessage.sendMessage(user.getKey(), user.getValue());
            LogMessage.outLogMessage(Service.SCHEDULER, Message.DISTRIBUTION.concat(Long.toString(user.getKey())));
        }
    }
}