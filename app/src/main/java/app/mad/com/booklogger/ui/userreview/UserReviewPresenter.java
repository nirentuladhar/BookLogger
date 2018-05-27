package app.mad.com.booklogger.ui.userreview;

import app.mad.com.booklogger.model.Book;
import app.mad.com.booklogger.utils.FirebaseHelper;

/**
 * Created by Niren on 26/5/18.
 */

public class UserReviewPresenter implements UserReviewContract.presenter {
    UserReviewContract.view mView;
    FirebaseHelper mFirebaseHelper;

    public UserReviewPresenter(){
        mFirebaseHelper = new FirebaseHelper();
    }

    @Override
    public void bind(UserReviewContract.view view) {
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
