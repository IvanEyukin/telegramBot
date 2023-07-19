package bot.session;

import bot.database.SessionDatabase;
import bot.database.redis.RedisTable;
import bot.entitie.Bot;
import bot.entitie.User;
import bot.state.State;

import java.math.BigDecimal;


public class Session {

    private final static String NULL_POINT = "null";
    SessionDatabase sessionDatabase = new SessionDatabase();

    private String checkNull(String value) {
        if (value != null) {
            return value;
        } else {
            return NULL_POINT;
        }
    }

    private String checkNull(BigDecimal value) {
        if (value != null) {
            return value.toEngineeringString();
        } else {
            return NULL_POINT;
        }
    }

    private String checkNull(Boolean value) {
        if (value != null) {
            return Boolean.toString(value);
        } else {
            return NULL_POINT;
        }
    }

    private String checkNull(State value) {
        if (value != null) {
            return value.toString();
        } else {
            return NULL_POINT;
        }
    }

    private String checkValue(String value) {
        if (value != null && !value.equals(NULL_POINT)) {
            return value;
        } else {
            return null;
        }
    }

    public void setSession(Bot bot) {
        RedisTable table = new RedisTable();
        
        table.setKey(Long.toString(bot.getUser().getId()));
        table.setDataTimeMessage(Integer.toString(bot.getUser().getDateMessage()));
        table.setState(bot.getState().toString());
        table.setBotMessageId(Integer.toString(bot.getBotMessageId()));
        table.setUserName(checkNull(bot.getUser().getName()));
        table.setUserFirstName(checkNull(bot.getUser().getFirstName()));
        table.setUserLastName(checkNull(bot.getUser().getLastName()));
        table.setCategory(checkNull(bot.getCategory()));
        table.setSubCategory(checkNull(bot.getSubCategory()));
        table.setSum(checkNull(bot.getSum()));
        table.setComment(checkNull(bot.getComment()));
        table.setMessageHasKeyboard(checkNull(bot.getMessageHasInLineKeyboaard()));
        table.setPreviousState(checkNull(bot.getPreviousState()));

        sessionDatabase.setSessions(table);
    }

    public Bot getSession(Bot bot) {

        RedisTable table = new RedisTable();
        
        table.setKey(Long.toString(bot.getUser().getId()));
        table = sessionDatabase.getSessions(table);

        if (table.getSessionHasRedis() == true) {
            User user = new User();

            user.setId(bot.getUser().getId());
            user.setName(checkValue(table.getUserName()));
            user.setFirstName(checkValue(table.getUserFirstName()));
            user.setLastName(checkValue(table.getUserLastName()));

            bot.setUser(user);
            bot.setCategory(checkValue(table.getCategory()));
            bot.setSubCategory(checkValue(table.getSubCategory()));
            bot.setComment(checkValue(table.getComment()));

            if (checkValue(table.getDataTimeMessage()) != null) {
                user.setDateMessage(Integer.parseInt(table.getDataTimeMessage()));
            }
            if (checkValue(table.getSum()) != null) {
                bot.setSum(new BigDecimal(table.getSum()));
            }
            if (checkValue(table.getState()) != null) {
                bot.setState(State.valueOf(table.getState()));
            }
            if (checkValue(table.getPreviousState()) != null) {
                bot.setPreviousState(State.valueOf(table.getPreviousState()));
            }
            if (checkValue(table.getMessageHasKeyboard()) != null) {
                bot.setMessageHasInLineKeyboaard(Boolean.parseBoolean(table.getMessageHasKeyboard()));
            }
            if (checkValue(table.getBotMessageId()) != null) {
                bot.setBotMessageId(Integer.parseInt(table.getBotMessageId()));
            }
        }

        return bot;
    }
}