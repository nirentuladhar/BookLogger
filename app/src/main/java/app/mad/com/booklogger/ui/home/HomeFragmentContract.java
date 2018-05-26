package app.mad.com.booklogger.ui.home;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import app.mad.com.booklogger.model.Book;
import app.mad.com.booklogger.ui.home.completed.CompletedContract;

/**
 * Created by Niren on 26/5/18.
 */

public class HomeFragmentContract {
    interface View {
        void addBook(Book book);
    }
    interface Presenter {
        void bind (CompletedContract.View view);
        void unbind();
        void getBooks(DataSnapshot dataSnapshot);
        DatabaseReference setDatabaseRef(String ref);
    }
}
