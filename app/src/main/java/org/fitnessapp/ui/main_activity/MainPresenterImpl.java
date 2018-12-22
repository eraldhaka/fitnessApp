package org.fitnessapp.ui.main_activity;

import org.fitnessapp.R;
import org.fitnessapp.data.db.DatabaseOperationsImp;
import org.fitnessapp.data.db.model.Users;
import org.fitnessapp.ui.login.LoginActivity;
import org.fitnessapp.util.Helper;
import org.fitnessapp.util.PrefManager;

import java.util.List;

public class MainPresenterImpl implements MainPresenter{


    private MainActivity mainActivity;
    private DatabaseOperationsImp databaseOperations;

    MainPresenterImpl(MainActivity activity) {
        this.mainActivity = activity;
        databaseOperations = new DatabaseOperationsImp(activity);
    }

    @Override
    public void showDailyStats() {

        int id = PrefManager.getID(PrefManager.USER_ID);
        Users users = databaseOperations.users(id);

        if(users != null){
            setDailyStat(users);
            //showAchieveMilestone(user.getDistanceCovered());
        }
        //scheduleNotification();
    }


    private void setDailyStat(Users user) {

        mainActivity.showDailyStats(user.getUsername(),user.getDistance(),user.getTimeWalk());

    }

}
