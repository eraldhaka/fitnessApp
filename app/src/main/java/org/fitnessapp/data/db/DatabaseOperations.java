package org.fitnessapp.data.db;

import org.fitnessapp.data.db.model.UserWalks;
import org.fitnessapp.data.db.model.Users;
import java.util.Date;
import java.util.List;

public interface DatabaseOperations {

    boolean create(Users users);

    List<Users> read();

    boolean checkIfUserNameExist(String users);

    boolean checkIfCredentialsAreCorrect(Users users);

    Users queryUserId(String username, String password);

    Users queryUser(int id);

    void updateData(Users users, float distanceWalked, long timeWalked);

    void createUserWalks(UserWalks userWalks);

    boolean checkIfDateExist(int userId, Date date);

    void updateUserWalks(int userId, float distanceWalked, long timeWalked);

    UserWalks getDailyStats(int id, Date date);

    UserWalks queryUserWalked(int userId);

    //If needed daily leaderboard
    List<UserWalks> readDaily();
}
