package org.fitnessapp.ui.register;

import org.fitnessapp.R;
import org.fitnessapp.data.db.DatabaseOperationsImp;
import org.fitnessapp.data.db.model.Users;
import org.fitnessapp.util.Helper;
import org.fitnessapp.util.PrefManager;

public class RegisterPresenterImpl implements RegisterPresenter {

    private RegisterActivity registerActivity;
    private DatabaseOperationsImp databaseOperations;

    public RegisterPresenterImpl(RegisterActivity activity) {
        this.registerActivity = activity;
        databaseOperations = new DatabaseOperationsImp(activity);
    }

    @Override
    public void registerUser(Users users) {
        if(checkUserName(users.getUsername()) && checkPassword(users.getPassword())){
            if(databaseOperations.checkIfUserNameExist(users.getUsername())){
                if(databaseOperations.create(users)){
                    int userId = databaseOperations.queryUserId(users.getUsername(),users.getPassword()).getUserId();
                    PrefManager.setID(PrefManager.USER_ID, userId);
                    registerActivity.showRegisteredSuccessfully(registerActivity.getString(R.string.success_while_registering));
                }else{
                    registerActivity.showError(registerActivity.getString(R.string.error_while_registering));
                }
            }else {
                registerActivity.usernameExists();
            }
        }
    }

    private Boolean checkUserName(String username) {
        if (Helper.checkIfValueIsEmpty(username)) {
            registerActivity.emptyFieldUsername();
            return false;
        }
        return true;
    }

    private Boolean checkPassword(String password) {
        if (Helper.checkIfValueIsEmpty(password)) {
            registerActivity.emptyFieldPassword();
            return false;
        }
        return true;
    }
}
