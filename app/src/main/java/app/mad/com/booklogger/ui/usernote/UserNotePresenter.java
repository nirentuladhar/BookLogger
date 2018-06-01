package app.mad.com.booklogger.ui.usernote;

import app.mad.com.booklogger.model.Book;
import app.mad.com.booklogger.utils.FirebaseHelper;

/**
 * Created by Niren on 26/5/18.
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

    @Override
    public void saveNote() {
//        mFirebaseHelper.updateRating();

        Book book = mView.getBook();
        mFirebaseHelper.updateRatingNotes("reading", book);
        mView.displaySaved();
    }
}
