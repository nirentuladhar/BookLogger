package app.mad.com.booklogger.ui.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;

import app.mad.com.booklogger.R;
import app.mad.com.booklogger.ui.signup.SignUp;
import app.mad.com.booklogger.ui.home.Home;

/**
 * A login screen that offers login via email/password.
 */
public class Login extends AppCompatActivity implements LoginContract.View {


    private static final String TAG = "Login";
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private LoginContract.Presenter mPresenter;
    TextView mEmail;
    TextView mPassword;
    Button mLoginButton;

    Button mGoToSignUpButton;
    SignInButton mSignInGoogleButton;

    public static int RC_SIGN_IN = 1234;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mPresenter = new LoginPresenter(mAuth);
        mPresenter.bind(this);


        mLoginButton = findViewById(R.id.email_sign_in_button);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mGoToSignUpButton = findViewById(R.id.goto_sign_up_button);
        mSignInGoogleButton = findViewById(R.id.button_google_sign_in);

        mSignInGoogleButton.setOnClickListener(v -> {
            signInWithGoogle();
        });

        mLoginButton.setOnClickListener(v -> {
            mPresenter.loginEmail();
        });

        mGoToSignUpButton.setOnClickListener(v -> {
            Intent i = new Intent(this, SignUp.class);
            startActivity(i);
        });

        setGoogleClient();
    }

    private void setGoogleClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }


    @Override
    public void onStart() {
        super.onStart();
        mPresenter.loginGoogle();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unbind();
    }

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                mPresenter.firebaseAuthWithGoogle(account);
            }
        }
    }


    @Override
    public String getEmail() {
        return mEmail.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return mPassword.getText().toString().trim();
    }

    @Override
    public void showLoginSuccess() {
        Intent i = new Intent(this, Home.class);
        startActivity(i);
    }

    @Override
    public void showLoginError(String errorMessage) {
        if (errorMessage == null) errorMessage = getString(R.string.generic_error);
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}

