package bot.main;

import Scheduler.BotReminderTask;
import Scheduler.ScheduledTask;
import bot.processors.SchedulerProcessor;
import bot.route.RouteMain;
import bot.setting.Setting;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.meta.api.objects.Update;


public class FinanceBot extends AbilityBot implements BotReminderTask.Callback{

    private final ScheduledTask scheduledTask;
    
    private FinanceBot(String botToken, String botUsername) {
        super(botToken, botUsername);
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
        RouteMain route = new RouteMain(update, sender);
        route.route();
    }

    @Override
    public void onTimeForBotReminderTask() {
        SchedulerProcessor scheduler = new SchedulerProcessor(sender);
        scheduler.schedulerStart();
    }
}