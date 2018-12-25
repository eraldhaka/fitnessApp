package org.fitnessapp.ui.loaderboard;

import org.fitnessapp.data.db.model.Users;

import java.util.List;

public interface LeaderboardView {
    void showUsersLoaderboard(List<Users> users);
}
