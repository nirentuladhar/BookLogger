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
import app.mad.com.booklogger.ui.bookinfo.BookInfoActivity;
import app.mad.com.booklogger.ui.home.HomeActivity;
import app.mad.com.booklogger.ui.search.BookListRecyclerAdapter.OnRowClickListener;
import app.mad.com.booklogger.model.BookList;
import app.mad.com.booklogger.api.GoogleBooksImpl;

public class SearchActivity extends AppCompatActivity implements SearchActivityContract.View {

    Context mContext;
    RecyclerView mRecyclerView;
    BookListRecyclerAdapter mAdapter;
    List<BookList.BookItem> mBookItem = new ArrayList<>();
    ProgressBar progressBar;
    private SearchActivityContract.Presenter mPresenter;

    public static final String SEARCH_ID = "id";
    public static final String SEARCH_TITLE = "title";
    public static final String SEARCH_AUTHORS = "authors";
    public static final String SEARCH_COVER = "cover";
    public static final String SEARCH_DESCRIPTION = "description";

    public static final String SEARCH_PAGE_COUNT = "page_count";
    public static final String SEARCH_AVERAGE_RATING = "average_rating";
    public static final String SEARCH_RATINGS_COUNT = "ratings_count";

    public static final String TRANSITION_NAME = "transitionSearchToBookInfo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        progressBar = findViewById(R.id.search_progress_bar);
        setUpActionBar();
        setRecyclerView();
        setSearchQueryListener();

        mPresenter = new SearchActivityPresenter(new GoogleBooksImpl());
        mPresenter.bind(this);
    }

    @Override
    protected void onDestroy() {
        mPresenter.unbind();
        super.onDestroy();
    }



    @Override
    public void displayBooks(List<BookList.BookItem> bookLists) {
        mBookItem.clear();
        mBookItem.addAll(bookLists);
        mAdapter = new BookListRecyclerAdapter(mBookItem, mContext);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnRowClickListener(new OnRowClickListener() {
            @Override
            public void onRowClick(BookList.BookItem bookItem, ImageView cover) {
                displayBookInfo(bookItem, cover);
            }
        });

        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void displayNoBooks() {
        Log.d(HomeActivity.TAG, "No books");
    }

    @Override
    public void displayBookInfo(BookList.BookItem bookItem, ImageView cover) {
        Intent intent = new Intent(getApplicationContext(), BookInfoActivity.class);

        intent.putExtra(SEARCH_ID, bookItem.getId());
        intent.putExtra(SEARCH_TITLE, bookItem.getVolumeInfo().getTitle());
        intent.putExtra(SEARCH_AUTHORS, bookItem.getVolumeInfo().getAuthors());
        intent.putExtra(SEARCH_COVER, bookItem.getVolumeInfo().getThumbnail());
        intent.putExtra(SEARCH_DESCRIPTION, bookItem.getVolumeInfo().getDescription());
        intent.putExtra(SEARCH_PAGE_COUNT, String.valueOf(bookItem.getVolumeInfo().getPageCount()));
        intent.putExtra(SEARCH_AVERAGE_RATING, String.valueOf(bookItem.getVolumeInfo().getAverageRating()));
        intent.putExtra(SEARCH_RATINGS_COUNT, String.valueOf(bookItem.getVolumeInfo().getRatingsCount()));

        intent.putExtra(TRANSITION_NAME, ViewCompat.getTransitionName(cover));

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                SearchActivity.this,
                cover,
                ViewCompat.getTransitionName(cover));

        startActivity(intent, options.toBundle());
    }


    /**
     *
     */
    public void setSearchQueryListener() {
        SearchView searchView = findViewById(R.id.book_search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressBar.setVisibility(View.VISIBLE);
                mPresenter.loadBooks(query);
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
