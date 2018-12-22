package org.fitnessapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveData {

    Context ctx;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public SaveData(Context ctx1) {
        this.ctx = ctx1;
        preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        editor = preferences.edit();

    }
    public void saveUserData(int id, String name, String surname, String email, String city, String bio, String gender, String sentimental_status, String born_at, String profession, int room_id, Boolean has_room, String admin) {
        editor.putInt("user_id", id);
        editor.putString("user_name", name);
        editor.putString("user_surname", surname);
        editor.putString("user_email", email);
        editor.putString("user_city", city);
        editor.putString("user_bio", bio);
        editor.putString("user_gender", gender);
        editor.putString("user_sentimental_status", sentimental_status);
        editor.putString("user_born_at", born_at);
        editor.putString("user_profession", profession);
        editor.putInt("user_room_id", room_id);
        editor.putBoolean("user_has_room", has_room);
        editor.putString("user_admin", admin);
        editor.commit();
    }

    public void setHasRoom(boolean user_has_room) {
        editor.putBoolean("user_has_room", user_has_room);
        editor.commit();
    }

    public void setRoomId(int room_id) {
        editor.putInt("user_room_id", room_id);
        editor.commit();
    }


    public int getUserId() {
        return preferences.getInt("user_id", 0);
    }
    public String getUserName() {
        return preferences.getString("user_name", "");
    }
    public String getUserSurname() {
        return preferences.getString("user_surname", "");
    }
    public String getUserEmail() {
        return preferences.getString("user_email", "");
    }
    public String getUserCity() {
        return preferences.getString("user_city", "");
    }
    public String getUserBio() {
        return preferences.getString("user_bio", "");
    }
    public String getUserGender() {
        return preferences.getString("user_gender", "");
    }
    public String getUserSentimentalStatus() {
        return preferences.getString("user_sentimental_status", "");
    }
    public String getUserBornAt() {
        return preferences.getString("user_born_at", "");
    }
    public String getUserProfession() {
        return preferences.getString("user_profession", "");
    }
    public int getUserRoomId() {
        return preferences.getInt("user_room_id", -1);
    }

    public Boolean getUserHasRoom() {
        return preferences.getBoolean("user_has_room", false);
    }

    public String getUserAdmin() {
        return preferences.getString("user_admin", "");
    }


    public void saveToken(String token) {
        editor.putString("user_token", token);
        editor.commit();
    }

    public String getToken() {
        return preferences.getString("user_token", "");
    }



    public void saveFlagOrigin(String flagOrigin) {
        editor.putString("flagOrigin", flagOrigin);
        editor.commit();
    }

    public String getFlagOrigin() {
        return preferences.getString("flagOrigin", "");
    }

    //////////////////////////// ClearAll is used to clear all data saved in SaveData, it is used when user log out //////////////////////////
    public void clearAll() {
        editor.clear();
        editor.commit();
    }
}
