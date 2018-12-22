package org.fitnessapp.data.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "person")
public class Users {

    // Fields
    // Primary key defined as an auto generated integer
    // Database table column name

    @DatabaseField(columnName = "user_id",generatedId = true)
    public int user_id;

    @DatabaseField(columnName = "username")
    public String username;

    @DatabaseField(columnName = "password")
    public String password;


    // Getter and Setter method of fields
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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
}
