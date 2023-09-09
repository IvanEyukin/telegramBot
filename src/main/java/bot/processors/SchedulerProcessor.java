package bot.processors;

import bot.message.log.Message;
import bot.message.send.ResponceMessage;
import bot.scheduler.SchedulerMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.abilitybots.api.sender.MessageSender;
import java.util.Map;


public class SchedulerProcessor {

    SchedulerMessage message = new SchedulerMessage();
    Map<Long, String> userMessage = message.botReminder();
    private final ResponceMessage responceMessage;
    final Logger logger = LoggerFactory.getLogger(SchedulerProcessor.class);

    public SchedulerProcessor(MessageSender sender) {
        this.responceMessage = new ResponceMessage(sender);
    }

    public void schedulerStart() {
        for (Map.Entry<Long, String> user : userMessage.entrySet()) {
            responceMessage.sendMessage(user.getKey(), user.getValue());
            logger.info(Message.DISTRIBUTION.concat(Long.toString(user.getKey())));
        }
    }
}