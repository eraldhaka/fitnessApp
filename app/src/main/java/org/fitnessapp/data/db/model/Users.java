package org.fitnessapp.data.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "person")
public class Users {

    // Fields
    // Primary key defined as an auto generated integer
    // Database table column name

    @DatabaseField(columnName = "userId",generatedId = true)
    public int userId;

    @DatabaseField(columnName = "username")
    public String username;

    @DatabaseField(columnName = "password")
    public String password;

    @DatabaseField(columnName = "totalDistanceWalked")
    public float totalDistanceWalked;

    @DatabaseField(columnName = "time")
    public long totalTimeWalked;


    // Getter and Setter method of fields


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public float getTotalDistanceWalked() {
        return totalDistanceWalked;
    }

    public void setTotalDistanceWalked(float totalDistanceWalked) {
        this.totalDistanceWalked = totalDistanceWalked;
    }

    public long getTotalTimeWalked() {
        return totalTimeWalked;
    }

    public void setTotalTimeWalked(long totalTimeWalked) {
        this.totalTimeWalked = totalTimeWalked;
    }

    public void updateDistanceCovered(float distanceWalked) {
        this.totalDistanceWalked = this.totalDistanceWalked + distanceWalked;
    }

    public void updateTotalTimeWalk(long timeWalked) {
        this.totalTimeWalked = this.totalTimeWalked +timeWalked;
    }
}
