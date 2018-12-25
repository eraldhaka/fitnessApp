package org.fitnessapp.util;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import org.fitnessapp.R;
import org.fitnessapp.ui.dispatch.DispatchActivity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public class Helper {

    public static final String ACTION_NAME_SPACE = "org.fitnessapp.LocationService";
    public static final String INTENT_EXTRA_RESULT_CODE = "resultCode";
    public static final String INTENT_USER_LAT_LNG = "userLatLng";

    public static AlertDialog displayMessageToUser(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setTitle(title)
                .setPositiveButton(android.R.string.ok, null);
        return builder.create();
    }

    public static Intent getIntent(Context context, Class<?> goToActivity) {
        Intent intent = new Intent(context, goToActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    public static int secondToMinuteConverter(long seconds) {
        return (int) seconds / 60;
    }

    public static float meterToMileConverter(float meter) {
        return meter / 1609;
    }

    public static int getNumberOfMilestones(float meter) {
        return (int) meter / 305; // 1000 feet -> 304.8
    }

    public static String secondToHHMMSS(long secondsCount) {
        long seconds = secondsCount % 60;
        secondsCount -= seconds;
        long minutesCount = secondsCount / 60;
        long minutes = minutesCount % 60;
        minutesCount -= minutes;
        long hoursCount = minutesCount / 60;
        return "" + hoursCount + ":" + minutes + ":" + seconds;
    }

    public static Notification createNotification (Context context,String title, String message){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel("ID", "Name", importance);
            Objects.requireNonNull(notificationManager).createNotificationChannel(notificationChannel);
            builder = new NotificationCompat.Builder(context, notificationChannel.getId());
        } else {
            builder = new NotificationCompat.Builder(context,"MyChannelId");
        }

        Intent resultIntent = new Intent(context, DispatchActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(context, 0, resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        builder = builder
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(Notification.PRIORITY_MAX)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(resultPendingIntent);

        return builder.build();
    }

    public static boolean CompareTime(String strTimeToCompare,String endTimeToCompare) {

        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        int dtHour;
        int dtMin;
        int iAMPM;
        String strAMorPM=null;
        Date dtCurrentDate;
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
        try {
            Date startTime = sdf.parse(strTimeToCompare);
            Date endTime = sdf.parse(endTimeToCompare);
            dtMin=cal.get(Calendar.MINUTE);
            dtHour=cal.get(Calendar.HOUR);
            iAMPM=cal.get(Calendar.AM_PM);
            if (iAMPM == 1) {
                strAMorPM="PM";
            }
            if (iAMPM == 0) {
                strAMorPM="AM";
            }
            dtCurrentDate = sdf.parse(dtHour + ":" + dtMin + " " + strAMorPM);
            if(dtCurrentDate.after(startTime) && dtCurrentDate.before(endTime)){
                return true;
            }
            if (dtCurrentDate.before(startTime)||dtCurrentDate.after(endTime)) {
                return false;
            }
            if (dtCurrentDate.equals(startTime)||dtCurrentDate.equals(endTime)) {
                return true;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }
}
