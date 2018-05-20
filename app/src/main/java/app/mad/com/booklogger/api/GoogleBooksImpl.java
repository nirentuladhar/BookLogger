package app.mad.com.booklogger.api;

import app.mad.com.booklogger.model.BookList;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Niren on 13/5/18.
 */

public class GoogleBooksImpl implements GoogleBooksApi {

    private GoogleBooksApi service;

    public GoogleBooksImpl() {
        // Configure Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GoogleBooksApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Create the Google Book API Service
        service = retrofit.create(GoogleBooksApi.class);
    }

    @Override
    public Call<BookList> getBooks(String q) {
        return service.getBooks(q);
    }
}
