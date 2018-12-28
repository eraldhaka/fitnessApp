package org.fitnessapp.util;

import android.annotation.SuppressLint;
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

import static org.fitnessapp.util.Constant.CHANNEL_ID;
import static org.fitnessapp.util.Constant.FEET_TO_METER;
import static org.fitnessapp.util.Constant.ID;
import static org.fitnessapp.util.Constant.MILE_TO_METER;
import static org.fitnessapp.util.Constant.MIN_TO_SEC;
import static org.fitnessapp.util.Constant.NAME;
import static org.fitnessapp.util.Constant.TIME_FORMAT_12_HOURS;
import static org.fitnessapp.util.Constant.TIME_FORMAT_AM;
import static org.fitnessapp.util.Constant.TIME_FORMAT_PM;

public class Helper {

    public static boolean checkIfValueIsEmpty(String s) {
        return s.equalsIgnoreCase("");
    }

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
        return (int) seconds / MIN_TO_SEC; // 1 min = 60 sec
    }

    public static float meterToMileConverter(float meter) {
        return meter / MILE_TO_METER; // 1 mile = 1609 meter
    }

    public static int getNumberOfMilestones(float meter) {
        return (int) meter / FEET_TO_METER; // 1000 feet -> 304.8
    }

    public static String secondToHHMMSS(long secondsCount) {
        long seconds = secondsCount % MIN_TO_SEC;
        secondsCount -= seconds;
        long minutesCount = secondsCount / MIN_TO_SEC;
        long minutes = minutesCount % MIN_TO_SEC;
        minutesCount -= minutes;
        long hoursCount = minutesCount / MIN_TO_SEC;
        return "" + hoursCount + ":" + minutes + ":" + seconds;
    }

    public static Notification createNotification (Context context,String title, String message){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel(ID, NAME, importance);
            Objects.requireNonNull(notificationManager).createNotificationChannel(notificationChannel);
            builder = new NotificationCompat.Builder(context, notificationChannel.getId());
        } else {
            builder = new NotificationCompat.Builder(context,CHANNEL_ID);
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
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT_12_HOURS, Locale.getDefault());
        try {
            Date startTime = sdf.parse(strTimeToCompare);
            Date endTime = sdf.parse(endTimeToCompare);
            dtMin = cal.get(Calendar.MINUTE);
            dtHour = cal.get(Calendar.HOUR);
            iAMPM = cal.get(Calendar.AM_PM);
            if (iAMPM == 1) {
                strAMorPM = TIME_FORMAT_PM;
            }
            if (iAMPM == 0) {
                strAMorPM = TIME_FORMAT_AM;
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

    public static Date getDate(){

        Date c = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        Date myDate = null;
        try {
             myDate = df.parse(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return myDate;
    }
}
