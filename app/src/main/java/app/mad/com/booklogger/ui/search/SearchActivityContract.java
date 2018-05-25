package app.mad.com.booklogger.ui.search;

import android.widget.ImageView;

import java.util.List;

import app.mad.com.booklogger.model.BookList;

/**
 * Created by Niren on 12/5/18.
 */

public interface SearchActivityContract {
    interface View {
        String getQuery();
        void displayBooks(List<BookList.BookItem> bookLists);
        void displayNoBooks();
        void displayBookInfo(BookList.BookItem bookItem, ImageView cover);
    }

    interface Presenter {
        void bind(SearchActivityContract.View view);
        void unbind();

        void loadBooks();
    }

}
