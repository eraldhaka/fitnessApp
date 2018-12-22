package org.fitnessapp.ui.register;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.fitnessapp.R;
import org.fitnessapp.data.db.model.Users;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements RegisterView{

    @BindView(R.id.edit_text_username)
    EditText edt_username;
    @BindView(R.id.edit_text_password)
    EditText edt_password;
    @BindView(R.id.button_register)
    Button btn_register;

    private Users users;
    private RegisterPresenterImpl registerPresenterImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);
        users = new Users();
        registerPresenterImpl = new RegisterPresenterImpl(this);
    }

    @OnClick(R.id.button_register)
    public void registerUser(View view){
        users.setUsername(edt_username.getText().toString());
        users.setPassword(edt_password.getText().toString());
        registerPresenterImpl.registerUser(users);

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
    public void showRegisteredSuccessfully(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void usernameExists() {
        Toast.makeText(getApplicationContext(),R.string.usernameExists,Toast.LENGTH_SHORT).show();
    }
}
