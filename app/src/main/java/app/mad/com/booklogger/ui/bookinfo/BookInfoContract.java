package app.mad.com.booklogger.ui.bookinfo;

import app.mad.com.booklogger.model.Book;

/**
 * Created by Niren on 13/5/18.
 */

public interface BookInfoContract {

    interface View {
        Book getCurrentBook();
        void displayBookInfo();
    }

    interface Presenter {
        void bind(BookInfoContract.View view);
        void unbind();

        void loadBook();
        void addBookToRead();
        void addBookToCompleted();
        void addBookToReading();
    }

}
