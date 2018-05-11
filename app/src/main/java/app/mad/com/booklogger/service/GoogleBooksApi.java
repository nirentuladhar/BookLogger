package app.mad.com.booklogger.service;

import java.util.List;

import app.mad.com.booklogger.model.Book;
import app.mad.com.booklogger.model.BookList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Niren on 6/5/18.
 */

public interface GoogleBooksApi {

    String BASE_URL = "https://www.googleapis.com/books/v1/";

    @GET("volumes")
    Call<BookList> getBook(@Query("q") String q);


}
