package app.mad.com.booklogger.bookinfo;

import app.mad.com.booklogger.model.Book;
import app.mad.com.booklogger.utils.FirebaseHelper;

/**
 * Created by Niren on 13/5/18.
 */

public class BookInfoPresenter {

    private static final String TAG = "BOOK_LOGGER " + BookInfoPresenter.class.getSimpleName();

    BookInfoView mView;

    BookInfoPresenter(){
    }


    public void loadBook() {
        mView.displayBookInfo();
    }

    public void bind(BookInfoActivity view) {
        this.mView = view;
    }

    public void unbind() {
        this.mView = null;
    }

    public void addBook(Book book) {
        FirebaseHelper firebaseHelper = new FirebaseHelper();
        firebaseHelper.addBook("toread", book);
    }
}
