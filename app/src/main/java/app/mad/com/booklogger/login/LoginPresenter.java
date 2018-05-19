package app.mad.com.booklogger.login;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import app.mad.com.booklogger.R;

/**
 * Created by Niren on 19/5/18.
 */

public class LoginPresenter {
    FirebaseAuth mAuth;
    LoginView mView;
    private GoogleSignInClient mGoogleSignInClient;

    private static final String TAG = "BOOK_LOGGER";

    LoginPresenter(FirebaseAuth auth) {
        this.mAuth = auth;
    }

    void bind(LoginView view) {
        this.mView = view;
    }

    void unbind() {
        this.mView = null;
    }


    void firebaseAuthWithGoogle(Task<GoogleSignInAccount> task) {
        try {
            // Google Sign In was successful, authenticate with Firebase
            GoogleSignInAccount acct = task.getResult(ApiException.class);
            Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
//        showProgressDialog();

            AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener((Activity) mView, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithCredential:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                mView.showLoginSuccess();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithCredential:failure", task.getException());
                                mView.showLoginError();
                            }

//                        hideProgressDialog();
                        }
                    });
        } catch (ApiException e) {
            // Google Sign In failed, update UI appropriately
            Log.w(TAG, "Google sign in failed", e);
            mView.showLoginError();
        }
    }

    void signIn(FirebaseUser user) {
        if (user != null) {
            mView.showLoginSuccess();
        } else {
            mView.showLoginError();
        }
    }
}