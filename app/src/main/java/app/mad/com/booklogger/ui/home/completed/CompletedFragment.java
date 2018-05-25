package app.mad.com.booklogger.ui.home.completed;

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
import java.util.List;

import app.mad.com.booklogger.R;
import app.mad.com.booklogger.ui.home.CoverRVAdapter;
import app.mad.com.booklogger.model.Book;
import app.mad.com.booklogger.ui.home.toread.BookRecyclerAdapter;


public class CompletedFragment extends Fragment implements CompletedContract.View {

    private static final String TAG = "BOOK_LOGGER " + CompletedFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private BookRecyclerAdapter mAdapter;
    private List<Book> mBookArrayList = new ArrayList<>();

    private CompletedContract.Presenter mPresenter;

    public CompletedFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new CompletedPresenter();
        mPresenter.bind(this);

        Log.i(TAG, "Completed fragment onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unbind();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reading, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new BookRecyclerAdapter(mBookArrayList);
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.loadBooks(mAdapter);
        Log.d("BOOK_LOGGER", "hello from comp");

        super.onViewCreated(view, savedInstanceState);
    }



}