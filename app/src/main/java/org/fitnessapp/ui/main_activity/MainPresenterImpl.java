package org.fitnessapp.ui.main_activity;

import org.fitnessapp.data.db.DatabaseOperationsImp;
import org.fitnessapp.data.db.model.UserWalks;
import org.fitnessapp.data.db.model.Users;
import org.fitnessapp.util.Helper;
import org.fitnessapp.data.prefs.PrefManager;

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
        Users users = databaseOperations.queryUser(id);
        UserWalks userWalks = databaseOperations.getDailyStats(id,Helper.getDate());
        mainActivity.showUsername(users.getUsername());
        if(userWalks != null){
            mainActivity.showDailyStats(userWalks.getDistanceWalked(),userWalks.getTimeWalked());
            // displaying Milestones on total steps but if needed just
            // set --> showAchieveMilestone(userWalks.getDistanceWalked();
            showAchieveMilestone(users.getTotalDistanceWalked());
        }
        mainActivity.scheduleNotification();
    }

    private void showAchieveMilestone(float distanceCovered) {
        int numberOfMilestones = Helper.getNumberOfMilestones(distanceCovered);
        if(numberOfMilestones > 0){
            mainActivity.showAchieveMilestone(numberOfMilestones);
        }
    }
}
