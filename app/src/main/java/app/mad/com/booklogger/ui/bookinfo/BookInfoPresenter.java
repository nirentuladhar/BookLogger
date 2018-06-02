package app.mad.com.booklogger.ui.bookinfo;

import android.util.Log;

import app.mad.com.booklogger.model.Book;
import app.mad.com.booklogger.ui.home.Home;
import app.mad.com.booklogger.ui.search.Search;
import app.mad.com.booklogger.utils.FirebaseHelper;

/**
 * Displays an expanded view of a book
 * This Activity is usually launched when a user clicks on a RecyclerView Book item
 */
public class BookInfoPresenter implements BookInfoContract.Presenter {

    public static final String TAG = "BL " + BookInfoPresenter.class.getName();

    private BookInfoContract.View mView;
    private FirebaseHelper mFirebaseHelper;
    private Book mBook;


    BookInfoPresenter(){
        mFirebaseHelper = new FirebaseHelper();
    }

    public void bind(BookInfoContract.View view) {
        this.mView = view;
    }

    public void unbind() {
        this.mView = null;
    }

    /**
     * Gets the current book from the view
     * and displays the book
     */
    public void loadBook() {
        mBook = mView.getSelectedBook();
        mView.displayBookInfo();
        Log.d(TAG, "Current book loaded and displayed");
    }

    /**
     * Removes the book from all the lists
     */
    @Override
    public void deleteBook() {
        mFirebaseHelper.removeBook(mView.getCurrentRef(), mBook);
        mView.displayBookDeleted();
        mView.closeActivity();
        Log.d(TAG, "Current book deleted");
    }

    /**
     * Sets and displays user rating
     * @param stars user rating
     */
    @Override
    public void setUserRating(int stars) {
        // sets user rating zero on pressing the same rating twice
        if (String.valueOf(stars).equals(mBook.getUserRating())) {
            mBook.setUserRating(String.valueOf(0));
        } else {
            mBook.setUserRating(String.valueOf(stars));
        }
        mFirebaseHelper.updateRating(mView.getCurrentRef(), mBook);
        mView.displayUserRating();
    }

    /**
     * Sets the view of the Notes container based on the current view
     * Eg. if the current view is Search, notes section is removed entirely
     */
    @Override
    public void setUserNotes() {
        mBook = mView.getCurrentBook();
        if (mView.getCurrentRef().equals(Search.SEARCH_REF)) {
            //hide container
            mView.hideNoteContainer();
            Log.d(TAG, "Activity launched from Search. Notes hidden");

        } else {
            //display container
            mView.displayNoteContainer();
            Log.d(TAG, "Activity launched from Home. Notes displayed");
            if (mBook.getNotes().equals("null") || mBook.getNotes().trim().equals("")) {
                // display only 'Write a Review'; hides the rest
                mView.displayWriteNote();
                Log.d(TAG, "User notes empty");
            } else {
                // display the review and 'Edit' button; hides 'Write a Review' button
                mView.displayEditNote();
                Log.d(TAG, "User notes present");
            }
        }
    }

    /**
     * Sets current view
     */
    @Override
    public void setCurrentBookList() {
        // button selected based on what the current view is
        switch (mView.getCurrentRef()) {
            case Home.TOREAD_REF:
                mView.displayToReadSelected();
                break;
            case Home.READING_REF:
                mView.displayReadingSelected();
                break;
            case Home.COMPLETED_REF:
                mView.displayCompletedSelected();
                break;
        }
    }

    @Override
    public void setUserRatingView() {
        if (mView.getCurrentRef().equals(Search.SEARCH_REF)) {
            mView.hideUserRating();
        } else {
            mView.displayUserRating();
        }
    }

    /**
     * Adds book to their respective list
     * based on the button pressed or current reference
     */
    @Override
    public void addBook() {
        // an instance of the book can only exist in one list at a time
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

    /**
     * Helper class for FireBase call to add the current book object
     * @param ref database reference
     */
    private void addBookTo(String ref) {
        mFirebaseHelper.addBook(ref, mBook);
    }

    /**
     * Helper class for FireBase call to remove the current book object
     * @param ref database reference
     */
    private void removeBookFrom(String ref) {
        mFirebaseHelper.removeBook(ref, mBook);
    }


}
