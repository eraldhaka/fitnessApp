package org.fitnessapp.ui.walk_activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import org.fitnessapp.R;
import org.fitnessapp.ui.dispatch.DispatchActivity;
import org.fitnessapp.util.Helper;
import org.fitnessapp.util.PrefManager;
import org.fitnessapp.util.service.LocationService;
import java.lang.ref.WeakReference;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WalkActivity extends AppCompatActivity implements WalkView{

    private final static int MSG_UPDATE_TIME = 0;

    @BindView(R.id.text_view_distance_walked)
    TextView txt_distance_walked;
    @BindView(R.id.text_view_time_walked)
    TextView txt_time_walked;
    @BindView(R.id.button_walk)
    Button btn_walk;

    private LocationService mLocationService;
    private boolean isServiceBound;
    private WalkPresenterImpl walkPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);
        ButterKnife.bind(this);

        walkPresenter = new WalkPresenterImpl(this);
    }

    private final Handler mUIUpdateHandler = new UIUpdateHandler(this);

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder binder) {
            isServiceBound = true;
            LocationService.LocalBinder localBinder = (LocationService.LocalBinder) binder;
            mLocationService = localBinder.getService();
            if(mLocationService.isUserWalking()){
                updateStartWalkUI();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isServiceBound = false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        startLocationService();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(isServiceBound){
            mLocationService.stopBroadcasting();
            if(!mLocationService.isUserWalking()){
                stopLocationService();
            }
        }
        updateStopWalkUI();
        unbindService(mServiceConnection);
        isServiceBound = false;
    }



    @OnClick(R.id.button_walk)
    public void walkActivityBtnClick() {
        if (isServiceBound && !mLocationService.isUserWalking()) {
            initializeWalkService();
            updateWalkPref(true);
            updateStartWalkUI();
        }else if (isServiceBound && mLocationService.isUserWalking()){
            stopWalkService();
            updateStopWalkUI();
            updateWalkPref(false);
            saveWalkData(mLocationService.distanceCovered(),mLocationService.elapsedTime());
        }
    }

    private void initializeWalkService() {
        mLocationService.startUserWalk();
        mLocationService.startBroadcasting();
        mLocationService.startForeground();
    }

    private void stopWalkService() {
        mLocationService.stopUserWalk();
        mLocationService.stopNotification();
    }

    private void saveWalkData(final float distanceWalked, final long timeWalked) {
        AlertDialog.Builder saveBuilder = new AlertDialog.Builder(this);
        saveBuilder.setTitle(getString(R.string.save_walk_data_title));
        saveBuilder.setMessage(getString(R.string.save_walk_data_message));
        saveBuilder.setNegativeButton(getString(R.string.dismiss_walk_data), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                goToDispatchActivity();
            }
        });
        saveBuilder.setPositiveButton(getString(R.string.save_walk_data), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                walkPresenter.saveUserData(distanceWalked,timeWalked);
            }
        });
        saveBuilder.setCancelable(false);
        saveBuilder.create().show();
    }

    private void updateWalkPref(boolean isUserWalk) {
        PrefManager.setUserWalk(PrefManager.USER_WALK, isUserWalk);
    }

    private void updateStopWalkUI() {
        if(mUIUpdateHandler.hasMessages(MSG_UPDATE_TIME)) {
            mUIUpdateHandler.removeMessages(MSG_UPDATE_TIME);
            btn_walk.setText(R.string.start_walk);
        }
    }

    private void updateStartWalkUI() {
        mUIUpdateHandler.sendEmptyMessage(MSG_UPDATE_TIME);
        btn_walk.setText(R.string.stop_walk);
    }

    private void startLocationService() {
        Intent intent = new Intent(this, LocationService.class);
        startService(intent);
        bindService(intent, mServiceConnection , Context.BIND_AUTO_CREATE);
    }

    private void stopLocationService() {
        Intent intentService = new Intent(this, LocationService.class);
        stopService(intentService);
    }

    private void updateUI() {
        if (isServiceBound) {
            txt_distance_walked.setText(getString(R.string.daily_distance,Helper.meterToMileConverter(mLocationService.distanceCovered())));
            txt_time_walked.setText(Helper.secondToHHMMSS(mLocationService.elapsedTime()));
        }
    }

    private static class UIUpdateHandler extends Handler {

        private final static int UPDATE_RATE_MS = 1000;
        private final WeakReference<WalkActivity> activity;

        UIUpdateHandler(WalkActivity activity) {
            this.activity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message message) {
            if (MSG_UPDATE_TIME == message.what) {
                activity.get().updateUI();
                sendEmptyMessageDelayed(MSG_UPDATE_TIME, UPDATE_RATE_MS);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(!PrefManager.isUserWalking()){
            goToDispatchActivity();
        }else {
            finish();
        }
    }

    @Override
    public void goToDispatchActivity() {
        startActivity(Helper.getIntent(this,DispatchActivity.class));
    }

}
