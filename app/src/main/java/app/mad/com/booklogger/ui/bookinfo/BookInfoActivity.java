package app.mad.com.booklogger.ui.bookinfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import app.mad.com.booklogger.R;
import app.mad.com.booklogger.model.Book;
import app.mad.com.booklogger.ui.search.SearchActivity;

public class BookInfoActivity extends AppCompatActivity implements BookInfoContract.View {

    public static final String TAG = "BOOK_LOGGER BIA";
    BookInfoPresenter mPresenter;

    TextView bookInfoTitle;
    TextView bookInfoAuthors;
    TextView bookInfoDescription;
    ImageView bookInfoImage;

    Book book;

    String mId;
    String mTitle;
    String mAuthors;
    String mImagePath;
    String mDescription;
    String mPageCount;
    String mAverageRating;
    String mRatingsCount;

    Button mToRead;
    Button mCompleted;
    Button mReading;
    ImageView mCloseButton;
    Button mAddToRead;
    Intent mIntent;

    RatingBar mAvgRating;

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
            bookInfoTitle.setText(book.getTitle());
            bookInfoAuthors.setText(book.getAuthors());
            bookInfoDescription.setText(book.getDescription());
            mAvgRating.setRating(Float.valueOf(book.getAverageRating()));

            String imageTransitionName = mIntent.getStringExtra(SearchActivity.TRANSITION_NAME);
            bookInfoImage.setTransitionName(imageTransitionName);

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
        mToRead = findViewById(R.id.button_to_read);
        mCompleted = findViewById(R.id.button_completed);
        mReading = findViewById(R.id.button_reading);
        mCloseButton = findViewById(R.id.button_close);
        mAddToRead = findViewById(R.id.button_add_to_read);
        mAvgRating = findViewById(R.id.ratingbar_average);
    }

    void setClickListeners() {
        mToRead.setOnClickListener(v -> mPresenter.addBookToRead());
        mCompleted.setOnClickListener(v -> mPresenter.addBookToCompleted());
        mReading.setOnClickListener(v -> mPresenter.addBookToReading());
        mCloseButton.setOnClickListener(v -> finish());
        mAddToRead.setOnClickListener(v -> {
            v.setSelected(!v.isSelected());
            if (v.isSelected()) {
                Toast.makeText(this, "Button Selected", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Deselected", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
