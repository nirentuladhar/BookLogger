package app.mad.com.booklogger.ui.bookinfo;

import android.util.Log;

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

    @Override
    public void addBook(String ref) {
        mFirebaseHelper.addBook(ref, mBook);
//        mView.closeActivity();
        mView.showBookAdded();
    }


    @Override
    public void removeBook(String ref) {
        mBook = mView.getCurrentBook();
        mFirebaseHelper.removeBook(ref, mBook);
        mView.showBookRemoved();
    }


}
