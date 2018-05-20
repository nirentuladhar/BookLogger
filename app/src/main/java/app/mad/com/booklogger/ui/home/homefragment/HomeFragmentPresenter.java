package app.mad.com.booklogger.ui.home.homefragment;

/**
 * Created by Niren on 18/5/18.
 */

class HomeFragmentPresenter {

    HomeFragmentView mView;

    HomeFragmentPresenter() {}

    void bind(HomeFragmentView view) {
        this.mView = view;
    }

    void unbind() {
        mView = null;
    }

}
