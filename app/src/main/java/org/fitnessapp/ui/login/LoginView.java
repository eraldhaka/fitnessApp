package org.fitnessapp.ui.login;

public interface LoginView {
    void emptyFieldUsername();

    void emptyFieldPassword();

    void incorrectCredentials();

    void loggedSuccessfully();

    void showAuthenicatedSuccessfullyWithTwitter();

    void showErrorAuthenicatedWithTwitter();
}
