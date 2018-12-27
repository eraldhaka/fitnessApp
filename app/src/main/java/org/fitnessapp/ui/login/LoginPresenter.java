package org.fitnessapp.ui.login;

import org.fitnessapp.data.db.model.Users;

public interface LoginPresenter {

    void loginUser(Users users);

    void twitterLogin();
}
