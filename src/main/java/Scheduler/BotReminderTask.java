package Scheduler;

public class BotReminderTask implements Scheduler{

    private final Callback callback;

    public interface Callback {
        void onTimeForBotReminderTask();
    }

    public BotReminderTask(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void execute() {
        callback.onTimeForBotReminderTask();
    }
    
}
