package app.mad.com.booklogger.search;

import app.mad.com.booklogger.model.BookList;
import app.mad.com.booklogger.service.GoogleBooksService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Niren on 12/5/18.
 */

public class SearchActivityPresenter {

    private SearchActivityView mView;
    private GoogleBooksService mGoogleBooksService;

    public SearchActivityPresenter(GoogleBooksService googleBooksService) {
        this.mGoogleBooksService = googleBooksService;
    }

    public void bind(SearchActivityView view) {
        this.mView = view;
    }

    public void unbind() {
        this.mView = null;
    }

    public void loadBooks(String userInput) {
        mGoogleBooksService.getBooks(userInput)
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
