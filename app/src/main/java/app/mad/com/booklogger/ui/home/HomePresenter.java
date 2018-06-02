package app.mad.com.booklogger.ui.home;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Main screen for users where they can view their saved books
 * and perform other actions
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

    @Override
    public void signOut() {
        mAuth.signOut();
        mView.displaySignedOut();
    }
}
