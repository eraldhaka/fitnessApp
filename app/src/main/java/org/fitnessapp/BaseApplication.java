package org.fitnessapp;

import android.app.Application;


import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.twitter.ParseTwitterUtils;

import org.fitnessapp.data.prefs.PrefManager;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PrefManager.initSharedPref(this);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                // if defined
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build()
        );
        ParseTwitterUtils.initialize(getString(R.string.twitter_consumer_key), getString(R.string.twitter_consumer_secret));
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
