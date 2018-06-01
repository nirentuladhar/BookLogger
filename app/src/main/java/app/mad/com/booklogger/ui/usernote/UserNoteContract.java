package app.mad.com.booklogger.ui.usernote;

import app.mad.com.booklogger.model.Book;

/**
 * Created by Niren on 26/5/18.
 */

public interface UserNoteContract {
    interface view {
        String getNote();
        void displaySaved();
        Book getBook();
    }
    interface presenter {
        void bind(UserNoteContract.view view);
        void unbind();

        void saveNote();
    }
}
