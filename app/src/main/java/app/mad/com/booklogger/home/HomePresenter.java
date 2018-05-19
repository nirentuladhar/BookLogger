package app.mad.com.booklogger.home;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import app.mad.com.booklogger.login.LoginActivity;
import app.mad.com.booklogger.search.SearchActivity;

/**
 * Created by Niren on 18/5/18.
 */

public class HomePresenter {
    private HomeView mView;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public HomePresenter() {}

    public void bind(HomeView view) {
        this.mView = view;
    }

    public void unbind() {
        mView = null;
    }

    public void signOut() {
        mAuth.signOut();
    }
}
