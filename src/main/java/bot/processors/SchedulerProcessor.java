package bot.processors;

import TelegramBot.ResponceMessage;
import bot.log.LogMessage;
import bot.log.Message;
import bot.log.Service;
import bot.scheduler.SchedulerMessage;

import org.telegram.abilitybots.api.sender.MessageSender;
import java.util.Map;


public class SchedulerProcessor {

    SchedulerMessage message = new SchedulerMessage();
    Map<Long, String> userMessage = message.botReminder();
    private final ResponceMessage responceMessage;

    public SchedulerProcessor(MessageSender sender) {
        this.responceMessage = new ResponceMessage(sender);
    }

    public void schedulerStart() {
        for (Map.Entry<Long, String> user : userMessage.entrySet()) {
            responceMessage.sendMessage(user.getKey(), user.getValue());
            LogMessage.outLogMessage(Service.SCHEDULER, Message.DISTRIBUTION.concat(Long.toString(user.getKey())));
        }
    }
}