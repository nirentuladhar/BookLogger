package app.mad.com.booklogger.ui.search;

import app.mad.com.booklogger.model.BookList;
import app.mad.com.booklogger.api.GoogleBooksApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Niren on 12/5/18.
 */

public class SearchPresenter implements SearchContract.Presenter {

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

    public void loadBooks() {
        mGoogleBooksApi.getBooks(mView.getQuery())
                .enqueue(new Callback<BookList>() {
                    @Override
                    public void onResponse(Call<BookList> call, Response<BookList> response) {
                        if (mView != null && response.body() != null) {
                            mView.displayBooks(response.body().getItems());
                        }
                    }

                    @Override
                    public void onFailure(Call<BookList> call, Throwable t) {
                            mView.displayNoBooks();
                    }
                });

    }
}
