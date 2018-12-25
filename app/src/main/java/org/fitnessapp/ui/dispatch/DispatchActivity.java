package org.fitnessapp.ui.dispatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.fitnessapp.ui.main_activity.MainActivity;
import org.fitnessapp.ui.login.LoginActivity;
import org.fitnessapp.ui.walk_activity.WalkActivity;
import org.fitnessapp.util.Helper;
import org.fitnessapp.util.PrefManager;

public class DispatchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(PrefManager.isAuthorized()){
            if(PrefManager.isUserWalking()) {
                startActivity(Helper.getIntent(this,WalkActivity.class));
            }else {
                startActivity(Helper.getIntent(this,MainActivity.class));
            }
        }else {
            startActivity(Helper.getIntent(this,LoginActivity.class));
        }
    }
}
