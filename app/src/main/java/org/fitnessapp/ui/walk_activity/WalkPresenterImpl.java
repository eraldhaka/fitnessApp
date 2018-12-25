package org.fitnessapp.ui.walk_activity;

import org.fitnessapp.data.db.DatabaseOperationsImp;
import org.fitnessapp.data.db.model.Users;
import org.fitnessapp.util.Helper;
import org.fitnessapp.util.PrefManager;

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
            walkActivity.goToDispatchActivity();
        }

    }
}
