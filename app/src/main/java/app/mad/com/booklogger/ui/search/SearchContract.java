package app.mad.com.booklogger.ui.search;

import android.widget.ImageView;

import java.util.List;

import app.mad.com.booklogger.model.BookList;

/**
 * Searches for books from Google Books
 * Displays the results if the attempt is successful
 */
public interface SearchContract {
    interface View {
        String getSearchQuery();
        void displayBooks(List<BookList.BookItem> bookLists);
        void displayNoBooks();
        void displayBookInfo(BookList.BookItem bookItem, ImageView cover);
    }

    interface Presenter {
        void bind(SearchContract.View view);
        void unbind();

        void loadBooks();
    }

}
