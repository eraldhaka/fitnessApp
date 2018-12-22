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

    @DatabaseField(columnName = "distance")
    public float distance;

    @DatabaseField(columnName = "time")
    public long timeWalk;


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

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public long getTimeWalk() {
        return timeWalk;
    }

    public void setTimeWalk(long timeWalk) {
        this.timeWalk = timeWalk;
    }
}
