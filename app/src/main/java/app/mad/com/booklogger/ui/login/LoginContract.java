package app.mad.com.booklogger.ui.login;

import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

/**
 * A login screen that offers login via email/password and through Google
 */
public interface LoginContract {

    interface View {
        String getEmail();
        String getPassword();
        void showLoginSuccess();
        void showLoginError(String errorMessage);
    }

    interface Presenter {
        void bind(LoginContract.View view);
        void unbind();

        void loginGoogle();
        void loginEmail();

        void firebaseAuthWithGoogle(GoogleSignInAccount account);

    }

}
