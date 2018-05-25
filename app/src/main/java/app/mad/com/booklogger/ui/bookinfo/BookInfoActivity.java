package app.mad.com.booklogger.ui.bookinfo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import app.mad.com.booklogger.R;
import app.mad.com.booklogger.model.Book;
import app.mad.com.booklogger.ui.home.HomeActivity;
import app.mad.com.booklogger.ui.home.toread.ToReadFragment;
import app.mad.com.booklogger.ui.search.SearchActivity;

public class BookInfoActivity extends AppCompatActivity implements BookInfoContract.View {

    public static final String TAG = "BOOK_LOGGER BIA";
    BookInfoContract.Presenter mPresenter;

    TextView bookInfoTitle;
    TextView bookInfoAuthors;
    TextView bookInfoDescription;
    ImageView bookInfoImage;
    TextView bookInfoRatingsCount;
    TextView bookInfoMetadata;
    RatingBar mbookInfoAvgRating;
    RatingBar mUserRating;
    Book book;

    String mId;
    String mTitle;
    String mAuthors;
    String mImagePath;
    String mDescription;
    String mPageCount;
    String mAverageRating;
    String mRatingsCount;

    Button mCompleted;
    Button mReading;
    Button mAddToRead;
    ImageView mCloseButton;
    Intent mIntent;

    String mCurrentView = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);

        setUpViews();

        mPresenter = new BookInfoPresenter();
        mPresenter.bind(this);
        mPresenter.loadBook();

        setClickListeners();
    }

    @Override
    protected void onDestroy() {
        mPresenter.unbind();
        super.onDestroy();
    }

    @Override
    public void displayBookInfo() {
        supportPostponeEnterTransition();
        if (book != null) {

            // sets book metadata to the view from the given intent
            bookInfoTitle.setText(book.getTitle());
            bookInfoAuthors.setText(book.getAuthors());
            bookInfoDescription.setText(book.getDescription());
            if (!book.getAverageRating().equals("null")) {
                mbookInfoAvgRating.setRating(Float.valueOf(book.getAverageRating()));
            }
            if (!book.getPageCount().equals(null)) {
                bookInfoMetadata.setText(book.getPageCount() + " pages");
            } else {
                bookInfoMetadata.setVisibility(View.GONE);
            }
            if (!book.getRatingsCount().equals(null)) {
                bookInfoRatingsCount.setText("(" + book.getRatingsCount() + ")");
            } else {
                bookInfoRatingsCount.setVisibility(View.GONE);
            }
            /**
             * todo add the literal string to the xml file
             */


            // image transition animation
            String imageTransitionName = mIntent.getStringExtra(SearchActivity.TRANSITION_NAME);
            bookInfoImage.setTransitionName(imageTransitionName);


            // checks what the current view is
            // and selects the button
            switch (mCurrentView) {
                case "toread":
                    mAddToRead.setSelected(true);
                    break;
                case "reading":
                    mReading.setSelected(true);
                    break;
                case "completed":
                    mCompleted.setSelected(true);
                    break;
            }

            // download image with the supplied link
            Picasso.get()
                    .load(mIntent.getStringExtra(SearchActivity.SEARCH_COVER))
                    .noFade()
                    .into(bookInfoImage, new Callback() {
                        @Override
                        public void onSuccess() {
                            supportStartPostponedEnterTransition();
                        }

                        @Override
                        public void onError(Exception e) {
                            supportStartPostponedEnterTransition();
                        }
                    });
        } else {
            Log.e(TAG, "Current book not set.");
        }

    }

    @Override
    public void closeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public Book getCurrentBook() {

        // get book info from google books
        mIntent = getIntent();
        mId = mIntent.getStringExtra(SearchActivity.SEARCH_ID);
        mTitle = mIntent.getStringExtra(SearchActivity.SEARCH_TITLE);
        mDescription = mIntent.getStringExtra(SearchActivity.SEARCH_DESCRIPTION);
        mAuthors = mIntent.getStringExtra(SearchActivity.SEARCH_AUTHORS);
        mImagePath = mIntent.getStringExtra(SearchActivity.SEARCH_COVER);
        mPageCount = mIntent.getStringExtra(SearchActivity.SEARCH_PAGE_COUNT);
        mAverageRating = mIntent.getStringExtra(SearchActivity.SEARCH_AVERAGE_RATING);
        mRatingsCount = mIntent.getStringExtra(SearchActivity.SEARCH_RATINGS_COUNT);

        if (mIntent.getStringExtra(ToReadFragment.SEARCH_REF) != null)
            mCurrentView = mIntent.getStringExtra(ToReadFragment.SEARCH_REF);

        // create new book object
        book = new Book();
        book.setId(mId);
        book.setTitle(mTitle);
        book.setDescription(mDescription);
        book.setAuthors(mAuthors);
        book.setImagePath(mImagePath);
        book.setPageCount(mPageCount);
        book.setAverageRating(mAverageRating);
        book.setRatingsCount(mRatingsCount);

        return book;
    }

    void setUpViews() {
        bookInfoTitle = findViewById(R.id.book_info_title_textview);
        bookInfoAuthors = findViewById(R.id.book_info_authors_textview);
        bookInfoDescription = findViewById(R.id.book_info_description_textview);
        bookInfoImage = findViewById(R.id.book_info_cover_imageview);
        bookInfoRatingsCount = findViewById(R.id.book_info_ratings_count);
        bookInfoMetadata = findViewById(R.id.book_info_metadata_textview);
        mUserRating = findViewById(R.id.ratingbar_user);


        mCompleted = findViewById(R.id.button_add_completed);
        mReading = findViewById(R.id.button_add_reading);
        mAddToRead = findViewById(R.id.button_add_to_read);

        mCloseButton = findViewById(R.id.button_close);
        mbookInfoAvgRating = findViewById(R.id.ratingbar_average);
    }

    @SuppressLint("ClickableViewAccessibility")
    void setClickListeners() {
        mCompleted.setOnClickListener(v -> {
            v.setSelected(!v.isSelected());
            if (v.isSelected()) {
                mPresenter.addBook("completed");
            } else {
                mPresenter.removeBook("completed");
            }
        });

        mReading.setOnClickListener(v -> {
            v.setSelected(!v.isSelected());
            if (v.isSelected()) {
                mPresenter.addBook("reading");
            } else {
                mPresenter.removeBook("reading");
            }
        });

        mAddToRead.setOnClickListener(v -> {
            v.setSelected(!v.isSelected());
            if (v.isSelected()) {
                mPresenter.addBook("toread");
            } else {
                mPresenter.addBook("toread");
            }
        });

        /**
         * todo make the views constant
         */

        mCloseButton.setOnClickListener(v -> finish());

        /**
         * TODO post the user rating to firebase
         */

        mUserRating.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    float touchPositionX = event.getX();
                    float width = mUserRating.getWidth();
                    float starsf = (touchPositionX / width) * 5.0f;
                    int stars = (int)starsf + 1;
                    mUserRating.setRating(stars);


                    Toast.makeText(BookInfoActivity.this, String.valueOf("test"), Toast.LENGTH_SHORT).show();
                    v.setPressed(false);
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setPressed(true);
                }

                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    v.setPressed(false);
                }
                return true;
            }
        });
    }

    @Override
    public void showBookAdded() {
        Toast.makeText(this, "Book added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showBookRemoved() {
        Toast.makeText(this, "Book removed", Toast.LENGTH_SHORT).show();
    }


}
