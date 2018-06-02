package app.mad.com.booklogger.ui.signup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import app.mad.com.booklogger.R;
import app.mad.com.booklogger.ui.login.Login;

/**
 * Sign Up users using email
 */
public class SignUp extends AppCompatActivity implements SignUpContract.View{

    public static final String TAG = "BOOK_LOGGER signup";

    Button mGoToLoginButton() { return findViewById(R.id.goto_sign_in_button); }
    Button mSignUpButton() { return findViewById(R.id.email_sign_up_button); }
    TextView mEmailTextView() { return findViewById(R.id.sign_up_email_textview); }
    TextView mPasswordTextView() { return findViewById(R.id.sign_up_password_textview); }
    FirebaseAuth mAuth;
    SignUpContract.Presenter mPresenter;



    SignUp() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        mPresenter = new SignUpPresenter(mAuth);
        mPresenter.bind(this);

        mGoToLoginButton().setOnClickListener(v -> {
            Intent i = new Intent(this, Login.class);
            startActivity(i);
        });

        mSignUpButton().setOnClickListener(v -> mPresenter.signUp());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unbind();
    }

    @Override
    public String getEmailTextView() {
        return mEmailTextView().getText().toString().trim();
    }

    @Override
    public String getPasswordTextView() {
        return mPasswordTextView().getText().toString().trim();
    }

    @Override
    public void displayError(String errorMessage) {
        if (errorMessage == null) errorMessage = getString(R.string.generic_error);
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress(String message) {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void displaySuccess() {
        Intent i = new Intent(this, Login.class);
        startActivity(i);
        Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();
    }
}
