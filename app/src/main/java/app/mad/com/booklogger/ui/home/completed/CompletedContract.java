package app.mad.com.booklogger.ui.home.completed;

import app.mad.com.booklogger.ui.home.toread.BookRecyclerAdapter;

/**
 * Created by Niren on 21/5/18.
 */

public interface CompletedContract {
    interface View {

    }
    interface Presenter {
        void bind (CompletedContract.View view);
        void unbind();

        void loadBooks(BookRecyclerAdapter adapter);
    }
}
