package app.mad.com.booklogger.service;

import app.mad.com.booklogger.model.BookList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Niren on 13/5/18.
 */

public interface GoogleBooksService {

    String BASE_URL = "https://www.googleapis.com/books/v1/";

    @GET("volumes")
    Call<BookList> getBooks(@Query("q") String q);
}
