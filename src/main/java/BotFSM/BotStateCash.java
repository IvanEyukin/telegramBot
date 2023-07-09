package BotFSM;

import Database.SessionDatabase;
import LibBaseDto.DtoBaseBot.BotMessage;
import LibBaseDto.DtoBaseRedis.RedisTable;
import LibBaseDto.DtoBaseUser.UserInfo;
import bot.state.State;

import java.math.BigDecimal;


public class BotStateCash {

    private final static String NULL_POINT = "null";

    SessionDatabase sessionDatabase = new SessionDatabase();

    public void setBotState(BotMessage botMessage) {

        RedisTable table = new RedisTable();
        
        table.setKey(Long.toString(botMessage.getUserInfo().getId()));
        table.setDataTimeMessage(Integer.toString(botMessage.getUserInfo().getDateMessage()));
        table.setState(botMessage.getBotState().toString());
        table.setPreviousBotMessageId(Integer.toString(botMessage.getPreviousBotMessageId()));

        if (botMessage.getUserInfo().getName() != null) {
            table.setUserName(botMessage.getUserInfo().getName());
        } else {
            table.setUserName(NULL_POINT);
        }

        if (botMessage.getUserInfo().getFirstName() != null) {
            table.setUserFirstName(botMessage.getUserInfo().getFirstName());
        } else {
            table.setUserFirstName(NULL_POINT);
        }

        if (botMessage.getUserInfo().getLastName() != null) {
            table.setUserLastName(botMessage.getUserInfo().getLastName());
        } else {
            table.setUserLastName(NULL_POINT);
        }

        if (botMessage.getFinanceCategory() != null) {
            table.setCategory(botMessage.getFinanceCategory());
        } else {
            table.setCategory(NULL_POINT);
        }

        if (botMessage.getFinanceSubCategory() != null) {
            table.setSubCategory(botMessage.getFinanceSubCategory());
        } else {
            table.setSubCategory(NULL_POINT);
        }

        if (botMessage.getFinanceSum() != null) {
            table.setSum(botMessage.getFinanceSum().toEngineeringString());
        } else {
            table.setSum(NULL_POINT);
        }

        if (botMessage.getComment() != null) {
            table.setComment(botMessage.getComment());
        } else {
            table.setComment(NULL_POINT);
        }

        if (botMessage.getMessageHasInLineKeyboaard() != null) {
            table.setMessageHasKeyboard(Boolean.toString(botMessage.getMessageHasInLineKeyboaard()));
        } else {
            table.setMessageHasKeyboard(NULL_POINT);
        }

        if (botMessage.getPreviousBotState() != null) {
            table.setPreviousState(botMessage.getPreviousBotState().toString());
        } else {
            table.setPreviousState(NULL_POINT);
        }
        
        sessionDatabase.setSessions(table);

    }

    public BotMessage getBotState(BotMessage botMessage) {

        RedisTable table = new RedisTable();
        UserInfo user = new UserInfo();

        table.setKey(Long.toString(botMessage.getUserInfo().getId()));

        table = sessionDatabase.getSessions(table);

        if (table.getSessionHasRedis() == true) {

            user.setId(botMessage.getUserInfo().getId());
            user.setName(table.getUserName());
            user.setFirstName(table.getUserFirstName());
            user.setLastName(table.getUserLastName());

            if (table.getDataTimeMessage() != null && !table.getDataTimeMessage().equals(NULL_POINT)) {
                user.setDateMessage(Integer.parseInt(table.getDataTimeMessage()));
            }

            botMessage.setUserInfo(user);
            botMessage.setFinanceCategory(table.getCategory());
            botMessage.setFinanceSubCategory(table.getSubCategory());
            botMessage.setComment(table.getComment());

            if (table.getSum() != null && !table.getSum().equals(NULL_POINT)) {
                botMessage.setFinanceSum(new BigDecimal(table.getSum()));
            }

            if (table.getState() != null && !table.getState().equals(NULL_POINT)) {
                botMessage.setBotState(State.valueOf(table.getState()));
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