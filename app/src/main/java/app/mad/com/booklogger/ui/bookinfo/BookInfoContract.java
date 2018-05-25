package app.mad.com.booklogger.ui.bookinfo;

import app.mad.com.booklogger.model.Book;

/**
 * Created by Niren on 13/5/18.
 */

public interface BookInfoContract {

    interface View {
        Book getCurrentBook();
        void displayBookInfo();

        void closeActivity();

        void showBookAdded();

        void showBookRemoved();
    }

    interface Presenter {
        void bind(BookInfoContract.View view);
        void unbind();

        void loadBook();

        void removeBook(String ref);

        void addBook(String ref);
    }

}
