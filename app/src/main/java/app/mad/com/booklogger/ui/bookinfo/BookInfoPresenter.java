package app.mad.com.booklogger.ui.bookinfo;

import app.mad.com.booklogger.model.Book;
import app.mad.com.booklogger.utils.FirebaseHelper;

/**
 * Created by Niren on 13/5/18.
 */

public class BookInfoPresenter implements BookInfoContract.Presenter {

    private static final String TAG = "BOOK_LOGGER " + BookInfoPresenter.class.getSimpleName();

    BookInfoContract.View mView;
    FirebaseHelper mFirebaseHelper;

    BookInfoPresenter(){
        mFirebaseHelper = new FirebaseHelper();
    }

    public void loadBook() {
        mView.displayBookInfo();
    }

    public void bind(BookInfoContract.View view) {
        this.mView = view;
    }

    public void unbind() {
        this.mView = null;
    }

    public void addBookToRead(Book book) {
        mFirebaseHelper.addBook("toread", book);
    }

    public void addBookToCompleted(Book book) {
        mFirebaseHelper.addBook("completed", book);
    }

    public void addBookToReading(Book book) {
        mFirebaseHelper.addBook("reading", book);
    }
}
