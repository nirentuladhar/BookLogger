package app.mad.com.booklogger.activity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import app.mad.com.booklogger.BuildConfig;
import app.mad.com.booklogger.R;
import app.mad.com.booklogger.adapter.CoverRVAdapter;
import app.mad.com.booklogger.adapter.SearchCoverRVAdapter;
import app.mad.com.booklogger.model.BookList;
import app.mad.com.booklogger.service.GoogleBooksApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {

    Context mContext;
    RecyclerView mRecyclerView;
    SearchCoverRVAdapter mAdapter;
    List<BookList.BookItem> mBookItem = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setUpActionBar();
        setRecyclerView();
        setSearchQueryListener();
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

    /**
     * Adds a listener to the search bar
     * Populates the result to recycler view on submit/return press
     */
    public void setSearchQueryListener() {
        SearchView searchView = findViewById(R.id.book_search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchBooks(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    /**
     * Fetches a list of books from Google Books API
     * Adds the list to the adapter
     * Notifies the adapter after adding the list
     * @param bookName Name of the book to be searched
     */
    public void fetchBooks(final String bookName) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GoogleBooksApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GoogleBooksApi booksApi = retrofit.create(GoogleBooksApi.class);

        Call<BookList> call = booksApi.getBook(bookName);

        call.enqueue(new Callback<BookList>() {
            @Override
            public void onResponse(@NonNull Call<BookList> call, @NonNull Response<BookList> response) {
                BookList bookList = response.body();
                if (bookList != null) {
                    mBookItem.clear();
                    mBookItem.addAll(bookList.getItems());
                    Log.d(MainActivity.TAG, "Number of returned books from API: " + bookList.getItems().size());
                }
                mAdapter = new SearchCoverRVAdapter(mBookItem, mContext);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<BookList> call, @NonNull Throwable t) {
//                Log.d(MainActivity.TAG, "Bye " + bookName);
            }
        });


    }
}
