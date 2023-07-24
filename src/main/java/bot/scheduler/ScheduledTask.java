package bot.scheduler;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ScheduledTask {

    private final ScheduledExecutorService executorService;
    private final Scheduler scheduler;

    public ScheduledTask(Scheduler scheduler) {
        this.executorService = Executors.newScheduledThreadPool(1);
        this.scheduler = scheduler;
    }

    public void startExecutionAt(int hour, int min, int sec) {
        Runnable taskWrapper = () -> {
            scheduler.execute();
            startExecutionAt(hour, min, sec);
        };
        long delay = computeNextDelay(hour, min, sec);
        executorService.schedule(taskWrapper, delay, TimeUnit.SECONDS);
    }

    private long computeNextDelay(int hour, int min, int sec) {
        LocalDateTime localNow = LocalDateTime.now();
        ZoneId currentZone = ZoneId.systemDefault();
        ZonedDateTime zonedNow = ZonedDateTime.of(localNow, currentZone);
        ZonedDateTime zonedNextTarget = zonedNow.withHour(hour).withMinute(min).withSecond(sec);
        if (zonedNow.compareTo(zonedNextTarget) >= 0) {
            zonedNextTarget = zonedNextTarget.plusDays(1);
        }
        Duration duration = Duration.between(zonedNow, zonedNextTarget);

        return duration.getSeconds();
    }

    public void stop() {
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException ex) {
            Logger.getLogger(ScheduledTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}