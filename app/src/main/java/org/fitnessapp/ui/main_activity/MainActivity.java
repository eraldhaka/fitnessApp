package org.fitnessapp.ui.main_activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.fitnessapp.R;
import org.fitnessapp.data.db.model.Users;
import org.fitnessapp.ui.dispatch.DispatchActivity;
import org.fitnessapp.util.Helper;
import org.fitnessapp.util.PrefManager;

import butterknife.BindView;
import butterknife.ButterKnife;

import static org.fitnessapp.util.PrefManager.USER_LOGGED_OUT;

public class MainActivity extends AppCompatActivity implements MainView {

    @BindView(R.id.text_view_title)
    TextView txt_title;
    @BindView(R.id.text_view_distance_walked)
    TextView txt_distance_walked;
    @BindView(R.id.text_view_time_walked)
    TextView txt_time_walked;

    private Users users;
    private MainPresenterImpl mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainPresenter = new MainPresenterImpl(this);

        mainPresenter.showDailyStats();


       // txt_title.setText();
        //Users users = realm.where(User.class).equalTo("id",id).findFirst();
       /* if(users != null){
            setDailyStat(user);
            showAchieveMilestone(user.getDistanceCovered());
        }
        scheduleNotification();*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       /* int id = item.getItemId();
        if (id == R.id.action_logout) {

            return true;
        }*/

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
}
