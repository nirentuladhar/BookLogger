package app.mad.com.booklogger.activity;

import java.util.List;

import app.mad.com.booklogger.model.BookList;

/**
 * Created by Niren on 12/5/18.
 */

public interface SearchActivityView {
    void displayBooks(List<BookList.BookItem> bookLists);

    void displayNoBooks();
}
