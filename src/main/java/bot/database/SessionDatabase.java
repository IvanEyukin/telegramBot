package bot.database;

import LibBaseDto.DtoBaseRedis.RedisFieldName;
import LibBaseDto.DtoBaseRedis.RedisTable;
import bot.setting.Setting;
import redis.clients.jedis.Jedis;
import java.util.HashMap;
import java.util.Map;


public class SessionDatabase {

    private Jedis redisConnection () {
        Jedis jedis = new Jedis(Setting.dbSessionHost, Setting.dbSessionPort);
        jedis.connect();
        return jedis;
    }

    public void setSessions (RedisTable table) {
        Jedis conn = redisConnection();
        Map<String, String> request = new HashMap<String, String>();

        request.put(RedisFieldName.UserName.toString(), table.getUserName());
        request.put(RedisFieldName.UserFirstName.toString(), table.getUserFirstName());
        request.put(RedisFieldName.UserLastName.toString(), table.getUserLastName());
        request.put(RedisFieldName.DataTimeMessage.toString(), table.getDataTimeMessage());
        request.put(RedisFieldName.Category.toString(), table.getCategory());
        request.put(RedisFieldName.SubCategory.toString(), table.getSubCategory());
        request.put(RedisFieldName.Sum.toString(), table.getSum());
        request.put(RedisFieldName.Comment.toString(), table.getComment());
        request.put(RedisFieldName.MessageHasKeyboard.toString(), table.getMessageHasKeyboard());
        request.put(RedisFieldName.PreviousBotMessageId.toString(), table.getPreviousBotMessageId());
        request.put(RedisFieldName.State.toString(), table.getState());
        request.put(RedisFieldName.PreviousState.toString(), table.getPreviousState());

        conn.hmset(table.getKey(), request);
        conn.expire(table.getKey(), Setting.sessionTimeToLive);
        conn.close();
    }

    public RedisTable getSessions (RedisTable table) {
        Jedis conn = redisConnection();
        Map<String, String> responce = conn.hgetAll(table.getKey());

        if (responce.size() != 0) {
            table.setUserName(responce.get(RedisFieldName.UserName.toString()));
            table.setUserFirstName(responce.get(RedisFieldName.UserFirstName.toString()));
            table.setUserLastName(responce.get(RedisFieldName.UserLastName.toString()));
            table.setDataTimeMessage(responce.get(RedisFieldName.DataTimeMessage.toString()));
            table.setCategory(responce.get(RedisFieldName.Category.toString()));
            table.setSubCategory(responce.get(RedisFieldName.SubCategory.toString()));
            table.setSum(responce.get(RedisFieldName.Sum.toString()));
            table.setComment(responce.get(RedisFieldName.Comment.toString()));
            table.setMessageHasKeyboard(responce.get(RedisFieldName.MessageHasKeyboard.toString()));
            table.setState(responce.get(RedisFieldName.State.toString()));
            table.setPreviousState(responce.get(RedisFieldName.PreviousState.toString()));
            table.setPreviousBotMessageId(responce.get(RedisFieldName.PreviousBotMessageId.toString()));
            table.setSessionHasRedis(true);
        } else {
            table.setSessionHasRedis(false);
        }
        conn.close();

        return table;
    }
}