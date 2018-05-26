package app.mad.com.booklogger.ui.home.fragments;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import app.mad.com.booklogger.model.Book;

/**
 * Created by Niren on 26/5/18.
 */

public class HomeFragmentPresenter implements HomeFragmentContract.Presenter {

    private HomeFragmentContract.View mView;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private String mUserId;

    public HomeFragmentPresenter() {
        mUserId = FirebaseAuth.getInstance().getUid();
    }

    @Override
    public void bind(HomeFragmentContract.View view) {
        this.mView = view;
    }

    @Override
    public void unbind() {
        this.mView = null;
    }


    /**
     * Loads items from the firebase database
     * from the root 'books'
     * @param
     */
    public void loadBooks() {

    }

    public DatabaseReference setDatabaseRef(String ref) {
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference(ref).child(mUserId);
        return mRef;
    }

    @Override
    public void getBooks(DataSnapshot dataSnapshot) {
        List<Book> books = new ArrayList<>();
        for(DataSnapshot snap: dataSnapshot.getChildren()) {
            Book book = snap.getValue(Book.class);
            mView.addBook(book);
            books.add(book);
        }
    }
}
