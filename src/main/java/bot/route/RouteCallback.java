package bot.route;

import bot.entitie.Bot;
import bot.handler.callback.HandlerFinance;
import bot.handler.callback.HandlerHelp;
import bot.handler.callback.HandlerReport;
import bot.handler.callback.HandlerSetting;
import bot.state.State;


public class RouteCallback {

    public Bot routeCallbacProcessor(Bot bot) {
        switch (bot.getState()) {
            case WaitCallbackSaveOrDelete -> {
                HandlerFinance finance = new HandlerFinance(bot);
                bot = finance.handlerStart();
            }
            case PeriodSelection -> {
                HandlerReport report = new HandlerReport(bot);
                bot = report.getReport();
                bot.updateBotState(bot.getPreviousState());
            }
            case InformationRetentionQuestionsSelection -> {
                HandlerHelp help = new HandlerHelp(bot);
                bot = help.getAnswer();
            }
            case ReminderOptionsSelection -> {
                HandlerSetting setting = new HandlerSetting(bot);
                bot = setting.setSetting();
                bot.updateBotState(State.Start);
            }
            default -> {
            }
        }
        bot.setSum(null);
        bot.setComment(null);
        bot.setMessageHasInLineKeyboaard(false);

        return bot;
    }
}