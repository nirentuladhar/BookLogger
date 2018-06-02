package app.mad.com.booklogger.ui.usernote;

import app.mad.com.booklogger.model.Book;
import app.mad.com.booklogger.utils.FirebaseHelper;

/**
 * Activity to display, edit and save user notes and rating
 */
public class UserNotePresenter implements UserNoteContract.presenter {
    UserNoteContract.view mView;
    FirebaseHelper mFirebaseHelper;

    public UserNotePresenter(){
        mFirebaseHelper = new FirebaseHelper();
    }

    @Override
    public void bind(UserNoteContract.view view) {
        this.mView = view;
    }

    @Override
    public void unbind() {
        this.mView = null;
    }

    /**
     * If the user saves the note, returns the new book object through an intent
     */
    @Override
    public void saveNote() {
        Book book = mView.getBook();
        mFirebaseHelper.updateRatingNotes("reading", book);
        mView.displaySaved();
    }
}
