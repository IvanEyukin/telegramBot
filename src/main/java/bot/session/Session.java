package bot.session;

import Database.SessionDatabase;
import LibBaseDto.DtoBaseBot.BotMessage;
import LibBaseDto.DtoBaseRedis.RedisTable;
import LibBaseDto.DtoBaseUser.UserInfo;
import BotFSM.BotState;

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

    private String checkNull(Boolean value) {
        if (value != null) {
            return Boolean.toString(value);
        } else {
            return NULL_POINT;
        }         
    }

    // private String checkNull(BotState value) {
    //     if (value != null) {
    //         return value.toString();
    //     } else {
    //         return NULL_POINT;
    //     }         
    // }

    public void setSession(BotMessage botMessage) {
        RedisTable table = new RedisTable();

        table.setKey(Long.toString(botMessage.getUserInfo().getId()));
        table.setDataTimeMessage(Integer.toString(botMessage.getUserInfo().getDateMessage()));
        table.setState(botMessage.getBotState().toString());
        // table.setPreviousState(checkNull(botMessage.getPreviousBotState()));
        table.setPreviousBotMessageId(Integer.toString(botMessage.getPreviousBotMessageId()));
        table.setUserName(checkNull(botMessage.getUserInfo().getName()));
        table.setUserFirstName(checkNull(botMessage.getUserInfo().getFirstName()));
        table.setUserLastName(checkNull(botMessage.getUserInfo().getLastName()));
        table.setCategory(checkNull(botMessage.getFinanceCategory()));
        table.setSubCategory(checkNull(botMessage.getFinanceSubCategory()));
        table.setSum(checkNull(botMessage.getFinanceSum().toEngineeringString()));
        table.setComment(checkNull(botMessage.getComment()));
        table.setMessageHasKeyboard(checkNull(botMessage.getMessageHasInLineKeyboaard()));
        
        sessionDatabase.setSessions(table);
    }

    public void getSession(BotMessage botMessage) {
    }
    
}