package app.mad.com.booklogger.ui.bookinfo;

import app.mad.com.booklogger.model.Book;
import app.mad.com.booklogger.utils.FirebaseHelper;

/**
 * Created by Niren on 13/5/18.
 */

public class BookInfoPresenter implements BookInfoContract.Presenter {

    private static final String TAG = "BOOK_LOGGER " + BookInfoPresenter.class.getSimpleName();

    private BookInfoContract.View mView;
    private FirebaseHelper mFirebaseHelper;
    private Book mBook;


    BookInfoPresenter(){
        mFirebaseHelper = new FirebaseHelper();
    }

    public void loadBook() {
        mBook = mView.getCurrentBook();
        mView.displayBookInfo();
    }

    public void bind(BookInfoContract.View view) {
        this.mView = view;
    }

    public void unbind() {
        this.mView = null;
    }

    public void addBookToRead() {
        mFirebaseHelper.addBook("toread", mBook);
    }

    public void addBookToCompleted() {
        mFirebaseHelper.addBook("completed", mBook);
    }

    public void addBookToReading() {
        mFirebaseHelper.addBook("reading", mBook);
    }
}
