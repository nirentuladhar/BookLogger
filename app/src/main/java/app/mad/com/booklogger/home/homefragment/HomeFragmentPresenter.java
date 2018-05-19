package app.mad.com.booklogger.home.homefragment;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import app.mad.com.booklogger.adapter.CoverRVAdapter;
import app.mad.com.booklogger.model.Book;

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
