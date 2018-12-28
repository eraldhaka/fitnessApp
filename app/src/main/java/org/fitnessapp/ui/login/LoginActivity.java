package org.fitnessapp.ui.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.fitnessapp.R;
import org.fitnessapp.data.db.model.Users;
import org.fitnessapp.ui.main_activity.MainActivity;
import org.fitnessapp.ui.register.RegisterActivity;
import org.fitnessapp.util.Helper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.edit_text_username)
    EditText edtUsername;
    @BindView(R.id.edit_text_password)
    EditText edtPassword;
    @BindView(R.id.button_login)
    Button btnLogin;
    @BindView(R.id.button_register)
    Button btnRegister;
    @BindView(R.id.button_twitter_login)
    Button btnTwitterLogin;

    private Users users;
    private LoginPresenterImpl loginPresenterImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        users = new Users();
        loginPresenterImpl = new LoginPresenterImpl(this);
    }

    @OnClick(R.id.button_login)
    public void loginUser(View view){
        users.setUsername(edtUsername.getText().toString());
        users.setPassword(edtPassword.getText().toString());
        loginPresenterImpl.loginUser(users);
    }

    @OnClick(R.id.button_register)
    public void registerUser(View view){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_twitter_login)
    public void loginWithTwitter(View view){
        loginPresenterImpl.twitterLogin();
    }

    @Override
    public void emptyFieldUsername() {
        edtUsername.setError(getString(R.string.username_field_empty));
    }

    @Override
    public void emptyFieldPassword() {
        edtPassword.setError(getString(R.string.password_field_empty));
    }

    @Override
    public void incorrectCredentials() {
        Toast.makeText(getApplicationContext(),R.string.incorrect_credentials,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loggedSuccessfully() {
        Toast.makeText(getApplicationContext(),R.string.logged_successfully,Toast.LENGTH_SHORT).show();
        initMainActivity();
    }

    @Override
    public void showAuthenicatedSuccessfullyWithTwitter() {
        initMainActivity();
    }

    @Override
    public void showErrorAuthenicatedWithTwitter() {
        Toast.makeText(getApplicationContext(),R.string.twitter_error_response,Toast.LENGTH_SHORT).show();
    }

    private void initMainActivity() {
        startActivity(Helper.getIntent(this,MainActivity.class));
    }


}
