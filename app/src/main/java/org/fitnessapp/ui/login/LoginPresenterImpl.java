package org.fitnessapp.ui.login;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.twitter.ParseTwitterUtils;
import org.fitnessapp.data.db.DatabaseOperationsImp;
import org.fitnessapp.data.db.model.Users;
import org.fitnessapp.util.Helper;
import org.fitnessapp.data.prefs.PrefManager;

public class LoginPresenterImpl implements LoginPresenter {

    private LoginActivity loginActivity;
    private DatabaseOperationsImp databaseOperations;

    LoginPresenterImpl(LoginActivity activity) {
        this.loginActivity = activity;
        databaseOperations = new DatabaseOperationsImp(activity);
    }

    @Override
    public void loginUser(Users users) {
        if(checkUserName(users.getUsername()) && checkPassword(users.getPassword())){
            if(databaseOperations.checkIfCredentialsAreCorrect(users)){
                loginActivity.incorrectCredentials();
            }else {
                int userId = databaseOperations.queryUserId(users.getUsername(),users.getPassword()).getUserId();
                PrefManager.setID(PrefManager.USER_ID, userId);
                loginActivity.loggedSuccessfully();
            }
        }
    }

    @Override
    public void twitterLogin() {

            ParseTwitterUtils.logIn(loginActivity, new LogInCallback() {

                @Override
                public void done(final ParseUser user, ParseException err) {
                    if (err != null) {
                        ParseUser.logOut();
                    }
                    if (user == null) {
                        ParseUser.logOut();
                    } else if (user.isNew()) {
                        user.setUsername(ParseTwitterUtils.getTwitter().getScreenName());
                        user.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (null == e) {
                                    registerinLocalDatabase(user);
                                } else {
                                    ParseUser.logOut();
                                    loginActivity.showErrorAuthenicatedWithTwitter();
                                }
                            }
                        });
                    } else {
                        if(databaseOperations.queryUserId(user.getUsername(),user.getUsername())!=null){
                            //have used username as pass filled as well
                            int userId = databaseOperations.queryUserId(user.getUsername(),user.getUsername()).getUserId();
                            PrefManager.setID(PrefManager.USER_ID, userId);
                            loginActivity.loggedSuccessfully();
                        }else {
                            registerinLocalDatabase(user);
                        }

                    }
                }
            });

    }

    private Boolean checkUserName(String username) {
        if (Helper.checkIfValueIsEmpty(username)) {
            loginActivity.emptyFieldUsername();
            return false;
        }
        return true;
    }

    private Boolean checkPassword(String password) {
        if (Helper.checkIfValueIsEmpty(password)) {
            loginActivity.emptyFieldPassword();
            return false;
        }
        return true;
    }


    private void registerinLocalDatabase(ParseUser user){
        if(databaseOperations.checkIfUserNameExist(user.getUsername())){
            Users users = new Users();
            users.setUsername(user.getUsername());
            //have used username as pass filled as well
            users.setPassword(user.getUsername());
            if(databaseOperations.create(users)){
                int userId = databaseOperations.queryUserId(users.getUsername(),users.getPassword()).getUserId();
                PrefManager.setID(PrefManager.USER_ID, userId);
                loginActivity.showAuthenicatedSuccessfullyWithTwitter();
            }
        }
    }
}
