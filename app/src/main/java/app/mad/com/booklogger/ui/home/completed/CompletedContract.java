package app.mad.com.booklogger.ui.home.completed;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import app.mad.com.booklogger.model.Book;
import app.mad.com.booklogger.ui.home.BookRecyclerAdapter;

/**
 * Created by Niren on 21/5/18.
 */

public interface CompletedContract {
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
