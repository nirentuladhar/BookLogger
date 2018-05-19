package app.mad.com.booklogger.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import app.mad.com.booklogger.home.homefragment.CompletedFragment;
import app.mad.com.booklogger.home.homefragment.HomeFragment;
import app.mad.com.booklogger.home.homefragment.HomeFragmentAdapter;
import app.mad.com.booklogger.home.homefragment.ReadingFragment;
import app.mad.com.booklogger.home.homefragment.ToReadFragment;
import app.mad.com.booklogger.login.LoginActivity;
import app.mad.com.booklogger.search.SearchActivity;
import app.mad.com.booklogger.R;

public class HomeActivity extends AppCompatActivity implements HomeView {
    public static final String TAG = "BOOK_LOGGER";
    public HomePresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new HomePresenter();
        mPresenter.bind(this);

        toolbarSetup();
        tabLayoutSetup();

        findViewById(R.id.search_books_fab).setOnClickListener(v -> {
            Intent intent = new Intent(this, SearchActivity.class);
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
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    private void setupViewPager(ViewPager viewPager) {
        HomeFragmentAdapter adapter = new HomeFragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new ReadingFragment(), "Reading");
        adapter.addFragment(new ToReadFragment(), "To Read");
        adapter.addFragment(new CompletedFragment(), "Completed");
        viewPager.setAdapter(adapter);

    }

    private void toolbarSetup () {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void tabLayoutSetup() {
        ViewPager viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }








    /**
     * for dummy data
     */

    /*//retrofit for api calls
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(GoogleBooksApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    GoogleBooksApi booksApi = retrofit.create(GoogleBooksApi.class);*/

    private void addBook() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        final DatabaseReference booksRef = database.getReference("reading").child(userId);

        booksRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() == 0) {
                    Map<String, Object> values = new HashMap<>();
                    values.put("title", "Harry Potter and the Prisoner of Azkaban");
                    values.put("author", "J.K. Rowling");
                    values.put("imagePath", "http://books.google.com/books/content?id=wHlDzHnt6x0C&printsec=frontcover&img=1&zoom=2&source=gbs_api");
                    booksRef.push().setValue(values);

                    values.clear();
                    values.put("title", "The Lord of the Rings: The Fellowship of the Ring");
                    values.put("author", "J.R.R. Tolkien");
                    values.put("imagePath", "http://books.google.com/books/content?id=wRP4dbYE0bAC&printsec=frontcover&img=1&zoom=2&source=gbs_api");
                    booksRef.push().setValue(values);

                    values.clear();
                    values.put("title", "Kafka on the Shore");
                    values.put("author", "Haruki Murakami");
                    values.put("imagePath", "http://books.google.com/books/content?id=L6AtuutQHpwC&printsec=frontcover&img=1&zoom=2&&source=gbs_api");
                    booksRef.push().setValue(values);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
