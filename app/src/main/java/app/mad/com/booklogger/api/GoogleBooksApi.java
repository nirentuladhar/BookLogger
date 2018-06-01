package app.mad.com.booklogger.api;

import app.mad.com.booklogger.model.BookList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * API for querying to Google Books
 */
public interface GoogleBooksApi {

    String BASE_URL = "https://www.googleapis.com/books/v1/";

    @GET("volumes")
    Call<BookList> getBooks(@Query("q") String q);
}
