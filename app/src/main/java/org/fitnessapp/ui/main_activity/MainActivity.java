package org.fitnessapp.ui.main_activity;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.parse.ParseUser;

import org.fitnessapp.R;
import org.fitnessapp.ui.dispatch.DispatchActivity;
import org.fitnessapp.ui.leaderboard.LeaderboardActivity;
import org.fitnessapp.ui.walk_activity.WalkActivity;
import org.fitnessapp.util.Helper;
import org.fitnessapp.data.prefs.PrefManager;
import org.fitnessapp.notifications.NotificationBroadcaster;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static org.fitnessapp.data.prefs.PrefManager.USER_LOGGED_OUT;

public class MainActivity extends AppCompatActivity implements MainView {

    @BindView(R.id.text_view_title)
    TextView txtTitle;
    @BindView(R.id.text_view_distance_walked)
    TextView txtDistanceWalked;
    @BindView(R.id.text_view_time_walked)
    TextView txtTimeWalked;
    @BindView(R.id.button_walk)
    Button btnWalk;
    @BindView(R.id.button_leaderboard)
    Button btnLeaderboard;

    MainPresenterImpl mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainPresenter = new MainPresenterImpl(this);
        mainPresenter.showDailyStats();

    }

        @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted!
                    startActivity(Helper.getIntent(this,WalkActivity.class));
                } else {
                    // permission denied!
                    Toast.makeText(MainActivity.this, R.string.permission_denied, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @OnClick(R.id.button_walk)
    public void goToWalk(View view){
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                1);
    }

    @OnClick(R.id.button_leaderboard)
    public void goToLeaderboard(View view){
        Intent intent = new Intent(this,LeaderboardActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        PrefManager.setID(PrefManager.USER_ID, USER_LOGGED_OUT);
        //Twitter log Out
        ParseUser.logOut();
        goToDispatchScreen();
        return super.onOptionsItemSelected(item);
    }

    private void goToDispatchScreen() {
        startActivity(Helper.getIntent(this,DispatchActivity.class));
    }

    @Override
    public void showDailyStats(float distance, long timeWalk) {
        String dailyDist = String.format(getString(R.string.daily_distance), Helper.meterToMileConverter(distance));
        String dailyTime = String.format(getString(R.string.daily_time_data), Helper.secondToMinuteConverter(timeWalk));

        txtDistanceWalked.setText(dailyDist);
        txtTimeWalked.setText(dailyTime);
    }

    @Override
    public void scheduleNotification() {

        Notification notification = Helper.createNotification(this,getString(R.string.app_name), getString(R.string.notification_message));
        Intent notificationIntent = getIntent(notification);
        PendingIntent pendingIntent = getBroadcast(notificationIntent);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 20);

        AlarmManager alarmManager = getSystemService();

        // Reminder every 1 hour
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),AlarmManager.INTERVAL_HOUR, pendingIntent);
    }

    @Override
    public void showUsername(String username) {
        String message = String.format(getString(R.string.daily_message), username);
        txtTitle.setText(message);
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
