package app.mad.com.booklogger.ui.home.fragments;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import app.mad.com.booklogger.R;
import app.mad.com.booklogger.model.Book;
import app.mad.com.booklogger.ui.bookinfo.BookInfoActivity;
import app.mad.com.booklogger.ui.home.BookRecyclerAdapter;

/**
 * Created by Niren on 26/5/18.
 */

public class HomeReadingFragment extends Fragment implements HomeFragmentContract.View {
    private static final String TAG = "BOOK_LOGGER " + HomeCompletedFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private BookRecyclerAdapter mAdapter;
    private List<Book> mBookArrayList = new ArrayList<>();

    public static final String SEARCH_ID = "id";
    public static final String SEARCH_TITLE = "title";
    public static final String SEARCH_AUTHORS = "authors";
    public static final String SEARCH_COVER = "cover";
    public static final String SEARCH_DESCRIPTION = "description";

    public static final String SEARCH_PAGE_COUNT = "page_count";
    public static final String SEARCH_AVERAGE_RATING = "average_rating";
    public static final String SEARCH_RATINGS_COUNT = "ratings_count";
    public static final String SEARCH_REF= "toread";

    public static final String TRANSITION_NAME = "transitionSearchToBookInfo";

    private HomeFragmentContract.Presenter mPresenter;

    public HomeReadingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new HomeFragmentPresenter();
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

        mPresenter.setDatabaseRef("reading")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mBookArrayList.clear();
                        mPresenter.getBooks(dataSnapshot);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        mAdapter.setOnRowClickListener(new BookRecyclerAdapter.OnRowClickListener() {
            @Override
            public void onRowClick(Book bookItem, ImageView cover) {
                // pass an intent to open a book activity
                Intent intent = new Intent(getContext(), BookInfoActivity.class);

                intent.putExtra(SEARCH_ID, bookItem.getId());
                intent.putExtra(SEARCH_TITLE, bookItem.getTitle());
                intent.putExtra(SEARCH_AUTHORS, bookItem.getAuthors());
                intent.putExtra(SEARCH_COVER, bookItem.getImagePath());
                intent.putExtra(SEARCH_DESCRIPTION, bookItem.getDescription());
                intent.putExtra(SEARCH_PAGE_COUNT, String.valueOf(bookItem.getPageCount()));
                intent.putExtra(SEARCH_AVERAGE_RATING, String.valueOf(bookItem.getAverageRating()));
                intent.putExtra(SEARCH_RATINGS_COUNT, String.valueOf(bookItem.getRatingsCount()));
                /**
                 * TODO: change the name of the constants
                 */
                intent.putExtra(SEARCH_REF, "reading");

//                intent.putExtra(TRANSITION_NAME, ViewCompat.getTransitionName(cover));

//                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                        ToReadFragment.this,
//                        cover,
//                        ViewCompat.getTransitionName(cover));

                startActivity(intent);
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void addBook(Book book) {
        mBookArrayList.add(book);
        mAdapter.notifyDataSetChanged();
        Log.d(TAG, "set books called");
    }
}
