package app.mad.com.booklogger.ui.login;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Niren on 19/5/18.
 */

public interface LoginContract {

    interface View {
        void showLoginSuccess();
        void showLoginError();
    }

    interface Presenter {
        void bind(LoginContract.View view);
        void unbind();

        void signIn();
        void firebaseAuthWithGoogle(Task<GoogleSignInAccount> task);
    }

}
