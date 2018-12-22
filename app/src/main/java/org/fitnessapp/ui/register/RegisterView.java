package org.fitnessapp.ui.register;

public interface RegisterView {
    void emptyFieldUsername();

    void emptyFieldPassword();

    void showRegisteredSuccessfully(String string);

    void showError(String string);

    void usernameExists();
}
