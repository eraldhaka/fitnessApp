package org.fitnessapp.ui.main_activity;

public interface MainView {


    void showAchieveMilestone(int numberOfMilestones);

    void showDailyStats(float distance, long timeWalk);

    void scheduleNotification();

    void showUsername(String username);
}
