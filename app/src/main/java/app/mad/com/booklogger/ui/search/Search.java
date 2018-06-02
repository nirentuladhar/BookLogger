package app.mad.com.booklogger.ui.search;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import app.mad.com.booklogger.R;
import app.mad.com.booklogger.ui.bookinfo.BookInfo;
import app.mad.com.booklogger.ui.home.Home;
import app.mad.com.booklogger.model.BookList;
import app.mad.com.booklogger.api.GoogleBooksImpl;

/**
 * Searches for books from Google Books
 * Displays the results if the attempt is successful
 */
public class Search extends AppCompatActivity implements SearchContract.View {

    public static final String TAG = "BL " + Search.class.getName();
    public static final String SEARCH_REF="search";
    Context mContext;
    RecyclerView mRecyclerView;
    BookListRecyclerAdapter mAdapter;
    String mSearchQuery;
    List<BookList.BookItem> mGBook = new ArrayList<>();

    private SearchContract.Presenter mPresenter;
    SearchView mSearchView() { return findViewById(R.id.book_search_view); }

    ProgressBar mProgressBar() { return findViewById(R.id.search_progress_bar); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setUpActionBar();
        setRecyclerView();
        setSearchQueryListener();

        mPresenter = new SearchPresenter(new GoogleBooksImpl());
        mPresenter.bind(this);
    }

    @Override
    protected void onDestroy() {
        mPresenter.unbind();
        super.onDestroy();
    }


    @Override
    public String getSearchQuery() {
        return mSearchQuery;
    }

    /**
     * Displays Search Results in the RecyclerView
     * @param bookLists: list of BookItem objects to be displayed as results
     */
    @Override
    public void displayBooks(List<BookList.BookItem> bookLists) {
        mGBook.clear();
        mGBook.addAll(bookLists);
        mAdapter = new BookListRecyclerAdapter(mGBook);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnRowClickListener((bookItem, cover) -> displayBookInfo(bookItem, cover));

        mProgressBar().setVisibility(View.INVISIBLE);
    }

    @Override
    public void displayNoBooks() {
        Log.d(TAG, "No books");
    }

    @Override
    public void displayBookInfo(BookList.BookItem bookItem, ImageView cover) {
        Intent intent = new Intent(getApplicationContext(), BookInfo.class);
        intent.putExtra(BookInfo.ID, bookItem.getId());
        intent.putExtra(BookInfo.TITLE, bookItem.getVolumeInfo().getTitle());
        intent.putExtra(BookInfo.AUTHORS, bookItem.getVolumeInfo().getAuthors());
        intent.putExtra(BookInfo.IMAGE_PATH, bookItem.getVolumeInfo().getThumbnail());
        intent.putExtra(BookInfo.DESCRIPTION, bookItem.getVolumeInfo().getDescription());
        intent.putExtra(BookInfo.PAGE_COUNT, String.valueOf(bookItem.getVolumeInfo().getPageCount()));
        intent.putExtra(BookInfo.AVERAGE_RATING, String.valueOf(bookItem.getVolumeInfo().getAverageRating()));
        intent.putExtra(BookInfo.RATINGS_COUNT, String.valueOf(bookItem.getVolumeInfo().getRatingsCount()));
        intent.putExtra(BookInfo.CURRENT_VIEW, SEARCH_REF);

        intent.putExtra(BookInfo.TRANSITION_NAME, ViewCompat.getTransitionName(cover));
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                Search.this,
                cover,
                ViewCompat.getTransitionName(cover));

        startActivity(intent, options.toBundle());
        Log.d(TAG, "BookInfo launched with intent");
    }


    /**
     * Attempts to search books from user's query
     */
    public void setSearchQueryListener() {
        mSearchView().setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchQuery = query;
                mProgressBar().setVisibility(View.VISIBLE);
                mPresenter.loadBooks();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.d(TAG, "Back button pressed");
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Adds a back button to the Action Bar
     */
    public void setUpActionBar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Sets up Recycler View to display search results
     */
    public void setRecyclerView() {
        mRecyclerView = findViewById(R.id.search_recycler);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 3);
        mRecyclerView.setLayoutManager(layoutManager);
    }


}
