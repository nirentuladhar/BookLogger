package app.mad.com.booklogger.ui.signup;

/**
 * Created by Niren on 25/5/18.
 */

public interface SignUpContract {
    interface View {
        String getEmail();
        String getPassword();

        void displayError(String errorMessage);

        void showProgress(String message);
        void dismissProgress();
        void showMessage(String message);
        void displaySuccess();
    }

    interface Presenter{
        void bind(View view);
        void unbind();

        void signUp();
    }
}
