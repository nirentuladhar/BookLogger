package app.mad.com.booklogger.ui.home.completed;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import app.mad.com.booklogger.model.Book;
import app.mad.com.booklogger.ui.home.HomeActivity;
import app.mad.com.booklogger.ui.home.toread.BookRecyclerAdapter;

/**
 * Created by Niren on 21/5/18.
 */

public class CompletedPresenter implements CompletedContract.Presenter {

    CompletedContract.View mView;

    @Override
    public void bind(CompletedContract.View view) {
        this.mView = view;
    }

    @Override
    public void unbind() {
        this.mView = null;
    }


    /**
     * Loads items from the firebase database
     * from the root 'books'
     * @param adapter
     */
    @Override
    public void loadBooks(BookRecyclerAdapter adapter) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        List<Book> bookArrayList = new ArrayList<Book>();
        final DatabaseReference booksRef = database.getReference("completed").child(FirebaseAuth.getInstance().getUid());
        Log.d("BOOK_LOGGER", "hello from comp");
        booksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snap: dataSnapshot.getChildren()) {
                    Book book = snap.getValue(Book.class);
                    bookArrayList.add(new Book(book.getTitle(), book.getAuthors(), book.getImagePath()));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
