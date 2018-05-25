package app.mad.com.booklogger.utils;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import app.mad.com.booklogger.model.Book;

/**
 * Created by Niren on 19/5/18.
 */

public class FirebaseHelper {
    private String mUserId;
    private FirebaseDatabase mDatabase;
    private ArrayList<Book> mBookArrayList = new ArrayList<>();

    public FirebaseHelper() {

    }

    public void addBook(String ref, Book book) {
        mDatabase = FirebaseDatabase.getInstance();
        mUserId = FirebaseAuth.getInstance().getUid();
        final DatabaseReference booksRef = mDatabase.getReference(ref).child(mUserId);

        booksRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean bookExists = false;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Book bookFH = ds.getValue(Book.class);
                    if (bookFH.getTitle().equals(book.getTitle())) {
                        Log.d("BOOK_LOGGER", "BOOK ADDED " + book.getTitle());
                        bookExists = true;
                    }
                }
                if (!bookExists) {
                    booksRef.push().setValue(book);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void removeBook(String ref, Book book) {
        mDatabase = FirebaseDatabase.getInstance();
        mUserId = FirebaseAuth.getInstance().getUid();
        final DatabaseReference booksRef = mDatabase.getReference(ref).child(mUserId);
        Log.d("BOOK_LOGGER", "FH removeBook");

        booksRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Book bookFH = ds.getValue(Book.class);
                    if (bookFH.getTitle().equals(book.getTitle())) {
                        Log.d("BOOK_LOGGER" , "BOOK DELETED " + book.getTitle());
                        ds.getRef().removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
