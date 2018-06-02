package app.mad.com.booklogger.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


//import app.mad.com.booklogger.ui.home.fragments.HomeCompletedFragment;
import app.mad.com.booklogger.ui.home.fragments.HomeFragment;
import app.mad.com.booklogger.ui.home.fragments.HomeFragmentAdapter;
//import app.mad.com.booklogger.ui.home.fragments.HomeReadingFragment;
//import app.mad.com.booklogger.ui.home.fragments.HomeToReadFragment;
//import app.mad.com.booklogger.ui.home.reading.ReadingFragment;
//import app.mad.com.booklogger.ui.home.toread.ToReadFragment;
import app.mad.com.booklogger.ui.login.Login;
import app.mad.com.booklogger.ui.search.Search;
import app.mad.com.booklogger.R;

/**
 * Main screen for users where they can view their saved books
 * and perform other actions
 */
public class Home extends AppCompatActivity implements HomeContract.view {
    public static final String TAG = "BOOK_LOGGER";
    public HomePresenter mPresenter;

    public static final String READING_REF = "reading";
    public static final String TOREAD_REF = "toread";
    public static final String COMPLETED_REF = "completed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new HomePresenter();
        mPresenter.bind(this);

        // View setup
        toolbarSetup();
        tabLayoutSetup();

        // Launches search activity on click
        findViewById(R.id.search_books_fab).setOnClickListener(v -> {
            Intent intent = new Intent(this, Search.class);
            startActivity(intent);
        });

    }

    @Override
    protected void onDestroy() {
        mPresenter.unbind();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sign_out) {
            mPresenter.signOut();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Create new instances of tabs
     * @param viewPager:
     */
    private void setupViewPager(ViewPager viewPager) {
        HomeFragmentAdapter adapter = new HomeFragmentAdapter(getSupportFragmentManager(), this);

        HomeFragment reading = new HomeFragment();
        reading.setArguments(getBundle(READING_REF));
        adapter.addFragment(reading, "Reading");

        HomeFragment toread = new HomeFragment();
        toread.setArguments(getBundle(TOREAD_REF));
        adapter.addFragment(toread, "To Read");

        HomeFragment completed = new HomeFragment();
        completed.setArguments(getBundle(COMPLETED_REF));
        adapter.addFragment(completed, "Completed");

        viewPager.setAdapter(adapter);
    }

    private Bundle getBundle(String ref) {
        Bundle bundle = new Bundle();
        bundle.putString(HomeFragment.CURRENT_TAB_KEY, ref);
        return bundle;
    }

    private void toolbarSetup () {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * Finds and sets the tab for the home screen
     */
    private void tabLayoutSetup() {
        ViewPager viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public void displaySignedOut() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}
