package app.mad.com.booklogger.ui.bookinfo;

import app.mad.com.booklogger.model.Book;

/**
 * Created by Niren on 13/5/18.
 */

public interface BookInfoContract {

    interface View {
        Book getCurrentBook();
        String getCurrentRef();
        void displayBookInfo();
        void displayBookDeleted();
        void displayBookAdded();
        void displayDeleteConfirmation();
        void displayUserRating();
        void closeActivity();

    }

    interface Presenter {
        void bind(BookInfoContract.View view);
        void unbind();

        void loadBook();
        void addBook();
        void deleteBook();
        void setUserRating(int stars);
    }

}
