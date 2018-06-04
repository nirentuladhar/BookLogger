package app.mad.com.booklogger.ui.bookinfo;

import app.mad.com.booklogger.model.Book;

/**
 * Displays an expanded view of a book
 * This Activity is usually launched when a user clicks on a RecyclerView Book item
 */
public interface BookInfoContract {

    interface View {
        Book getSelectedBook();
        Book getCurrentBook();
        String getCurrentRef();

        void displayBookInfo();
        void displayBookDeleted();
        void displayBookAdded();
        void displayDeleteConfirmation();
        void displayUserRating();
        void closeActivity();

        void displayNoteContainer();
        void hideNoteContainer();
        void displayWriteNote();
        void displayEditNote();

        void displayToReadSelected();
        void displayReadingSelected();
        void displayCompletedSelected();

        void hideUserRating();

        void displayDeleteButton();

        void hideDeleteButton();
    }

    interface Presenter {
        void bind(BookInfoContract.View view);
        void unbind();

        void loadBook();
        void addBook();
        void deleteBook();
        void setUserRating(int stars);
        void setUserNotes();
        void setCurrentBookList();

        void setUserRatingView();
    }

}
