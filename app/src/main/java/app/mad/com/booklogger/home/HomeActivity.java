package app.mad.com.booklogger.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import app.mad.com.booklogger.login.LoginActivity;
import app.mad.com.booklogger.search.SearchActivity;
import app.mad.com.booklogger.service.GoogleBooksApi;
import app.mad.com.booklogger.R;
import app.mad.com.booklogger.model.BookList;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    public static final String TAG = "BOOK_LOGGER";

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private GoogleApiClient mGoogleApiClient;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // tab layout setup
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        // add books to firebase if firebase doesn't have any books
//        bookFactory();


        //retrofit for api calls
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GoogleBooksApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GoogleBooksApi booksApi = retrofit.create(GoogleBooksApi.class);

//        Call<BookList> call = booksApi.getBook("freakonomics");
//
//        call.enqueue(new Callback<BookList>() {
//            @Override
//            public void onResponse(@NonNull Call<BookList> call, @NonNull Response<BookList> response) {
//                BookList book = response.body();
//                List<BookList.BookItem> bookItems = book.getItems();
//
//                for (BookList.BookItem b : bookItems) {
//                    BookList.VolumeInfo volumeInfo = b.getVolumeInfo();
////                    Log.d(TAG, volumeInfo.getThumbnail());
//                }
//            }
//            @Override
//            public void onFailure(Call<BookList> call, Throwable t) {
////                Log.d(TAG, t.toString());
////                Log.d(TAG, "Bye");
//            }
//        });

        mAuth= FirebaseAuth.getInstance();



    }


    /**
     * Creates three dummy books in firebase
     * only if there are no books in the database
     */
//    private void bookFactory() {
//        // Firebase instantiation
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//
//
//        final DatabaseReference booksRef = database.getReference("books");
//        booksRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                if (snapshot.getChildrenCount() == 0) {
//                    Map<String, Object> values = new HashMap<>();
//                    values.put("title", "Harry Potter and the Prisoner of Azkaban");
//                    values.put("author", "J.K. Rowling");
//                    values.put("imagePath", "http://books.google.com/books/content?id=wHlDzHnt6x0C&printsec=frontcover&img=1&zoom=2&source=gbs_api");
//                    booksRef.push().setValue(values);
//
//                    values.clear();
//                    values.put("title", "The Lord of the Rings: The Fellowship of the Ring");
//                    values.put("author", "J.R.R. Tolkien");
//                    values.put("imagePath", "http://books.google.com/books/content?id=wRP4dbYE0bAC&printsec=frontcover&img=1&zoom=2&source=gbs_api");
//                    booksRef.push().setValue(values);
//
//                    values.clear();
//                    values.put("title", "Kafka on the Shore");
//                    values.put("author", "Haruki Murakami");
//                    values.put("imagePath", "http://books.google.com/books/content?id=L6AtuutQHpwC&printsec=frontcover&img=1&zoom=2&&source=gbs_api");
//                    booksRef.push().setValue(values);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//    }

    private void addBook(String reference, BookList.BookItem bookItem) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference booksRef = database.getReference("reading");

        booksRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
            mAuth.signOut();
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new ReadingFragment(), "Reading");
        adapter.addFragment(new ToReadFragment(), "To Read");
        adapter.addFragment(new CompletedFragment(), "Completed");
        viewPager.setAdapter(adapter);

    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }


    }

}
