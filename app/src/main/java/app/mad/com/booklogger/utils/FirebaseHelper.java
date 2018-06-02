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
    public static final String TAG = "BL " + FirebaseHelper.class.getName();
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
                    if (bookFH.getId().equals(book.getId())) {
                        Log.d(TAG, "BOOK ADDED " + book.getTitle());
                        bookExists = true;
                    }
                }
                if (!bookExists) {
                    booksRef.push().setValue(book);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "addBook" + databaseError.toString());
            }
        });
    }

    public void removeBook(String ref, Book book) {
        mDatabase = FirebaseDatabase.getInstance();
        mUserId = FirebaseAuth.getInstance().getUid();
        final DatabaseReference booksRef = mDatabase.getReference(ref).child(mUserId);
        Log.d(TAG, "remove book called");

        booksRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Book bookFH = ds.getValue(Book.class);
                    if (bookFH.getId().equals(book.getId())) {
                        Log.d(TAG , "BOOK DELETED " + book.getTitle());
                        ds.getRef().removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "removeBook" + databaseError.toString());
            }
        });
    }

    public void updateRating(String ref, Book book) {
        mDatabase = FirebaseDatabase.getInstance();
        mUserId = FirebaseAuth.getInstance().getUid();
        final DatabaseReference booksRef = mDatabase.getReference(ref).child(mUserId);

        booksRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Book bookFH = ds.getValue(Book.class);
                    if (bookFH.getId().equals(book.getId())) {
                        ds.getRef().child("userRating").setValue(book.getUserRating());
                        Log.d(TAG, "Rating updated");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "updateRating" + databaseError.toString());
            }
        });
    }

    public void updateRatingNotes(String ref, Book book) {
        mDatabase = FirebaseDatabase.getInstance();
        mUserId = FirebaseAuth.getInstance().getUid();
        final DatabaseReference booksRef = mDatabase.getReference(ref).child(mUserId);

        booksRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Book bookFH = ds.getValue(Book.class);
                    if (bookFH.getId().equals(book.getId())) {
                        ds.getRef().child("userRating").setValue(book.getUserRating());
                        ds.getRef().child("notes").setValue(book.getNotes());
                        Log.d(TAG, "Rating and notes updated");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "updateRatingNotes" + databaseError.toString());
            }
        });
    }
}
