package app.mad.com.booklogger.ui.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
 * A login screen that offers login via email/password and through Google
 */
public class Login extends AppCompatActivity implements LoginContract.View {


    private static final String TAG = "BL " + Login.class.getName();
    private GoogleSignInClient mGoogleSignInClient;
    private LoginContract.Presenter mPresenter;

    TextView mEmail() { return findViewById(R.id.email); }
    TextView mPassword () { return findViewById(R.id.password); }
    Button mLoginButton() { return findViewById(R.id.email_sign_in_button); }
    Button mGoToSignUpButton() { return findViewById(R.id.goto_sign_up_button); }
    SignInButton mSignInGoogleButton() { return findViewById(R.id.button_google_sign_in); }


    public static int RC_SIGN_IN = 1234;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mPresenter = new LoginPresenter();
        mPresenter.bind(this);

        //login using google
        mSignInGoogleButton().setOnClickListener(v -> {
            findViewById(R.id.login_progress_bar).setVisibility(View.VISIBLE);
            signInWithGoogle();
        });

        // login via email
        mLoginButton().setOnClickListener(v -> {
            findViewById(R.id.login_progress_bar).setVisibility(View.VISIBLE);
            mPresenter.loginEmail();
        });

        // create a new account
        // launches sign up activity
        mGoToSignUpButton().setOnClickListener(v -> {
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


    /**
     * Automatic login if the system account was previously signed in.
     */
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
                Log.d(TAG, "Successfully signed in with Google");
            }
        }
    }


    @Override
    public String getEmail() {
        return mEmail().getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return mPassword().getText().toString().trim();
    }

    @Override
    public void showLoginSuccess() {
        findViewById(R.id.login_progress_bar).setVisibility(View.INVISIBLE);
        Intent i = new Intent(this, Home.class);
        startActivity(i);
        Log.d(TAG, "Successfully logged in. Show home screen");
    }

    @Override
    public void showLoginError(String errorMessage) {
        if (errorMessage == null) errorMessage = getString(R.string.generic_error);
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}

