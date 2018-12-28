package org.fitnessapp.data.db;

import android.content.Context;
import android.database.SQLException;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.UpdateBuilder;
import org.fitnessapp.data.db.model.UserWalks;
import org.fitnessapp.data.db.model.Users;
import java.util.Date;
import java.util.List;


public class DatabaseOperationsImp implements DatabaseOperations{

    private DBHelper helper;
    private RuntimeExceptionDao<Users, Integer> userDao;
    private RuntimeExceptionDao<UserWalks, Integer> userWalksDao;

    public DatabaseOperationsImp(Context contex) {
        helper = new DBHelper(contex);
        userDao = helper.getRuntimeExceptionDao(Users.class);
        userWalksDao = helper.getRuntimeExceptionDao(UserWalks.class);
    }

    @Override
    public boolean create(Users users) {
        try {
            userDao.create(users);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Users> read() {
        List<Users> users = userDao.queryForAll();
        if(users.size() != 0){
            return users;
        }
        return users;
    }


    @Override
    public boolean checkIfUserNameExist(String username) {

        List<Users> results;
        try {
            results = userDao.queryBuilder().where().eq("username",username).query();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            return false;
        }
        return results.size() == 0;
    }

    @Override
    public boolean checkIfCredentialsAreCorrect(Users users) {
        List<Users> results;
        try {
            results = userDao.queryBuilder().where().eq("username",users.getUsername()).and().eq("password",users.getPassword()).query();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            return false;
        }
        return results.size() == 0;
    }

    @Override
    public Users queryUserId(String username, String password) {
        Users results = null;
        try {
            results = userDao.queryBuilder().where().eq("username",username).and().eq("password",password).queryForFirst();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return results;

    }

    @Override
    public Users queryUser(int id) {
        Users results = null;
        try {
            //results = userDao.queryBuilder().where().eq("userId",id).query();
            results = userDao.queryBuilder().where().eq("userId",id).queryForFirst();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public void updateData(Users users, float distanceWalked, long timeWalked) {

            UpdateBuilder<Users, Integer> updateBuilder = userDao.updateBuilder();
            try {
                updateBuilder.where().eq("userId", users.getUserId());
                updateBuilder.updateColumnValue("totalDistanceWalked" , distanceWalked);
                updateBuilder.updateColumnValue("time" , timeWalked);
                updateBuilder.update();
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
    }



    @Override
    public void createUserWalks(UserWalks userWalks) {
        try {
            userWalksDao.create(userWalks);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkIfDateExist(int userId, Date date) {
        List<UserWalks> results;
        try {
            results = userWalksDao.queryBuilder().where().eq("date",date).and().eq("userId",userId).query();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            return false;
        }
        if(results.size()==0){
            return false;
        }else {
            return true;
        }
        //return results.size() == 0;
    }

    @Override
    public void updateUserWalks(int userId, float distanceWalked, long timeWalked) {

        UpdateBuilder<UserWalks, Integer> updateBuilder = userWalksDao.updateBuilder();
        try {
            updateBuilder.where().eq("userId", userId);
            updateBuilder.updateColumnValue("distanceWalked" , distanceWalked);
            updateBuilder.updateColumnValue("timeWalked" , timeWalked);
            updateBuilder.update();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserWalks getDailyStats(int id, Date date) {

        UserWalks results = null;
        try {
            //results = userDao.queryBuilder().where().eq("userId",id).query();
            results = userWalksDao.queryBuilder().where().eq("userId",id).and().eq("date",date).queryForFirst();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public UserWalks queryUserWalked(int userId) {
        UserWalks results = null;
        try {
            //results = userDao.queryBuilder().where().eq("userId",id).query();
            results = userWalksDao.queryBuilder().where().eq("userId",userId).queryForFirst();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public List<UserWalks> readDaily() {
        List<UserWalks> userWalks = userWalksDao.queryForAll();
        if(userWalks.size() != 0){
            return userWalks;
        }
        return userWalks;
    }


    public List<UserWalks> readUserWalks() {

        List<UserWalks> userWalks = userWalksDao.queryForAll();

        if(userWalks.size() != 0){
            return userWalks;
        }

        return userWalks;
    }
}
