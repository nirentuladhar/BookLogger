package app.mad.com.booklogger.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fasterxml.jackson.core.JsonFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.services.books.Books;
import com.google.api.services.books.model.Volume;
import com.google.gson.Gson;

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


    public static final String BOOKNAME = "freakonomics";
    Context mContext;
    RecyclerView mRecyclerView;
    private SearchCoverRVAdapter mAdapter;

    List<BookList.BookItem> mBookItem = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mRecyclerView = findViewById(R.id.search_recycler);

        getSearchButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchBooks(getSearchQuery().getText().toString());
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 3);
                mRecyclerView.setLayoutManager(layoutManager);

            }
        });
    }




    public Button getSearchButton() { return findViewById(R.id.search_query_button); }
    public EditText getSearchQuery() { return findViewById(R.id.search_query_edittext); }


    public void fetchBooks(final String bookname) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GoogleBooksApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GoogleBooksApi booksApi = retrofit.create(GoogleBooksApi.class);

        Call<BookList> call = booksApi.getBook(bookname);

        call.enqueue(new Callback<BookList>() {
            @Override
            public void onResponse(@NonNull Call<BookList> call, @NonNull Response<BookList> response) {
                BookList bookList = response.body();
                if (bookList != null) {
                    mBookItem.addAll(bookList.getItems());
                }
                mAdapter = new SearchCoverRVAdapter(mBookItem, mContext);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<BookList> call, Throwable t) {
                Log.d(MainActivity.TAG, "Bye " + bookname);
            }
        });


    }
}
