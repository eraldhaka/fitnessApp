package org.fitnessapp;

import android.app.Application;

import org.fitnessapp.util.PrefManager;


/**
 * Created by Marcel on 9/12/2016.
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PrefManager.initSharedPref(this);
    }
}
