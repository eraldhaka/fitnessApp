package org.fitnessapp.ui.main_activity;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.fitnessapp.R;
import org.fitnessapp.ui.dispatch.DispatchActivity;
import org.fitnessapp.ui.loaderboard.LeaderboardActivity;
import org.fitnessapp.ui.walk_activity.WalkActivity;
import org.fitnessapp.util.Helper;
import org.fitnessapp.util.PrefManager;
import org.fitnessapp.util.notifications.NotificationBroadcaster;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static org.fitnessapp.util.PrefManager.USER_LOGGED_OUT;

public class MainActivity extends AppCompatActivity implements MainView {

    @BindView(R.id.text_view_title)
    TextView txt_title;
    @BindView(R.id.text_view_distance_walked)
    TextView txt_distance_walked;
    @BindView(R.id.text_view_time_walked)
    TextView txt_time_walked;
    @BindView(R.id.button_walk)
    Button btn_walk;
    @BindView(R.id.button_loaderboard)
    Button btn_loaderboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MainPresenterImpl mainPresenter = new MainPresenterImpl(this);
        mainPresenter.showDailyStats();
    }

    @OnClick(R.id.button_walk)
    public void registerUser(View view){
        startActivity(Helper.getIntent(this,WalkActivity.class));
    }

    @OnClick(R.id.button_loaderboard)
    public void goTOLoaderboard(View view){
        startActivity(Helper.getIntent(this,LeaderboardActivity.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        PrefManager.setID(PrefManager.USER_ID, USER_LOGGED_OUT);
        goToDispatchScreen();
        return super.onOptionsItemSelected(item);
    }

    private void goToDispatchScreen() {
        startActivity(Helper.getIntent(this,DispatchActivity.class));
    }

    @Override
    public void showDailyStats(String username, float distance, long timeWalk) {

        String message = String.format(getString(R.string.daily_message), username);
        String dailyDist = String.format(getString(R.string.daily_distance), Helper.meterToMileConverter(distance));
        String dailyTime = String.format(getString(R.string.daily_time_data), Helper.secondToMinuteConverter(timeWalk));

        txt_title.setText(message);
        txt_distance_walked.setText(dailyDist);
        txt_time_walked.setText(dailyTime);
    }

    @Override
    public void scheduleNotification() {

       // Notification notification = createNotification(getString(R.string.app_name), getString(R.string.notification_message));
        Notification notification = Helper.createNotification(this,getString(R.string.app_name), getString(R.string.notification_message));
        Intent notificationIntent = getIntent(notification);
        PendingIntent pendingIntent = getBroadcast(notificationIntent);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 20);

        AlarmManager alarmManager = getSystemService();

        // Reminder every 1 hour
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),AlarmManager.INTERVAL_HOUR, pendingIntent);
    }

    @NonNull
    private Intent getIntent(Notification notification) {
        Intent notificationIntent = new Intent(this, NotificationBroadcaster.class);
        notificationIntent.putExtra(NotificationBroadcaster.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationBroadcaster.NOTIFICATION_KEY, notification);
        return notificationIntent;
    }

    private AlarmManager getSystemService() {
        return (AlarmManager)getSystemService(Context.ALARM_SERVICE);
    }
    private PendingIntent getBroadcast(Intent notificationIntent) {
        return PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void showAchieveMilestone(int numberOfMilestones) {
        String title = getString(R.string.achievement_title);
        String message = String.format(getString(R.string.achievement_message), numberOfMilestones);
        Helper.displayMessageToUser(this,title,message).show();
    }
}
