package app.mad.com.booklogger.ui.home.fragments;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import app.mad.com.booklogger.model.Book;

/**
 * An instance of the fragment in the home activity
 * Sets up recycler view and click listener for individual items
 */
public interface HomeFragmentContract {
    interface View {
        void addBook(Book book);
    }
    interface Presenter {
        void bind (HomeFragmentContract.View view);
        void unbind();

        void getBooks(DataSnapshot dataSnapshot);
        DatabaseReference setDatabaseRef(String ref);
    }
}
