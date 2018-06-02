package app.mad.com.booklogger.ui.home;

import android.support.v4.app.Fragment;

/**
 * Main screen for users where they can view their saved books
 * and perform other actions
 */
public interface HomeContract {
    interface view {
        void displaySignedOut();

    }
    interface presenter {
        void bind(HomeContract.view view);
        void unbind();

        void signOut();
    }
}
