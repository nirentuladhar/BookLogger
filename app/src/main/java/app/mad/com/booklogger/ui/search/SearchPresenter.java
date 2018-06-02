package app.mad.com.booklogger.ui.search;

import android.util.Log;

import app.mad.com.booklogger.model.BookList;
import app.mad.com.booklogger.api.GoogleBooksApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Searches for books from Google Books
 * Displays the results if the attempt is successful
 */
public class SearchPresenter implements SearchContract.Presenter {

    public static final String TAG = "BL " + SearchPresenter.class.getName();
    private SearchContract.View mView;
    private GoogleBooksApi mGoogleBooksApi;

    public SearchPresenter(GoogleBooksApi googleBooksApi) {
        this.mGoogleBooksApi = googleBooksApi;
    }

    public void bind(SearchContract.View view) {
        this.mView = view;
    }

    public void unbind() {
        this.mView = null;
    }

    /**
     * Sends a API call based the user's query
     * If there's a response, view displays a list of books
     */
    public void loadBooks() {
        mGoogleBooksApi.getBooks(mView.getSearchQuery())
                .enqueue(new Callback<BookList>() {
                    @Override
                    public void onResponse(Call<BookList> call, Response<BookList> response) {
                        if (mView != null && response.body() != null) {
                            mView.displayBooks(response.body().getItems());
                            Log.d(TAG, "Books successfully returned");
                        }
                    }

                    @Override
                    public void onFailure(Call<BookList> call, Throwable t) {
                        mView.displayNoBooks();
                        Log.d(TAG, "No books returned");
                    }
                });

    }
}
