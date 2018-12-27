package org.fitnessapp.data.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "user_walks")
public class UserWalks {

    @DatabaseField(columnName = "walksId",generatedId = true)
    public int walksId;

    @DatabaseField(columnName = "userId")
    public int userId;

    @DatabaseField(columnName = "distanceWalked")
    public float distanceWalked;

    @DatabaseField(columnName = "timeWalked")
    public long timeWalked;

    @DatabaseField(columnName = "date")
    public Date date;


    public int getWalksId() {
        return walksId;
    }

    public void setWalksId(int walksId) {
        this.walksId = walksId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public float getDistanceWalked() {
        return distanceWalked;
    }

    public void setDistanceWalked(float distanceWalked) {
        this.distanceWalked = distanceWalked;
    }

    public long getTimeWalked() {
        return timeWalked;
    }

    public void setTimeWalked(long timeWalked) {
        this.timeWalked = timeWalked;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void updateDistanceCovered(float distance) {
        this.distanceWalked = this.distanceWalked + distance;
    }

    public void updateTotalTimeWalk(long time) {
        this.timeWalked = this.timeWalked + time;
    }
}
