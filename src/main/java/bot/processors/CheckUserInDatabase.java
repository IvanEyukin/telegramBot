package bot.processors;

import bot.database.ReportDatabase;
import bot.entitie.User;


public class CheckUserInDatabase {

    ReportDatabase database = new ReportDatabase();

    private Boolean checkNeedUpdate(String oldData, String newData) {
        if (oldData != null && !oldData.equals(newData)) {
            return true;
        } else {
            return false;
        }
    }

    public void checkUser(User user) {
        User userDb = database.selectUser(user);
        if (!userDb.getIsInDatabase()) {
            database.insertUser(user);
        } else if (checkNeedUpdate(userDb.getName(), user.getName()) || checkNeedUpdate(userDb.getFirstName(), user.getFirstName()) || checkNeedUpdate(userDb.getLastName(), user.getLastName())) {
            database.updateUser(user);
        }
    }
}