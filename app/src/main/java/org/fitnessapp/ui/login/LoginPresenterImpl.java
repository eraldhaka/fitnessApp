package org.fitnessapp.ui.login;

import org.fitnessapp.data.db.DatabaseOperationsImp;
import org.fitnessapp.data.db.model.Users;
import org.fitnessapp.util.Util;

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
                if(users!=null){
                    int userId = databaseOperations.queryUserId(users.getUsername(),users.getPassword()).getUserId();
                    loginActivity.loggedSuccessfully(userId);
                }

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
}
