package org.fitnessapp.ui.leaderboard;

import org.fitnessapp.data.db.model.Users;

import java.util.List;

public interface LeaderboardView {
    void showUsersLeaderboard(List<Users> users);
}
