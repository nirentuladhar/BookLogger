package app.mad.com.booklogger.ui.home.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import app.mad.com.booklogger.R;
import app.mad.com.booklogger.model.Book;
import app.mad.com.booklogger.ui.bookinfo.BookInfoActivity;
import app.mad.com.booklogger.ui.home.BookRecyclerAdapter;

/**
 * Created by Niren on 26/5/18.
 */

public class HomeFragment extends Fragment implements HomeFragmentContract.View {
    private static final String TAG = "BOOK_LOGGER";

    public static final String CURRENT_FRAG_KEY = "key";

    private RecyclerView mRecyclerView;
    private BookRecyclerAdapter mAdapter;
    private List<Book> mBookList = new ArrayList<>();
    private TextView mNoBooksMessage;

    private HomeFragmentContract.Presenter mPresenter;
    private String mCurrentFragment;

    public HomeFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentFragment = getArguments().getString(CURRENT_FRAG_KEY);
        mPresenter = new HomeFragmentPresenter();
        mPresenter.bind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unbind();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mNoBooksMessage = view.findViewById(R.id.no_books_message);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new BookRecyclerAdapter(mBookList);
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.setDatabaseRef(mCurrentFragment)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mBookList.clear();
                        mPresenter.getBooks(dataSnapshot);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        mAdapter.setOnRowClickListener((bookItem, cover) -> {
            // pass an intent to open a book activity


            Intent intent = new Intent(getContext(), BookInfoActivity.class);

            Gson gson = new Gson();
            String bookAsString = gson.toJson(bookItem);
            intent.putExtra("intent", bookAsString);

            intent.putExtra(BookInfoActivity.ID, bookItem.getId());
            intent.putExtra(BookInfoActivity.TITLE, bookItem.getTitle());
            intent.putExtra(BookInfoActivity.AUTHORS, bookItem.getAuthors());
            intent.putExtra(BookInfoActivity.IMAGE_PATH, bookItem.getImagePath());
            intent.putExtra(BookInfoActivity.DESCRIPTION, bookItem.getDescription());
            intent.putExtra(BookInfoActivity.PAGE_COUNT, String.valueOf(bookItem.getPageCount()));
            intent.putExtra(BookInfoActivity.AVERAGE_RATING, String.valueOf(bookItem.getAverageRating()));
            intent.putExtra(BookInfoActivity.RATINGS_COUNT, String.valueOf(bookItem.getRatingsCount()));
            intent.putExtra(BookInfoActivity.USER_RATING, String.valueOf(bookItem.getUserRating()));
            /**
             * TODO: change the name of the constants
             */
            intent.putExtra(BookInfoActivity.CURRENT_VIEW, mCurrentFragment);

            intent.putExtra(BookInfoActivity.TRANSITION_NAME, ViewCompat.getTransitionName(cover));

            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    getActivity(),
                    cover,
                    ViewCompat.getTransitionName(cover));

            startActivity(intent, options.toBundle());
        });

        super.onViewCreated(view, savedInstanceState);
    }



    @Override
    public void addBook(Book book) {
        mBookList.add(book);
        mAdapter.notifyDataSetChanged();
    }


}
