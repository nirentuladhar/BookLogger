package app.mad.com.booklogger.ui.home;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Niren on 18/5/18.
 */

public class HomePresenter implements HomeContract.presenter{
    private HomeContract.view mView;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public HomePresenter() {}

    @Override
    public void bind(HomeContract.view view) {
        this.mView = view;
    }

    @Override
    public void unbind() {
        mView = null;
    }

    public void signOut() {
        mAuth.signOut();
    }
}
