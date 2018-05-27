package app.mad.com.booklogger.ui.userreview;

import app.mad.com.booklogger.model.Book;

/**
 * Created by Niren on 26/5/18.
 */

public interface UserReviewContract {
    interface view {
        String getNote();
        void displaySaved();
        Book getBook();
    }
    interface presenter {
        void bind(UserReviewContract.view view);
        void unbind();

        void saveNote();
    }
}
