package app.mad.com.booklogger.ui.usernote;

import app.mad.com.booklogger.model.Book;

/**
 * Activity to display, edit and save user notes and rating
 */
public interface UserNoteContract {
    interface view {
        void displaySaved();
        Book getBook();
    }
    interface presenter {
        void bind(UserNoteContract.view view);
        void unbind();

        void saveNote();
    }
}
