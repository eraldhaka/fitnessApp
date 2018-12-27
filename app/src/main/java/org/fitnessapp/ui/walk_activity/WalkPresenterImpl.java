package org.fitnessapp.ui.walk_activity;

import org.fitnessapp.data.db.DatabaseOperationsImp;
import org.fitnessapp.data.db.model.UserWalks;
import org.fitnessapp.data.db.model.Users;
import org.fitnessapp.util.Helper;
import org.fitnessapp.util.PrefManager;
import java.util.Date;

public class WalkPresenterImpl implements WalkPresenter {

    private WalkActivity walkActivity;
    private DatabaseOperationsImp databaseOperations;

     WalkPresenterImpl(WalkActivity activity) {
        this.walkActivity = activity;
        databaseOperations = new DatabaseOperationsImp(activity);
    }

    @Override
    public void saveUserData(float distanceWalked, long timeWalked) {

        Users users = databaseOperations.queryUser(PrefManager.getID(PrefManager.USER_ID));

        if(users != null){
            users.updateDistanceCovered(distanceWalked);
            users.updateTotalTimeWalk(timeWalked);
            databaseOperations.updateData(users,users.getTotalDistanceWalked(),users.getTotalTimeWalked());
            fillUserWalkModel(users.getUserId(),distanceWalked,timeWalked,Helper.getDate());
            walkActivity.goToDispatchActivity();
        }

    }

    private void fillUserWalkModel(int userId, float distanceWalked, long timeWalked, Date date){
         
        if(databaseOperations.checkIfDateExist(userId,date)){
            UserWalks userWalks = databaseOperations.queryUserWalked(userId);
            userWalks.updateDistanceCovered(distanceWalked);
            userWalks.updateTotalTimeWalk(timeWalked);
            databaseOperations.updateUserWalks(userId,userWalks.getDistanceWalked(),userWalks.getTimeWalked());
        }else {
            UserWalks userWalks = new UserWalks();
            userWalks.setUserId(userId);
            userWalks.setDistanceWalked(distanceWalked);
            userWalks.setTimeWalked(timeWalked);
            userWalks.setDate(date);
            databaseOperations.createUserWalks(userWalks);
        }
    }
}
