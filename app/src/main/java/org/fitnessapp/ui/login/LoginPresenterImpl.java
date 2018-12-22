package org.fitnessapp.ui.login;

import org.fitnessapp.R;
import org.fitnessapp.data.db.DatabaseOperationsImp;
import org.fitnessapp.data.db.model.Users;
import org.fitnessapp.util.Util;

import java.util.List;

public class LoginPresenterImpl implements LoginPresenter {

    private LoginActivity loginActivity;
    private DatabaseOperationsImp databaseOperations;

    public LoginPresenterImpl(LoginActivity activity) {
        this.loginActivity = activity;
        databaseOperations = new DatabaseOperationsImp(activity);
    }

    @Override
    public void loginUser(Users users) {
        if(checkUserName(users.getUsername()) && checkPassword(users.getPassword())){
            if(databaseOperations.checkIfCredentialsAreCorrect(users)){
                loginActivity.incorrectCredentials();
            }else {
                loginActivity.loggedSuccessfully();
            }
        }
    }




    private Boolean checkUserName(String username) {
        if (Util.checkIfValueIsEmpty(username)) {
            loginActivity.emptyFieldUsername();
            return false;
        }
        return true;
    }

    private Boolean checkPassword(String password) {
        if (Util.checkIfValueIsEmpty(password)) {
            loginActivity.emptyFieldPassword();
            return false;
        }
        return true;
    }

    private boolean checkIfCredentialsAreCorrect(String username, String password) {

        List<Users> users =  databaseOperations.read();
        for(int i=0 ; i<users.size();i++){
            if(username.equals(users.get(i).getUsername())){
               // registerActivity.usernameExists();
                return false;
            }
        }

        return true;
    }
}
