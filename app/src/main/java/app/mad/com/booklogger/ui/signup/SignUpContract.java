package app.mad.com.booklogger.ui.signup;

/**
 * Sign Up users using email
 */
public interface SignUpContract {
    interface View {
        String getEmailTextView();
        String getPasswordTextView();

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
