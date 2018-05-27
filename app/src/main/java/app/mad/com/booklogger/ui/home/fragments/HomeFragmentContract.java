package app.mad.com.booklogger.ui.home.fragments;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import app.mad.com.booklogger.model.Book;

/**
 * Created by Niren on 26/5/18.
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
