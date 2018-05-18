package app.mad.com.booklogger.bookinfo;

/**
 * Created by Niren on 13/5/18.
 */

public class BookInfoPresenter {

    BookInfoView mView;

    BookInfoPresenter(){
    }


    public void loadBook() {
        mView.displayBookInfo();
    }

    public void bind(BookInfoActivity view) {
        this.mView = view;
    }

    public void unbind() {
        this.mView = null;
    }

    public void addToRead() {
    }
}
