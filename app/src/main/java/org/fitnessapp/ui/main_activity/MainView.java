package org.fitnessapp.ui.main_activity;

public interface MainView {


    void showAchieveMilestone(int numberOfMilestones);

    void showDailyStats(String username, float distance, long timeWalk);

    void scheduleNotification();
}
