package org.fitnessapp.data.db;

import android.content.Context;
import android.database.SQLException;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import org.fitnessapp.data.db.model.Users;
import java.util.List;


public class DatabaseOperationsImp implements DatabaseOperations{

    private DBHelper helper;
    private RuntimeExceptionDao<Users, Integer> userDao;

    public DatabaseOperationsImp(Context contex) {
        helper = new DBHelper(contex);
        userDao = helper.getRuntimeExceptionDao(Users.class);

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
    public boolean update(Users users) {

        if(checkIfExist(users) == true){
            UpdateBuilder<Users, Integer> updateBuilder = userDao.updateBuilder();

            try {
                updateBuilder.where().eq("first_name", users.getUsername());
                updateBuilder.updateColumnValue("first_name" , "Your name changed to Eljo");

                updateBuilder.update();
                return true;
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
                return false;

            }
        }
       return false;
    }

    @Override
    public boolean delete(Users users) {

        if(checkIfExist(users) == true){
            DeleteBuilder<Users, Integer> deleteBuilder = userDao.deleteBuilder();
            try {
                deleteBuilder.where().eq("first_name", users.getUsername());
                deleteBuilder.delete();
                return true;
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
                return false;
            }
        }else{
            return false;
        }

    }

    @Override
    public boolean checkIfExist(Users users) {
        List<Users> results = null;
        try {
            results = userDao.queryBuilder().where().eq("username",users.getUsername()).query();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            return false;
        }
        if(results.size() == 0){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public boolean checkIfUserNameExist(Users users) {

        List<Users> results;
        try {
            results = userDao.queryBuilder().where().eq("username",users.getUsername()).query();
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
}
