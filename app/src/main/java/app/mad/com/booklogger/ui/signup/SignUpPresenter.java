package app.mad.com.booklogger.ui.signup;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Sign Up users using email
 */
public class SignUpPresenter implements SignUpContract.Presenter {
    public static final String TAG = "BOOK_LOGGER signup";
    private FirebaseAuth mAuth;
    private SignUpContract.View mView;

    SignUpPresenter(FirebaseAuth auth) {
        this.mAuth = auth;
    }

    @Override
    public void bind(SignUpContract.View view) {
        mView = view;
    }

    @Override
    public void unbind() {
        mView = null;
    }

    @Override
    public void signUp() {
        mAuth.createUserWithEmailAndPassword(mView.getEmailTextView(), mView.getPasswordTextView())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        mView.displaySuccess();
                    }
                })
                .addOnFailureListener(e -> mView.displayError(e.getLocalizedMessage()));
    }


}
