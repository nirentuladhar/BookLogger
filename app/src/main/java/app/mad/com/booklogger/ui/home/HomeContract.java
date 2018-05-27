package app.mad.com.booklogger.ui.home;

import android.support.v4.app.Fragment;

/**
 * Created by Niren on 18/5/18.
 */

public interface HomeContract {
    interface view {

    }
    interface presenter {
        void bind(HomeContract.view view);
        void unbind();


    }
}
