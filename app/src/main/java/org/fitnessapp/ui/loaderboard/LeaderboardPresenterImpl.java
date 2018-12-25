package org.fitnessapp.ui.loaderboard;

import org.fitnessapp.data.db.DatabaseOperationsImp;
import org.fitnessapp.data.db.model.Users;

import java.util.List;

public class LeaderboardPresenterImpl implements LeaderboardPresenter {


    private LeaderboardActivity leaderboardActivity;
    private DatabaseOperationsImp databaseOperations;

    public LeaderboardPresenterImpl(LeaderboardActivity activity) {
        this.leaderboardActivity = activity;
        databaseOperations = new DatabaseOperationsImp(activity);
    }


    @Override
    public void getAllUsersWalkingHistory() {

        List<Users> users = databaseOperations.read();

        for (int i=0; i<users.size(); i++){
            System.out.println("Username "+users.get(i).getUsername());
            System.out.println("distance "+users.get(i).totalDistanceWalked);
            System.out.println("time "+users.get(i).totalTimeWalked);
        }
        leaderboardActivity.showUsersLoaderboard(users);

    }
}
