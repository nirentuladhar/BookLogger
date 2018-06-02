package app.mad.com.booklogger.ui.login;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * A login screen that offers login via email/password and through Google
 */
public class LoginPresenter implements LoginContract.Presenter {
    FirebaseAuth mAuth;
    LoginContract.View mView;
    private GoogleSignInClient mGoogleSignInClient;

    private static final String TAG = "BOOK_LOGGER";

    LoginPresenter() {
        this.mAuth = FirebaseAuth.getInstance();
    }

    public void bind(LoginContract.View view) {
        this.mView = view;
    }

    public void unbind() {
        this.mView = null;
    }


    @Override
    public void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        mView.showLoginSuccess();
                    }
                })
                .addOnFailureListener(e -> mView.showLoginError(e.getLocalizedMessage()));
    }

    public void loginGoogle() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            mView.showLoginSuccess();
        }
    }

    @Override
    public void loginEmail() {
        mAuth.signInWithEmailAndPassword(mView.getEmail(), mView.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        mView.showLoginSuccess();
                    }
                })
                .addOnFailureListener(e -> mView.showLoginError(e.getLocalizedMessage()));
    }
}