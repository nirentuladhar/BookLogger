package app.mad.com.booklogger.home.homefragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import app.mad.com.booklogger.R;
import app.mad.com.booklogger.adapter.CoverRVAdapter;
import app.mad.com.booklogger.model.Book;
import app.mad.com.booklogger.utils.FirebaseHelper;


public class ToReadFragment extends Fragment{

    private static final String TAG = "BOOK_LOGGER " + ToReadFragment.class.getSimpleName();


    private RecyclerView mRecyclerView;
    private CoverRVAdapter mAdapter;
    private ArrayList<Book> mBookArrayList = new ArrayList<>();


    public ToReadFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchBooks();
        Log.i(TAG, "ToRead fragment onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_read, container, false);
        mRecyclerView = view.findViewById(R.id.to_read_recycler);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new CoverRVAdapter(mBookArrayList, getActivity());
        mRecyclerView.setAdapter(mAdapter);
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * Loads items from the firebase database
     * from the root 'books'
     */
    public void fetchBooks() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference booksRef = database.getReference("toread").child(FirebaseAuth.getInstance().getUid());


        booksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int index = 0;
                mBookArrayList.clear();
                for(DataSnapshot snap: dataSnapshot.getChildren()) {
                    Book book = snap.getValue(Book.class);
                    mBookArrayList.add(new Book(book.getTitle(), book.getAuthors(), book.getImagePath()));
                    mAdapter.notifyItemInserted(index + 1);
                    index++;
                    Log.i(TAG, "ToRead fetchbooks");

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}