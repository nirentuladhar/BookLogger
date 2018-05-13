package app.mad.com.booklogger.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import app.mad.com.booklogger.R;
import app.mad.com.booklogger.adapter.SearchCoverRVAdapter;
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
        mBookItem.addAll(bookLists);
        mAdapter = new SearchCoverRVAdapter(mBookItem, mContext);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void displayNoBooks() {
        Log.d(MainActivity.TAG, "No books");
    }


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

//


}
