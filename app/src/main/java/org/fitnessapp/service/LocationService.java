package org.fitnessapp.service;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.fitnessapp.util.Constant;
import org.fitnessapp.util.Helper;

public class LocationService extends Service{

    private static final int NOTIFICATION_ID = 11;

    private boolean isUserWalking;
    private Location mCurrentLocation;
    private Location mPreviousLocation;
    private boolean isBroadcastAllow;
    private float mDistanceCovered;
    private long startTime;
    LocationCallback mLocationCallback;
    private IBinder mIBinder = new LocalBinder();
    private  LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    public void onCreate() {
        super.onCreate();
        isUserWalking = false;
        startTime = 0;

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)
                .setFastestInterval(5 * 1000);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationProvider();
    }

    private void locationProvider(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations, this can be null.
                if (location != null) {
                      handleInitialLocation(location);
                }
            }
        });

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    handleNewLocation(location);
                }
            }
        };
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,mLocationCallback,null /* Looper */);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    public void handleInitialLocation(Location location) {
        mCurrentLocation = location;
    }

    public void handleNewLocation(Location location) {
        // Getting new location
        mCurrentLocation = location;
        calculateDistance();
        broadCastLocation(mCurrentLocation);
    }
    // Assume this algorithm calculates precise totalDistanceWalked
    private void calculateDistance(){
        if(isUserWalking) {
            float distanceDiff = mPreviousLocation.distanceTo(mCurrentLocation); // Return meter unit
            mDistanceCovered = mDistanceCovered + distanceDiff;
            mPreviousLocation = mCurrentLocation;
        }
    }

    public void startBroadcasting() {
        // Start broadcast
        isBroadcastAllow = true;
        broadcastFirstLocation();
    }

    private void broadcastFirstLocation() {
        if(mCurrentLocation != null){
            broadCastLocation(mCurrentLocation);
        }
    }

    public void stopBroadcasting() {
        // Stop broadcast
        isBroadcastAllow = false;
    }

    @Override
    public void onDestroy() {
        isUserWalking = false;
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    private void broadCastLocation(Location location) {
        if(isBroadcastAllow){
            broadcastUserLocation(location);
        }
    }

    private void broadcastUserLocation(Location location) {
        Intent in = new Intent(Constant.ACTION_NAME_SPACE);
        in.putExtra(Constant.INTENT_EXTRA_RESULT_CODE, Activity.RESULT_OK);
        in.putExtra(Constant.INTENT_USER_LAT_LNG, location);
        LocalBroadcastManager.getInstance(this).sendBroadcast(in);
    }

    public long elapsedTime() {
        return (System.currentTimeMillis() - startTime) / 1000;
    }

    public float distanceCovered() {
        return mDistanceCovered;
    }

    public boolean isUserWalking() {
        return isUserWalking;
    }

    public void startUserWalk() {
        if(!isUserWalking){
            startTime = System.currentTimeMillis();
            mPreviousLocation = mCurrentLocation;
            mDistanceCovered = 0;
            isUserWalking = true;
        }
    }

    public void stopUserWalk() {
        if(isUserWalking){
            isUserWalking = false;
        }
    }

    public class LocalBinder extends Binder {
        public LocationService getService(){
            return LocationService.this;
        }
    }

    // Prevent system killing the background service
    public void startForeground() {
        startForeground(NOTIFICATION_ID, Helper.createNotification(this,"Keep Walking","Tap to return to walk activity"));
    }

    public void stopNotification() {
        stopForeground(true);
    }
}
