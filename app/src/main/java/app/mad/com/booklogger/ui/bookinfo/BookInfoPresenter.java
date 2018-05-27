package app.mad.com.booklogger.ui.bookinfo;

import app.mad.com.booklogger.model.Book;
import app.mad.com.booklogger.ui.home.Home;
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
    public void deleteBook() {
        mFirebaseHelper.removeBook(mView.getCurrentRef(), mBook);
        mView.displayBookDeleted();
        mView.closeActivity();
    }

    @Override
    public void setUserRating(int stars) {
        if (String.valueOf(stars).equals(mBook.getUserRating())) {
            mBook.setUserRating(String.valueOf(0));
        } else {
            mBook.setUserRating(String.valueOf(stars));
        }
        mFirebaseHelper.updateRating(mView.getCurrentRef(), mBook);
        mView.displayUserRating();
    }

    @Override
    public void addBook() {
        switch (mView.getCurrentRef()) {
            case Home.COMPLETED_REF:
                addBookTo(Home.COMPLETED_REF);
                removeBookFrom(Home.READING_REF);
                removeBookFrom(Home.TOREAD_REF);
                break;
            case Home.READING_REF:
                addBookTo(Home.READING_REF);
                removeBookFrom(Home.COMPLETED_REF);
                removeBookFrom(Home.TOREAD_REF);
                break;
            case Home.TOREAD_REF:
                addBookTo(Home.TOREAD_REF);
                removeBookFrom(Home.COMPLETED_REF);
                removeBookFrom(Home.READING_REF);
                break;
        }
        mView.displayBookAdded();
    }

    private void addBookTo(String ref) {
        mFirebaseHelper.addBook(ref, mBook);
    }

    private void removeBookFrom(String ref) {
        mFirebaseHelper.removeBook(ref, mBook);
    }


}
