package org.fitnessapp.ui.leaderboard;

import org.fitnessapp.data.db.DatabaseOperationsImp;
import org.fitnessapp.data.db.model.Users;
import java.util.List;

public class LeaderboardPresenterImpl implements LeaderboardPresenter {

    private LeaderboardActivity leaderboardActivity;
    private DatabaseOperationsImp databaseOperations;

     LeaderboardPresenterImpl(LeaderboardActivity activity) {
        this.leaderboardActivity = activity;
        databaseOperations = new DatabaseOperationsImp(activity);
    }


    @Override
    public void getAllUsersWalkingHistory() {
        List<Users> users = databaseOperations.read();
        leaderboardActivity.showUsersLeaderboard(users);

    }
}
