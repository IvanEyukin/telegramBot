package bot.session;

import LibBaseDto.DtoBaseUser.UserInfo;
import bot.database.SessionDatabase;
import bot.database.redis.RedisTable;
import bot.message.BotMessage;
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

    public void setSession(BotMessage botMessage) {
        RedisTable table = new RedisTable();
        
        table.setKey(Long.toString(botMessage.getUserInfo().getId()));
        table.setDataTimeMessage(Integer.toString(botMessage.getUserInfo().getDateMessage()));
        table.setState(botMessage.getSession().toString());
        table.setPreviousBotMessageId(Integer.toString(botMessage.getPreviousBotMessageId()));
        table.setUserName(checkNull(botMessage.getUserInfo().getName()));
        table.setUserFirstName(checkNull(botMessage.getUserInfo().getFirstName()));
        table.setUserLastName(checkNull(botMessage.getUserInfo().getLastName()));
        table.setCategory(checkNull(botMessage.getFinanceCategory()));
        table.setSubCategory(checkNull(botMessage.getFinanceSubCategory()));
        table.setSum(checkNull(botMessage.getFinanceSum()));
        table.setComment(checkNull(botMessage.getComment()));
        table.setMessageHasKeyboard(checkNull(botMessage.getMessageHasInLineKeyboaard()));
        table.setPreviousState(checkNull(botMessage.getPreviousBotState()));

        sessionDatabase.setSessions(table);
    }

    public BotMessage getSession(BotMessage botMessage) {

        RedisTable table = new RedisTable();
        
        table.setKey(Long.toString(botMessage.getUserInfo().getId()));
        table = sessionDatabase.getSessions(table);

        if (table.getSessionHasRedis() == true) {
            UserInfo user = new UserInfo();

            user.setId(botMessage.getUserInfo().getId());
            user.setName(table.getUserName());
            user.setFirstName(table.getUserFirstName());
            user.setLastName(table.getUserLastName());

            botMessage.setUserInfo(user);
            botMessage.setFinanceCategory(table.getCategory());
            botMessage.setFinanceSubCategory(table.getSubCategory());
            botMessage.setComment(table.getComment());

            if (table.getDataTimeMessage() != null && !table.getDataTimeMessage().equals(NULL_POINT)) {
                user.setDateMessage(Integer.parseInt(table.getDataTimeMessage()));
            }
            if (table.getSum() != null && !table.getSum().equals(NULL_POINT)) {
                botMessage.setFinanceSum(new BigDecimal(table.getSum()));
            }
            if (table.getState() != null && !table.getState().equals(NULL_POINT)) {
                botMessage.setSession(State.valueOf(table.getState()));
            }
            if (table.getPreviousState() != null && !table.getPreviousState().equals(NULL_POINT)) {
                botMessage.setPreviousBotState(State.valueOf(table.getPreviousState()));
            }
            if (table.getMessageHasKeyboard() != null && !table.getMessageHasKeyboard().equals(NULL_POINT)) {
                botMessage.setMessageHasInLineKeyboaard(Boolean.parseBoolean(table.getMessageHasKeyboard()));
            }
            if (table.getPreviousBotMessageId() != null && !table.getPreviousBotMessageId().equals(NULL_POINT)) {
                botMessage.setPreviousBotMessageId(Integer.parseInt(table.getPreviousBotMessageId()));
            }
        }

        return botMessage;
    }
}