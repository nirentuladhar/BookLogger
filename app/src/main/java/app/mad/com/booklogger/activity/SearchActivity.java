package app.mad.com.booklogger.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.mad.com.booklogger.R;
import app.mad.com.booklogger.adapter.SearchCoverRVAdapter;
import app.mad.com.booklogger.adapter.SearchCoverRVAdapter.OnRowClickListener;
import app.mad.com.booklogger.model.BookList;
import app.mad.com.booklogger.repositories.GoogleBooksImpl;
import app.mad.com.booklogger.service.GoogleBooksService;

public class SearchActivity extends AppCompatActivity implements SearchActivityView {

    Context mContext;
    RecyclerView mRecyclerView;
    SearchCoverRVAdapter mAdapter;
    List<BookList.BookItem> mBookItem = new ArrayList<>();
    ProgressBar progressBar;
    private SearchActivityPresenter mPresenter;

    public static final String SEARCH_TITLE = "title";
    public static final String SEARCH_AUTHORS = "authors";
    public static final String SEARCH_COVER = "cover";
    public static final String SEARCH_DESCRIPTION = "description";
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
        mAdapter = new SearchCoverRVAdapter(mBookItem, mContext);
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
        Log.d(MainActivity.TAG, "No books");
    }

    @Override
    public void displayBookInfo(BookList.BookItem bookItem, ImageView cover) {
        Intent intent = new Intent(getApplicationContext(), BookInfoActivity.class);
        intent.putExtra(SEARCH_TITLE, bookItem.getVolumeInfo().getTitle());
        intent.putExtra(SEARCH_AUTHORS, bookItem.getVolumeInfo().getAuthors());
        intent.putExtra(SEARCH_COVER, bookItem.getVolumeInfo().getThumbnail());
        intent.putExtra(SEARCH_DESCRIPTION, bookItem.getVolumeInfo().getDescription());

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
