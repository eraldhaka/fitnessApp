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
import org.fitnessapp.util.PrefManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.edit_text_username)
    EditText edt_username;
    @BindView(R.id.edit_text_password)
    EditText edt_password;
    @BindView(R.id.button_login)
    Button btn_login;
    @BindView(R.id.button_register)
    Button btn_register;

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
        users.setUsername(edt_username.getText().toString());
        users.setPassword(edt_password.getText().toString());
        loginPresenterImpl.loginUser(users);
    }

    @OnClick(R.id.button_register)
    public void registerUser(View view){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void emptyFieldUsername() {
        edt_username.setError(getString(R.string.username_field_empty));
    }

    @Override
    public void emptyFieldPassword() {
        edt_password.setError(getString(R.string.password_field_empty));
    }

    @Override
    public void incorrectCredentials() {
        Toast.makeText(getApplicationContext(),R.string.incorrect_credentials,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loggedSuccessfully(int userId) {
        PrefManager.setID(PrefManager.USER_ID, userId);
        Toast.makeText(getApplicationContext(),R.string.logged_successfully,Toast.LENGTH_SHORT).show();
        initMainActivity();
    }

    private void initMainActivity() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
