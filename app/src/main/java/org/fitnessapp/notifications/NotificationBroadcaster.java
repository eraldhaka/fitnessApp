package org.fitnessapp.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.fitnessapp.util.Helper;

import java.util.Calendar;
import java.util.Objects;

import static org.fitnessapp.util.Constant.END_TIME_TO_COMPARE;
import static org.fitnessapp.util.Constant.START_TIME_TO_COMPARE;


public class NotificationBroadcaster extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "walk_reminder";
    public static String NOTIFICATION_KEY = "reminder_ notification";

    @Override
    public void onReceive(Context context, Intent intent) {
        // The requirement was to notify the person whenever he is in office
        // so I have supposed he works from Monday to Friday 9:00 AM to 18:00 PM

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK); //Sunday = 1, Saterday = 7
        switch(day){

            case 1: //Sunday

            case 2: //Monday
                launchActivity(context,intent);
            case 3://Tuesday
                launchActivity(context,intent);
            case 4://Wednesday
                launchActivity(context,intent);
            case 5://Thursday
                launchActivity(context,intent);
            case 6://Friday
                launchActivity(context,intent);
            case 7: //Saturday

        }
    }

    void launchActivity(Context context,Intent intent){
        if(Helper.CompareTime(START_TIME_TO_COMPARE,END_TIME_TO_COMPARE)){
            NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification = intent.getParcelableExtra(NOTIFICATION_KEY);
            int id = intent.getIntExtra(NOTIFICATION_ID, 0);
            Objects.requireNonNull(notificationManager).notify(id, notification);
        }
    }
}
