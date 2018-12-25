package org.fitnessapp.ui.loaderboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.fitnessapp.R;
import org.fitnessapp.data.db.model.Users;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LeaderboardActivity extends AppCompatActivity implements LeaderboardView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    LeaderboardPresenterImpl loaderboardPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loaderboardPresenter = new LeaderboardPresenterImpl(this);
        loaderboardPresenter.getAllUsersWalkingHistory();
    }

    @Override
    public void showUsersLoaderboard(List<Users> users) {

        Collections.sort(users, new Comparator<Users>() {
            @Override
            public int compare(Users lhs, Users rhs) {
                return Float.compare(rhs.getTotalTimeWalked(), lhs.getTotalTimeWalked());
            }
        });

        LeaderboardAdapter notificationAdapter = new LeaderboardAdapter(this, users);
        recyclerView.setAdapter(notificationAdapter);
        notificationAdapter.notifyDataSetChanged();
    }


}
